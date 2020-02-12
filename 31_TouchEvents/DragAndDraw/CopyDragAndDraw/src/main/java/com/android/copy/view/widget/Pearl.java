package com.android.copy.view.widget;

import android.graphics.PointF;

/**
 * @author Administrator  on 2019/3/29.
 */
public class Pearl {
    PointF mOriginal;
    PointF mCurrent;
    final int mColor;

    public Pearl(PointF original, int color) {
        this.mOriginal = original;
        this.mCurrent = original;
        this.mColor = color;
    }

    public void update(PointF current) {
        this.mCurrent = current;
    }
}
