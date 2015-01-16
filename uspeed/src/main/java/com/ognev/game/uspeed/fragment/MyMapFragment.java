// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.ognev.game.uspeed.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.google.android.gms.maps.SupportMapFragment;

public class MyMapFragment extends SupportMapFragment
{
    public static interface OnTouchListener
    {

        public abstract void onTouch();
    }

    public class TouchableWrapper extends FrameLayout
    {

        final MyMapFragment this$0;

        public boolean dispatchTouchEvent(MotionEvent motionevent)
        {
            motionevent.getAction();
            JVM INSTR tableswitch 0 1: default 28
        //                       0 34
        //                       1 59;
               goto _L1 _L2 _L3
_L1:
            return super.dispatchTouchEvent(motionevent);
_L2:
            if (listener != null)
            {
                listener.onTouch();
            }
            continue; /* Loop/switch isn't completed */
_L3:
            if (listener != null)
            {
                listener.onTouch();
            }
            if (true) goto _L1; else goto _L4
_L4:
        }

        public TouchableWrapper(Context context)
        {
            this$0 = MyMapFragment.this;
            super(context);
        }
    }


    private OnTouchListener listener;

    public MyMapFragment()
    {
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutinflater, ViewGroup viewgroup, Bundle bundle)
    {
        View view = super.onCreateView(layoutinflater, viewgroup, bundle);
        TouchableWrapper touchablewrapper = new TouchableWrapper(getActivity());
        touchablewrapper.setBackgroundColor(getResources().getColor(0x106000d));
        ((ViewGroup)view).addView(touchablewrapper, new android.view.ViewGroup.LayoutParams(-1, -1));
        return view;
    }

    public void setListener(OnTouchListener ontouchlistener)
    {
        listener = ontouchlistener;
    }

}
