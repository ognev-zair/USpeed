// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.ognev.game.uspeed.fragment;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionLoginBehavior;
import com.facebook.SessionState;
import com.facebook.internal.ImageDownloader;
import com.facebook.internal.ImageRequest;
import com.facebook.internal.ImageResponse;
import com.google.android.gms.maps.model.LatLng;
import com.ognev.game.uspeed.R;
import com.ognev.game.uspeed.activity.USpeedActivity;
import com.ognev.game.uspeed.ormlite.HelperFactory;
import com.ognev.game.uspeed.ormlite.model.User;
import com.ognev.game.uspeed.util.Listener;
import com.ognev.game.uspeed.util.LocationAsyncTask;
import com.ognev.game.uspeed.util.LocationUtil;
import com.pkmmte.view.CircularImageView;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.ognev.game.uspeed.fragment:
//            BaseFragment

public class ProfileFragment extends BaseFragment {

    private TextView androidVersion;
    private TextView deviceModel;
    private LinearLayout facebookProfileInfo;
    private int height;
    private TextView horizontalPixels;
    private String locality;
    private Location location;
    private LocationManager locationManager;
    private TextView locationTv;
    private TextView name;
    private TextView phoneNumber;
    private CircularImageView profileAvatar;
    private User user;
    private TextView verticalPixels;
    private int width;
    private TextView toastTextMsg;

