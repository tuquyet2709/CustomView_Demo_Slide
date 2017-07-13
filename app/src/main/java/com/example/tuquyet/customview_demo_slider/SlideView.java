package com.example.tuquyet.customview_demo_slider;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by tuquyet on 13/07/2017.
 */
public class SlideView extends View {
    private int mSliderColor;
    private int mSliderBackground;
    private Slider mSlider;
    private float mDx;
    private boolean isDragMode;
    private SlideListener mSlideListener;

    public SlideView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public SlideView(Context context,
                     @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public SlideView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SlideView,
            defStyleAttr, 0);
        try {
            mSliderColor = array.getColor(R.styleable.SlideView_slideColor, 0);
            mSliderBackground = array.getColor(R.styleable.SlideView_slideBackground, 0);
            setBackgroundColor(mSliderBackground);
        } catch (Exception e) {
        } finally {
            array.recycle();
        }
        mSlider = new Slider(0, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = getResources().getDimensionPixelOffset(R.dimen.sliderHeight);
        int width = getMeasuredWidth();
        mSlider.setWidth(width / 8);
        mSlider.setHeight(height);
        setMeasuredDimension(width,
            height);
    }

    public void setSlideListener(SlideListener slideListener) {
        mSlideListener = slideListener;
    }

    public void setSliderBackground(int sliderBackground) {
        mSliderBackground = sliderBackground;
        invalidate();
        requestLayout();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                mSlideListener.onSlide(this, mSlider.getX(), SlideListener.SlideState.OPEN);
                if (!onHaftWayPassed()) {

                } else {
                }
                setInDragMode(false);
                break;

            case MotionEvent.ACTION_DOWN:
                if (mSlider.isGripper((int) x, (int) y, 3, 3)) {
                    setInDragMode(true);
                }
                mDx = x - mSlider.getX();
                break;

            case MotionEvent.ACTION_MOVE:
                mSlider.setX(x - mDx);
                if (mSlideListener != null) {
                    mSlideListener.onSlide(this, mSlider.getX(), SlideListener.SlideState.CLOSE);
                }
                if (isDragMode) {
                }
                break;

            case MotionEvent.ACTION_CANCEL:
        }
        return true;
    }

    private void setInDragMode(boolean dragMode) {
        isDragMode = dragMode;
    }

    private boolean onHaftWayPassed() {
        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isInEditMode()) {
            return;
        }
        mSlider.drawSlider(canvas);
    }

    private class Slider {
        private Paint mPaint;
        private int mWidth, mHeight;

        public void setWidth(int width) {
            mWidth = width;
        }

        public void setHeight(int height) {
            mHeight = height;
        }

        private float mX, mY;

        public Slider(float x, float y) {
            mX = x;
            mY = y;
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(Color.GREEN);
        }

        public float getX() {
            return mX;
        }

        public void setX(float x) {
            mX = x;
            invalidate();
        }

        public float getY() {
            return mY;
        }

        public void setY(float y) {
            mY = y;
            invalidate();
        }

        public boolean isGripper(int x, int y, int w, int h) {
            Rect rect = new Rect((int) mX, (int) mY, (int) mX + mWidth, mHeight);
            return rect.intersects(x, y, w, h);
        }

        public void drawSlider(Canvas canvas) {
            canvas.drawRect(mX, mY, mX + mWidth, mHeight, mPaint);
        }
    }
}
