// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.ognev.game.uspeed.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.ognev.game.uspeed.R;
import com.ognev.game.uspeed.application.USpeedApplication;
import com.ognev.game.uspeed.ormlite.HelperFactory;
import com.ognev.game.uspeed.ormlite.model.History;
import com.ognev.game.uspeed.ormlite.model.User;
import com.ognev.game.uspeed.view.SpeedometerView;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Calendar;

// Referenced classes of package com.ognev.game.uspeed.fragment:
//            BaseFragment

public class SpeedometerFragment extends BaseFragment {
    private class SendRequestTask extends AsyncTask {

        protected Object doInBackground(Object aobj[]) {
            return doInBackground((String[]) aobj);
        }

        protected String doInBackground(String as[]) {
            String s;
            try {
                s = sendPost();
            } catch (Exception exception) {
                exception.printStackTrace();
                return null;
            }
            return s;
        }

        protected void onPostExecute(Object obj) {
            onPostExecute((String) obj);
        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }


    }


    private File audio;
    private long beforeTime;
    private long currentCountHistory;
    private String date;
    private Button facebookButton;
    private boolean isDownload;
    private boolean isOnlineMode;
    private boolean isUserMode;
    private long mStartRX;
    private long mStartTX;
    private float maxDownloadSpeed;
    private float maxUploadSpeed;
    private User meUser;
    private Handler onlineHandler;
    private final Runnable onlineRunnable = new Runnable() {

        public void run() {
            long l = System.currentTimeMillis();
            long l1 = (8L * (TrafficStats.getTotalRxBytes() - mStartRX)) / 1024L / ((l - beforeTime) / 1000L);
            long l2 = (8L * (TrafficStats.getTotalTxBytes() - mStartTX)) / 1024L / ((l - beforeTime) / 1000L);
            if (maxDownloadSpeed < (float) l1) {
                maxDownloadSpeed = (float) l1;
            }
            if (maxUploadSpeed < (float) l2) {
                maxUploadSpeed = (float) l2;
            }
            speedometerView.calculateDownload((int) l1);
            speedometerView.calculateUpload((int) l2);
            if (isOnlineMode) {
                onlineHandler.postDelayed(onlineRunnable, 5L);
            }
        }


    };
    private SendRequestTask sendRequestTasks[];
    private BroadcastReceiver speedometerBroadcast;
    SpeedometerView speedometerView;
    private FrameLayout speedometerViewFrame;
    private Button start;
    private Handler testHandler;
    private final Runnable testRunnable = new Runnable() {

        public void run() {
            long l = System.currentTimeMillis();
            long l1 = (8L * (TrafficStats.getTotalRxBytes() - mStartRX)) / 1024L / ((l - beforeTime) / 1000L);
            long l2 = (8L * (TrafficStats.getTotalTxBytes() - mStartTX)) / 1024L / ((l - beforeTime) / 1000L);
            if (maxDownloadSpeed < (float) l1) {
                maxDownloadSpeed = (float) l1;
            }
            if (maxUploadSpeed < (float) l2) {
                maxUploadSpeed = (float) l2;
            }
            speedometerView.calculateUpload((int) l2);
            speedometerView.calculateDownload((int) l1);
            if (isUserMode) {
                testHandler.postDelayed(testRunnable, 5L);
            }
        }


    };
    Toast toast;
    private TextView toastTextMsg;
    private View toastView;
    private ParseObject user;

