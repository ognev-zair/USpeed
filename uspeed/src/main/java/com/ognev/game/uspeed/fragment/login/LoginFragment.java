// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.ognev.game.uspeed.fragment.login;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import com.ognev.game.uspeed.activity.USpeedActivity;
import com.ognev.game.uspeed.fragment.BaseFragment;
import com.ognev.game.uspeed.ormlite.HelperFactory;
import com.ognev.game.uspeed.ormlite.model.User;

import java.sql.SQLException;
import net.simonvt.menudrawer.MenuDrawer;

// Referenced classes of package com.ognev.game.uspeed.fragment.login:
//            FaceBookLoginFragment

public class LoginFragment extends BaseFragment
    implements android.view.View.OnClickListener
{

    private Button enter;
    private LinearLayout leftRadioLinear;
    private EditText phoneNumberEt;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private LinearLayout rightRadioLinear;
    private TextView toastTextMsg;
    private View toastView;
    private User user;

    public LoginFragment()
    {
    }

    public void onActivityCreated(Bundle bundle)
    {
        super.onActivityCreated(bundle);
        Runnable runnable = new Runnable() {

            final LoginFragment this$0;

            public void run()
            {
                if (((USpeedActivity)getActivity()).getMenu() != null)
                {
                    ((USpeedActivity)getActivity()).getMenu().getItem(0).setVisible(false);
                    ((USpeedActivity)getActivity()).getMenu().getItem(1).setVisible(false);
                }
            }

            
            {
                this$0 = LoginFragment.this;
                super();
            }
        };
        (new Handler()).postDelayed(runnable, 500L);
    }

    public void onClick(View view)
    {
        switch (view.getId())
        {
        default:
            return;

        case 2131099700: 
            radioButton1.setChecked(true);
            radioButton2.setChecked(false);
            return;

        case 2131099701: 
            radioButton1.setChecked(false);
            radioButton2.setChecked(true);
            return;

        case 2131099732: 
            break;
        }
        if (!TextUtils.isEmpty(phoneNumberEt.getText().toString()) && phoneNumberEt.getText().length() == 7)
        {
            user = new User("me");
            String s;
            FaceBookLoginFragment facebookloginfragment;
            if (radioButton1.isChecked())
            {
                s = "(998 93)";
            } else
            {
                s = "(998 94)";
            }
            user.setPhoneNumber((new StringBuilder()).append(s).append(phoneNumberEt.getText().toString()).toString());
            try
            {
                HelperFactory.getHelper().getUserDao().createOrUpdate(user);
            }
            catch (SQLException sqlexception)
            {
                sqlexception.printStackTrace();
            }
            ((USpeedActivity)getActivity()).enableToggleMenu();
            facebookloginfragment = (FaceBookLoginFragment)getFragmentManager().findFragmentByTag("facebookLoginPage");
            if (facebookloginfragment == null)
            {
                facebookloginfragment = new FaceBookLoginFragment();
            }
            ((USpeedActivity)getActivity()).detachFragment(this);
            ((USpeedActivity)getActivity()).attachFragment(((USpeedActivity)getActivity()).getmMenuDrawer().getContentContainer().getId(), facebookloginfragment, "facebookLoginPage");
            ((USpeedActivity)getActivity()).commitTransactions();
            return;
        } else
        {
            Toast toast = new Toast(getActivity());
            toastTextMsg.setText("\u041D\u043E\u043C\u0435\u0440 \u0432\u0432\u0435\u0434\u0435\u043D \u043D\u0435\u043F\u0440\u0430\u0432\u0438\u043B\u044C\u043D\u043E...");
            toast.setView(toastView);
            toast.show();
            return;
        }
    }

    public View onCreateView(LayoutInflater layoutinflater, ViewGroup viewgroup, Bundle bundle)
    {
        View view = layoutinflater.inflate(0x7f030017, null);
        toastView = layoutinflater.inflate(0x7f030020, null);
        toastTextMsg = (TextView)toastView.findViewById(0x7f060066);
        radioButton1 = (RadioButton)view.findViewById(0x7f060030);
        radioButton2 = (RadioButton)view.findViewById(0x7f060032);
        phoneNumberEt = (EditText)view.findViewById(0x7f060036);
        enter = (Button)view.findViewById(0x7f060054);
        leftRadioLinear = (LinearLayout)view.findViewById(0x7f060034);
        rightRadioLinear = (LinearLayout)view.findViewById(0x7f060035);
        enter.setOnClickListener(this);
        leftRadioLinear.setOnClickListener(this);
        rightRadioLinear.setOnClickListener(this);
        return view;
    }
}
