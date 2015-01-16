// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.ognev.game.uspeed.fragment;

import android.graphics.drawable.BitmapDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.facebook.internal.ImageDownloader;
import com.facebook.internal.ImageRequest;
import com.facebook.internal.ImageResponse;
import com.google.android.gms.maps.model.LatLng;
import com.ognev.game.uspeed.activity.USpeedActivity;
import com.ognev.game.uspeed.ormlite.HelperFactory;
import com.ognev.game.uspeed.ormlite.model.User;
import com.ognev.game.uspeed.util.Listener;
import com.ognev.game.uspeed.util.LocationAsyncTask;
import com.ognev.game.uspeed.util.LocationUtil;
import com.pkmmte.view.CircularImageView;
import java.net.URISyntaxException;
import java.sql.SQLException;

// Referenced classes of package com.ognev.game.uspeed.fragment:
//            BaseFragment

public class ProfileFragment extends BaseFragment
{

    private TextView androidVersion;
    private TextView deviceModel;
    private LinearLayout facebookProfileInfo;
    private int height;
    private TextView horizontalPixels;
    private String locality;
    private Location location;
    private LocationManager locationManager;
    private TextView locationTv;
    private TextView name;
    private TextView phoneNumber;
    private CircularImageView profileAvatar;
    private User user;
    private TextView verticalPixels;
    private int width;

    public ProfileFragment()
    {
    }

    private String capitalize(String s)
    {
        if (s == null || s.length() == 0)
        {
            s = "";
        } else
        {
            char c = s.charAt(0);
            if (!Character.isUpperCase(c))
            {
                return (new StringBuilder()).append(Character.toUpperCase(c)).append(s.substring(1)).toString();
            }
        }
        return s;
    }

    private ImageRequest getImageRequest()
    {
        ImageRequest imagerequest;
        try
        {
            imagerequest = (new com.facebook.internal.ImageRequest.Builder(getActivity(), ImageRequest.getProfilePictureUrl(user.getFacebookId(), getResources().getDimensionPixelSize(0x7f07001d), getResources().getDimensionPixelSize(0x7f07001c)))).setCallerTag(this).setCallback(new com.facebook.internal.ImageRequest.Callback() {

                final ProfileFragment this$0;

                public void onCompleted(ImageResponse imageresponse)
                {
                    processImageResponse(user.getFacebookId(), imageresponse);
                }

            
            {
                this$0 = ProfileFragment.this;
                super();
            }
            }).build();
        }
        catch (URISyntaxException urisyntaxexception)
        {
            return null;
        }
        return imagerequest;
    }

    private void processImageResponse(String s, ImageResponse imageresponse)
    {
        if (imageresponse != null)
        {
            android.graphics.Bitmap bitmap = imageresponse.getBitmap();
            if (bitmap != null)
            {
                BitmapDrawable bitmapdrawable = new BitmapDrawable(getResources(), bitmap);
                bitmapdrawable.setBounds(0, 0, getResources().getDimensionPixelSize(0x7f07001d), getResources().getDimensionPixelSize(0x7f07001c));
                profileAvatar.setImageDrawable(bitmapdrawable);
            }
        }
    }

    private void updateUI()
    {
        if (isAdded() && user != null && !TextUtils.isEmpty(user.getFacebookId()))
        {
            ImageRequest imagerequest = getImageRequest();
            if (imagerequest != null)
            {
                imagerequest.getImageUri();
                ImageDownloader.downloadAsync(imagerequest);
                return;
            }
        }
    }

    public String getDeviceName()
    {
        String s = Build.MANUFACTURER;
        String s1 = Build.MODEL;
        if (s1.startsWith(s))
        {
            return capitalize(s1);
        } else
        {
            return (new StringBuilder()).append(capitalize(s)).append(" ").append(s1).toString();
        }
    }

