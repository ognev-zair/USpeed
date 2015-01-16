// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.ognev.game.uspeed.fragment;

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
import com.ognev.game.uspeed.fragment.dilaog.AboutAppDialogFragment;
import com.ognev.game.uspeed.fragment.dilaog.ChangeNumberDialogFragment;
import com.ognev.game.uspeed.fragment.dilaog.ChangeSystemLanguageDialogFragment;
import com.ognev.game.uspeed.fragment.dilaog.ClearCacheDialogFragment;
import com.ognev.game.uspeed.ormlite.HelperFactory;
import com.ognev.game.uspeed.ormlite.model.User;

import java.sql.SQLException;

// Referenced classes of package com.ognev.game.uspeed.fragment:
//            BaseFragment

public class SettingsFragment extends BaseFragment
    implements android.view.View.OnClickListener
{

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

    public SettingsFragment()
    {
    }

    public void onClick(View view)
    {
        switch (view.getId())
        {
        case 2131099762: 
        default:
            return;

        case 2131099761: 
            loginButton.performClick();
            return;

        case 2131099763: 
            (new ChangeNumberDialogFragment()).show(getFragmentManager(), "number");
            return;

        case 2131099764: 
            (new ChangeSystemLanguageDialogFragment()).show(getFragmentManager(), "language");
            return;

        case 2131099765: 
            (new ClearCacheDialogFragment()).show(getFragmentManager(), "cache");
            return;

        case 2131099766: 
            (new AboutAppDialogFragment()).show(getFragmentManager(), "aboutMe");
            break;
        }
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        LayoutInflater layoutinflater = (LayoutInflater)getActivity().getSystemService("layout_inflater");
        try
        {
            localUser = (User) HelperFactory.getHelper().getUserDao().queryForId("me");
        }
        catch (SQLException sqlexception)
        {
            sqlexception.printStackTrace();
        }
        toastView = layoutinflater.inflate(0x7f030020, null);
        toastTextMsg = (TextView)toastView.findViewById(0x7f060066);
    }

    public View onCreateView(LayoutInflater layoutinflater, ViewGroup viewgroup, Bundle bundle)
    {
        View view = layoutinflater.inflate(0x7f030023, viewgroup, false);
        changeFacebookAccount = (FrameLayout)view.findViewById(0x7f060071);
        changePhoneNumber = (FrameLayout)view.findViewById(0x7f060073);
        changeSystemLanguage = (FrameLayout)view.findViewById(0x7f060074);
        clearCache = (FrameLayout)view.findViewById(0x7f060075);
        aboutApp = (FrameLayout)view.findViewById(0x7f060076);
        loginButton = (LoginButton)view.findViewById(0x7f060072);
        loginButton.setFragment(this);
        loginButton.setPublishPermissions(new String[] {
            "user_photos", "publish_checkins", "publish_actions", "publish_stream"
        });
        loginButton.setLoginLogoutEventName("fb_user_settings_vc_usage");
        Session session = getSession();
        if (session != null && !session.equals(Session.getActiveSession()))
        {
            loginButton.setSession(session);
        }
        loginButton.setSessionStatusCallback(new com.facebook.Session.StatusCallback() {

            final SettingsFragment this$0;

            public void call(Session session1, SessionState sessionstate, Exception exception)
            {
                if (session1.isOpened())
                {
                    Log.i("settings", (new StringBuilder()).append("Access Token").append(session1.getAccessToken()).toString());
                    Request.executeMeRequestAsync(session1, new com.facebook.Request.GraphUserCallback() {

                        final _cls1 this$1;

                        public void onCompleted(GraphUser graphuser, Response response)
                        {
                            if (graphuser != null)
                            {
                                Log.i("settings", (new StringBuilder()).append("User ID ").append(graphuser.getId()).toString());
                                Log.i("settings", (new StringBuilder()).append("Email ").append(graphuser.asMap().get("email")).toString());
                                User user1;
                                Toast toast;
                                try
                                {
                                    user1 = (User)HelperFactory.getHelper().getUserDao().queryForId("me");
                                }
                                catch (SQLException sqlexception)
                                {
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
                                try
                                {
                                    HelperFactory.getHelper().getUserDao().createOrUpdate(user1);
                                }
                                catch (SQLException sqlexception1)
                                {
                                    sqlexception1.printStackTrace();
                                }
                                toast = new Toast(getActivity());
                                toastTextMsg.setText((new StringBuilder()).append(getString(0x7f090052)).append(" ").append(graphuser.getName()).toString());
                                toast.setView(toastView);
                                toast.show();
                            }
                        }

            
            {
                this$1 = _cls1.this;
                super();
            }
                    });
                    return;
                } else
                {
                    Toast toast = new Toast(getActivity());
                    toastTextMsg.setText(getString(0x7f090072));
                    toast.setView(toastView);
                    toast.show();
                    return;
                }
            }

            
            {
                this$0 = SettingsFragment.this;
                super();
            }
        });
        changeFacebookAccount.setOnClickListener(this);
        changePhoneNumber.setOnClickListener(this);
        changeSystemLanguage.setOnClickListener(this);
        clearCache.setOnClickListener(this);
        aboutApp.setOnClickListener(this);
        return view;
    }

    public void onResume()
    {
        super.onResume();
    }


}
