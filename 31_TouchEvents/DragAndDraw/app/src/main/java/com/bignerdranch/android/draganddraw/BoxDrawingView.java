package com.bignerdranch.android.draganddraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * drag 拖拽
 * draw 画
 *
 * @author Administrator
 */
public class BoxDrawingView extends View {
    private static final String TAG = "BoxDrawingView";

    private Box mCurrentBox;
    private List<Box> mBoxen = new ArrayList<>();
    private Paint mBoxPaint;
    private Paint mBackgroundPaint;

    /**
     * Used when creating the view in code
     */
    public BoxDrawingView(Context context) {
        this(context, null);
    }

    /**
     * Used when inflating the view from XML
     */
    public BoxDrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //2个画笔
        // Paint the boxes a nice semitransparent red (ARGB)
        mBoxPaint = new Paint();
        // TODO: 2019/6/17 设着透明度！！！ 
        // FIXME: 2019/6/17 
        mBoxPaint.setColor(0x22ff0000);

        // Paint the background off-white
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(0xfff8efe0);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        // Fill the background
        canvas.drawPaint(mBackgroundPaint);

        for (Box box : mBoxen) {
            float left = Math.min(box.getOrigin().x, box.getCurrent().x);
            float right = Math.max(box.getOrigin().x, box.getCurrent().x);
            float top = Math.min(box.getOrigin().y, box.getCurrent().y);
            float bottom = Math.max(box.getOrigin().y, box.getCurrent().y);

            canvas.drawRect(left, top, right, bottom, mBoxPaint);
        }
    }

    @Override
    public boolean performClick() {
        // TODO: 2019/3/22 performClick 是使用代码主动去调用控件的点击事件（模拟人手去触摸控件）
        // TODO: 2019/3/22 onClick 使用者真实点击
        return super.performClick();
    }

    /**
     * 相应屏幕点击事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //获取事件的位置
        Log.d(TAG, "onTouchEvent: event.getX() = " + event.getX());
        Log.d(TAG, "onTouchEvent: event.getY() = " + event.getY());
        Log.d(TAG, "onTouchEvent: event.getAction() = " + event.getAction());
        PointF current = new PointF(event.getX(), event.getY());
        String action = "";
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                action = "ACTION_DOWN";
                // Reset drawing state
                //TODO 按下时生成一个Box:包含当前的位置(原始位置)
                mCurrentBox = new Box(current);
                mBoxen.add(mCurrentBox);
                break;
            case MotionEvent.ACTION_MOVE:
                action = "ACTION_MOVE";
                if (mCurrentBox != null) {
                    //TODO 移动时更新当前的位置，并刷新页面
                    mCurrentBox.setCurrent(current);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                action = "ACTION_UP";
                // TODO: 2019/3/22 抬起时 不再操作该对象,该对象已经被安全地保存到Box的集合之中
                mCurrentBox = null;
                break;
            case MotionEvent.ACTION_CANCEL:
                action = "ACTION_CANCEL";
                mCurrentBox = null;
                break;
            default:
                break;
        }

        Log.i(TAG, action + " at x=" + current.x +
                ", y=" + current.y);

        return true;
    }

    // TODO: 2019/3/22 实现多点触摸+ 来旋转画布

    // TODO: 2019/3/22 设备旋转之后：恢复数据


}
