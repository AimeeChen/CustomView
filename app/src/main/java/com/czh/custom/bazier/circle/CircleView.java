package com.czh.custom.bazier.circle;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * 用贝塞尔曲线绘制圆形
 * Created by chenzhihua on 2017/6/16.
 */

public class CircleView extends View{

    private Paint mPaint;
    private Path mPath;

    private float radius = 1;
    private float c = 0.551915024494f;
    private int mScreenWith;
    private int mScreenHeight;

    //圆心坐标
    private int mCircleX;
    private int mCircleY;
    private float offsetY;
    private float compressOffset = 0;
    private float compressOffsetTop = 0;
    private float compressOffsetBottom = 0;

    private HorizontalLine mTopLine, mBottomLine;
    private VerticalLine mLeftLine, mRightLine;

    private int direction = 1;
    private final static int DIRECTION_UP = 0;
    private final static int DIRECTION_DOWN = 1;

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.i("CircleView", " CircleView()");
        initPaint();
        initAnimator();
    }

    private void initPaint() {
        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
    }

    private void initAnimator() {
        ValueAnimator animator = ValueAnimator.ofInt(0, 1000);
        animator.setDuration(1000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        //插入器
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
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
        Log.i("CircleView", " onMeasure()");
        mScreenWith = MeasureSpec.getSize(widthMeasureSpec);
        mScreenHeight = MeasureSpec.getSize(heightMeasureSpec);
        radius = mScreenWith / 10;
        mCircleX = mScreenWith / 2;
        mCircleY = mScreenHeight / 2;
        initLine();
    }

    private void initLine() {
        mTopLine = new HorizontalLine(0, -radius);
        mBottomLine = new HorizontalLine(0, radius);
        mLeftLine = new VerticalLine(-radius, 0);
        mRightLine = new VerticalLine(radius, 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i("CircleView", " onDraw()");
        getOffset();
        getDirection();
        mPath.reset();
        canvas.translate(mCircleX, mCircleY + offsetY);
        mPath.moveTo(mTopLine.middle.x, mTopLine.middle.y);

        mPath.cubicTo(mTopLine.right.x, mTopLine.right.y, mRightLine.top.x, mRightLine.top.y,
                mRightLine.middle.x, mRightLine.middle.y);
        mPath.cubicTo(mRightLine.bottom.x, mRightLine.bottom.y, mBottomLine.right.x, mBottomLine.right.y,
                mBottomLine.middle.x, mBottomLine.middle.y);
        mPath.cubicTo(mBottomLine.left.x, mBottomLine.left.y, mLeftLine.bottom.x, mLeftLine.bottom.y,
                mLeftLine.middle.x, mLeftLine.middle.y);
        mPath.cubicTo(mLeftLine.top.x, mLeftLine.top.y, mTopLine.left.x, mTopLine.left.y,
                mTopLine.middle.x, mTopLine.middle.y);
        canvas.drawPath(mPath, mPaint);
    }

    private void getDirection() {
        if (offsetY == -40) {
            direction = DIRECTION_DOWN;
        } else if (offsetY == (mCircleY - (radius - 40))){
            direction = DIRECTION_UP;
        }
    }

    private void getOffset() {
        if (direction == DIRECTION_DOWN) {
            if (compressOffsetTop != 0 && compressOffsetTop > 0) {
                compressOffsetTop = compressOffsetTop - 5;
                mTopLine.setY(-(radius - compressOffsetTop));
            }
            offsetY = offsetY + 5;
            if (offsetY > (mCircleY - radius)) {
                if (offsetY > (mCircleY - (radius - 40))) {
                    offsetY = mCircleY - (radius - 40);
                    compressOffset = 0;
                } else {
                    compressOffset = compressOffset + 5;
                    compressOffsetBottom = compressOffset;
                    mBottomLine.setY(radius-compressOffset);
                }
            }
        } else if(direction == DIRECTION_UP){
            if (compressOffsetBottom != 0 && compressOffsetBottom > 0) {
                compressOffsetBottom = compressOffsetBottom - 5;
                mBottomLine.setY(radius- compressOffsetBottom);
            }
            offsetY = offsetY - 5;
            if (offsetY < 0) {
                if (offsetY < -40) {
                    offsetY = -40;
                    compressOffset = 0;
                } else {
                    compressOffset = compressOffset + 5;
                    compressOffsetTop = compressOffset;
                    mTopLine.setY(-(radius - compressOffset));
                }

            }
        }
    }

    class HorizontalLine {

        public PointF left = new PointF();
        public PointF middle = new PointF();
        public PointF right = new PointF();

        public HorizontalLine(float x, float y) {
            left.x = -c*radius;
            left.y = y;
            middle.x = x;
            middle.y = y;
            right.x = c*radius;
            right.y = y;
        }

        public void setY(float y) {
            left.y = y;
            middle.y = y;
            right.y = y;
        }
    }

    class VerticalLine {
        public PointF top = new PointF();
        public PointF middle = new PointF();
        public PointF bottom = new PointF();

        public VerticalLine(float x, float y) {
            top.x = x;
            top.y = -c*radius;
            middle.x = x;
            middle.y = y;
            bottom.x = x;
            bottom.y = c*radius;
        }
    }
}
