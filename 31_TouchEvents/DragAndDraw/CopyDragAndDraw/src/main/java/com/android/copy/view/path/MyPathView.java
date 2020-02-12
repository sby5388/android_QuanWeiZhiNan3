package com.android.copy.view.path;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Administrator  on 2019/10/31.
 */
public class MyPathView extends View {
    public MyPathView(Context context) {
        super(context);
    }

    public MyPathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private final Paint mPaintPath = new Paint();

    {
        mPaintPath.setColor(Color.BLACK);
        mPaintPath.setStyle(Paint.Style.STROKE);
        mPaintPath.setAntiAlias(true);
        mPaintPath.setStrokeWidth(5.0f);
    }


    /**
     *
     * @param fileName
     */
    public void saveImage(String fileName){
        // TODO: 2019/10/31
    }





}
