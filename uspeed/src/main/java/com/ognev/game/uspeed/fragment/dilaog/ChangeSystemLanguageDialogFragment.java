// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.ognev.game.uspeed.fragment.dilaog;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import com.ognev.game.uspeed.ormlite.HelperFactory;
import com.ognev.game.uspeed.ormlite.model.User;

import java.sql.SQLException;
import java.util.Locale;

public class ChangeSystemLanguageDialogFragment extends DialogFragment
    implements android.view.View.OnClickListener
{

    private Button changeBtn;
    private ImageView close;
    private LinearLayout list1;
    private LinearLayout list2;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private TextView toastTextMsg;
    private View toastView;
    private User user;

    public ChangeSystemLanguageDialogFragment()
    {
    }

    public void onClick(View view)
    {
        switch (view.getId())
        {
        default:
            return;

        case 2131099695: 
            radioButton1.setChecked(true);
            radioButton2.setChecked(false);
            return;

        case 2131099697: 
            radioButton1.setChecked(false);
            radioButton2.setChecked(true);
            return;

        case 2131099699: 
            Resources resources;
            android.util.DisplayMetrics displaymetrics;
            Configuration configuration;
            String s;
            Toast toast;
            Intent intent;
            try
            {
                HelperFactory.getHelper().getUserDao().changeSystemLanguage(radioButton1.isChecked());
            }
            catch (SQLException sqlexception)
            {
                sqlexception.printStackTrace();
            }
            resources = getActivity().getResources();
            displaymetrics = resources.getDisplayMetrics();
            configuration = resources.getConfiguration();
            if (radioButton1.isChecked())
            {
                s = "en";
            } else
            {
                s = "ru";
            }
            configuration.locale = new Locale(s);
            resources.updateConfiguration(configuration, displaymetrics);
            toast = new Toast(getActivity());
            toast.setView(toastView);
            intent = new Intent(getActivity(), com/ognev/game/uspeed/activity/USpeedActivity);
            intent.putExtra("language", 1);
            getActivity().finish();
            startActivityForResult(intent, 777);
            toastTextMsg.setTextColor(getResources().getColor(0x7f05001a));
            toastTextMsg.setText(getString(0x7f090067));
            toast.show();
            dismiss();
            return;

        case 2131099689: 
            dismiss();
            return;
        }
    }

    public Dialog onCreateDialog(Bundle bundle)
    {
        Dialog dialog = new Dialog(getActivity(), 0x7f0a0001);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public View onCreateView(LayoutInflater layoutinflater, ViewGroup viewgroup, Bundle bundle)
    {
        View view = layoutinflater.inflate(0x7f030003, null);
        toastView = ((LayoutInflater)getActivity().getSystemService("layout_inflater")).inflate(0x7f030020, null);
        toastTextMsg = (TextView)toastView.findViewById(0x7f060066);
        radioButton1 = (RadioButton)view.findViewById(0x7f060030);
        radioButton2 = (RadioButton)view.findViewById(0x7f060032);
        changeBtn = (Button)view.findViewById(0x7f060033);
        close = (ImageView)view.findViewById(0x7f060029);
        list1 = (LinearLayout)view.findViewById(0x7f06002f);
        list2 = (LinearLayout)view.findViewById(0x7f060031);
        try
        {
            user = (User)HelperFactory.getHelper().getUserDao().queryForId("me");
        }
        catch (SQLException sqlexception)
        {
            sqlexception.printStackTrace();
        }
        if (user != null)
        {
            if (user.getSystemLanguage())
            {
                radioButton1.setChecked(true);
            } else
            {
                radioButton2.setChecked(true);
            }
        } else
        {
            radioButton2.setChecked(true);
        }
        changeBtn.setOnClickListener(this);
        close.setOnClickListener(this);
        list1.setOnClickListener(this);
        list2.setOnClickListener(this);
        return view;
    }
}
