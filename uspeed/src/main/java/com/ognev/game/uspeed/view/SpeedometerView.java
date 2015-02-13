// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.ognev.game.uspeed.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.ognev.game.uspeed.R;
import com.ognev.game.uspeed.application.USpeedApplication;

import java.text.DecimalFormat;

public class SpeedometerView extends View {

    public static float angle_of_deviation;
    public static float download_angle = 0.0F;
    public static float exact_angle = 0.0F;
    public static Boolean isInternetConnection = null;
    public static float maxAngle = 212F;
    public static float maxValue = 6000F;
    public static float minAngle;
    public static float upload_angle = 0.0F;
    Bitmap center_wheel;
    Context context;
    Bitmap counter_text;
    private DecimalFormat decimalFormat;
    Bitmap downloadCircle;
    private float downloadMaxSpeed;
    private boolean downloadMb;
    private boolean downloadMode;
    private String downloadSpeedText;
    Paint downloadTextPaint;
    Paint downloadTextSpeedPaint;
    int height;
    Matrix matrix_needle;
    Bitmap needle;
    android.graphics.BitmapFactory.Options options;
    Paint paint_needle;
    Bitmap speedo_meterInActive;
    Bitmap speedo_meterMobile;
    Bitmap speedo_meterWifi;
    SpeedometerView speedo_obj;
    Bitmap uploadCircle;
    private float uploadMaxSpeed;
    private boolean uploadMb;
    private boolean uploadMode;
    private String uploadSpeedText;
    Paint uploadTextPaint;
    Paint uploadTextSpeedPaint;
    int width;
    private boolean online;
    private String downloadPostfix;
    private String uploadPostfix;

    public SpeedometerView(Context context1) {
        super(context1);
        needle = null;
        speedo_meterInActive = null;
        speedo_meterMobile = null;
        speedo_meterWifi = null;
        center_wheel = null;
        counter_text = null;
        downloadCircle = null;
        uploadCircle = null;
        decimalFormat = new DecimalFormat("#.#");
        options = new android.graphics.BitmapFactory.Options();
        initializeView(context1);
    }

    public SpeedometerView(Context context1, AttributeSet attributeset) {
        super(context1, attributeset);
        needle = null;
        speedo_meterInActive = null;
        speedo_meterMobile = null;
        speedo_meterWifi = null;
        center_wheel = null;
        counter_text = null;
        downloadCircle = null;
        uploadCircle = null;
        decimalFormat = new DecimalFormat("#.#");
        options = new android.graphics.BitmapFactory.Options();
        initializeView(context1);
    }

    public SpeedometerView(Context context1, AttributeSet attributeset, int i) {
        super(context1, attributeset, i);
        needle = null;
        speedo_meterInActive = null;
        speedo_meterMobile = null;
        speedo_meterWifi = null;
        center_wheel = null;
        counter_text = null;
        downloadCircle = null;
        uploadCircle = null;
        decimalFormat = new DecimalFormat("#.#");
        options = new android.graphics.BitmapFactory.Options();
        initializeView(context1);
    }

