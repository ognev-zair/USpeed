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
import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.ognev.game.uspeed.application.USpeedApplication;
import com.ognev.game.uspeed.ormlite.HelperFactory;
import com.ognev.game.uspeed.ormlite.model.History;
import com.ognev.game.uspeed.ormlite.model.User;
import com.ognev.game.uspeed.view.SpeedometerView;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Calendar;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

// Referenced classes of package com.ognev.game.uspeed.fragment:
//            BaseFragment

public class SpeedometerFragment extends BaseFragment
{
    private class SendRequestTask extends AsyncTask
    {

        final SpeedometerFragment this$0;

        protected volatile Object doInBackground(Object aobj[])
        {
            return doInBackground((String[])aobj);
        }

        protected transient String doInBackground(String as[])
        {
            String s;
            try
            {
                s = sendPost();
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                return null;
            }
            return s;
        }

        protected volatile void onPostExecute(Object obj)
        {
            onPostExecute((String)obj);
        }

        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
        }

        private SendRequestTask()
        {
            this$0 = SpeedometerFragment.this;
            super();
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

        final SpeedometerFragment this$0;

        public void run()
        {
            long l = System.currentTimeMillis();
            long l1 = (8L * (TrafficStats.getTotalRxBytes() - mStartRX)) / 1024L / ((l - beforeTime) / 1000L);
            long l2 = (8L * (TrafficStats.getTotalTxBytes() - mStartTX)) / 1024L / ((l - beforeTime) / 1000L);
            if (maxDownloadSpeed < (float)l1)
            {
                maxDownloadSpeed = (float)l1;
            }
            if (maxUploadSpeed < (float)l2)
            {
                maxUploadSpeed = (float)l2;
            }
            speedometerView.calculateAngleOfDeviation((int)l1);
            if (isOnlineMode)
            {
                onlineHandler.postDelayed(onlineRunnable, 5L);
            }
        }

            
            {
                this$0 = SpeedometerFragment.this;
                super();
            }
    };
    private SendRequestTask sendRequestTasks[];
    private BroadcastReceiver speedometerBroadcast;
    SpeedometerView speedometerView;
    private FrameLayout speedometerViewFrame;
    private Button start;
    private Handler testHandler;
    private final Runnable testRunnable = new Runnable() {

        final SpeedometerFragment this$0;

        public void run()
        {
            long l = System.currentTimeMillis();
            long l1 = (8L * (TrafficStats.getTotalRxBytes() - mStartRX)) / 1024L / ((l - beforeTime) / 1000L);
            long l2 = (8L * (TrafficStats.getTotalTxBytes() - mStartTX)) / 1024L / ((l - beforeTime) / 1000L);
            if (maxDownloadSpeed < (float)l1)
            {
                maxDownloadSpeed = (float)l1;
            }
            if (maxUploadSpeed < (float)l2)
            {
                maxUploadSpeed = (float)l2;
            }
            if (isDownload)
            {
                speedometerView.calculateAngleOfDeviation((int)l2);
            } else
            {
                speedometerView.calculateAngleOfDeviation((int)l1);
            }
            if (isUserMode)
            {
                testHandler.postDelayed(testRunnable, 5L);
            }
        }

            
            {
                this$0 = SpeedometerFragment.this;
                super();
            }
    };
    Toast toast;
    private TextView toastTextMsg;
    private View toastView;
    private ParseObject user;

    public SpeedometerFragment()
    {
        onlineHandler = new Handler();
        testHandler = new Handler();
        speedometerBroadcast = new BroadcastReceiver() {

            final SpeedometerFragment this$0;

            public void onReceive(Context context, Intent intent)
            {
                speedometerView.setDownloadMode(intent.getBooleanExtra("isOnline", false));
                if (intent.getBooleanExtra("isOnline", false))
                {
                    isOnlineMode = true;
                    isUserMode = false;
                    onlineHandler.postDelayed(onlineRunnable, 1000L);
                    start.setVisibility(8);
                    speedometerView.calculateAngleOfDeviation(0);
                    return;
                } else
                {
                    isOnlineMode = false;
                    isUserMode = true;
                    start.setVisibility(0);
                    speedometerView.calculateAngleOfDeviation(0);
                    return;
                }
            }

            
            {
                this$0 = SpeedometerFragment.this;
                super();
            }
        };
    }

