//// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
//// Jad home page: http://www.geocities.com/kpdus/jad.html
//// Decompiler options: braces fieldsfirst space lnc
//
//package com.ognev.game.uspeed.view;
//
//import android.content.Context;
//import android.content.res.TypedArray;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.Path;
//import android.graphics.RectF;
//import android.graphics.Typeface;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.View;
//
//// Referenced classes of package com.ognev.game.uspeed.view:
////            SpeedChangeListener
//
//public class Speedometer extends View
//    implements SpeedChangeListener
//{
//
//    public static final float DEFAULT_MAX_SPEED = 300F;
//    private static final String TAG = com/ognev/game/uspeed/view/Speedometer.getSimpleName();
//    private int OFF_COLOR;
//    private int ON_COLOR;
//    private float READING_SIZE;
//    private int SCALE_COLOR;
//    private float SCALE_SIZE;
//    private Paint backgroundPaint;
//    private float centerX;
//    private float centerY;
//    private float mCurrentSpeed;
//    private float mMaxSpeed;
//    private Paint offMarkPaint;
//    private Path offPath;
//    private Paint onMarkPaint;
//    private Path onPath;
//    final RectF oval;
//    private float radius;
//    private Paint readingPaint;
//    private Paint scalePaint;
//
//    public Speedometer(Context context)
//    {
//        super(context);
//        oval = new RectF();
//        ON_COLOR = Color.argb(255, 255, 165, 0);
//        OFF_COLOR = Color.argb(255, 62, 62, 62);
//        SCALE_COLOR = Color.argb(255, 255, 255, 255);
//        SCALE_SIZE = 14F;
//        READING_SIZE = 60F;
//        Log.d(TAG, "Speedometer(Context) called");
//        setBackgroundColor(getResources().getColor(0x106000b));
//        setDrawingCacheBackgroundColor(getResources().getColor(0x106000b));
//    }
//
//    public Speedometer(Context context, AttributeSet attributeset)
//    {
//        TypedArray typedarray;
//        super(context, attributeset);
//        oval = new RectF();
//        ON_COLOR = Color.argb(255, 255, 165, 0);
//        OFF_COLOR = Color.argb(255, 62, 62, 62);
//        SCALE_COLOR = Color.argb(255, 255, 255, 255);
//        SCALE_SIZE = 14F;
//        READING_SIZE = 60F;
//        Log.d(TAG, "Speedometer(Context, AttributeSet) called");
//        typedarray = context.getTheme().obtainStyledAttributes(attributeset, com.ognev.game.uspeed.R.styleable.Speedometer, 0, 0);
//        mMaxSpeed = typedarray.getFloat(0, 300F);
//        mCurrentSpeed = typedarray.getFloat(1, 0.0F);
//        ON_COLOR = typedarray.getColor(2, ON_COLOR);
//        OFF_COLOR = typedarray.getColor(3, OFF_COLOR);
//        SCALE_COLOR = typedarray.getColor(4, SCALE_COLOR);
//        SCALE_SIZE = typedarray.getDimension(5, SCALE_SIZE);
//        READING_SIZE = typedarray.getDimension(6, READING_SIZE);
//        typedarray.recycle();
//        initDrawingTools();
//        setBackgroundColor(getResources().getColor(0x106000b));
//        setDrawingCacheBackgroundColor(getResources().getColor(0x106000b));
//        return;
//        Exception exception;
//        exception;
//        typedarray.recycle();
//        throw exception;
//    }
//
//    private int chooseDimension(int i, int j)
//    {
//        if (i == 0x80000000 || i == 0x40000000)
//        {
//            return j;
//        } else
//        {
//            return getPreferredSize();
//        }
//    }
//
//    private void drawLegend(Canvas canvas)
//    {
//        canvas.save(1);
//        canvas.rotate(-180F, centerX, centerY);
//        Path path = new Path();
//        double d = 3.1415926535897931D * (double)radius;
//        for (int i = 0; (float)i < mMaxSpeed; i = (int)(20D + (double)i))
//        {
//            path.addCircle(centerX, centerY, radius, android.graphics.Path.Direction.CW);
//            Object aobj[] = new Object[1];
//            aobj[0] = Integer.valueOf(i);
//            canvas.drawTextOnPath(String.format("%d", aobj), path, (float)((d * (double)i) / (double)mMaxSpeed), -30F, scalePaint);
//        }
//
//        canvas.restore();
//    }
//
//    private void drawReading(Canvas canvas)
//    {
//        Path path = new Path();
//        Object aobj[] = new Object[1];
//        aobj[0] = Integer.valueOf((int)mCurrentSpeed);
//        String s = String.format("%d", aobj);
//        float af[] = new float[s.length()];
//        readingPaint.getTextWidths(s, af);
//        float f = 0.0F;
//        int i = af.length;
//        for (int j = 0; j < i; j++)
//        {
//            f = (float)((double)af[j] + (double)f);
//        }
//
//        Log.d(TAG, (new StringBuilder()).append("advance: ").append(f).toString());
//        path.moveTo(centerX - f / 2.0F, centerY);
//        path.lineTo(centerX + f / 2.0F, centerY);
//        canvas.drawTextOnPath(s, path, 0.0F, 0.0F, readingPaint);
//    }
//
//    private void drawScale(Canvas canvas)
//    {
//        onPath.reset();
//        for (int i = -180; (float)i < 180F * (mCurrentSpeed / mMaxSpeed) - 180F; i += 4)
//        {
//            onPath.addArc(oval, i, 2.0F);
//        }
//
//        canvas.drawPath(onPath, onMarkPaint);
//    }
//
//    private void drawScaleBackground(Canvas canvas)
//    {
//        Log.d(TAG, "drawScaleBackground");
//        offPath.reset();
//        for (int i = -180; i < 0; i += 4)
//        {
//            offPath.addArc(oval, i, 2.0F);
//        }
//
//        canvas.drawPath(offPath, offMarkPaint);
//    }
//
//    private int getPreferredSize()
//    {
//        return 300;
//    }
//
//    private void initDrawingTools()
//    {
//        onMarkPaint = new Paint();
//        onMarkPaint.setStyle(android.graphics.Paint.Style.STROKE);
//        onMarkPaint.setColor(ON_COLOR);
//        onMarkPaint.setStrokeWidth(35F);
//        onMarkPaint.setShadowLayer(5F, 0.0F, 0.0F, ON_COLOR);
//        onMarkPaint.setAntiAlias(true);
//        offMarkPaint = new Paint(onMarkPaint);
//        offMarkPaint.setColor(OFF_COLOR);
//        offMarkPaint.setStyle(android.graphics.Paint.Style.FILL_AND_STROKE);
//        offMarkPaint.setShadowLayer(0.0F, 0.0F, 0.0F, OFF_COLOR);
//        scalePaint = new Paint(offMarkPaint);
//        scalePaint.setStrokeWidth(2.0F);
//        scalePaint.setTextSize(SCALE_SIZE);
//        scalePaint.setShadowLayer(5F, 0.0F, 0.0F, 0xffff0000);
//        scalePaint.setColor(0xff000000);
//        readingPaint = new Paint(scalePaint);
//        readingPaint.setStyle(android.graphics.Paint.Style.FILL_AND_STROKE);
//        offMarkPaint.setShadowLayer(3F, 0.0F, 0.0F, -1);
//        readingPaint.setTextSize(65F);
//        readingPaint.setTypeface(Typeface.SANS_SERIF);
//        readingPaint.setColor(0xff000000);
//        backgroundPaint = new Paint(1);
//        backgroundPaint.setStyle(android.graphics.Paint.Style.FILL);
//        backgroundPaint.setColor(getResources().getColor(0x106000b));
//        onPath = new Path();
//        offPath = new Path();
//    }
//
//    public float getCurrentSpeed()
//    {
//        return mCurrentSpeed;
//    }
//
//    public void onDraw(Canvas canvas)
//    {
//        drawScaleBackground(canvas);
//        drawScale(canvas);
//        drawLegend(canvas);
//        drawReading(canvas);
//    }
//
//    protected void onMeasure(int i, int j)
//    {
//        int k = android.view.View.MeasureSpec.getMode(i);
//        int l = android.view.View.MeasureSpec.getSize(i);
//        int i1 = android.view.View.MeasureSpec.getMode(j);
//        int j1 = android.view.View.MeasureSpec.getSize(j);
//        int k1 = Math.min(chooseDimension(k, l), chooseDimension(i1, j1));
//        centerX = k1 / 2;
//        centerY = k1 / 2;
//        setMeasuredDimension(k1, k1);
//    }
//
//    protected void onSizeChanged(int i, int j, int k, int l)
//    {
//        Log.d(TAG, (new StringBuilder()).append("Size changed to ").append(i).append("x").append(j).toString());
//        if (i > j)
//        {
//            radius = j / 4;
//        } else
//        {
//            radius = i / 4;
//        }
//        oval.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
//    }
//
//    public void onSpeedChanged(float f)
//    {
//        setCurrentSpeed(f);
//        invalidate();
//    }
//
//    public void setCurrentSpeed(float f)
//    {
//        if (f > mMaxSpeed)
//        {
//            mCurrentSpeed = mMaxSpeed;
//            return;
//        }
//        if (f < 0.0F)
//        {
//            mCurrentSpeed = 0.0F;
//            return;
//        } else
//        {
//            mCurrentSpeed = f;
//            return;
//        }
//    }
//
//}
