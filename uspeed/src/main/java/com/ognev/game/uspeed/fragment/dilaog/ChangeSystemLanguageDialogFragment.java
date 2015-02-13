// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.ognev.game.uspeed.fragment.dilaog;

import android.app.Dialog;
import android.content.Context;
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

import com.ognev.game.uspeed.R;
import com.ognev.game.uspeed.activity.USpeedActivity;
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

        case R.id.list1:
            radioButton1.setChecked(true);
            radioButton2.setChecked(false);
            return;

        case R.id.list2:
            radioButton1.setChecked(false);
            radioButton2.setChecked(true);
            return;

        case R.id.changeBtn:
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
            intent = new Intent(getActivity(), USpeedActivity.class);
            intent.putExtra("language", 1);
            getActivity().finish();
            startActivityForResult(intent, 777);
            toastTextMsg.setTextColor(getResources().getColor(R.color.ucellColor));
            toastTextMsg.setText(getString(R.string.languageChanged));
            toast.show();
            dismiss();
            return;

        case R.id.closeDialog:
            dismiss();
            return;
        }
    }

    public Dialog onCreateDialog(Bundle bundle)
    {
        Dialog dialog = new Dialog(getActivity(), R.style.DialogPopUp);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public View onCreateView(LayoutInflater layoutinflater, ViewGroup viewgroup, Bundle bundle)
    {
        View view = layoutinflater.inflate(R.layout.change_language_dialog_view, null);
        toastView = ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.qapp_toast, null);
        toastTextMsg = (TextView)toastView.findViewById(R.id.toastMsg);
        radioButton1 = (RadioButton)view.findViewById(R.id.radioBtn1);
        radioButton2 = (RadioButton)view.findViewById(R.id.radioBtn2);
        changeBtn = (Button)view.findViewById(R.id.changeBtn);
        close = (ImageView)view.findViewById(R.id.closeDialog);
        list1 = (LinearLayout)view.findViewById(R.id.list1);
        list2 = (LinearLayout)view.findViewById(R.id.list2);
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