    private String sendPost()
        throws Exception
    {
        HttpGet httpget = new HttpGet("https://www.google.com/search?q=Ognev+ZaiR");
        httpget.addHeader("User-Agent", "agent");
        BufferedReader bufferedreader = new BufferedReader(new InputStreamReader((new DefaultHttpClient()).execute(httpget).getEntity().getContent()));
        StringBuffer stringbuffer = new StringBuffer();
        do
        {
            String s = bufferedreader.readLine();
            if (s != null)
            {
                stringbuffer.append(s);
            } else
            {
                bufferedreader.close();
                return stringbuffer.toString();
            }
        } while (true);
    }

    public boolean isMobileConnection()
    {
        NetworkInfo networkinfo = ((ConnectivityManager)getActivity().getSystemService("connectivity")).getActiveNetworkInfo();
        return networkinfo != null && networkinfo.getType() == 0;
    }

    public boolean isNetworkOnline()
    {
        NetworkInfo networkinfo = ((ConnectivityManager)getActivity().getSystemService("connectivity")).getActiveNetworkInfo();
        return networkinfo != null && networkinfo.isConnectedOrConnecting();
    }

    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        beforeTime = System.currentTimeMillis();
        mStartRX = TrafficStats.getTotalRxBytes();
        mStartTX = TrafficStats.getTotalTxBytes();
        sendRequestTasks = new SendRequestTask[6];
        LayoutInflater layoutinflater;
        try
        {
            meUser = (User) HelperFactory.getHelper().getUserDao().queryForId("me");
        }
        catch (SQLException sqlexception)
        {
            sqlexception.printStackTrace();
        }
        layoutinflater = (LayoutInflater)getActivity().getSystemService("layout_inflater");
        new DisplayMetrics();
        toastView = layoutinflater.inflate(0x7f030020, null);
        toastTextMsg = (TextView)toastView.findViewById(0x7f060066);
        toast = new Toast(getActivity());
        toast.setGravity(17, 0, 0);
        toast.setView(toastView);
        date = (new StringBuilder()).append(Calendar.getInstance().get(5)).append(".").append(Calendar.getInstance().get(2)).append(".").append(Calendar.getInstance().get(1)).append(" ").append(Calendar.getInstance().get(11)).append(":").append(Calendar.getInstance().get(12)).toString();
        maxDownloadSpeed = (8L * (TrafficStats.getTotalRxBytes() - mStartRX)) / 1024L / (beforeTime / 1000L);
        maxUploadSpeed = (8L * (TrafficStats.getTotalRxBytes() - mStartTX)) / 1024L / (beforeTime / 1000L);
        if (mStartRX == -1L || mStartTX == -1L)
        {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
            builder.setTitle("Uh Oh!");
            builder.setMessage("Your device does not support traffic stat monitoring.");
            builder.show();
        }
    }

    public View onCreateView(LayoutInflater layoutinflater, ViewGroup viewgroup, Bundle bundle)
    {
        View view = layoutinflater.inflate(0x7f030001, null);
        start = (Button)view.findViewById(0x7f060009);
        facebookButton = (Button)view.findViewById(0x7f06002d);
        speedometerViewFrame = (FrameLayout)view.findViewById(0x7f06002b);
        Boolean boolean1;
        if (isNetworkOnline())
        {
            boolean flag;
            if (isMobileConnection())
            {
                flag = true;
            } else
            {
                flag = false;
            }
            boolean1 = Boolean.valueOf(flag);
        } else
        {
            boolean1 = null;
        }
        SpeedometerView.isInternetConnection = boolean1;
        facebookButton.setOnClickListener(new android.view.View.OnClickListener() {

            final SpeedometerFragment this$0;

            public void onClick(View view1)
            {
                getActivity().setProgressBarIndeterminateVisibility(true);
                toastTextMsg.setTextColor(getResources().getColor(0x7f05001a));
                toastTextMsg.setText(getString(0x7f090076));
                toast.show();
                speedometerViewFrame.setDrawingCacheEnabled(true);
                speedometerViewFrame.buildDrawingCache(true);
                Bitmap bitmap = Bitmap.createBitmap(speedometerViewFrame.getDrawingCache());
                speedometerViewFrame.setDrawingCacheEnabled(false);
                Session.setActiveSession(Session.openActiveSessionFromCache(getActivity()));
                Request request = Request.newUploadPhotoRequest(Session.getActiveSession(), bitmap, new com.facebook.Request.Callback() {

                    final _cls1 this$1;

                    public void onCompleted(Response response)
                    {
                        getActivity().setProgressBarIndeterminateVisibility(false);
                        if (response.getError() == null)
                        {
                            toastTextMsg.setTextColor(getResources().getColor(0x7f050031));
                            toastTextMsg.setText(getString(0x7f090075));
                            toast.show();
                        } else
                        {
                            toastTextMsg.setTextColor(getResources().getColor(0x7f05001f));
                            toastTextMsg.setText(getString(0x7f090074));
                            toast.show();
                            if (response.getError().getErrorCode() == 200)
                            {
                                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                                builder.setMessage(getString(0x7f090063));
                                builder.setPositiveButton(getString(0x7f090070), new android.content.DialogInterface.OnClickListener() {

                                    final _cls1 this$2;

                                    public void onClick(DialogInterface dialoginterface, int i)
                                    {
                                        dialoginterface.dismiss();
                                        toastTextMsg.setTextColor(getResources().getColor(0x7f050031));
                                        toastTextMsg.setText(getString(0x7f090064));
                                        toast.show();
                                    }

            
            {
                this$2 = _cls1.this;
                super();
            }
                                });
                                builder.show();
                                return;
                            }
                        }
                    }

            
            {
                this$1 = _cls1.this;
                super();
            }
                });
                request.getParameters().putString("message", "Internet speed test");
                request.executeAsync();
            }

            
            {
                this$0 = SpeedometerFragment.this;
                super();
            }
        });
        speedometerView = (SpeedometerView)view.findViewById(0x7f06002c);
        start.setOnClickListener(new android.view.View.OnClickListener() {

            final SpeedometerFragment this$0;

            public void onClick(View view1)
            {
                maxDownloadSpeed = (float)((8L * (TrafficStats.getTotalRxBytes() - mStartRX)) / 1024L / (beforeTime / 1000L));
                maxUploadSpeed = (float)((8L * (TrafficStats.getTotalRxBytes() - mStartTX)) / 1024L / (beforeTime / 1000L));
                if (isNetworkOnline())
                {
                    start.setEnabled(false);
                    isDownload = true;
                    getActivity().setProgressBarIndeterminateVisibility(true);
                    isUserMode = true;
                    isOnlineMode = false;
                    speedometerView.setDownloadMode(true);
                    speedometerView.setUploadMode(false);
                    testHandler.postDelayed(testRunnable, 1000L);
                    for (int i = 0; i < 6; i++)
                    {
                        sendRequestTasks[i] = new SendRequestTask();
                        sendRequestTasks[i].execute(new String[0]);
                    }

                    (new CountDownTimer(15000L, 1000L) {

                        final _cls2 this$1;

                        public void onFinish()
                        {
                        }

                        public void onTick(long l)
                        {
                            if (l < 2000L)
                            {
                                cancel();
                                isDownload = false;
                                speedometerView.calculateAngleOfDeviation(0);
                                speedometerView.setDownloadMode(false);
                                speedometerView.setUploadMode(true);
                                isUserMode = true;
                                isOnlineMode = false;
                                speedometerView.setDownloadMaxSpeed(maxDownloadSpeed);
                                Runnable runnable = new Runnable() {

                                    final _cls1 this$2;

                                    public void run()
                                    {
                                        byte abyte0[];
                                        (new CountDownTimer(15000L, 1000L) {

                                            final _cls1 this$3;

                                            public void onFinish()
                                            {
                                            }

                                            public void onTick(long l)
                                            {
                                                if (l >= 2000L) goto _L2; else goto _L1
_L1:
                                                cancel();
                                                speedometerView.calculateAngleOfDeviation(0);
                                                speedometerView.setUploadMode(true);
                                                speedometerView.setUploadMaxSpeed(maxUploadSpeed);
                                                isUserMode = false;
                                                isOnlineMode = false;
                                                toastTextMsg.setTextColor(getResources().getColor(0x7f05001a));
                                                toastTextMsg.setText(getString(0x7f09007d));
                                                toast.show();
                                                History history = (History)HelperFactory.getHelper().getHistoryDao().queryForId(Long.valueOf(currentCountHistory));
                                                if (history != null)
                                                {
                                                    break MISSING_BLOCK_LABEL_266;
                                                }
                                                history = new History();
                                                history.setDate(date);
                                                history.setDownloadSpeed((new StringBuilder()).append(maxDownloadSpeed).append("").toString());
                                                history.setUploadSpeed((new StringBuilder()).append(maxUploadSpeed).append("").toString());
                                                if (USpeedApplication.getPreferences().getLocation().length() >= 3)
                                                {
                                                    break MISSING_BLOCK_LABEL_477;
                                                }
                                                history.setLocation(USpeedApplication.getContext().getResources().getString(0x7f090082));
_L3:
                                                String s;
                                                if (isMobileConnection())
                                                {
                                                    s = "mobile";
                                                } else
                                                {
                                                    s = "wifi";
                                                }
                                                try
                                                {
                                                    history.setNetworkType(s);
                                                    HelperFactory.getHelper().getHistoryDao().createOrUpdate(history);
                                                }
                                                catch (SQLException sqlexception)
                                                {
                                                    sqlexception.printStackTrace();
                                                }
                                                getActivity().setProgressBarIndeterminateVisibility(false);
                                                start.setEnabled(true);
_L2:
                                                return;
                                                history.setLocation(USpeedApplication.getPreferences().getLocation());
                                                  goto _L3
                                            }

            
            {
                this$3 = _cls1.this;
                super(l, l1);
            }
                                        }).start();
                                        user = new ParseObject("Audio");
                                        abyte0 = (new ByteArrayOutputStream()).toByteArray();
                                        InputStream inputstream;
                                        FileOutputStream fileoutputstream;
                                        inputstream = getActivity().getAssets().open("parse_test_upload.jpg");
                                        audio = new File(getActivity().getCacheDir(), "cacheFileAppeal.srl");
                                        fileoutputstream = new FileOutputStream(audio);
                                        byte abyte1[] = new byte[1024];
_L3:
                                        int i = inputstream.read(abyte1);
                                        if (i == -1) goto _L2; else goto _L1
_L1:
                                        fileoutputstream.write(abyte1, 0, i);
                                          goto _L3
                                        Exception exception;
                                        exception;
                                        fileoutputstream.close();
                                        throw exception;
                                        Exception exception2;
                                        exception2;
                                        exception2.printStackTrace();
                                        ParseFile parsefile;
                                        ParseFile parsefile1;
                                        Exception exception1;
                                        try
                                        {
                                            inputstream.close();
                                        }
                                        catch (FileNotFoundException filenotfoundexception)
                                        {
                                            filenotfoundexception.printStackTrace();
                                        }
                                        catch (IOException ioexception)
                                        {
                                            ioexception.printStackTrace();
                                        }
                                        if (!TextUtils.isEmpty(meUser.getName()))
                                        {
                                            user.put("name", meUser.getName());
                                            user.put("password", meUser.getFacebookId());
                                            user.put("username", meUser.getSurname());
                                            user.put("email", meUser.getEmail());
                                        } else
                                        {
                                            user.put("name", "user");
                                            user.put("password", "user");
                                            user.put("username", "user");
                                            user.put("email", "user@user.com");
                                        }
                                        if (abyte0 == null) goto _L5; else goto _L4
_L4:
                                        parsefile = new ParseFile("parse_test_upload.jpg", FileUtils.readFileToByteArray(audio));
                                        parsefile1 = parsefile;
_L7:
                                        parsefile1.saveInBackground();
                                        user.put("file", parsefile1);
_L5:
                                        user.saveInBackground(new SaveCallback() {

                                            final _cls1 this$3;

                                            public void done(ParseException parseexception)
                                            {
                                            }

            
            {
                this$3 = _cls1.this;
                super();
            }
                                        });
                                        return;
_L2:
                                        fileoutputstream.flush();
                                        fileoutputstream.close();
                                        inputstream.close();
                                        break MISSING_BLOCK_LABEL_193;
                                        exception1;
                                        inputstream.close();
                                        throw exception1;
                                        IOException ioexception1;
                                        ioexception1;
                                        ioexception1.printStackTrace();
                                        parsefile1 = null;
                                        if (true) goto _L7; else goto _L6
_L6:
                                    }

            
            {
                this$2 = _cls1.this;
                super();
            }
                                };
                                (new Handler()).postDelayed(runnable, 3300L);
                            }
                        }

            
            {
                this$1 = _cls2.this;
                super(l, l1);
            }
                    }).start();
                    return;
                } else
                {
                    toastTextMsg.setTextColor(getResources().getColor(0x7f05001f));
                    toastTextMsg.setText(getString(0x7f09006e));
                    toast.show();
                    return;
                }
            }

            
            {
                this$0 = SpeedometerFragment.this;
                super();
            }
        });
        return view;
    }

    public void onDetach()
    {
        super.onDetach();
    }

    public void onPause()
    {
        super.onPause();
        getActivity().unregisterReceiver(speedometerBroadcast);
        for (int i = 0; i < 6; i++)
        {
            if (sendRequestTasks[i] != null)
            {
                sendRequestTasks[i].cancel(true);
            }
        }

    }

    public void onResume()
    {
        super.onResume();
        IntentFilter intentfilter = new IntentFilter("speedometerMode");
        getActivity().registerReceiver(speedometerBroadcast, intentfilter);
        if (!isNetworkOnline())
        {
            toastTextMsg.setTextColor(getResources().getColor(0x7f05001f));
            toastTextMsg.setText(getString(0x7f090066));
            toast.show();
            SpeedometerView.isInternetConnection = null;
        }
        try
        {
            currentCountHistory = 1L + HelperFactory.getHelper().getHistoryDao().countOf();
            return;
        }
        catch (SQLException sqlexception)
        {
            sqlexception.printStackTrace();
        }
    }





/*
    static boolean access$1002(SpeedometerFragment speedometerfragment, boolean flag)
    {
        speedometerfragment.isOnlineMode = flag;
        return flag;
    }

*/








/*
    static ParseObject access$1702(SpeedometerFragment speedometerfragment, ParseObject parseobject)
    {
        speedometerfragment.user = parseobject;
        return parseobject;
    }

*/



/*
    static File access$1802(SpeedometerFragment speedometerfragment, File file)
    {
        speedometerfragment.audio = file;
        return file;
    }

*/





/*
    static float access$202(SpeedometerFragment speedometerfragment, float f)
    {
        speedometerfragment.maxDownloadSpeed = f;
        return f;
    }

*/







/*
    static float access$502(SpeedometerFragment speedometerfragment, float f)
    {
        speedometerfragment.maxUploadSpeed = f;
        return f;
    }

*/





/*
    static boolean access$802(SpeedometerFragment speedometerfragment, boolean flag)
    {
        speedometerfragment.isDownload = flag;
        return flag;
    }

*/



/*
    static boolean access$902(SpeedometerFragment speedometerfragment, boolean flag)
    {
        speedometerfragment.isUserMode = flag;
        return flag;
    }

*/
}
