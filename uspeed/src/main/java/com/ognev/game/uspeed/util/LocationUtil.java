// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.ognev.game.uspeed.util;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

// Referenced classes of package com.ognev.game.uspeed.util:
//            Listener

public class LocationUtil
{

    LocationClient client;
    Context context;

    public LocationUtil(Context context1)
    {
        context = context1;
    }

    public void getLocation(Context context1, final Listener result)
    {
        client = new LocationClient(context1, new com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks() {

            final LocationUtil this$0;
            final Listener val$result;

            public void onConnected(Bundle bundle)
            {
label0:
                {
                    if (client != null)
                    {
                        Location location = client.getLastLocation();
                        if (location == null)
                        {
                            break label0;
                        }
                        result.onSuccess(location);
                        client.disconnect();
                    }
                    return;
                }
                LocationRequest locationrequest = LocationRequest.create();
                locationrequest.setPriority(102);
                locationrequest.setInterval(5L);
                locationrequest.setFastestInterval(1L);
                client.requestLocationUpdates(locationrequest, new LocationListener() {

                    final _cls1 this$1;

                    public void onLocationChanged(Location location)
                    {
                        result.onSuccess(location);
                        client.removeLocationUpdates(this);
                        client.disconnect();
                    }

            
            {
                this$1 = _cls1.this;
                super();
            }
                });
            }

            public void onDisconnected()
            {
            }

            
            {
                this$0 = LocationUtil.this;
                result = listener;
                super();
            }
        }, new com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener() {

            final LocationUtil this$0;

            public void onConnectionFailed(ConnectionResult connectionresult)
            {
            }

            
            {
                this$0 = LocationUtil.this;
                super();
            }
        });
        client.connect();
    }
}
