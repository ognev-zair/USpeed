// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.ognev.game.uspeed.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.ognev.game.uspeed.ormlite.model.History;

import java.util.List;

public class ReportAdapter extends BaseAdapter
{
    private class ViewHolder
    {

        private TextView address;
        private TextView count;
        private TextView date;
        private TextView downloadSpeed;
        private TextView networkType;
        final ReportAdapter this$0;
        private TextView uploadSpeed;



/*
        static TextView access$102(ViewHolder viewholder, TextView textview)
        {
            viewholder.count = textview;
            return textview;
        }

*/



/*
        static TextView access$202(ViewHolder viewholder, TextView textview)
        {
            viewholder.date = textview;
            return textview;
        }

*/



/*
        static TextView access$302(ViewHolder viewholder, TextView textview)
        {
            viewholder.address = textview;
            return textview;
        }

*/



/*
        static TextView access$402(ViewHolder viewholder, TextView textview)
        {
            viewholder.networkType = textview;
            return textview;
        }

*/



/*
        static TextView access$502(ViewHolder viewholder, TextView textview)
        {
            viewholder.downloadSpeed = textview;
            return textview;
        }

*/



/*
        static TextView access$602(ViewHolder viewholder, TextView textview)
        {
            viewholder.uploadSpeed = textview;
            return textview;
        }

*/

        private ViewHolder()
        {
            this$0 = ReportAdapter.this;
            super();
        }

    }


    private Context context;
    private List histories;
    private LayoutInflater inflater;

    public ReportAdapter(Context context1, List list)
    {
        context = context1;
        histories = list;
        inflater = LayoutInflater.from(context1);
    }

    public int getCount()
    {
        return histories.size();
    }

    public Object getItem(int i)
    {
        return histories.get(i);
    }

    public long getItemId(int i)
    {
        return (long)i;
    }

    public View getView(int i, View view, ViewGroup viewgroup)
    {
        History history = (History)getItem(i);
        ViewHolder viewholder;
        if (view == null)
        {
            viewholder = new ViewHolder();
            view = inflater.inflate(0x7f030021, null);
            viewholder.count = (TextView)view.findViewById(0x7f060068);
            viewholder.date = (TextView)view.findViewById(0x7f060069);
            viewholder.address = (TextView)view.findViewById(0x7f060059);
            viewholder.networkType = (TextView)view.findViewById(0x7f06006a);
            viewholder.downloadSpeed = (TextView)view.findViewById(0x7f06006b);
            viewholder.uploadSpeed = (TextView)view.findViewById(0x7f06006c);
            view.setTag(viewholder);
        } else
        {
            viewholder = (ViewHolder)view.getTag();
        }
        viewholder.count.setText((new StringBuilder()).append(history.getId()).append("").toString());
        viewholder.date.setText(history.getDate());
        viewholder.address.setText(history.getLocation());
        viewholder.networkType.setText(history.getNetworkType());
        viewholder.downloadSpeed.setText(history.getDownloadSpeed());
        viewholder.uploadSpeed.setText(history.getUploadSpeed());
        return view;
    }
}
