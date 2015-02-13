// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.ognev.game.uspeed.database;

import android.content.SharedPreferences;

import com.ognev.game.uspeed.R;
import com.ognev.game.uspeed.application.USpeedApplication;

public class LocalPreferences
{

    private android.content.SharedPreferences.Editor editor;
    private SharedPreferences preferences;

    public LocalPreferences()
    {
        preferences = USpeedApplication.getContext().getSharedPreferences("qappDb", 0);
        editor = preferences.edit();
    }

    public String getLocation()
    {
        return preferences.getString("locationAddress", USpeedApplication.getContext().getResources().getString(R.string.locationUnknown));
    }

    public String getMenuBackgroundPath()
    {
        return preferences.getString("menuBackground", "");
    }

    public void restoreAll()
    {
        editor.clear().commit();
    }

    public void saveLocation(String s)
    {
        editor.putString("locationAddress", s).commit();
    }

    public void setMenuBackground(String s)
    {
        editor.putString("menuBackground", s).commit();
    }
}
