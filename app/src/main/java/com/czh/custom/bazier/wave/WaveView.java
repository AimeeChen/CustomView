package com.czh.custom.bazier.wave;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * 贝塞尔曲线实现波浪效果
 * Created by chenzhihua on 2017/6/15.
 */

public class WaveView extends View {

    //初始化画笔
    private Paint mPaint;
    private Path path;
    private ValueAnimator animator;

    private int mCenterY;
    private int mScreenWith;
    private int mScreenHeight;
    //波浪的长度
    private int mWL = 1000;
    private int offset = 0;
    private int mWaveCount = 2;

    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        initPaint();
        initAnimator();
    }

    private void initPaint() {
        path = new Path();
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    private void initAnimator() {
        animator = ValueAnimator.ofInt(0, mWL);
        //动画的持续时间
        animator.setDuration(1000);
        //指定动画的执行次数和动画的重复模式
        animator.setRepeatCount(ValueAnimator.INFINITE);
        //指定时间变化的百分比，就是当前流失时间除以指定的持续时间
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                postInvalidate();
            }
        });
        animator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mScreenWith = MeasureSpec.getSize(widthMeasureSpec);
        mScreenHeight = MeasureSpec.getSize(heightMeasureSpec);
        mCenterY = (int) (mScreenHeight / 2);
        mWaveCount = (int)Math.round((mScreenWith / mWL) + 1.5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (path == null)
            return;
        if (offset == mWL) {
            offset = 0;
        }
        offset = offset + 10;
        path.reset();
        path.moveTo(-mWL + offset, mCenterY);
        int aa = -mWL + offset;

        for (int i=0; i<mWaveCount; i++) {
            path.quadTo((-3 * mWL / 4) + (i * mWL) + offset, mCenterY + 50, (-2 * mWL / 4) + (i * mWL) + offset, mCenterY);
            path.quadTo((-mWL / 4) + (i * mWL) + offset, mCenterY - 50, offset + (i * mWL), mCenterY);
        }

        path.lineTo(mScreenWith, mScreenHeight);
        path.lineTo(0, mScreenHeight);
        path.close();
        canvas.drawPath(path, mPaint);
    }
}
