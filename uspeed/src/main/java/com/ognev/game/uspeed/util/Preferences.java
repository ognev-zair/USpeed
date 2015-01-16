// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.ognev.game.uspeed.util;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences
{

    Context context;
    String dbName;
    android.content.SharedPreferences.Editor editor;
    private String key;
    SharedPreferences preferences;

    public Preferences(Context context1, String s)
    {
        dbName = "com.ognev.uspeed";
        context = context1;
        preferences = context1.getSharedPreferences(dbName, 0);
        editor = preferences.edit();
        key = s;
    }

    public void clear()
    {
        editor.clear();
    }

    public int getSelectedIndex()
    {
        return preferences.getInt(key, 0);
    }

    public String getValue()
    {
        return preferences.getString(key, "");
    }

    public void setSelectedIndex(int i)
    {
        editor.putInt(key, i);
        editor.commit();
    }

    public void setValue(String s)
    {
        editor.putString("value", s);
        editor.commit();
    }
}
