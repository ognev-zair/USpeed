// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.ognev.game.uspeed.fragment;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ognev.game.uspeed.R;
import com.ognev.game.uspeed.application.USpeedApplication;
import com.ognev.game.uspeed.util.Listener;
import com.ognev.game.uspeed.util.LocationAsyncTask;
import com.ognev.game.uspeed.util.LocationUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

// Referenced classes of package com.ognev.game.uspeed.fragment:
//            BaseFragment, MyMapFragment

public class MapFragment extends BaseFragment {

    private TextView address;
    private LatLng currentLocation;
    private TextView currentLocationTv;
    private List latLngs;
    private double lng;
    private Location location;
    private LocationAsyncTask locationAsyncTask;
    private LocationManager locationManager;
    private double lt;
    private GoogleMap map;
    private Fragment mapFragment;
    private MarkerOptions options;
    private TextView toastTextMsg;
    private View toastView;
    private HashMap ucellAddressName;
    private HashMap ucellInfoAddress;
    private HashMap ucellInfoTime;
    private TextView workingTime;
    private boolean isFirst;

    public MapFragment() {
    }

    private void blinkText(TextView textview) {
        AlphaAnimation alphaanimation = new AlphaAnimation(0.0F, 1.0F);
        alphaanimation.setDuration(800L);
        alphaanimation.setRepeatMode(2);
        alphaanimation.setRepeatCount(-1);
        textview.startAnimation(alphaanimation);
    }

