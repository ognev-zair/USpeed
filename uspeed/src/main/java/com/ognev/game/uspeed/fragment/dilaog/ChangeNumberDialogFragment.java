// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.ognev.game.uspeed.fragment.dilaog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ognev.game.uspeed.R;
import com.ognev.game.uspeed.ormlite.HelperFactory;
import com.ognev.game.uspeed.ormlite.model.User;

import java.sql.SQLException;

public class ChangeNumberDialogFragment extends DialogFragment
        implements android.view.View.OnClickListener {

    private Button changeBtn;
    private ImageView close;
    private LinearLayout leftRadioLinear;
    private EditText phoneNumber;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private LinearLayout rightRadioLinear;
    private TextView toastTextMsg;
    private View toastView;
    private User user;

    public ChangeNumberDialogFragment() {
    }

    public void onClick(View view) {
        switch (view.getId()) {
            default:
                return;

            case R.id.leftId:
                radioButton1.setChecked(true);
                radioButton2.setChecked(false);
                return;

            case R.id.rightId:
                radioButton1.setChecked(false);
                radioButton2.setChecked(true);
                return;

            case R.id.changeBtn:
                if (!TextUtils.isEmpty(phoneNumber.getText()) && phoneNumber.getText().length() == 7) {
                    String s;
                    Toast toast1;
                    try {
                        user = (User) HelperFactory.getHelper().getUserDao().queryForId("me");
                    } catch (SQLException sqlexception) {
                        sqlexception.printStackTrace();
                    }
                    if (radioButton1.isChecked()) {
                        s = "(998 93)";
                    } else {
                        s = "(998 94)";
                    }
                    user.setPhoneNumber((new StringBuilder()).append(s).append(" ").append(phoneNumber.getText().toString()).toString());
                    try {
                        HelperFactory.getHelper().getUserDao().createOrUpdate(user);
                    } catch (SQLException sqlexception1) {
                        sqlexception1.printStackTrace();
                    }
                    toast1 = new Toast(getActivity());
                    toast1.setView(toastView);
                    toastTextMsg.setText(getString(R.string.numberChanged));
                    toast1.show();
                    dismiss();
                    return;
                } else {
                    Toast toast = new Toast(getActivity());
                    toast.setView(toastView);
                    toastTextMsg.setText("\u041D\u043E\u043C\u0435\u0440 \u0432\u0432\u0435\u0434\u0435\u043D \u043D\u0435\u043F\u0440\u0430\u0432\u0438\u043B\u044C\u043D\u043E...");
                    toast.show();
                    return;
                }

            case R.id.closeDialog:
                dismiss();
                return;
        }
    }

    public Dialog onCreateDialog(Bundle bundle) {
        Dialog dialog = new Dialog(getActivity(), R.style.DialogPopUp);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public View onCreateView(LayoutInflater layoutinflater, ViewGroup viewgroup, Bundle bundle) {
        View view = layoutinflater.inflate(R.layout.change_number_dialog_view, null);
        toastView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.qapp_toast, null);
        toastTextMsg = (TextView) toastView.findViewById(R.id.toastMsg);
        radioButton1 = (RadioButton) view.findViewById(R.id.radioBtn1);
        radioButton2 = (RadioButton) view.findViewById(R.id.radioBtn2);
        changeBtn = (Button) view.findViewById(R.id.changeBtn);
        close = (ImageView) view.findViewById(R.id.closeDialog);
        phoneNumber = (EditText) view.findViewById(R.id.phoneNumber);
        leftRadioLinear = (LinearLayout) view.findViewById(R.id.leftId);
        rightRadioLinear = (LinearLayout) view.findViewById(R.id.rightId);
        changeBtn.setOnClickListener(this);
        close.setOnClickListener(this);
        leftRadioLinear.setOnClickListener(this);
        rightRadioLinear.setOnClickListener(this);
        return view;
    }
}
