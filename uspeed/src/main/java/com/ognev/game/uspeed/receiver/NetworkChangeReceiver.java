// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.ognev.game.uspeed.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ognev.game.uspeed.view.SpeedometerView;

public class NetworkChangeReceiver extends BroadcastReceiver {

    public NetworkChangeReceiver() {
    }

    public void onReceive(Context context, Intent intent) {
        label0:
        {
            if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                NetworkInfo networkinfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
                if (networkinfo == null) {
                    break label0;
                }
                if (networkinfo.getType() == 0) {
                    SpeedometerView.isInternetConnection = Boolean.valueOf(true);
                }
                if (networkinfo.getType() == 1) {
                    SpeedometerView.isInternetConnection = Boolean.valueOf(false);
                }
            }
            return;
        }
        SpeedometerView.isInternetConnection = null;
    }
}