    public void hintText(TextView textview) {
        AlphaAnimation alphaanimation = new AlphaAnimation(0.0F, 0.0F);
        alphaanimation.setRepeatMode(2);
        alphaanimation.setRepeatCount(-1);
        textview.startAnimation(alphaanimation);
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        isFirst = true;
        latLngs = new ArrayList();
        ucellAddressName = new HashMap();
        ucellInfoAddress = new HashMap();
        ucellInfoTime = new HashMap();
        toastView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.qapp_toast, null);
        toastTextMsg = (TextView) toastView.findViewById(R.id.toastMsg);
        FragmentTransaction fragmenttransaction = getChildFragmentManager().beginTransaction();
        mapFragment = getChildFragmentManager().findFragmentByTag("myMap");
        if (mapFragment == null) {
            mapFragment = new MyMapFragment();
        }
        fragmenttransaction.replace(R.id.mapHolder, mapFragment, "myMap");
        fragmenttransaction.commit();
        getChildFragmentManager().executePendingTransactions();
        locationAsyncTask = new LocationAsyncTask(getActivity());
    }

    public View onCreateView(LayoutInflater layoutinflater, ViewGroup viewgroup, Bundle bundle) {
        View view = layoutinflater.inflate(R.layout.map_view, null);
        address = (TextView) view.findViewById(R.id.address);
        workingTime = (TextView) view.findViewById(R.id.workingTime);
        currentLocationTv = (TextView) view.findViewById(R.id.currentLocation);
        blinkText(currentLocationTv);
        locationAsyncTask.setLocationTextView(address);
        SpannableString spannablestring = new SpannableString(getResources().getString(R.string.detectingLocation));
        spannablestring.setSpan(new StyleSpan(1), 0, spannablestring.length(), 0);
        spannablestring.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.ucellColor)), 0, spannablestring.length(), 33);
        address.setText(spannablestring);
        address.setText(USpeedApplication.getPreferences().getLocation());
        return view;
    }

    public void onResume() {
        super.onResume();
        if (location == null) {
            map = ((MyMapFragment) mapFragment).getMap();
            map.setMyLocationEnabled(true);
            latLngs.add(new LatLng(41.316977000000001D, 69.282214999999994D));
            latLngs.add(new LatLng(41.295479D, 69.264854D));
            latLngs.add(new LatLng(41.325674999999997D, 69.229603999999995D));
            latLngs.add(new LatLng(41.325788000000003D, 69.328579000000005D));
            latLngs.add(new LatLng(41.279783000000002D, 69.225804999999994D));
            latLngs.add(new LatLng(41.327820000000003D, 69.338553000000005D));
            latLngs.add(new LatLng(41.324998000000001D, 69.324135999999996D));
            ucellInfoAddress.put(Double.valueOf(41.316977000000001D), "\u042E\u043D\u0443\u0441-\u0410\u0431\u0430\u0434\u0441\u043A\u0438\u0439 \u0440-\u043D, \u0443\u043B.\u0428\u0430\u0445\u0440\u0438\u0441\u0430\u0431\u0437, 1. \u041E\u0440\u0438\u0435\u043D\u0442\u0438\u0440: \u041E\u0442\u0435\u043B\u044C \"DEDEMAN\"");
            ucellInfoAddress.put(Double.valueOf(41.295479D), "\u0443\u043B.\u0412\u0430\u0445\u0438\u0434\u043E\u0432\u0430, 118. \u041E\u0440\u0438\u0435\u043D\u0442\u0438\u0440: \u041E\u0442\u0435\u043B\u044C \"GRAND MIR\" \u2013 \u0431\u044B\u0432\u0448\u0430\u044F \u0433\u043E\u0441\u0442\u0438\u043D\u0438\u0446\u0430 \"\u0420\u043E\u0441\u0441\u0438\u044F\".");
            ucellInfoAddress.put(Double.valueOf(41.325674999999997D), "\u041E\u043B\u043C\u0430\u0437\u0430\u0440 \u0440-\u043D, \u0443\u043B.\u0411\u0435\u0440\u0443\u043D\u0438\u0439, \u0411-1 \u0434.2, \u0441\u0435\u043A\u0446\u0438\u044F 3.");
            ucellInfoAddress.put(Double.valueOf(41.325788000000003D), "\u041C\u0438\u0440\u0437\u043E-\u0423\u043B\u0443\u0433\u0431\u0435\u043A\u0441\u043A\u0438\u0439 \u0440-\u043D, \u0411\u0443\u044E\u043A \u0418\u043F\u0430\u043A \u0419\u0443\u043B\u0438, \u0434.58, \u043A\u0432.76.");
            ucellInfoAddress.put(Double.valueOf(41.279783000000002D), "\u0423\u0447\u0442\u0435\u043F\u0438\u043D\u0441\u043A\u0438\u0439 \u0440\u0430\u0439\u043E\u043D, \u043A\u0432-\u043B 21, \u0434.30\u0410. \u041E\u0440\u0438\u0435\u043D\u0442\u0438\u0440: \u043D\u0430\u043F\u0440\u043E\u0442\u0438\u0432 \u0413\u043E\u0440\u043E\u0434\u0441\u043A\u043E\u0433\u043E \u041D\u0430\u0440\u043A\u043E\u043B\u043E\u0433\u0438\u0447\u0435\u0441\u043A\u043E\u0433\u043E \u0434\u0438\u0441\u043F\u0430\u043D\u0441\u0435\u0440\u0430.");
            ucellInfoAddress.put(Double.valueOf(41.327820000000003D), "\u0422\u0430\u0448\u043A\u0435\u043D\u0442, \u041C\u0438\u0440\u0437\u043E \u0423\u043B\u0443\u0433\u0431\u0435\u043A\u0441\u043A\u0438\u0439 \u0440-\u043D, \u0443\u043B. \u0411\u0443\u044E\u043A \u0418\u043F\u0430\u043A \u0419\u0443\u043B\u0438 \u0434-129");
            ucellInfoAddress.put(Double.valueOf(41.324998000000001D), "\u0422\u0430\u0448\u043A\u0435\u043D\u0442, \u041C-\u0423\u043B\u0443\u0433\u0431\u0435\u043A\u0441\u043A\u0438\u0439 \u0440\u0430\u0439\u043E\u043D, \u0443\u043B,\u041C-\u0423\u043B\u0433\u0431\u0435\u043A \u0434\u043E\u043C,53 \u043A\u0432");
            ucellInfoTime.put(Double.valueOf(41.316977000000001D), "8:00 - 19:00 ; 9:00 - 17:00");
            ucellInfoTime.put(Double.valueOf(41.295479D), "8:00 - 19:00 ; 9:00 - 17:00");
            ucellInfoTime.put(Double.valueOf(41.325674999999997D), "9:00 - 18:00 ; 9:00 - 17:00");
            ucellInfoTime.put(Double.valueOf(41.325788000000003D), "9:00 - 18:00 ; 9:00 - 17:00");
            ucellInfoTime.put(Double.valueOf(41.279783000000002D), "8:00 - 19:00 ; 9:00 - 17:00");
            ucellInfoTime.put(Double.valueOf(41.327820000000003D), "8:00 - 19:00 ; 9:00 - 17:00");
            ucellInfoTime.put(Double.valueOf(41.324998000000001D), "8:00 - 19:00 ; 9:00 - 17:00");
            ucellAddressName.put(Double.valueOf(41.316977000000001D), "Ucell-Markazi:");
            ucellAddressName.put(Double.valueOf(41.295479D), "Ucell-Tashkent: ");
            ucellAddressName.put(Double.valueOf(41.325674999999997D), "Ucell-Tinchlik: ");
            ucellAddressName.put(Double.valueOf(41.325788000000003D), "Ucell-7: ");
            ucellAddressName.put(Double.valueOf(41.279783000000002D), "Ucell-Chilonzor: ");
            ucellAddressName.put(Double.valueOf(41.327820000000003D), "Ucell: ");
            ucellAddressName.put(Double.valueOf(41.324998000000001D), "Ucell: ");
            for (int i = 0; i < 7; i++) {
                options = new MarkerOptions();
                options.position((LatLng) latLngs.get(i));
                options.icon(BitmapDescriptorFactory.fromResource(R.drawable.ucell_marker));
                map.addMarker(options);
            }

            com.google.android.gms.maps.CameraUpdate cameraupdate = CameraUpdateFactory.newLatLngZoom((LatLng) latLngs.get(0), 11F);
            CameraUpdateFactory.zoomTo(15F);
            map.moveCamera(cameraupdate);
            map.setOnMarkerClickListener(new com.google.android.gms.maps.GoogleMap.OnMarkerClickListener() {

                public boolean onMarkerClick(Marker marker) {
                    if (ucellInfoTime.get(Double.valueOf(marker.getPosition().latitude)) != null && !((String) ucellInfoTime.get(Double.valueOf(marker.getPosition().latitude))).isEmpty()) {
                        address.setText((CharSequence) ucellInfoAddress.get(Double.valueOf(marker.getPosition().latitude)));
                        String as[] = ((String) ucellInfoTime.get(Double.valueOf(marker.getPosition().latitude))).split(";");
                        workingTime.setText((new StringBuilder()).append(getResources().getString(R.string.monFri)).append(": ").append(as[0]).append("\n").append(getResources().getString(R.string.satSun)).append(": ").append(as[1]).toString());
                        SpannableString spannablestring1 = new SpannableString((CharSequence) ucellAddressName.get(Double.valueOf(marker.getPosition().latitude)));
                        spannablestring1.setSpan(new StyleSpan(1), 0, spannablestring1.length(), 0);
                        spannablestring1.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.white)), 0, spannablestring1.length(), 51);
                        address.setText(spannablestring1);
                        address.append((new StringBuilder()).append(" ").append((String) ucellInfoAddress.get(Double.valueOf(marker.getPosition().latitude))).toString());
                        return false;
                    }
                    SpannableString spannablestring = new SpannableString(getResources().getString(R.string.yourLocation));
                    spannablestring.setSpan(new StyleSpan(1), 0, spannablestring.length(), 0);
                    spannablestring.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.white)), 0, spannablestring.length(), 51);
                    address.setText(spannablestring);
                    address.append("\n");
                    TextView textview = address;
                    String s;
