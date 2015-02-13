// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.ognev.game.uspeed.fragment.dilaog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ognev.game.uspeed.R;
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
        switch (view.getId()) {


        case R.id.closeDialog:
            dismiss();
            return;

        case R.id.okBtn:
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
            toastTextMsg.setText(getString(R.string.cleared));
            toast = new Toast(getActivity());
            toast.setView(toastView);
            toast.show();
            dismiss();
            return;

        case R.id.cancelBtn:
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
        View view = layoutinflater.inflate(R.layout.clear_cache_dialog_view, null);
        toastView = ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.qapp_toast, null);
        toastTextMsg = (TextView)toastView.findViewById(R.id.toastMsg);
        cancelBtn = (Button)view.findViewById(R.id.cancelBtn);
        okBtn = (Button)view.findViewById(R.id.okBtn);
        close = (ImageView)view.findViewById(R.id.closeDialog);
        cancelBtn.setOnClickListener(this);
        close.setOnClickListener(this);
        okBtn.setOnClickListener(this);
        return view;
    }
}