    public static Bitmap RotateBitmap(Bitmap bitmap, float f) {
        Matrix matrix = new Matrix();
        matrix.postRotate(f);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static float convertDpToPixel(float f, Context context1) {
        return f * ((float) context1.getResources().getDisplayMetrics().densityDpi / 160F);
    }

    public static float convertPixelsToDp(float f) {
        return f / ((float) USpeedApplication.getContext().getResources().getDisplayMetrics().densityDpi / 160F);
    }

    private void drawNeedle(Canvas canvas) {
        float f = 20F + 0.35F * (float) speedo_meterInActive.getWidth();
        float f1 = angle_of_deviation;
        canvas.drawLine((float) ((double) (speedo_meterInActive.getWidth() / 2 + (canvas.getWidth() / 2 - speedo_meterInActive.getWidth() / 2)) + 0.5D * (Math.cos(3.1415926535897931D * (double) ((180F - f1) / 180F)) * (double) center_wheel.getWidth())), (float) ((double) (speedo_meterInActive.getHeight() / 2 + (canvas.getHeight() / 2 - speedo_meterInActive.getHeight() / 2)) - 0.5D * (Math.sin(3.1415926535897931D * (double) (f1 / 180F)) * (double) center_wheel.getWidth())), (float) ((double) (-40 + (speedo_meterInActive.getWidth() / 2 + (canvas.getWidth() / 2 - speedo_meterInActive.getWidth() / 2))) + Math.cos(3.1415926535897931D * (double) ((180F - f1) / 180F)) * (double) f), (float) ((double) (speedo_meterInActive.getHeight() / 2 + (canvas.getHeight() / 2 - speedo_meterInActive.getHeight() / 2)) - Math.sin(3.1415926535897931D * (double) (f1 / 180F)) * (double) f), paint_needle);
        canvas.drawArc(new RectF(), angle_of_deviation, angle_of_deviation, true, paint_needle);
    }

    private void initializeView(Context context1) {
        context = context1;
        speedo_obj = this;
        setLayoutParams(new android.view.ViewGroup.LayoutParams(-2, -2));
        releaseImageResources();
        speedo_meterInActive = getImage(R.drawable.speedometer_inactive);
        speedo_meterMobile = getImage(R.drawable.speedometer_mobile);
        speedo_meterWifi = getImage(R.drawable.speedometer_wifi);
        center_wheel = getImage(R.drawable.speedometer_wheel);
        counter_text = getImage(R.drawable.counter_text_gradient);
        needle = getImage(R.drawable.speedometer1);
        downloadCircle = getImage(R.drawable.download_circle);
        uploadCircle = getImage(R.drawable.upload_circle);
        paint_needle = new Paint();
        paint_needle.setStyle(android.graphics.Paint.Style.FILL_AND_STROKE);
        paint_needle.setAntiAlias(true);
        paint_needle.setColor(getResources().getColor(R.color.red));
        paint_needle.setStrokeWidth(6F);
        downloadTextSpeedPaint = new Paint();
        downloadTextSpeedPaint.setColor(getResources().getColor(R.color.black));
        downloadTextSpeedPaint.setAntiAlias(true);
        downloadTextSpeedPaint.setStyle(android.graphics.Paint.Style.FILL_AND_STROKE);
        downloadTextSpeedPaint.setTextSize(getResources().getDimension(R.dimen.activity_horizontal_margin));
        downloadTextPaint = new Paint();
        downloadTextPaint.setColor(getResources().getColor(R.color.green));
        downloadTextPaint.setAntiAlias(true);
        downloadTextPaint.setStyle(android.graphics.Paint.Style.FILL_AND_STROKE);
        downloadTextPaint.setTextSize(getResources().getDimension(R.dimen.activity_horizontal_margin));
        uploadTextPaint = new Paint();
        uploadTextPaint.setColor(getResources().getColor(R.color.red));
        uploadTextPaint.setAntiAlias(true);
        uploadTextPaint.setStyle(android.graphics.Paint.Style.FILL_AND_STROKE);
        uploadTextPaint.setTextSize(getResources().getDimension(R.dimen.activity_horizontal_margin));
    }


    public void calculateAngleOfDeviation(int i) {
        float f = i;
        if (i > 1000) {
            maxValue = 6000F;
            if (isUploadMode()) {
                uploadMb = true;
            }
            if (isDownloadMode()) {
                downloadMb = true;
            }
            int j = i / 100;
            minAngle = 90F;
            exact_angle = j;
            angle_of_deviation = (((maxAngle + minAngle) - 130F) * exact_angle) / maxValue + minAngle;
            if (isDownloadMode()) {
                download_angle = f;
            } else {
                upload_angle = f;
            }
            speedo_obj.invalidate();
            return;
        }
        if (i < 1024) {
            maxValue = 2500F;
        } else {
            minAngle = 0;
        }
        exact_angle = i;
        angle_of_deviation = (((maxAngle + minAngle) - 130F) * exact_angle) / maxValue + minAngle;
        if (isDownloadMode()) {
            download_angle = f;
        } else {
            upload_angle = f;
        }
        speedo_obj.invalidate();
    }

    public Bitmap getImage(int i) {
        return BitmapFactory.decodeResource(getResources(), i, options);
    }

    public boolean isDownloadMode() {
        return downloadMode;
    }

    public boolean isUploadMode() {
        return uploadMode;
    }

    protected void onDraw(Canvas canvas) {
        if (isInternetConnection == null) {
            canvas.drawBitmap(speedo_meterInActive, canvas.getWidth() / 2 - speedo_meterInActive.getWidth() / 2, canvas.getHeight() / 2 - speedo_meterInActive.getHeight() / 2, null);
        }
        StringBuilder stringbuilder;
        DecimalFormat decimalformat;
        double d;
        StringBuilder stringbuilder1;
        StringBuilder stringbuilder2;
        DecimalFormat decimalformat1;
        double d1;
        StringBuilder stringbuilder3;
        if (isInternetConnection != null && isInternetConnection.booleanValue()) {
            canvas.drawBitmap(speedo_meterMobile, canvas.getWidth() / 2 - speedo_meterMobile.getWidth() / 2, canvas.getHeight() / 2 - speedo_meterMobile.getHeight() / 2, null);
        } else if (isInternetConnection != null && !isInternetConnection.booleanValue()) {
            canvas.drawBitmap(speedo_meterWifi, canvas.getWidth() / 2 - speedo_meterWifi.getWidth() / 2, canvas.getHeight() / 2 - speedo_meterWifi.getHeight() / 2, null);
        }
        drawNeedle(canvas);
        canvas.drawBitmap(center_wheel, canvas.getWidth() / 2 - center_wheel.getWidth() / 2, canvas.getHeight() / 2 - center_wheel.getHeight() / 2, null);
        canvas.drawBitmap(downloadCircle, canvas.getWidth() / 2 - speedo_meterInActive.getWidth() / 2, (int) ((double) (canvas.getHeight() / 2) - (double) speedo_meterInActive.getHeight() / 1.3D), null);
        canvas.drawBitmap(uploadCircle, 50 + ((canvas.getWidth() / 2 - speedo_meterInActive.getWidth() / 2) + speedo_meterInActive.getWidth() / 2), (int) ((double) (canvas.getHeight() / 2) - (double) speedo_meterInActive.getHeight() / 1.3999999999999999D), null);
        stringbuilder = new StringBuilder();
        decimalformat = decimalFormat;
        if (isDownloadMode() || isOnline()) {
            d = download_angle;
        } else {
            d = downloadMaxSpeed;
        }
        stringbuilder1 = stringbuilder.append(decimalformat.format(d)).append(" ");
        if (downloadMb) {
            downloadPostfix = "Mb/s";
        } else {
            downloadPostfix = "Kb/s";
        }
        downloadSpeedText = stringbuilder1.append(downloadPostfix).toString();
        stringbuilder2 = new StringBuilder();
        decimalformat1 = decimalFormat;
        if (isUploadMode() || isOnline()) {
            d1 = upload_angle;
        } else {
            d1 = uploadMaxSpeed;
        }
        stringbuilder3 = stringbuilder2.append(decimalformat1.format(d1)).append(" ");
        if (uploadMb) {
            uploadPostfix = "Mb/s";
        } else {
            uploadPostfix = "Kb/s";
        }
        uploadSpeedText = stringbuilder3.append(uploadPostfix).toString();
        canvas.drawText(downloadSpeedText, (int) ((double) (canvas.getWidth() / 2 - speedo_meterInActive.getWidth() / 2) + (double) downloadCircle.getWidth() / 2.6000000000000001D), (int) ((double) (canvas.getHeight() / 2) - (double) speedo_meterInActive.getHeight() / 1.3999999999999999D) + downloadCircle.getHeight() / 2, downloadTextPaint);
        canvas.drawText(uploadSpeedText, 45 + ((canvas.getWidth() / 2 - speedo_meterInActive.getWidth() / 2) + speedo_meterInActive.getWidth() / 2) + downloadCircle.getWidth() / 3, (int) ((double) (canvas.getHeight() / 2) - (double) speedo_meterInActive.getHeight() / 1.3999999999999999D) + downloadCircle.getHeight() / 2, uploadTextPaint);
        canvas.drawText(getResources().getString(R.string.download), (canvas.getWidth() / 2 - speedo_meterInActive.getWidth() / 2) + downloadCircle.getWidth() / 6, (int) ((double) (canvas.getHeight() / 2) - (double) speedo_meterInActive.getHeight() / 1.3999999999999999D) + downloadCircle.getHeight() / 3, downloadTextSpeedPaint);
        canvas.drawText(getResources().getString(R.string.upload), 50 + ((canvas.getWidth() / 2 - speedo_meterInActive.getWidth() / 2) + speedo_meterInActive.getWidth() / 2) + downloadCircle.getWidth() / 6, (int) ((double) (canvas.getHeight() / 2) - (double) speedo_meterInActive.getHeight() / 1.3999999999999999D) + downloadCircle.getHeight() / 3, downloadTextSpeedPaint);
    }

    public String getDownloadSpeedText() {
        return downloadSpeedText;
    }

    public String getUploadSpeedText() {
        return uploadSpeedText;
    }

    public void releaseImageResources() {
        if (speedo_meterInActive != null) {
            speedo_meterInActive = null;
        }
        if (needle != null) {
            needle = null;
        }
        if (center_wheel != null) {
            center_wheel = null;
        }
    }

    public void setDownloadMaxSpeed(float f) {
        downloadMaxSpeed = f;
    }

    public void setDownloadMode(boolean flag) {
        downloadMode = flag;
    }

    public void setUploadMaxSpeed(float f) {
        uploadMaxSpeed = f;
    }

    public void setUploadMode(boolean flag) {
        uploadMode = flag;
    }

    static {
        minAngle = 330F;
        angle_of_deviation = minAngle;
    }
    public void invalidateSpeedometer() {
        maxValue = 2800F;
        minAngle = 330;
        exact_angle = 0;
        angle_of_deviation = (((maxAngle + minAngle) - 130F) * exact_angle) / maxValue + minAngle;
        speedo_obj.invalidate();
    }

    public void calculateDownload(int i) {
        float f = i;
        if (i > 1000) {
            maxValue = 5000F;
            if (isUploadMode() || isOnline()) {
                uploadMb = true;
            }
            if (isDownloadMode()||isOnline()) {
                downloadMb = true;
            }
            float j = i / 1024F;
            minAngle = 80F;
            exact_angle = i;
            angle_of_deviation = (((maxAngle + minAngle) - 130F) * exact_angle) / maxValue + minAngle;
            download_angle = j;

            speedo_obj.invalidate();
            return;
        }
        if (i < 1024) {
            maxValue = 2800F;
            minAngle = 330;
        }else  {
            minAngle = 330;
        }
        exact_angle = i;
        angle_of_deviation = (((maxAngle + minAngle) - 130F) * exact_angle) / maxValue + minAngle;
//        if (isDownloadMode()) {
        download_angle = f;
//        } else {
//            upload_angle = f;
//        }
        speedo_obj.invalidate();
    }

    public void calculateUpload(int i) {
        float f = i;
        if (i > 1000) {
            maxValue = 5000F;
            if (isUploadMode() ||isOnline()) {
                uploadMb = true;
            }
            if (isDownloadMode()|| isOnline()) {
                downloadMb = true;
            }
            float j = i / 1024F;
            minAngle = 80F;
            exact_angle = i/100;
            if(!isOnline())
                angle_of_deviation = (((maxAngle + minAngle) - 130F) * exact_angle) / maxValue + minAngle;
            upload_angle = j;
            speedo_obj.invalidate();
            return;
        }
        if (i < 1024) {
            maxValue = 2800F;
            minAngle = 330;
        } else {
            minAngle = 330;
        }
        exact_angle = i;
        if(!isOnline() && isUploadMode()) {
            angle_of_deviation = (((maxAngle + minAngle) - 130F) * exact_angle) / maxValue + minAngle;
            upload_angle = f;
        }
//        if (isDownloadMode()) {
//            download_angle = f;
//        } else {
//        }
        speedo_obj.invalidate();
    }

//    public void calculateDownload(int i) {
//        if (i > 1000) {
//            maxValue = 5000F;
//            if (isUploadMode() || isOnline()) {
//                uploadMb = true;
//            }
//            if (isDownloadMode()||isOnline()) {
//                downloadMb = true;
//            }
//            float j = i / 1024F;
//            minAngle = 2800F;
//            exact_angle = i;
//            angle_of_deviation = (((maxAngle + minAngle) - 130F) * exact_angle) / maxValue + minAngle;
//            download_angle = j;
//
//            speedo_obj.invalidate();
//            return;
//        }
////        if (i < 1024) {
////            maxValue = 2800F;
////            minAngle = 330;
////        }else  {
////            maxValue = 2800F;
////            minAngle = 330;
////        }
//        exact_angle = i;
//        angle_of_deviation = (((maxAngle + minAngle) - 130F) * exact_angle) / maxValue + minAngle;
//        download_angle = i;
//        speedo_obj.invalidate();
//    }
//
//    public void calculateUpload(int i) {
//        float f = i;
//        if (i > 1000) {
//            maxValue = 5000F;
//            if (isUploadMode() ||isOnline()) {
//                uploadMb = true;
//            }
//            if (isDownloadMode()|| isOnline()) {
//                downloadMb = true;
//            }
//            float j = i / 1024F;
//            minAngle = 80F;
//            exact_angle = i/100;
//            if(!isOnline()) {
//                angle_of_deviation = (((maxAngle + minAngle) - 130F) * exact_angle) / maxValue + minAngle;
//                upload_angle = j;
//            }
//            speedo_obj.invalidate();
//            return;
//        }
////        if (i < 1024) {
////            maxValue = 2800F;
////            minAngle = 330;
////        } else {
////            minAngle = 330;
////        }
//        exact_angle = i;
//        if(!isOnline()) {
//            angle_of_deviation = (((maxAngle + minAngle) - 130F) * exact_angle) / maxValue + minAngle;
//            upload_angle = f;
//            speedo_obj.invalidate();
//        }
//    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getDownloadPostfix() {
        return downloadPostfix;
    }

    public String getUploadPostfix() {
        return uploadPostfix;
    }
}
