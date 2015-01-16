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

    protected volatile Object doInBackground(Object aobj[])
    {
        return doInBackground((Void[])aobj);
    }

    protected transient String doInBackground(Void avoid[])
    {
        Geocoder geocoder;
        Object obj;
        geocoder = new Geocoder(context, Locale.getDefault());
        obj = new ArrayList();
        List list = geocoder.getFromLocation(latLong.latitude, latLong.longitude, 1);
        obj = list;
_L2:
        String s = "";
        String s1 = "";
        String s2 = "";
        if (((List) (obj)).size() > 0)
        {
            s = ((Address)((List) (obj)).get(0)).getAddressLine(0);
            s1 = ((Address)((List) (obj)).get(0)).getAddressLine(1);
            s2 = ((Address)((List) (obj)).get(0)).getAddressLine(2);
        }
        return (new StringBuilder()).append(s2).append(", ").append(s1).append(", ").append(s).toString();
        IOException ioexception;
        ioexception;
        ioexception.printStackTrace();
        if (true) goto _L2; else goto _L1
_L1:
    }

    public String getAddress()
    {
        return address;
    }

    protected volatile void onPostExecute(Object obj)
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
