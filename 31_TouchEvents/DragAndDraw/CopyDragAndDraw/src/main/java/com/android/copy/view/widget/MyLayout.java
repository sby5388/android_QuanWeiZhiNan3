package com.android.copy.view.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.android.copy.view.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * @author Administrator  on 2019/3/29.
 */
public class MyLayout extends View {

    private final Paint mPaint = new Paint();
    private final Paint mBackgroundPaint = new Paint();
    private final Paint bitmapPaint = new Paint();
    private final List<Pearl> mPearls = new ArrayList<>();
    private final Set<ElementData> mElementDataList = new HashSet<>();
    private Pearl mPearl;
    public final int DEFAULT_COLOR = getResources().getColor(R.color.defaultColor);
    /**
     * 当前颜色
     */
    private int mCurrentColor = DEFAULT_COLOR;


    private final RectF mRectF = new RectF();

    {
        mBackgroundPaint.setColor(0xfff8efe0);
        mPaint.setColor(mCurrentColor);
        bitmapPaint.setStyle(Paint.Style.FILL);

    }

    public MyLayout(Context context) {
        this(context, null);
    }

    public MyLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public MyLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.drawColor(mBackgroundPaint.getColor());
        canvas.drawPaint(mBackgroundPaint);

        drawablePearl(canvas);
        drawableDrawable(canvas);
    }

    private void drawableDrawable(Canvas canvas) {
        Random random = new Random();
        final int width = getWidth();
        final int height = getHeight();
        for (ElementData data : mElementDataList) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), data.drawableID);
            canvas.drawBitmap(bitmap, random.nextInt(width), random.nextInt(height), bitmapPaint);
        }
    }

    private void drawablePearl(Canvas canvas) {
        for (Pearl pearl : mPearls) {
            float left = Math.min(pearl.mCurrent.x, pearl.mOriginal.x);
            float top = Math.min(pearl.mCurrent.y, pearl.mOriginal.y);
            float right = Math.max(pearl.mCurrent.x, pearl.mOriginal.x);
            float bottom = Math.max(pearl.mCurrent.y, pearl.mOriginal.y);
            mRectF.set(left, top, right, bottom);
            mPaint.setColor(pearl.mColor);
            canvas.drawRect(mRectF, mPaint);
        }
    }


    @Override
    public boolean performContextClick() {
        return super.performContextClick();
    }

    @Override
    public boolean performContextClick(float x, float y) {
        return super.performContextClick(x, y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPearl = new Pearl(new PointF(x, y), mCurrentColor);
                mPearls.add(mPearl);
                break;
            case MotionEvent.ACTION_MOVE:
                if (mPearl != null) {
                    mPearl.update(new PointF(x, y));
                    postInvalidate();
//                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mPearl = null;
                break;
            default:
                break;

        }
        // TODO: 2019/3/29 一定要返回true：否则不绘制
//       return super.onTouchEvent(event);
        return true;
    }

    private void initView(Context context) {

    }

    public void reset() {
        mPearls.clear();
        mElementDataList.clear();
        mCurrentColor = DEFAULT_COLOR;
        mPaint.setColor(mCurrentColor);
        postInvalidate();
    }


    // TODO: 2019/6/17 修改颜色
    public void changeColor(@ColorInt int colorValue) {
        // TODO: 2019/6/17
        mCurrentColor = ContextCompat.getColor(getContext(), colorValue);
    }

    public void addBrush(@DrawableRes int drawable) {
        mElementDataList.add(new ElementData(drawable));
        postInvalidate();
    }

}
