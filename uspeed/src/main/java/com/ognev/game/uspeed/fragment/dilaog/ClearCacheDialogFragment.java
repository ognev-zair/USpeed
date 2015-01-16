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
import android.widget.TextView;
import android.widget.Toast;
import com.ognev.game.uspeed.ormlite.HelperFactory;

import java.sql.SQLException;

public class ClearCacheDialogFragment extends DialogFragment
    implements android.view.View.OnClickListener
{

    private Button cancelBtn;
    private ImageView close;
    private Button okBtn;
    private TextView toastTextMsg;
    private View toastView;

    public ClearCacheDialogFragment()
    {
    }

    public void onClick(View view)
    {
        switch (view.getId())
        {
        default:
            return;

        case 2131099703: 
            dismiss();
            return;

        case 2131099690: 
            Toast toast;
            try
            {
                HelperFactory.getHelper().releaseHelper();
                HelperFactory.getHelper().getHistoryDao().clearHistory();
            }
            catch (SQLException sqlexception)
            {
                sqlexception.printStackTrace();
            }
            toastTextMsg.setText(getString(0x7f090077));
            toast = new Toast(getActivity());
            toast.setView(toastView);
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
        View view = layoutinflater.inflate(0x7f030005, null);
        toastView = ((LayoutInflater)getActivity().getSystemService("layout_inflater")).inflate(0x7f030020, null);
        toastTextMsg = (TextView)toastView.findViewById(0x7f060066);
        cancelBtn = (Button)view.findViewById(0x7f060037);
        okBtn = (Button)view.findViewById(0x7f06002a);
        close = (ImageView)view.findViewById(0x7f060029);
        cancelBtn.setOnClickListener(this);
        close.setOnClickListener(this);
        okBtn.setOnClickListener(this);
        return view;
    }
}
