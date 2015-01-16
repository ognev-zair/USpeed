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

            final BaseMenuListActivity this$0;

            public void onItemClick(AdapterView adapterview, View view, int i, long l)
            {
                mActivePosition = i;
                mMenuDrawer.setActiveView(view, i);
                mAdapter.setActivePosition(i);
                onMenuItemClicked(i, (Item)mAdapter.getItem(i));
            }

            
            {
                this$0 = BaseMenuListActivity.this;
                super();
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
        View view = LayoutInflater.from(this).inflate(0x7f03001d, null, false);
        mMenuDrawer.setDropShadowColor(getResources().getColor(0x7f050019));
        mMenuDrawer.setBackgroundColor(getResources().getColor(0x7f050019));
        mMenuDrawer.setDropShadowEnabled(true);
        mMenuDrawer.setDrawingCacheBackgroundColor(getResources().getColor(0x7f050019));
        ArrayList arraylist = new ArrayList();
        arraylist.add(new Item(getResources().getString(0x7f090073), 0x7f02006d));
        arraylist.add(new Item(getResources().getString(0x7f09007a), 0x7f020073));
        arraylist.add(new Item(getResources().getString(0x7f09007c), 0x7f02006f));
        arraylist.add(new Item(getResources().getString(0x7f09006b), 0x7f02006b));
        arraylist.add(new Item(getResources().getString(0x7f090079), 0x7f020071));
        mList = (ListView)view.findViewById(0x7f06005c);
        new TypedValue();
        mAdapter = new MenuAdapter(this, arraylist);
        mAdapter.setListener(this);
        mAdapter.setActivePosition(mActivePosition);
        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(mItemClickListener);
        Display display = ((WindowManager)getApplicationContext().getSystemService("window")).getDefaultDisplay();
        mMenuDrawer.setMenuSize((int)(0.69999999999999996D * (double)display.getWidth()));
        mMenuDrawer.setMenuView(view);
    }

    protected abstract void onMenuItemClicked(int i, Item item);


/*
    static int access$002(BaseMenuListActivity basemenulistactivity, int i)
    {
        basemenulistactivity.mActivePosition = i;
        return i;
    }

*/
}
