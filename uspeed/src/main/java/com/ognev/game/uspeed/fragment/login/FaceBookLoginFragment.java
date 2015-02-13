// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.ognev.game.uspeed.fragment.login;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionDefaultAudience;
import com.facebook.SessionLoginBehavior;
import com.facebook.SessionState;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.ImageDownloader;
import com.facebook.internal.ImageRequest;
import com.facebook.internal.ImageResponse;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookFragment;
import com.facebook.widget.LoginButton;
import com.ognev.game.uspeed.R;
import com.ognev.game.uspeed.activity.USpeedActivity;
import com.ognev.game.uspeed.fragment.ProfileFragment;
import com.ognev.game.uspeed.ormlite.HelperFactory;
import com.ognev.game.uspeed.ormlite.model.User;
import com.ognev.game.uspeed.util.Preferences;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import net.simonvt.menudrawer.MenuDrawer;

public class FaceBookLoginFragment extends FacebookFragment {

    private static final String EMAIL = "email";
    private static final String FIELDS = "fields";
    private static final String FIRST_NAME = "first_name";
    private static final String GENDER = "gender";
    private static final String ID = "id";
    private static final String LAST_NAME = "last_name";
    private static final String LINK = "link";
    private static final String LOCALE = "locale";
    private static final String NAME = "name";
    private static final String PICTURE = "picture";
    private static final String REQUEST_FIELDS = TextUtils.join(",", new String[]{
            "id", "name", "picture", "email", "first_name", "last_name", "gender", "link", "timezone", "verified",
            "locale", "updated_time"
    });
    private static final String TIME_ZONE = "timezone";
    private static final String UPDATED_TIME = "updated_time";
    private static final String VERIFIED = "verified";
    private TextView connectedStateLabel;
    private LoginButton loginButton;
    private com.facebook.widget.LoginButton.LoginButtonProperties loginButtonProperties;
    private Button next;
    private com.facebook.Session.StatusCallback sessionStatusCallback;
    private GraphUser user;
    private Session userInfoSession;
    private Drawable userProfilePic;
    private String userProfilePicID;

    public FaceBookLoginFragment() {
        loginButtonProperties = new com.facebook.widget.LoginButton.LoginButtonProperties();
    }

    private void fetchUserInfo() {
        Session session = getSession();
        if (session != null && session.isOpened()) {
            if (session != userInfoSession) {
                Request request = Request.newMeRequest(session, new com.facebook.Request.GraphUserCallback() {

                    public void onCompleted(GraphUser graphuser, Response response) {
                        (new Preferences(getActivity(), "accessToken")).setValue(response.getRequest().getSession().getAccessToken());
                        user = graphuser;
                        User user1;
                        try {
                            user1 = (User) HelperFactory.getHelper().getUserDao().queryForId("me");
                        } catch (SQLException sqlexception) {
                            sqlexception.printStackTrace();
                            user1 = null;
                        }
                        user1.setEmail(String.valueOf(user.getProperty("email")));
                        user1.setBirthDay(user.getBirthday());
                        user1.setGender(String.valueOf(user.getProperty("gender")));
                        user1.setName(user.getFirstName());
                        user1.setSurname(user.getLastName());
                        user1.setFacebookToken(response.getRequest().getSession().getAccessToken());
                        user1.setFacebookId(user.getId());
                        try {
                            HelperFactory.getHelper().getUserDao().createOrUpdate(user1);
                        } catch (SQLException sqlexception1) {
                            sqlexception1.printStackTrace();
                        }
                        updateUI();
                        if (response.getError() != null) {
                            loginButton.handleError(response.getError().getException());
                        }
                    }

                });
                Bundle bundle = new Bundle();
                bundle.putString("fields", REQUEST_FIELDS);
                request.setParameters(bundle);
                Request.executeBatchAsync(new Request[]{
                        request
                });
                userInfoSession = session;
            }
            return;
        } else {
            user = null;
            return;
        }
    }


