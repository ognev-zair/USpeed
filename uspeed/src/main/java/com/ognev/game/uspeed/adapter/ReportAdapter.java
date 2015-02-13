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

import com.ognev.game.uspeed.R;
import com.ognev.game.uspeed.ormlite.model.History;

import java.util.List;

public class ReportAdapter extends BaseAdapter {



    private Context context;
    private List histories;
    private LayoutInflater inflater;

    public ReportAdapter(Context context1, List list) {
        context = context1;
        histories = list;
        inflater = LayoutInflater.from(context1);
    }

    public int getCount() {
        return histories.size();
    }

    public Object getItem(int i) {
        return histories.get(i);
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public View getView(int i, View view, ViewGroup viewgroup) {
        History history = (History) getItem(i);
        ViewHolder viewholder;
        if (view == null) {
            viewholder = new ViewHolder();
            view = inflater.inflate(R.layout.report_item, null);
            viewholder.count = (TextView) view.findViewById(R.id.count);
            viewholder.date = (TextView) view.findViewById(R.id.date);
            viewholder.address = (TextView) view.findViewById(R.id.address);
            viewholder.networkType = (TextView) view.findViewById(R.id.networkType);
            viewholder.downloadSpeed = (TextView) view.findViewById(R.id.downloadSpeed);
            viewholder.uploadSpeed = (TextView) view.findViewById(R.id.uploadSpeed);
            view.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) view.getTag();
        }
        viewholder.count.setText((new StringBuilder()).append(history.getId()).append("").toString());
        viewholder.date.setText(history.getDate());
        viewholder.address.setText(history.getLocation());
        viewholder.networkType.setText(history.getNetworkType());
        viewholder.downloadSpeed.setText(history.getDownloadSpeed() + " " +  history.getDownloadPostFix());
        viewholder.uploadSpeed.setText(history.getUploadSpeed() + " " + history.getUploadPostFix());
        return view;
    }

    private class ViewHolder {

        private TextView address;
        private TextView count;
        private TextView date;
        private TextView downloadSpeed;
        private TextView networkType;
        private TextView uploadSpeed;

    }
}
