// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.ognev.game.uspeed.application;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Location;
import com.google.android.gms.maps.model.LatLng;
import com.ognev.game.uspeed.database.LocalPreferences;
import com.ognev.game.uspeed.ormlite.HelperFactory;
import com.ognev.game.uspeed.ormlite.model.User;
import com.ognev.game.uspeed.util.Listener;
import com.ognev.game.uspeed.util.LocationAsyncTask;
import com.ognev.game.uspeed.util.LocationUtil;
import com.parse.Parse;
import com.parse.ParseCrashReporting;
import java.sql.SQLException;
import java.util.Locale;

public class USpeedApplication extends Application
{

    private static Context context;
    private static LocalPreferences preferences;
    private static User user;

    public USpeedApplication()
    {
    }

    public static Context getContext()
    {
        return context;
    }

    public static LocalPreferences getPreferences()
    {
        return preferences;
    }

    public static User getUser()
    {
        return user;
    }

    public static void setUser(User user1)
    {
        user = user1;
    }

    public void onCreate()
    {
        super.onCreate();
        (new LocationUtil(getApplicationContext())).getLocation(getApplicationContext(), new Listener() {

            final USpeedApplication this$0;

            public void onError(Exception exception)
            {
            }

            public void onSuccess(Location location)
            {
                LocationAsyncTask locationasynctask = new LocationAsyncTask(getApplicationContext());
                locationasynctask.setLatLong(new LatLng(location.getLatitude(), location.getLongitude()));
                locationasynctask.execute(new Void[0]);
            }

            public volatile void onSuccess(Object obj)
            {
                onSuccess((Location)obj);
            }

            
            {
                this$0 = USpeedApplication.this;
                super();
            }
        });
        context = getApplicationContext();
        preferences = new LocalPreferences();
        HelperFactory.setHelper(context);
        ParseCrashReporting.enable(this);
        Parse.initialize(this, "BUIk6ugKtJTLRXnF24LMarI5d1rNRXCCubEEehYM", "L6DnwzSyzMyhX93XuMYeRNt22sbNXHrPpfKpLf3h");
        User user1;
        Resources resources;
        android.util.DisplayMetrics displaymetrics;
        Configuration configuration;
        try
        {
            user1 = (User)HelperFactory.getHelper().getUserDao().queryForId("me");
        }
        catch (SQLException sqlexception)
        {
            sqlexception.printStackTrace();
            user1 = null;
        }
        resources = context.getResources();
        displaymetrics = resources.getDisplayMetrics();
        configuration = resources.getConfiguration();
        if (user1 != null)
        {
            String s;
            if (user1.getSystemLanguage())
            {
                s = "en";
            } else
            {
                s = "ru";
            }
            configuration.locale = new Locale(s);
        } else
        {
            configuration.locale = new Locale("ru");
        }
        resources.updateConfiguration(configuration, displaymetrics);
    }

    public void onTerminate()
    {
        super.onTerminate();
        preferences.restoreAll();
    }
}
