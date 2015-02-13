// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.ognev.game.uspeed.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.ognev.game.uspeed.R;
import com.ognev.game.uspeed.fragment.dilaog.AboutAppDialogFragment;
import com.ognev.game.uspeed.fragment.dilaog.ChangeNumberDialogFragment;
import com.ognev.game.uspeed.fragment.dilaog.ChangeSystemLanguageDialogFragment;
import com.ognev.game.uspeed.fragment.dilaog.ClearCacheDialogFragment;
import com.ognev.game.uspeed.ormlite.HelperFactory;
import com.ognev.game.uspeed.ormlite.model.User;

import java.sql.SQLException;


public class SettingsFragment extends BaseFragment
        implements android.view.View.OnClickListener {

    private FrameLayout aboutApp;
    private FrameLayout changeFacebookAccount;
    private FrameLayout changePhoneNumber;
    private FrameLayout changeSystemLanguage;
    private FrameLayout clearCache;
    private User localUser;
    private LoginButton loginButton;
    private TextView toastTextMsg;
    private View toastView;
    private GraphUser user;

    public SettingsFragment() {
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.changeFacebookAccount:
                loginButton.performClick();
                return;

            case R.id.changePhoneNumber:
                (new ChangeNumberDialogFragment()).show(getFragmentManager(), "number");
                return;

            case R.id.changeLanguage:
                (new ChangeSystemLanguageDialogFragment()).show(getFragmentManager(), "language");
                return;

            case R.id.clearCache:
                (new ClearCacheDialogFragment()).show(getFragmentManager(), "cache");
                return;

            case R.id.aboutApp:
                (new AboutAppDialogFragment()).show(getFragmentManager(), "aboutMe");
                break;
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        LayoutInflater layoutinflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        try {
            localUser = (User) HelperFactory.getHelper().getUserDao().queryForId("me");
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
        }
        toastView = layoutinflater.inflate(R.layout.qapp_toast, null);
        toastTextMsg = (TextView) toastView.findViewById(R.id.toastMsg);
    }

    public View onCreateView(LayoutInflater layoutinflater, ViewGroup viewgroup, Bundle bundle) {
        View view = layoutinflater.inflate(R.layout.settings_view, viewgroup, false);
        changeFacebookAccount = (FrameLayout) view.findViewById(R.id.changeFacebookAccount);
        changePhoneNumber = (FrameLayout) view.findViewById(R.id.changePhoneNumber);
        changeSystemLanguage = (FrameLayout) view.findViewById(R.id.changeLanguage);
        clearCache = (FrameLayout) view.findViewById(R.id.clearCache);
        aboutApp = (FrameLayout) view.findViewById(R.id.aboutApp);
        loginButton = (LoginButton) view.findViewById(R.id.settingsFacebookBtn);
        loginButton.setFragment(this);
        loginButton.setPublishPermissions(new String[]{
                "user_photos", "publish_checkins", "publish_actions", "publish_stream"
        });
//        loginButton.setLoginLogoutEventName("fb_user_settings_vc_usage");
        Session session = getSession();
        if (session != null && !session.equals(Session.getActiveSession())) {
            loginButton.setSession(session);
        }
        loginButton.setSessionStatusCallback(new com.facebook.Session.StatusCallback() {

            public void call(Session session1, SessionState sessionstate, Exception exception) {
                if (session1.isOpened()) {
                    Log.i("settings", (new StringBuilder()).append("Access Token").append(session1.getAccessToken()).toString());
                    Request.executeMeRequestAsync(session1, new com.facebook.Request.GraphUserCallback() {

                        public void onCompleted(GraphUser graphuser, Response response) {
                            if (graphuser != null) {
                                Log.i("settings", (new StringBuilder()).append("User ID ").append(graphuser.getId()).toString());
                                Log.i("settings", (new StringBuilder()).append("Email ").append(graphuser.asMap().get("email")).toString());
                                User user1;
                                Toast toast;
                                try {
                                    user1 = (User) HelperFactory.getHelper().getUserDao().queryForId("me");
                                } catch (SQLException sqlexception) {
                                    sqlexception.printStackTrace();
                                    user1 = null;
                                }
                                user1.setEmail(String.valueOf(graphuser.getProperty("email")));
                                user1.setBirthDay(graphuser.getBirthday());
                                user1.setGender(String.valueOf(graphuser.getProperty("gender")));
                                user1.setName(graphuser.getFirstName());
                                user1.setSurname(graphuser.getLastName());
                                user1.setFacebookToken(response.getRequest().getSession().getAccessToken());
                                user1.setFacebookId(graphuser.getId());
                                try {
                                    HelperFactory.getHelper().getUserDao().createOrUpdate(user1);
                                } catch (SQLException sqlexception1) {
                                    sqlexception1.printStackTrace();
                                }
                                toast = new Toast(getActivity());
                                toastTextMsg.setText((new StringBuilder()).append(getString(R.string.accauntChanged)).append(" ").append(graphuser.getName()).toString());
                                toast.setView(toastView);
                                toast.show();
                            }
                        }


                    });
                    return;
                } else {
//                    Toast toast = new Toast(getActivity());
//                    toastTextMsg.setText(getString(R.string.aboutApp));
//                    toast.setView(toastView);
//                    toast.show();
//                    return;
                }
            }


        });
        changeFacebookAccount.setOnClickListener(this);
        changePhoneNumber.setOnClickListener(this);
        changeSystemLanguage.setOnClickListener(this);
        clearCache.setOnClickListener(this);
        aboutApp.setOnClickListener(this);
        return view;
    }

    public void onResume() {
        super.onResume();
    }


}
