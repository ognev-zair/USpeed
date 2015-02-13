// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.ognev.game.uspeed.fragment.dilaog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.ognev.game.uspeed.R;

public class AboutAppDialogFragment extends DialogFragment
    implements android.view.View.OnClickListener
{

    private ImageView close;
    private Button okBtn;

    public AboutAppDialogFragment()
    {
    }

    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.okBtn:
            dismiss();
            return;

        case R.id.closeDialog:
            dismiss();
            break;
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
        View view = layoutinflater.inflate(R.layout.about_app_dialog_view, null);
        okBtn = (Button)view.findViewById(R.id.okBtn);
        close = (ImageView)view.findViewById(R.id.closeDialog);
        okBtn.setOnClickListener(this);
        close.setOnClickListener(this);
        return view;
    }
}
