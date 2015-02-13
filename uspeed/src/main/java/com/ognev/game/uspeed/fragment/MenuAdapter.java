// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.ognev.game.uspeed.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ognev.game.uspeed.R;

import java.util.List;

// Referenced classes of package com.ognev.game.uspeed.fragment:
//            Item

public class MenuAdapter extends BaseAdapter {
    public static interface MenuListener {

        public abstract void onActiveViewChanged(View view);
    }


    private int mActivePosition;
    private Context mContext;
    private List mItems;
    private MenuListener mListener;

    public MenuAdapter(Context context, List list) {
        mActivePosition = -1;
        mContext = context;
        mItems = list;
    }

    public boolean areAllItemsEnabled() {
        return false;
    }

    public int getCount() {
        return mItems.size();
    }

    public Object getItem(int i) {
        return mItems.get(i);
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public int getItemViewType(int i) {
        return !(getItem(i) instanceof Item) ? 1 : 0;
    }

    public View getView(int i, View view, ViewGroup viewgroup) {
        Object obj = getItem(i);
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.menu_row_item, null, false);
        }
        TextView textview = (TextView) view.findViewById(R.id.text);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        imageView.setImageResource(((Item) obj).mIconRes);
        textview.setText(((Item) obj).mTitle);
//        if (android.os.Build.VERSION.SDK_INT >= 17) {
//            textview.setCompoundDrawablesRelativeWithIntrinsicBounds(((Item) obj).mIconRes, 0, 0, 0);
//        } else {
//            textview.setCompoundDrawablesWithIntrinsicBounds(((Item) obj).mIconRes, 0, 0, 0);
//        }
        view.setTag(Integer.valueOf(i));
        if (i == mActivePosition) {
            mListener.onActiveViewChanged(view);
        }
        return view;
    }

    public int getViewTypeCount() {
        return 2;
    }

    public boolean isEnabled(int i) {
        return getItem(i) instanceof Item;
    }

    public void setActivePosition(int i) {
        mActivePosition = i;
    }

    public void setListener(MenuListener menulistener) {
        mListener = menulistener;
    }
}