    public ProfileFragment() {
    }

    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            s = "";
        } else {
            char c = s.charAt(0);
            if (!Character.isUpperCase(c)) {
                return (new StringBuilder()).append(Character.toUpperCase(c)).append(s.substring(1)).toString();
            }
        }
        return s;
    }

    private ImageRequest getImageRequest() {
        ImageRequest imagerequest;
        try {
            imagerequest = (new com.facebook.internal.ImageRequest.Builder(getActivity(), ImageRequest.getProfilePictureUrl(user.getFacebookId(), getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin), getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin)))).setCallerTag(this).setCallback(new com.facebook.internal.ImageRequest.Callback() {

                public void onCompleted(ImageResponse imageresponse) {
                    processImageResponse(user.getFacebookId(), imageresponse);
                }


            }).build();
        } catch (URISyntaxException urisyntaxexception) {
            return null;
        }
        return imagerequest;
    }

    private void processImageResponse(String s, ImageResponse imageresponse) {
        if (imageresponse != null) {
            android.graphics.Bitmap bitmap = imageresponse.getBitmap();
            if (bitmap != null) {
                BitmapDrawable bitmapdrawable = new BitmapDrawable(getResources(), bitmap);
                bitmapdrawable.setBounds(0, 0, getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin), getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin));
                profileAvatar.setImageDrawable(bitmapdrawable);
            }
        }
    }

    private void updateUI() {
        if (isAdded() && user != null && !TextUtils.isEmpty(user.getFacebookId())) {
            ImageRequest imagerequest = getImageRequest();
            if (imagerequest != null) {
                imagerequest.getImageUri();
                ImageDownloader.downloadAsync(imagerequest);
                return;
            }
        }
    }

    public String getDeviceName() {
        String s = Build.MANUFACTURER;
        String s1 = Build.MODEL;
        if (s1.startsWith(s)) {
            return capitalize(s1);
        } else {
            return (new StringBuilder()).append(capitalize(s)).append(" ").append(s1).toString();
        }
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
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        width = displaymetrics.widthPixels;
        height = displaymetrics.heightPixels;
    }

    public View onCreateView(LayoutInflater layoutinflater, ViewGroup viewgroup, Bundle bundle) {
        View view = layoutinflater.inflate(R.layout.profile_view, null);
        try {
            user = (User) HelperFactory.getHelper().getUserDao().queryForId("me");
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
        }
        LayoutInflater lf = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View toastView =  lf.inflate(R.layout.qapp_toast, null);
        toastTextMsg = (TextView) toastView.findViewById(R.id.toastMsg);
        final Toast toast = new Toast(getActivity());
        toast.setView(toastView);

        LocationUtil locationUtil = new LocationUtil(getActivity());
        locationUtil.getLocation(getActivity(), new Listener() {
            @Override
            public void onSuccess(Object obj) {
                LocationAsyncTask locationAsyncTask = new LocationAsyncTask(getActivity());
                Location location = (Location) obj;
                locationAsyncTask.setLatLong(new LatLng(location.getLatitude(), location.getLongitude()));
                locationAsyncTask.setLocationTextView(locationTv);
                locationAsyncTask.execute();
            }

            @Override
            public void onError(Exception exception) {

            }
        });
        final ImageButton button = (ImageButton) view.findViewById(R.id.like_view);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle params = new Bundle();
                        params.putString("object", "http://samples.ogp.me/226075010839791");
                        new Request(
                                getSession(),
                                "/me/og.likes",
                                params,
                                HttpMethod.POST,
                                new Request.Callback() {
                                    public void onCompleted(Response response) {
                                        if(response.getError()!=null && response.getError().getErrorCode() == 3501) {
                                            toastTextMsg.setTextColor(getResources().getColor(R.color.red));
                                            toastTextMsg.setText("Вы уже отдали свой голос");
                                            toast.show();
                                        } else {
                                            createSession();
                                            toastTextMsg.setTextColor(getResources().getColor(R.color.red));
                                            toastTextMsg.setText("Пожалуйста авторизуйтесь");
                                            toast.show();
                                        }
                                        if(response.getError() == null) {
                                            toastTextMsg.setTextColor(getResources().getColor(R.color.speedGreenColor));
                                            toastTextMsg.setText("Ваш голос принят");
                                            toast.show();
                                        }
                                    }
                                }
                        ).executeAsync();
                    }
                });

        deviceModel = (TextView) view.findViewById(R.id.deviceModel);
        androidVersion = (TextView) view.findViewById(R.id.androidVersion);
        horizontalPixels = (TextView) view.findViewById(R.id.horizontalPix);
        verticalPixels = (TextView) view.findViewById(R.id.verticalPix);
        deviceModel.setText(getDeviceName());
        androidVersion.setText(android.os.Build.VERSION.RELEASE);
        horizontalPixels.setText((new StringBuilder()).append(width).append("").toString());
        verticalPixels.setText((new StringBuilder()).append(height).append("").toString());
        name = (TextView) view.findViewById(R.id.name);
        phoneNumber = (TextView) view.findViewById(R.id.phoneNumber);
        locationTv = (TextView) view.findViewById(R.id.location);
        profileAvatar = (CircularImageView) view.findViewById(R.id.profileAvatar);
        facebookProfileInfo = (LinearLayout) view.findViewById(R.id.facebookProfileInfo);
        if (user != null) {
            if (TextUtils.isEmpty(user.getFacebookToken())) {
                facebookProfileInfo.setVisibility(View.INVISIBLE);
            } else {
                name.setText((new StringBuilder()).append(user.getName()).append(" ").append(user.getSurname()).toString());
            }
            phoneNumber.setText(user.getPhoneNumber());
        }
        locationTv.setText(locality);
        updateUI();
        return view;
    }

    public void onStart() {
        super.onStart();
    }

    private void createSession() {
        Session session = Session.getActiveSession();

        if (session == null) {
            session = new Session.Builder(getActivity()).setApplicationId(getResources().getString(R.string.app_id)).build();

            Session.setActiveSession(session);
            session.addCallback(new Session.StatusCallback() {
                public void call(Session session, SessionState state, Exception exception) {
                    if (state == SessionState.OPENED) {
                        List<String> strings = new ArrayList<String>();
                        strings.add("publish_actions");
                        strings.add("publish_stream");
                        strings.add("publish_checkins");
                        strings.add("public_profile");
                        Session.OpenRequest openRequest = new Session.OpenRequest(getActivity());
                        openRequest.setLoginBehavior(SessionLoginBehavior.SSO_WITH_FALLBACK);
                        session.requestNewPublishPermissions(
                                new Session.NewPermissionsRequest(getActivity(), strings));
                    }
                    else if (state == SessionState.OPENED_TOKEN_UPDATED) {
                        Toast.makeText(getActivity(), "s", Toast.LENGTH_SHORT).show();

                    }
                    else if (state == SessionState.CLOSED_LOGIN_FAILED) {
                        session.closeAndClearTokenInformation();
                        // Possibly finish the activity
                    }
                    else if (state == SessionState.CLOSED) {
                        session.close();
                        // Possibly finish the activity
                    }
                }});
        }

        if (!session.isOpened()) {
            Session.OpenRequest openRequest = new Session.OpenRequest(this);
            openRequest.setLoginBehavior(SessionLoginBehavior.SSO_WITH_FALLBACK);
            session.openForRead(openRequest);
        }

    }
}