//                   LocationAsyncTask locationAsyncTask1  = new LocationAsyncTask(getActivity());
//                    locationAsyncTask1.execute();
                    if (!TextUtils.isEmpty(locationAsyncTask.getAddress())) {
                        s = locationAsyncTask.getAddress();
                    } else {
                        s = getString(R.string.detectingLocation);
                    }
                    textview.append(s);
                    workingTime.setText((new StringBuilder()).append(getString(R.string.latitude)).append(": ").append(marker.getPosition().latitude).append("\n").append(getString(R.string.longitude)).append(": ").append(marker.getPosition().longitude).toString());
                    return false;
                }

            });
//            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
//            (new LocationUtil(getActivity())).getLocation(getActivity(), new Listener() {
//
//                public void onError(Exception exception) {
//                    Toast toast = new Toast(getActivity());
//                    currentLocationTv.setText(getString(R.string.errorLocation));
//                    currentLocationTv.setVisibility(View.GONE);
//                    hintText(currentLocationTv);
//                    toastTextMsg.setText(getString(R.string.whatsWrong));
//                    toast.setView(toastView);
//                    toast.setDuration(Toast.LENGTH_SHORT);
//                    toast.show();
//                }

//                public void onSuccess(Location location1) {
//                    location = location1;
//            location=  map.getCameraPosition().target;
//            locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
//            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//            if(location == null)
//                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            if(location == null) {
//                location = new Location(Context.LOCATION_SERVICE);
//                location.setLatitude(map.getCameraPosition().target.latitude);
//                location.setLongitude(map.getCameraPosition().target.longitude);
//            }
//            map.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
//                @Override
//                public boolean onMyLocationButtonClick() {
//                    LatLng latlng = new LatLng(lt, lng);
//                    MapsInitializer.initialize(getActivity());
//                    options = new MarkerOptions();
//                    options.position(latlng);
//                    map.addMarker(options);
////                    com.google.android.gms.maps.CameraUpdate cameraupdate1 = CameraUpdateFactory.newLatLngZoom(latlng, 11F);
////                    CameraUpdateFactory.zoomTo(15F);
//                    locationAsyncTask.setLatLong(latlng);
////                    map.moveCamera(cameraupdate1);
//                    if (locationAsyncTask.isCancelled()) {
//                        locationAsyncTask.execute(new Void[0]);
//                    }
//                    currentLocationTv.setVisibility(View.GONE);
//                    hintText(currentLocationTv);
//                    SpannableString spannablestring = new SpannableString(getResources().getString(R.string.yourLocation));
//                    spannablestring.setSpan(new StyleSpan(1), 0, spannablestring.length(), 0);
//                    spannablestring.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.white)), 0, spannablestring.length(), 51);
//                    address.setText(spannablestring);
//                    address.append("\n");
//                    TextView textview = address;
//                    String s;
//                    if (!TextUtils.isEmpty(locationAsyncTask.getAddress())) {
//                        s = locationAsyncTask.getAddress();
//                    } else {
//                        s = getString(R.string.aboutApp);
//                    }
//                    textview.append(s);
//                    workingTime.setText((new StringBuilder()).append(getString(R.string.latitude)).append(": ").append(lt).append("\n").append(getString(R.string.longitude)).append(": ").append(lng).toString());
////                }
//                    return false;
//                }
//            });
            map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

                @Override
                public void onMyLocationChange(Location location) {
                    if (isFirst) {
                        isFirst = false;
                        lt = location.getLatitude();
                        lng = location.getLongitude();
                        LatLng latlng = new LatLng(lt, lng);
                        MapsInitializer.initialize(getActivity());
                        options = new MarkerOptions();
                        options.position(latlng);
                        map.addMarker(options);
//                    com.google.android.gms.maps.CameraUpdate cameraupdate1 = CameraUpdateFactory.newLatLngZoom(latlng, 11F);
//                    CameraUpdateFactory.zoomTo(15F);
                        locationAsyncTask.setLatLong(latlng);
//                    map.moveCamera(cameraupdate1);
//                        if (locationAsyncTask.isCancelled()) {
                            locationAsyncTask.execute();
//                        }
                        currentLocationTv.setVisibility(View.GONE);
                        hintText(currentLocationTv);
                        SpannableString spannablestring = new SpannableString(getResources().getString(R.string.yourLocation));
                        spannablestring.setSpan(new StyleSpan(1), 0, spannablestring.length(), 0);
                        spannablestring.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.white)), 0, spannablestring.length(), 51);
                        address.setText(spannablestring);
                        address.append("\n");
                        TextView textview = address;
                        String s;
                        if (!TextUtils.isEmpty(locationAsyncTask.getAddress())) {
                            s = locationAsyncTask.getAddress();
                        } else {
                            s = getString(R.string.detectingLocation);
                        }
                        textview.append(s);
                        workingTime.setText((new StringBuilder()).append(getString(R.string.latitude)).append(": ").append(lt).append("\n").append(getString(R.string.longitude)).append(": ").append(lng).toString());
//                }
                    }
                }
            });
        }
    }

    public void onStart() {
        super.onStart();
    }

    public void onStop() {
        super.onStop();
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
    }

}
