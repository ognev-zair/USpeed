// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.ognev.game.uspeed.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ognev.game.uspeed.R;
import com.ognev.game.uspeed.fragment.Item;
import com.ognev.game.uspeed.fragment.MenuAdapter;

import java.util.ArrayList;

import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;

public abstract class BaseMenuListActivity extends FragmentActivity
    implements MenuAdapter.MenuListener
{

    private int mActivePosition;
    protected MenuAdapter mAdapter;
    private android.widget.AdapterView.OnItemClickListener mItemClickListener;
    protected ListView mList;
    protected MenuDrawer mMenuDrawer;
    public Menu menu;

    public BaseMenuListActivity()
    {
        mActivePosition = 0;
        mItemClickListener = new android.widget.AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView adapterview, View view, int i, long l) {
                mActivePosition = i;
                mMenuDrawer.setActiveView(view, i);
                mAdapter.setActivePosition(i);
                onMenuItemClicked(i, (Item)mAdapter.getItem(i));
            }

        };
    }

    protected abstract int getDragMode();

    protected abstract Position getDrawerPosition();

    public MenuDrawer getmMenuDrawer()
    {
        return mMenuDrawer;
    }

    public void onActiveViewChanged(View view)
    {
        mMenuDrawer.setActiveView(view, mActivePosition);
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        requestWindowFeature(5);
        mMenuDrawer = MenuDrawer.attach(this, net.simonvt.menudrawer.MenuDrawer.Type.BEHIND, getDrawerPosition(), getDragMode());
        View view = LayoutInflater.from(this).inflate(R.layout.menu_view, null, false);
        mMenuDrawer.setDropShadowColor(getResources().getColor(R.color.appBackground));
        mMenuDrawer.setBackgroundColor(getResources().getColor(R.color.ucellColor));
        mMenuDrawer.setDropShadowEnabled(true);
        ArrayList arraylist = new ArrayList();
        arraylist.add(new Item(getResources().getString(R.string.profile), R.drawable.menu_profile_ic));
        arraylist.add(new Item(getResources().getString(R.string.speedometer), R.drawable.menu_speedometer_ic));
        arraylist.add(new Item(getResources().getString(R.string.reports), R.drawable.menu_reports_ic));
        arraylist.add(new Item(getResources().getString(R.string.map), R.drawable.menu_map_ic));
        arraylist.add(new Item(getResources().getString(R.string.settings), R.drawable.menu_settings_ic));
        mList = (ListView)view.findViewById(R.id.list);
        new TypedValue();
        mAdapter = new MenuAdapter(this, arraylist);
        mAdapter.setListener(this);
        mAdapter.setActivePosition(mActivePosition);
        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(mItemClickListener);
        Display display = ((WindowManager)getApplicationContext().getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        mMenuDrawer.setMenuSize((int)(0.7 * (double)display.getWidth()));
        mMenuDrawer.setMenuView(view);
    }

    protected abstract void onMenuItemClicked(int i, Item item);

}
