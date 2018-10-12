package com.example.westf.homework2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.Calendar;

public class AnalogClockView extends View {

    int hour, minute, second;
    private int height, width = 0;
    private int padding = 0;
    private int fontSize = 0;
    private int numeralSpacing = 0;
    private int handTruncation, hourHandTruncation;
    private int radius = 0;
    private Paint paint;
    private boolean isInit;
    private int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    private Rect rect = new Rect();
    android.text.format.Time time;
    int i;

    public AnalogClockView(Context context) {
        super(context);
    }

    public AnalogClockView(Context context, AttributeSet attr) {
        super(context, attr);
    }

    public AnalogClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initClock() {
        height = getHeight();
        width = getWidth();
        padding = numeralSpacing + 50;
        fontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 13, getResources().getDisplayMetrics());
        int min = Math.min(height, width);
        radius = min / 2 - padding;
        handTruncation = min / 20;
        hourHandTruncation = min / 7;
        paint = new Paint();
        isInit = true;
    }

    public void onDraw(Canvas canvas) {
        if (!isInit) {
            initClock();
        }

        canvas.drawColor(Color.BLACK);
        drawCircle(canvas);
        drawCenter(canvas);
        drawNumeral(canvas);
        drawHands(canvas);

        postInvalidateDelayed(500);
        invalidate();
    }

    public void drawHand(Canvas canvas, double loc, boolean isHour) {
        double angle = Math.PI * loc / 30 - Math.PI / 2;
        int handRadius = isHour ? radius - handTruncation - hourHandTruncation : radius - handTruncation;
        canvas.drawLine(width / 2, height / 2, (float) (width / 2 + Math.cos(angle) * handRadius),
                (float) (height / 2 + Math.sin(angle) * handRadius), paint);
    }

    public void drawHands(Canvas canvas) {
        Calendar c = Calendar.getInstance();
        float hour = c.get(Calendar.HOUR_OF_DAY);
        hour = hour > 12 ? hour - 12 : hour;

        drawHand(canvas, (hour + getMinute() / 60) * 5f, true);
        drawHand(canvas, getMinute(), false);
        drawHand(canvas, getSecond(), false);
    }

    public void drawNumeral(Canvas canvas) {
        paint.setTextSize(fontSize);

        for (int number : nums) {
            String tmp = String.valueOf(number);
            paint.getTextBounds(tmp, 0, tmp.length(), rect);
            double angle = Math.PI / 6 * (number - 3);
            int x = (int) (width / 2 + Math.cos(angle) * radius - rect.width() / 2);
            int y = (int) (height / 2 + Math.sin(angle) * radius + rect.height() / 2);
            canvas.drawText(tmp, x, y, paint);
        }
    }

    public void drawCenter(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(width / 2, height / 2, 12, paint);
    }

    public void drawCircle(Canvas canvas) {
        paint.reset();
        paint.setColor(getResources().getColor(android.R.color.white));
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        canvas.drawCircle(width / 2, height / 2, radius + padding - 10, paint);
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    public void setTime(int[] time) {
        hour = time[0];
        minute = time[1];
        second = time[2];
    }

}