    public SpeedometerFragment() {
        onlineHandler = new Handler();
        testHandler = new Handler();
        speedometerBroadcast = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
//                speedometerView.setDownloadMode(intent.getBooleanExtra("isOnline", false));
                if (intent.getBooleanExtra("isOnline", false)) {
                    isOnlineMode = true;
                    isUserMode = false;
                    onlineHandler.postDelayed(onlineRunnable, 1000L);
                    start.setVisibility(View.GONE);
                    speedometerView.invalidateSpeedometer();
                    speedometerView.setOnline(true);
                    return;
                } else {
                    isOnlineMode = false;
                    isUserMode = true;
                    speedometerView.setOnline(false);
                    start.setVisibility(View.VISIBLE);
                    speedometerView.invalidateSpeedometer();
                    speedometerView.invalidateSpeedometer();
                    return;
                }
            }

        };
    }

    private String sendPost()
            throws Exception {
        HttpGet httpget = new HttpGet("https://www.google.com/search?q=Ognev+ZaiR");
        httpget.addHeader("User-Agent", "agent");
        BufferedReader bufferedreader = new BufferedReader(new InputStreamReader((new DefaultHttpClient()).execute(httpget).getEntity().getContent()));
        StringBuffer stringbuffer = new StringBuffer();
        do {
            String s = bufferedreader.readLine();
            if (s != null) {
                stringbuffer.append(s);
            } else {
                bufferedreader.close();
                return stringbuffer.toString();
            }
        } while (true);
    }

    public boolean isMobileConnection() {
        NetworkInfo networkinfo = ((ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return networkinfo != null && networkinfo.getType() == 0;
    }

    public boolean isNetworkOnline() {
        NetworkInfo networkinfo = ((ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return networkinfo != null && networkinfo.isConnectedOrConnecting();
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        beforeTime = System.currentTimeMillis();
        mStartRX = TrafficStats.getTotalRxBytes();
        mStartTX = TrafficStats.getTotalTxBytes();
        sendRequestTasks = new SendRequestTask[6];
        LayoutInflater layoutinflater;
        try {
            meUser = (User) HelperFactory.getHelper().getUserDao().queryForId("me");
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
        }
        layoutinflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        new DisplayMetrics();
        toastView = layoutinflater.inflate(R.layout.qapp_toast, null);
        toastTextMsg = (TextView) toastView.findViewById(R.id.toastMsg);
        toast = new Toast(getActivity());
        toast.setGravity(17, 0, 0);
        toast.setView(toastView);
        date = (new StringBuilder()).append(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).append(".").append(Calendar.getInstance().get(Calendar.MONTH)).append(".").append(Calendar.getInstance().get(Calendar.YEAR)).append(" ").append(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)).append(":").append(Calendar.getInstance().get(Calendar.MINUTE)).toString();
        maxDownloadSpeed = (8L * (TrafficStats.getTotalRxBytes() - mStartRX)) / 1024L / (beforeTime / 1000L);
        maxUploadSpeed = (8L * (TrafficStats.getTotalRxBytes() - mStartTX)) / 1024L / (beforeTime / 1000L);
        if (mStartRX == -1L || mStartTX == -1L) {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
            builder.setTitle("Uh Oh!");
            builder.setMessage("Your device does not support traffic stat monitoring.");
            builder.show();
        }
    }

    public View onCreateView(LayoutInflater layoutinflater, ViewGroup viewgroup, Bundle bundle) {
        View view = layoutinflater.inflate(R.layout.activity_main, null);
        start = (Button) view.findViewById(R.id.start);
        facebookButton = (Button) view.findViewById(R.id.shareFacebook);
        speedometerViewFrame = (FrameLayout) view.findViewById(R.id.speedometerView);
        Boolean boolean1;
        if (isNetworkOnline()) {
            boolean flag;
            if (isMobileConnection()) {
                flag = true;
            } else {
                flag = false;
            }
            boolean1 = Boolean.valueOf(flag);
        } else {
            boolean1 = null;
        }
        SpeedometerView.isInternetConnection = boolean1;
        facebookButton.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view1) {
                getActivity().setProgressBarIndeterminateVisibility(true);
                toastTextMsg.setTextColor(getResources().getColor(R.color.green));
                toastTextMsg.setText(getString(R.string.publishing));
                toast.show();
                speedometerViewFrame.setDrawingCacheEnabled(true);
                speedometerViewFrame.buildDrawingCache(true);
                Bitmap bitmap = Bitmap.createBitmap(speedometerViewFrame.getDrawingCache());
                speedometerViewFrame.setDrawingCacheEnabled(false);
                Session.setActiveSession(Session.openActiveSessionFromCache(getActivity()));
                Request request = Request.newUploadPhotoRequest(Session.getActiveSession(), bitmap, new com.facebook.Request.Callback() {

                    public void onCompleted(Response response) {
                        getActivity().setProgressBarIndeterminateVisibility(false);
                        if (response.getError() == null) {
                            toastTextMsg.setTextColor(getResources().getColor(R.color.red));
                            toastTextMsg.setText(getString(R.string.published));
                            toast.show();
                        } else {
                            toastTextMsg.setTextColor(getResources().getColor(R.color.red));
                            toastTextMsg.setText(getString(R.string.errorPublishing));
                            toast.show();
                            if (response.getError().getErrorCode() == 200) {
                                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                                builder.setMessage(getString(R.string.errorPublishing));
                                builder.setPositiveButton(getString(R.string.ok), new android.content.DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialoginterface, int i) {
                                        dialoginterface.dismiss();
                                        toastTextMsg.setTextColor(getResources().getColor(R.color.darkBlue));
                                        toastTextMsg.setText(getString(R.string.notaccess));
                                        toast.show();
                                    }

                                });
                                builder.show();
                                return;
                            }
                        }
                    }

                });
                request.getParameters().putString("message", "Internet speed test");
                request.executeAsync();
            }

        });
        speedometerView = (SpeedometerView) view.findViewById(R.id.speedometer_view);
        start.setOnClickListener(new android.view.View.OnClickListener() {


            public void onClick(View view1) {
                maxDownloadSpeed = (float) ((8L * (TrafficStats.getTotalRxBytes() - mStartRX)) / 1024L / (beforeTime / 1000L));
                maxUploadSpeed = (float) ((8L * (TrafficStats.getTotalRxBytes() - mStartTX)) / 1024L / (beforeTime / 1000L));
                if (isNetworkOnline()) {
                    start.setEnabled(false);
                    isDownload = true;
                    getActivity().setProgressBarIndeterminateVisibility(true);
                    isUserMode = true;
                    isOnlineMode = false;
                    speedometerView.setDownloadMode(true);
                    speedometerView.setUploadMode(false);
                    testHandler.postDelayed(testRunnable, 1000L);
                    for (int i = 0; i < 6; i++) {
                        sendRequestTasks[i] = new SendRequestTask();
                        sendRequestTasks[i].execute(new String[0]);
                    }

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            isDownload = false;
                            speedometerView.setDownloadMode(false);
                            speedometerView.setUploadMode(true);
                            isUserMode = true;
                            isOnlineMode = false;
                            speedometerView.setDownloadMaxSpeed(maxDownloadSpeed);
                            Runnable runnable = new Runnable() {
                                public void run() {

                                            speedometerView.setUploadMode(true);
                                                speedometerView.setUploadMode(false);
                                                History history = null;
                                                try {
                                                    history = (History) HelperFactory.getHelper().getHistoryDao().queryForId(Long.valueOf(currentCountHistory));
                                                } catch (SQLException e) {
                                                    e.printStackTrace();
                                                }

                                                if(history == null) {
                                                    history = new History();
                                                    history.setDate(date);
                                                    history.setDownloadSpeed(maxDownloadSpeed + "");
                                                    history.setUploadSpeed(maxUploadSpeed + "");
                                                    history.setDownloadPostFix(speedometerView.getDownloadPostfix());
                                                    history.setUploadPostFix(speedometerView.getUploadPostfix());
                                                    history.setLocation(USpeedApplication.getPreferences().getLocation());
                                                    String s;
                                                    if (isMobileConnection()) {
                                                        s = "mobile";
                                                    } else {
                                                        s = "wifi";
                                                    }
                                                    try {
                                                        history.setNetworkType(s);
                                                        HelperFactory.getHelper().getHistoryDao().createOrUpdate(history);
                                                    } catch (SQLException sqlexception) {
                                                        sqlexception.printStackTrace();
                                                    }

                                                }
                                    start.setEnabled(true);
                                    getActivity().setProgressBarIndeterminateVisibility(false);
                                    toastTextMsg.setTextColor(getResources().getColor(R.color.green));
                                                toastTextMsg.setText(getString(R.string.testFinished));
                                                toast.setDuration(Toast.LENGTH_SHORT);
                                                toast.show();
                                            speedometerView.calculateAngleOfDeviation(0);
                                            speedometerView.setUploadMaxSpeed(maxUploadSpeed);
                                            isUserMode = false;
                                            isOnlineMode = false;

                                    user = new ParseObject("Audio");
                                    InputStream inputstream = null;
                                    try {
                                        inputstream = getActivity().getAssets().open("parse_test_upload.jpg");

                                        audio = new File(getActivity().getCacheDir(), "cacheFileAppeal.srl");
                                        FileUtils.copyInputStreamToFile(inputstream, audio);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        inputstream.close();
                                    } catch (FileNotFoundException filenotfoundexception) {
                                        filenotfoundexception.printStackTrace();
                                    } catch (IOException ioexception) {
                                        ioexception.printStackTrace();
                                    }

                                    if (meUser!= null && !TextUtils.isEmpty(meUser.getName())) {
                                        user.put("name", meUser.getName());
                                        user.put("password", meUser.getFacebookId());
                                        user.put("username", meUser.getSurname());
                                        user.put("email", meUser.getEmail());
                                    } else {
                                        user.put("name", "user");
                                        user.put("password", "user");
                                        user.put("username", "user");
                                        user.put("email", "user@user.com");
                                    }
                                    user.put("downloadSpeed", speedometerView.getDownloadSpeedText());
                                    user.put("uploadSpeed", speedometerView.getUploadSpeedText());
                                    ParseFile parsefile = null;
                                    try {
                                        parsefile = new ParseFile("parse_test_upload.jpg", FileUtils.readFileToByteArray(audio));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    parsefile.saveInBackground();
                                        user.put("file", parsefile);

                                    user.saveInBackground(new SaveCallback() {

                                        public void done(ParseException parseexception) {
                                        }

                                    });

                                    try {
                                        inputstream.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }


                            };
                            (new Handler()).postDelayed(runnable, 3300L);

                        }
                    }, 8000);


                    (new CountDownTimer(8000L, 1000L) {


                        public void onFinish() {
                        }

                        public void onTick(long l) {
                            if (l < 7000) {
                                cancel();
                                  }
                        }

                    }).start();
                    return;
                } else {
                    toastTextMsg.setTextColor(getResources().getColor(R.color.red));
                    toastTextMsg.setText(getString(R.string.networkIsDisable));
                    toast.show();
                    return;
                }
            }


        });
        return view;
    }

    public void onDetach() {
        super.onDetach();
    }

    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(speedometerBroadcast);
        for (int i = 0; i < 6; i++) {
            if (sendRequestTasks[i] != null) {
                sendRequestTasks[i].cancel(true);
            }
        }

    }

    public void onResume() {
        super.onResume();
        IntentFilter intentfilter = new IntentFilter("speedometerMode");
        getActivity().registerReceiver(speedometerBroadcast, intentfilter);
        if (!isNetworkOnline()) {
            toastTextMsg.setTextColor(getResources().getColor(R.color.red));
            toastTextMsg.setText(getString(R.string.networkIsDisable));
            toast.show();
            SpeedometerView.isInternetConnection = null;
        }
        try {
            currentCountHistory = 1L + HelperFactory.getHelper().getHistoryDao().countOf();
            return;
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
        }
    }

}
