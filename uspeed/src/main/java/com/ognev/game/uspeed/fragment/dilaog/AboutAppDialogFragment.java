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
        default:
            return;

        case 2131099689: 
            dismiss();
            return;

        case 2131099690: 
            dismiss();
            break;
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
        View view = layoutinflater.inflate(0x7f030000, null);
        okBtn = (Button)view.findViewById(0x7f06002a);
        close = (ImageView)view.findViewById(0x7f060029);
        okBtn.setOnClickListener(this);
        close.setOnClickListener(this);
        return view;
    }
}
