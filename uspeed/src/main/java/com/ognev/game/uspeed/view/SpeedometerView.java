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
import com.ognev.game.uspeed.application.USpeedApplication;

import java.text.DecimalFormat;

public class SpeedometerView extends View
{

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

    public SpeedometerView(Context context1)
    {
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

    public SpeedometerView(Context context1, AttributeSet attributeset)
    {
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

    public SpeedometerView(Context context1, AttributeSet attributeset, int i)
    {
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

    public static Bitmap RotateBitmap(Bitmap bitmap, float f)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(f);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static float convertDpToPixel(float f, Context context1)
    {
        return f * ((float)context1.getResources().getDisplayMetrics().densityDpi / 160F);
    }

    public static float convertPixelsToDp(float f)
    {
        return f / ((float) USpeedApplication.getContext().getResources().getDisplayMetrics().densityDpi / 160F);
    }

    private void drawNeedle(Canvas canvas)
    {
        float f = 20F + 0.35F * (float)speedo_meterInActive.getWidth();
        float f1 = angle_of_deviation;
        canvas.drawLine((float)((double)(speedo_meterInActive.getWidth() / 2 + (canvas.getWidth() / 2 - speedo_meterInActive.getWidth() / 2)) + 0.5D * (Math.cos(3.1415926535897931D * (double)((180F - f1) / 180F)) * (double)center_wheel.getWidth())), (float)((double)(speedo_meterInActive.getHeight() / 2 + (canvas.getHeight() / 2 - speedo_meterInActive.getHeight() / 2)) - 0.5D * (Math.sin(3.1415926535897931D * (double)(f1 / 180F)) * (double)center_wheel.getWidth())), (float)((double)(-40 + (speedo_meterInActive.getWidth() / 2 + (canvas.getWidth() / 2 - speedo_meterInActive.getWidth() / 2))) + Math.cos(3.1415926535897931D * (double)((180F - f1) / 180F)) * (double)f), (float)((double)(speedo_meterInActive.getHeight() / 2 + (canvas.getHeight() / 2 - speedo_meterInActive.getHeight() / 2)) - Math.sin(3.1415926535897931D * (double)(f1 / 180F)) * (double)f), paint_needle);
        canvas.drawArc(new RectF(), angle_of_deviation, angle_of_deviation, true, paint_needle);
    }

    private void initializeView(Context context1)
    {
        context = context1;
        speedo_obj = this;
        setLayoutParams(new android.view.ViewGroup.LayoutParams(-2, -2));
        releaseImageResources();
        speedo_meterInActive = getImage(0x7f020088);
        speedo_meterMobile = getImage(0x7f020089);
        speedo_meterWifi = getImage(0x7f02008b);
        center_wheel = getImage(0x7f02008a);
        counter_text = getImage(0x7f020050);
        needle = getImage(0x7f02008a);
        downloadCircle = getImage(0x7f020052);
        uploadCircle = getImage(0x7f020091);
        paint_needle = new Paint();
        paint_needle.setStyle(android.graphics.Paint.Style.FILL_AND_STROKE);
        paint_needle.setAntiAlias(true);
        paint_needle.setColor(0xffff0000);
        paint_needle.setStrokeWidth(6F);
        downloadTextSpeedPaint = new Paint();
        downloadTextSpeedPaint.setColor(getResources().getColor(0x7f050022));
        downloadTextSpeedPaint.setAntiAlias(true);
        downloadTextSpeedPaint.setStyle(android.graphics.Paint.Style.FILL_AND_STROKE);
        downloadTextSpeedPaint.setTextSize(getResources().getDimension(0x7f07001f));
        downloadTextPaint = new Paint();
        downloadTextPaint.setColor(getResources().getColor(0x7f050021));
        downloadTextPaint.setAntiAlias(true);
        downloadTextPaint.setStyle(android.graphics.Paint.Style.FILL_AND_STROKE);
        downloadTextPaint.setTextSize(getResources().getDimension(0x7f07001f));
        uploadTextPaint = new Paint();
        uploadTextPaint.setColor(getResources().getColor(0x7f05001f));
        uploadTextPaint.setAntiAlias(true);
        uploadTextPaint.setStyle(android.graphics.Paint.Style.FILL_AND_STROKE);
        uploadTextPaint.setTextSize(getResources().getDimension(0x7f070021));
    }

    public void calculateAngleOfDeviation(int i)
    {
        float f = i;
        if (i >= 1024)
        {
            maxValue = 6000F;
            if (isUploadMode())
            {
                uploadMb = true;
            }
            if (isDownloadMode())
            {
                downloadMb = true;
            }
            int j = i / 100;
            minAngle = 90F;
            exact_angle = j;
            angle_of_deviation = (((maxAngle + minAngle) - 130F) * exact_angle) / maxValue + minAngle;
            speedo_obj.invalidate();
            return;
        }
        if (i < 1024)
        {
            maxValue = 2500F;
        } else
        {
            maxValue = 6000F;
        }
        exact_angle = i;
        angle_of_deviation = (((maxAngle + minAngle) - 130F) * exact_angle) / maxValue + minAngle;
        if (isDownloadMode())
        {
            download_angle = f;
        } else
        {
            upload_angle = f;
        }
        speedo_obj.invalidate();
    }

    public Bitmap getImage(int i)
    {
        return BitmapFactory.decodeResource(getResources(), i, options);
    }

    public boolean isDownloadMode()
    {
        return downloadMode;
    }

    public boolean isUploadMode()
    {
        return uploadMode;
    }

    protected void onDraw(Canvas canvas)
    {
        if (isInternetConnection == null)
        {
            canvas.drawBitmap(speedo_meterInActive, canvas.getWidth() / 2 - speedo_meterInActive.getWidth() / 2, canvas.getHeight() / 2 - speedo_meterInActive.getHeight() / 2, null);
        }
        StringBuilder stringbuilder;
        DecimalFormat decimalformat;
        double d;
        StringBuilder stringbuilder1;
        String s;
        StringBuilder stringbuilder2;
        DecimalFormat decimalformat1;
        double d1;
        StringBuilder stringbuilder3;
        String s1;
        if (isInternetConnection != null && isInternetConnection.booleanValue())
        {
            canvas.drawBitmap(speedo_meterMobile, canvas.getWidth() / 2 - speedo_meterMobile.getWidth() / 2, canvas.getHeight() / 2 - speedo_meterMobile.getHeight() / 2, null);
        } else
        if (isInternetConnection != null && !isInternetConnection.booleanValue())
        {
            canvas.drawBitmap(speedo_meterWifi, canvas.getWidth() / 2 - speedo_meterWifi.getWidth() / 2, canvas.getHeight() / 2 - speedo_meterWifi.getHeight() / 2, null);
        }
        drawNeedle(canvas);
        canvas.drawBitmap(center_wheel, canvas.getWidth() / 2 - center_wheel.getWidth() / 2, canvas.getHeight() / 2 - center_wheel.getHeight() / 2, null);
        canvas.drawBitmap(downloadCircle, canvas.getWidth() / 2 - speedo_meterInActive.getWidth() / 2, (int)((double)(canvas.getHeight() / 2) - (double)speedo_meterInActive.getHeight() / 1.3D), null);
        canvas.drawBitmap(uploadCircle, 50 + ((canvas.getWidth() / 2 - speedo_meterInActive.getWidth() / 2) + speedo_meterInActive.getWidth() / 2), (int)((double)(canvas.getHeight() / 2) - (double)speedo_meterInActive.getHeight() / 1.3999999999999999D), null);
        stringbuilder = new StringBuilder();
        decimalformat = decimalFormat;
        if (isDownloadMode())
        {
            d = download_angle;
        } else
        {
            d = downloadMaxSpeed;
        }
        stringbuilder1 = stringbuilder.append(decimalformat.format(d)).append(" ");
        if (downloadMb)
        {
            s = "Mb/s";
        } else
        {
            s = "Kb/s";
        }
        downloadSpeedText = stringbuilder1.append(s).toString();
        stringbuilder2 = new StringBuilder();
        decimalformat1 = decimalFormat;
        if (isUploadMode())
        {
            d1 = upload_angle;
        } else
        {
            d1 = uploadMaxSpeed;
        }
        stringbuilder3 = stringbuilder2.append(decimalformat1.format(d1)).append(" ");
        if (uploadMb)
        {
            s1 = "Mb/s";
        } else
        {
            s1 = "Kb/s";
        }
        uploadSpeedText = stringbuilder3.append(s1).toString();
        canvas.drawText(downloadSpeedText, (int)((double)(canvas.getWidth() / 2 - speedo_meterInActive.getWidth() / 2) + (double)downloadCircle.getWidth() / 2.6000000000000001D), (int)((double)(canvas.getHeight() / 2) - (double)speedo_meterInActive.getHeight() / 1.3999999999999999D) + downloadCircle.getHeight() / 2, downloadTextPaint);
        canvas.drawText(uploadSpeedText, 45 + ((canvas.getWidth() / 2 - speedo_meterInActive.getWidth() / 2) + speedo_meterInActive.getWidth() / 2) + downloadCircle.getWidth() / 3, (int)((double)(canvas.getHeight() / 2) - (double)speedo_meterInActive.getHeight() / 1.3999999999999999D) + downloadCircle.getHeight() / 2, uploadTextPaint);
        canvas.drawText(getResources().getString(0x7f090061), (canvas.getWidth() / 2 - speedo_meterInActive.getWidth() / 2) + downloadCircle.getWidth() / 6, (int)((double)(canvas.getHeight() / 2) - (double)speedo_meterInActive.getHeight() / 1.3999999999999999D) + downloadCircle.getHeight() / 3, downloadTextSpeedPaint);
        canvas.drawText(getResources().getString(0x7f090083), 50 + ((canvas.getWidth() / 2 - speedo_meterInActive.getWidth() / 2) + speedo_meterInActive.getWidth() / 2) + downloadCircle.getWidth() / 6, (int)((double)(canvas.getHeight() / 2) - (double)speedo_meterInActive.getHeight() / 1.3999999999999999D) + downloadCircle.getHeight() / 3, downloadTextSpeedPaint);
    }

    public void releaseImageResources()
    {
        if (speedo_meterInActive != null)
        {
            speedo_meterInActive = null;
        }
        if (needle != null)
        {
            needle = null;
        }
        if (center_wheel != null)
        {
            center_wheel = null;
        }
    }

    public void setDownloadMaxSpeed(float f)
    {
        downloadMaxSpeed = f;
    }

    public void setDownloadMode(boolean flag)
    {
        downloadMode = flag;
    }

    public void setUploadMaxSpeed(float f)
    {
        uploadMaxSpeed = f;
    }

    public void setUploadMode(boolean flag)
    {
        uploadMode = flag;
    }

    static 
    {
        minAngle = 330F;
        angle_of_deviation = minAngle;
    }
}
