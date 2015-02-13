// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.ognev.game.uspeed.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.widget.TextView;
import com.google.android.gms.maps.model.LatLng;
import com.ognev.game.uspeed.application.USpeedApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocationAsyncTask extends AsyncTask
{

    private String address;
    private Context context;
    private LatLng latLong;
    private TextView locationTextView;

    public LocationAsyncTask(Context context1)
    {
        context = context1;
    }

    protected String doInBackground()
    {

        Geocoder geocoder;
        geocoder = new Geocoder(context, Locale.getDefault());
        List list = null;
        try {
            list = geocoder.getFromLocation(latLong.latitude, latLong.longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String s1 = "";
        String s2 = "";
        String s3 = "";
        if (list != null && list.size() > 0)
        {
            s1 = ((Address)((List) (list)).get(0)).getAddressLine(0);
            s2 = ((Address)((List) (list)).get(0)).getAddressLine(1);
            s3 = ((Address)((List) (list)).get(0)).getAddressLine(2);
        }
        String address = new StringBuilder().append(s3).append(", ").append(s2).append(", ").append(s1).toString();

        return address;
    }

    public String getAddress()
    {
        return address;
    }

    protected void onPostExecute(Object obj)
    {
        onPostExecute((String)obj);
    }

    protected void onPostExecute(String s)
    {
        super.onPostExecute(s);
        if (locationTextView != null)
        {
            locationTextView.setText(s);
        }
        address = s;
        USpeedApplication.getPreferences().saveLocation(s);
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return doInBackground();
    }

    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    public void setLatLong(LatLng latlng)
    {
        latLong = latlng;
    }

    public void setLocationTextView(TextView textview)
    {
        locationTextView = textview;
    }
}
