package com.czh.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by chenzhihua on 2017/6/13.
 */
public class MyScrollView extends View {

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context) {
        super(context);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        TextPaint paint = new TextPaint();
        paint.setAntiAlias(true);
        canvas.drawColor(Color.GRAY);
        for (int i = 10; i < 500; i++) {
            canvas.drawText("This is the scroll text.", 10, i, paint);
            i = i+15;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        String tag="onMeasure";
        Log.e(tag, "Scroll View on measure...");
        //这个View的大小，即MyScrollView这个父View
        setMeasuredDimension(1000, 1600);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        String tag = "onScrollChanged";
        Log.e(tag, "Scroll....");
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
