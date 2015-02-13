//// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
//// Jad home page: http://www.geocities.com/kpdus/jad.html
//// Decompiler options: braces fieldsfirst space lnc
//
//package com.ognev.game.uspeed.view;
//
//import android.animation.TypeEvaluator;
//import android.animation.ValueAnimator;
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.RectF;
//import android.util.AttributeSet;
//import android.view.View;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//public class SpeedometerGauge extends View
//{
//    public static class ColoredRange
//    {
//
//        private double begin;
//        private int color;
//        private double end;
//
//        public double getBegin()
//        {
//            return begin;
//        }
//
//        public int getColor()
//        {
//            return color;
//        }
//
//        public double getEnd()
//        {
//            return end;
//        }
//
//        public void setBegin(double d)
//        {
//            begin = d;
//        }
//
//        public void setColor(int i)
//        {
//            color = i;
//        }
//
//        public void setEnd(double d)
//        {
//            end = d;
//        }
//
//        public ColoredRange(int i, double d, double d1)
//        {
//            color = i;
//            begin = d;
//            end = d1;
//        }
//    }
//
//    public static interface LabelConverter
//    {
//
//        public abstract String getLabelFor(double d, double d1);
//    }
//
//
//    public static final int DEFAULT_LABEL_TEXT_SIZE_DP = 12;
//    public static final double DEFAULT_MAJOR_TICK_STEP = 20D;
//    public static final double DEFAULT_MAX_SPEED = 800D;
//    public static final int DEFAULT_MINOR_TICKS = 1;
//    private static final String TAG = com/ognev/game/uspeed/view/SpeedometerGauge.getSimpleName();
//    private Paint backgroundInnerPaint;
//    private Paint backgroundPaint;
//    private Paint colorLinePaint;
//    private int defaultColor;
//    private LabelConverter labelConverter;
//    private int labelTextSize;
//    private Bitmap mMask;
//    private double majorTickStep;
//    private Paint maskPaint;
//    private double maxSpeed;
//    private int minorTicks;
//    private Paint needlePaint;
//    private List ranges;
//    private double speed;
//    private Paint ticksPaint;
//    private Paint txtPaint;
//
//    public SpeedometerGauge(Context context)
//    {
//        super(context);
//        maxSpeed = 800D;
//        speed = 0.0D;
//        defaultColor = Color.rgb(180, 180, 180);
//        majorTickStep = 20D;
//        minorTicks = 1;
//        ranges = new ArrayList();
//        init();
//        setLabelTextSize(Math.round(12F * getResources().getDisplayMetrics().density));
//    }
//
//    public SpeedometerGauge(Context context, AttributeSet attributeset)
//    {
//        super(context, attributeset);
//        maxSpeed = 800D;
//        speed = 0.0D;
//        defaultColor = Color.rgb(180, 180, 180);
//        majorTickStep = 20D;
//        minorTicks = 1;
//        ranges = new ArrayList();
//        setLabelTextSize(Math.round(12F * (2.0F + getResources().getDisplayMetrics().density)));
//        init();
//    }
//
//    private void drawBackground(Canvas canvas)
//    {
//        RectF rectf = getOval(canvas, 1.0F);
//        canvas.drawArc(rectf, 20F, 340F, true, backgroundPaint);
//        canvas.drawArc(getOval(canvas, 0.9F), 20F, 349F, true, backgroundInnerPaint);
//        canvas.drawBitmap(Bitmap.createScaledBitmap(mMask, (int)(1.1000000000000001D * (double)rectf.width()), (int)(1.1000000000000001D * (double)rectf.height()) / 2, true), rectf.centerX(), rectf.centerY(), maskPaint);
//    }
//
//    private void drawNeedle(Canvas canvas)
//    {
//        RectF rectf = getOval(canvas, 1.0F);
//        float f = 10F + 0.35F * rectf.width();
//        RectF rectf1 = getOval(canvas, 0.2F);
//        float f1 = 10F + (float)(160D * (getSpeed() / getMaxSpeed()));
//        canvas.drawLine((float)((double)rectf.centerX() + 0.5D * (Math.cos(3.1415926535897931D * (double)((180F - f1) / 180F)) * (double)rectf1.width())), (float)((double)rectf.centerY() - 0.5D * (Math.sin(3.1415926535897931D * (double)(f1 / 180F)) * (double)rectf1.width())), (float)((double)rectf.centerX() + Math.cos(3.1415926535897931D * (double)((180F - f1) / 180F)) * (double)f), (float)((double)rectf.centerY() - Math.sin(3.1415926535897931D * (double)(f1 / 180F)) * (double)f), needlePaint);
//        canvas.drawArc(rectf1, 180F, 180F, true, backgroundPaint);
//    }
//
//    private void drawTicks(Canvas canvas)
//    {
//        float f = (float)((majorTickStep / maxSpeed) * (double)160F);
//        float f1 = f / (float)(1 + minorTicks);
//        float f2 = 30F / 2.0F;
//        RectF rectf = getOval(canvas, 1.0F);
//        float f3 = 0.35F * rectf.width();
//        float f4 = 10F;
//        double d = 0.0D;
//label0:
//        do
//        {
//            if (f4 <= 170F)
//            {
//                canvas.drawLine((float)((double)rectf.centerX() + Math.cos(3.1415926535897931D * (double)((180F - f4) / 180F)) * (double)(f3 - 30F / 2.0F)), (float)((double)rectf.centerY() - Math.sin(3.1415926535897931D * (double)(f4 / 180F)) * (double)(f3 - 30F / 2.0F)), (float)((double)rectf.centerX() + Math.cos(3.1415926535897931D * (double)((180F - f4) / 180F)) * (double)(f3 + 30F / 2.0F)), (float)((double)rectf.centerY() - Math.sin(3.1415926535897931D * (double)(f4 / 180F)) * (double)(f3 + 30F / 2.0F)), ticksPaint);
//                int i = 1;
//                do
//                {
//                    float f7;
//label1:
//                    {
//                        if (i <= minorTicks)
//                        {
//                            f7 = f4 + f1 * (float)i;
//                            if (f7 < 170F + f1 / 2.0F)
//                            {
//                                break label1;
//                            }
//                        }
//                        if (labelConverter != null)
//                        {
//                            canvas.save();
//                            canvas.rotate(180F + f4, rectf.centerX(), rectf.centerY());
//                            float f5 = 8F + (f3 + rectf.centerX() + 30F / 2.0F);
//                            float f6 = rectf.centerY();
//                            canvas.rotate(90F, f5, f6);
//                            canvas.drawText(labelConverter.getLabelFor(d, maxSpeed), f5, f6, txtPaint);
//                            canvas.restore();
//                        }
//                        f4 += f;
//                        d += majorTickStep;
//                        continue label0;
//                    }
//                    canvas.drawLine((float)((double)rectf.centerX() + Math.cos(3.1415926535897931D * (double)((180F - f7) / 180F)) * (double)f3), (float)((double)rectf.centerY() - Math.sin(3.1415926535897931D * (double)(f7 / 180F)) * (double)f3), (float)((double)rectf.centerX() + Math.cos(3.1415926535897931D * (double)((180F - f7) / 180F)) * (double)(f3 + f2)), (float)((double)rectf.centerY() - Math.sin(3.1415926535897931D * (double)(f7 / 180F)) * (double)(f3 + f2)), ticksPaint);
//                    i++;
//                } while (true);
//            }
//            RectF rectf1 = getOval(canvas, 0.7F);
//            colorLinePaint.setColor(defaultColor);
//            canvas.drawArc(rectf1, 185F, 170F, false, colorLinePaint);
//            ColoredRange coloredrange;
//            for (Iterator iterator = ranges.iterator(); iterator.hasNext(); canvas.drawArc(rectf1, (float)(190D + 160D * (coloredrange.getBegin() / maxSpeed)), (float)(160D * ((coloredrange.getEnd() - coloredrange.getBegin()) / maxSpeed)), false, colorLinePaint))
//            {
//                coloredrange = (ColoredRange)iterator.next();
//                colorLinePaint.setColor(coloredrange.getColor());
//            }
//
//            return;
//        } while (true);
//    }
//
//    private RectF getOval(Canvas canvas, float f)
//    {
//        int i = canvas.getWidth() - getPaddingLeft() - getPaddingRight();
//        int j = canvas.getHeight() - getPaddingTop() - getPaddingBottom();
//        RectF rectf;
//        if (j * 2 >= i)
//        {
//            rectf = new RectF(0.0F, 0.0F, f * (float)i, f * (float)i);
//        } else
//        {
//            rectf = new RectF(0.0F, 0.0F, f * (float)(j * 2), f * (float)(j * 2));
//        }
//        rectf.offset(((float)i - rectf.width()) / 2.0F + (float)getPaddingLeft(), ((float)(j * 2) - rectf.height()) / 2.0F + (float)getPaddingTop());
//        return rectf;
//    }
//
//    private void init()
//    {
//        if (android.os.Build.VERSION.SDK_INT >= 11 && !isInEditMode())
//        {
//            setLayerType(2, null);
//        }
//        backgroundPaint = new Paint(1);
//        backgroundPaint.setStyle(android.graphics.Paint.Style.FILL);
//        backgroundPaint.setColor(getResources().getColor(0x106000b));
//        backgroundInnerPaint = new Paint(1);
//        backgroundInnerPaint.setStyle(android.graphics.Paint.Style.FILL);
//        backgroundInnerPaint.setColor(getResources().getColor(0x1060012));
//        txtPaint = new Paint(1);
//        txtPaint.setColor(0xff000000);
//        txtPaint.setTextSize(labelTextSize);
//        txtPaint.setTextAlign(android.graphics.Paint.Align.CENTER);
//        mMask = BitmapFactory.decodeResource(getResources(), 0x7f02008a);
//        mMask = Bitmap.createBitmap(mMask, 0, 0, mMask.getWidth(), mMask.getHeight());
//        maskPaint = new Paint(1);
//        maskPaint.setDither(true);
//        ticksPaint = new Paint(1);
//        ticksPaint.setStrokeWidth(3F);
//        ticksPaint.setStyle(android.graphics.Paint.Style.STROKE);
//        ticksPaint.setColor(defaultColor);
//        colorLinePaint = new Paint(1);
//        colorLinePaint.setStyle(android.graphics.Paint.Style.STROKE);
//        colorLinePaint.setStrokeWidth(5F);
//        colorLinePaint.setColor(defaultColor);
//        needlePaint = new Paint(1);
//        needlePaint.setStrokeWidth(5F);
//        needlePaint.setStyle(android.graphics.Paint.Style.STROKE);
//        needlePaint.setColor(Color.argb(200, 255, 0, 0));
//    }
//
//    public void addColoredRange(double d, double d1, int i)
//    {
//        if (d >= d1)
//        {
//            throw new IllegalArgumentException("Incorrect number range specified!");
//        }
//        if (d < -0.03125D * maxSpeed)
//        {
//            d = -0.03125D * maxSpeed;
//        }
//        if (d1 > 1.03125D * maxSpeed)
//        {
//            d1 = 1.03125D * maxSpeed;
//        }
//        ranges.add(new ColoredRange(i, d, d1));
//        invalidate();
//    }
//
//    public void clearColoredRanges()
//    {
//        ranges.clear();
//        invalidate();
//    }
//
//    public int getDefaultColor()
//    {
//        return defaultColor;
//    }
//
//    public LabelConverter getLabelConverter()
//    {
//        return labelConverter;
//    }
//
//    public int getLabelTextSize()
//    {
//        return labelTextSize;
//    }
//
//    public double getMajorTickStep()
//    {
//        return majorTickStep;
//    }
//
//    public double getMaxSpeed()
//    {
//        return maxSpeed;
//    }
//
//    public int getMinorTicks()
//    {
//        return minorTicks;
//    }
//
//    public double getSpeed()
//    {
//        return speed;
//    }
//
//    protected void onDraw(Canvas canvas)
//    {
//        super.onDraw(canvas);
//        canvas.drawColor(0);
//        drawBackground(canvas);
//        drawTicks(canvas);
//        drawNeedle(canvas);
//    }
//
//    protected void onMeasure(int i, int j)
//    {
//        int k = android.view.View.MeasureSpec.getMode(i);
//        int l = android.view.View.MeasureSpec.getSize(i);
//        int i1 = android.view.View.MeasureSpec.getMode(j);
//        int j1 = android.view.View.MeasureSpec.getSize(j);
//        int k1;
//        int l1;
//        if (k == 0x40000000 || k == 0x80000000)
//        {
//            k1 = l;
//        } else
//        {
//            k1 = -1;
//        }
//        if (i1 == 0x40000000 || i1 == 0x80000000)
//        {
//            l1 = j1;
//        } else
//        {
//            l1 = -1;
//        }
//        if (l1 >= 0 && k1 >= 0)
//        {
//            k1 = Math.min(l1, k1);
//            l1 = k1 / 2;
//        } else
//        if (k1 >= 0)
//        {
//            l1 = k1 / 2;
//        } else
//        if (l1 >= 0)
//        {
//            k1 = l1 * 2;
//        } else
//        {
//            l1 = 0;
//            k1 = 0;
//        }
//        setMeasuredDimension(k1, l1);
//    }
//
//    public void setDefaultColor(int i)
//    {
//        defaultColor = i;
//        invalidate();
//    }
//
//    public void setLabelConverter(LabelConverter labelconverter)
//    {
//        labelConverter = labelconverter;
//        invalidate();
//    }
//
//    public void setLabelTextSize(int i)
//    {
//        labelTextSize = i;
//        if (txtPaint != null)
//        {
//            txtPaint.setTextSize(i);
//            invalidate();
//        }
//    }
//
//    public void setMajorTickStep(double d)
//    {
//        if (d <= 0.0D)
//        {
//            throw new IllegalArgumentException("Non-positive value specified as a major tick step.");
//        } else
//        {
//            majorTickStep = d;
//            invalidate();
//            return;
//        }
//    }
//
//    public void setMaxSpeed(double d)
//    {
//        if (d <= 0.0D)
//        {
//            throw new IllegalArgumentException("Non-positive value specified as max speed.");
//        } else
//        {
//            maxSpeed = d;
//            invalidate();
//            return;
//        }
//    }
//
//    public void setMinorTicks(int i)
//    {
//        minorTicks = i;
//        invalidate();
//    }
//
//    public ValueAnimator setSpeed(double d, long l, long l1)
//    {
//        if (d < 0.0D)
//        {
//            throw new IllegalArgumentException("Negative value specified as a speed.");
//        }
//        if (d > maxSpeed)
//        {
//            d = maxSpeed;
//        }
//        TypeEvaluator typeevaluator = new TypeEvaluator() {
//
//            final SpeedometerGauge this$0;
//
//            public Double evaluate(float f, Double double1, Double double2)
//            {
//                return Double.valueOf(double1.doubleValue() + (double)f * (double2.doubleValue() - double1.doubleValue()));
//            }
//
//            public volatile Object evaluate(float f, Object obj, Object obj1)
//            {
//                return evaluate(f, (Double)obj, (Double)obj1);
//            }
//
//
//            {
//                this$0 = SpeedometerGauge.this;
//                super();
//            }
//        };
//        Object aobj[] = new Object[2];
//        aobj[0] = Double.valueOf(getSpeed());
//        aobj[1] = Double.valueOf(d);
//        ValueAnimator valueanimator = ValueAnimator.ofObject(typeevaluator, aobj);
//        valueanimator.setDuration(l);
//        valueanimator.setStartDelay(l1);
//        valueanimator.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() {
//
//            final SpeedometerGauge this$0;
//
//            public void onAnimationUpdate(ValueAnimator valueanimator1)
//            {
//                Double double1 = (Double)valueanimator1.getAnimatedValue();
//                if (double1 != null)
//                {
//                    setSpeed(double1.doubleValue());
//                }
//            }
//
//
//            {
//                this$0 = SpeedometerGauge.this;
//                super();
//            }
//        });
//        valueanimator.start();
//        return valueanimator;
//    }
//
//    public ValueAnimator setSpeed(double d, boolean flag)
//    {
//        return setSpeed(d, 1500L, 200L);
//    }
//
//    public void setSpeed(double d)
//    {
//        if (d < 0.0D)
//        {
//            throw new IllegalArgumentException("Non-positive value specified as a speed.");
//        }
//        if (d > maxSpeed)
//        {
//            d = maxSpeed;
//        }
//        speed = d;
//        invalidate();
//    }
//
//    public void setTextColor(int i)
//    {
//        txtPaint.setColor(i);
//    }
//
//}