    private ImageRequest getImageRequest() {
        ImageRequest request = null;
        try {
            ImageRequest.Builder requestBuilder = new ImageRequest.Builder(
                    getActivity(),
                    ImageRequest.getProfilePictureUrl(
                            user.getId(),
                            getResources().getDimensionPixelSize(R.dimen.com_facebook_usersettingsfragment_profile_picture_width),
                            getResources().getDimensionPixelSize(R.dimen.com_facebook_usersettingsfragment_profile_picture_height)));

            request = requestBuilder.setCallerTag(this)
                    .setCallback(
                            new ImageRequest.Callback() {
                                @Override
                                public void onCompleted(ImageResponse response) {
                                    processImageResponse(user.getId(), response);
                                }
                            })
                    .build();
        } catch (URISyntaxException e) {
        }
        return request;
    }


    private void processImageResponse(String id, ImageResponse response) {
        if (response != null) {
            Bitmap bitmap = response.getBitmap();
            if (bitmap != null) {
                BitmapDrawable drawable = new BitmapDrawable(FaceBookLoginFragment.this.getResources(), bitmap);
                drawable.setBounds(0, 0,
                        getResources().getDimensionPixelSize(R.dimen.com_facebook_usersettingsfragment_profile_picture_width),
                        getResources().getDimensionPixelSize(R.dimen.com_facebook_usersettingsfragment_profile_picture_height));
                userProfilePic = drawable;
                userProfilePicID = id;
                connectedStateLabel.setCompoundDrawables(null, drawable, null, null);
                connectedStateLabel.setTag(response.getRequest().getImageUri());
            }
        }
    }

    private void updateUI() {
        if (!isAdded()) {
            return;
        }
        if (isSessionOpen()) {
            connectedStateLabel.setTextColor(getResources().getColor(R.color.com_facebook_usersettingsfragment_connected_text_color));
            connectedStateLabel.setShadowLayer(1f, 0f, -1f,
                    getResources().getColor(R.color.com_facebook_usersettingsfragment_connected_shadow_color));

            if (user != null) {
                ImageRequest imagerequest = getImageRequest();
                ProfileFragment profilefragment;
                if (imagerequest != null) {
                    URI uri = imagerequest.getImageUri();
                    if (!uri.equals(connectedStateLabel.getTag())) {
                        if (user.getId().equals(userProfilePicID)) {
                            connectedStateLabel.setCompoundDrawables(null, userProfilePic, null, null);
                            connectedStateLabel.setTag(uri);
                        } else {
                            ImageDownloader.downloadAsync(imagerequest);
                        }
                    }
                }
                connectedStateLabel.setText(user.getName());
                next.setText("\u041F\u0440\u0438\u0441\u0442\u0443\u043F\u0438\u0442\u044C");
                ((USpeedActivity) getActivity()).enableToggleMenu();
                profilefragment = new ProfileFragment();
                ((USpeedActivity) getActivity()).detachFragment(this);
                ((USpeedActivity) getActivity()).attachFragment(((USpeedActivity) getActivity()).getmMenuDrawer().getContentContainer().getId(), profilefragment, "0");
                ((USpeedActivity) getActivity()).commitTransactions();
                return;
            } else {
                connectedStateLabel.setText(getResources().getString(
                        R.string.com_facebook_usersettingsfragment_logged_in));
                Drawable noProfilePic = getResources().getDrawable(R.drawable.com_facebook_profile_default_icon);
                noProfilePic.setBounds(0, 0,
                        getResources().getDimensionPixelSize(R.dimen.com_facebook_usersettingsfragment_profile_picture_width),
                        getResources().getDimensionPixelSize(R.dimen.com_facebook_usersettingsfragment_profile_picture_height));
                connectedStateLabel.setCompoundDrawables(null, noProfilePic, null, null);
                return;
            }
        } else {
            int textColor = getResources().getColor(R.color.com_facebook_usersettingsfragment_not_connected_text_color);
            connectedStateLabel.setTextColor(textColor);
            connectedStateLabel.setShadowLayer(0f, 0f, 0f, textColor);
            connectedStateLabel.setText(getResources().getString(
                    R.string.com_facebook_usersettingsfragment_not_logged_in));
            connectedStateLabel.setCompoundDrawables(null, null, null, null);
            connectedStateLabel.setTag(null);
        }
    }

    public void clearPermissions() {
        loginButtonProperties.clearPermissions();
    }

    public SessionDefaultAudience getDefaultAudience() {
        return loginButtonProperties.getDefaultAudience();
    }

    public SessionLoginBehavior getLoginBehavior() {
        return loginButtonProperties.getLoginBehavior();
    }

    public com.facebook.widget.LoginButton.OnErrorListener getOnErrorListener() {
        return loginButtonProperties.getOnErrorListener();
    }

