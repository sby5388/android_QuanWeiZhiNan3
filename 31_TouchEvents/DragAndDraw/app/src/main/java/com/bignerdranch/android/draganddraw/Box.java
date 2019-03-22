package com.bignerdranch.android.draganddraw;

import android.graphics.PointF;

/**
 * @author Administrator
 */
public class Box {
    /**
     * 原来的位置
     */
    private PointF mOrigin;
    /**
     * 当前的位置
     */
    private PointF mCurrent;

    public Box(PointF origin) {
        mOrigin = origin;
        mCurrent = origin;
    }

    public PointF getCurrent() {
        return mCurrent;
    }

    public void setCurrent(PointF current) {
        mCurrent = current;
    }

    public PointF getOrigin() {
        return mOrigin;
    }
}