    public void onActivityCreated(Bundle bundle)
    {
        super.onActivityCreated(bundle);
        Runnable runnable = new Runnable() {

            final ProfileFragment this$0;

            public void run()
            {
                if (((USpeedActivity)getActivity()).getMenu() != null)
                {
                    ((USpeedActivity)getActivity()).getMenu().getItem(0).setVisible(false);
                    ((USpeedActivity)getActivity()).getMenu().getItem(1).setVisible(false);
                }
            }

            
            {
                this$0 = ProfileFragment.this;
                super();
            }
        };
        (new Handler()).postDelayed(runnable, 500L);
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        width = displaymetrics.widthPixels;
        height = displaymetrics.heightPixels;
    }

    public View onCreateView(LayoutInflater layoutinflater, ViewGroup viewgroup, Bundle bundle)
    {
        View view = layoutinflater.inflate(0x7f03001f, null);
        try
        {
            user = (User) HelperFactory.getHelper().getUserDao().queryForId("me");
        }
        catch (SQLException sqlexception)
        {
            sqlexception.printStackTrace();
        }
        deviceModel = (TextView)view.findViewById(0x7f060062);
        androidVersion = (TextView)view.findViewById(0x7f060063);
        horizontalPixels = (TextView)view.findViewById(0x7f060065);
        verticalPixels = (TextView)view.findViewById(0x7f060064);
        deviceModel.setText(getDeviceName());
        androidVersion.setText(android.os.Build.VERSION.RELEASE);
        horizontalPixels.setText((new StringBuilder()).append(width).append("").toString());
        verticalPixels.setText((new StringBuilder()).append(height).append("").toString());
        name = (TextView)view.findViewById(0x7f060060);
        phoneNumber = (TextView)view.findViewById(0x7f060036);
        locationTv = (TextView)view.findViewById(0x7f060061);
        profileAvatar = (CircularImageView)view.findViewById(0x7f06005f);
        facebookProfileInfo = (LinearLayout)view.findViewById(0x7f06005e);
        if (user != null)
        {
            if (TextUtils.isEmpty(user.getFacebookToken()))
            {
                facebookProfileInfo.setVisibility(4);
            } else
            {
                name.setText((new StringBuilder()).append(user.getName()).append(" ").append(user.getSurname()).toString());
            }
            phoneNumber.setText(user.getPhoneNumber());
        }
        locationTv.setText(locality);
        updateUI();
        return view;
    }

    public void onStart()
    {
        super.onStart();
        (new LocationUtil(getActivity())).getLocation(getActivity(), new Listener() {

            final ProfileFragment this$0;

            public void onError(Exception exception)
            {
            }

            public void onSuccess(Location location1)
            {
                double d;
                double d1;
                locationManager = (LocationManager)getActivity().getSystemService("location");
                new Criteria();
                location = location1;
                d = 0.0D;
                d1 = 0.0D;
                if (location == null) goto _L2; else goto _L1
_L1:
                d = location.getLatitude();
                d1 = location.getLongitude();
_L4:
                LatLng latlng = new LatLng(d, d1);
                LocationAsyncTask locationasynctask = new LocationAsyncTask(getActivity());
                locationasynctask.setLocationTextView(locationTv);
                locationasynctask.setLatLong(latlng);
                locationasynctask.execute(new Void[0]);
                return;
_L2:
                location = locationManager.getLastKnownLocation("gps");
                if (location != null)
                {
                    d = location.getLatitude();
                    d1 = location.getLongitude();
                }
                if (true) goto _L4; else goto _L3
_L3:
            }

            public volatile void onSuccess(Object obj)
            {
                onSuccess((Location)obj);
            }

            
            {
                this$0 = ProfileFragment.this;
                super();
            }
        });
    }



/*
    static LocationManager access$002(ProfileFragment profilefragment, LocationManager locationmanager)
    {
        profilefragment.locationManager = locationmanager;
        return locationmanager;
    }

*/



/*
    static Location access$102(ProfileFragment profilefragment, Location location1)
    {
        profilefragment.location = location1;
        return location1;
    }

*/



}