    List getPermissions() {
        return loginButtonProperties.getPermissions();
    }

    public com.facebook.Session.StatusCallback getSessionStatusCallback() {
        return sessionStatusCallback;
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        Runnable runnable = new Runnable() {

            public void run() {
                if (((USpeedActivity) getActivity()).getMenu() != null) {
                    ((USpeedActivity) getActivity()).getMenu().getItem(0).setVisible(false);
                    ((USpeedActivity) getActivity()).getMenu().getItem(1).setVisible(false);
                }
            }
        };
        (new Handler()).postDelayed(runnable, 500L);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

    }

    public View onCreateView(LayoutInflater layoutinflater, ViewGroup viewgroup, Bundle bundle) {
        View view = layoutinflater.inflate(R.layout.facebook_user_settings_fragment, viewgroup, false);

        loginButton = (LoginButton) view.findViewById(R.id.com_facebook_usersettingsfragment_login_button);
        loginButton.setProperties(loginButtonProperties);
        loginButton.setFragment(this);
//        loginButton.setLoginLogoutEventName(AnalyticsEvents.EVENT_USER_SETTINGS_USAGE);
        loginButton.setPublishPermissions(new String[]{
                "user_photos", "publish_checkins", "publish_actions", "publish_stream"
        });
        loginButton.setLoginLogoutEventName("fb_user_settings_vc_usage");
        next = (Button) view.findViewById(R.id.next);
        next.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view1) {
                ((USpeedActivity) getActivity()).detachFragment(FaceBookLoginFragment.this);
                ((USpeedActivity) getActivity()).enableToggleMenu();
                ProfileFragment profilefragment = new ProfileFragment();
                ((USpeedActivity) getActivity()).attachFragment(((USpeedActivity) getActivity()).getmMenuDrawer().getContentContainer().getId(), profilefragment, "0");
                ((USpeedActivity) getActivity()).commitTransactions();
            }


        });
        Session session = getSession();
        if (session != null && !session.equals(Session.getActiveSession())) {
            loginButton.setSession(session);
        }
        connectedStateLabel = (TextView) view.findViewById(R.id.com_facebook_usersettingsfragment_profile_name);

        if (view.getBackground() == null) {
            view.setBackgroundColor(getResources().getColor(R.color.com_facebook_blue));

            return view;
        } else {
            view.getBackground().setDither(true);
            return view;
        }
    }

    public void onResume() {
        super.onResume();
        fetchUserInfo();
        updateUI();
    }

    protected void onSessionStateChange(SessionState sessionstate, Exception exception) {
        fetchUserInfo();
        updateUI();
        if (sessionStatusCallback != null) {
            sessionStatusCallback.call(getSession(), sessionstate, exception);
        }
    }

    public void setDefaultAudience(SessionDefaultAudience sessiondefaultaudience) {
        loginButtonProperties.setDefaultAudience(sessiondefaultaudience);
    }

    public void setLoginBehavior(SessionLoginBehavior sessionloginbehavior) {
        loginButtonProperties.setLoginBehavior(sessionloginbehavior);
    }

    public void setOnErrorListener(com.facebook.widget.LoginButton.OnErrorListener onerrorlistener) {
        loginButtonProperties.setOnErrorListener(onerrorlistener);
    }

    public void setPublishPermissions(List list) {
        loginButtonProperties.setPublishPermissions(list, getSession());
    }

    public void setPublishPermissions(String as[]) {
        loginButtonProperties.setPublishPermissions(Arrays.asList(as), getSession());
    }

    public void setReadPermissions(List list) {
        loginButtonProperties.setReadPermissions(list, getSession());
    }

    public void setReadPermissions(String as[]) {
        loginButtonProperties.setReadPermissions(Arrays.asList(as), getSession());
    }

    public void setSession(Session session) {
        super.setSession(session);
        if (loginButton != null) {
            loginButton.setSession(session);
        }
        fetchUserInfo();
        updateUI();
    }

    public void setSessionStatusCallback(com.facebook.Session.StatusCallback statuscallback) {
        sessionStatusCallback = statuscallback;
    }




/*
    static GraphUser access$002(FaceBookLoginFragment facebookloginfragment, GraphUser graphuser)
    {
        facebookloginfragment.user = graphuser;
        return graphuser;
    }

*/


}
