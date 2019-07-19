package com.bignerdranch.android.sunset;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;

/**
 * TODO 海上日出日落的动画
 * 与Sunset相比，地平线下面部分的颜色也要变化
 *
 * @author Administrator  on 2019/7/16.
 */
public class SeaSunsetFragment extends Fragment {
    public static final String TAG = SeaSunsetFragment.class.getSimpleName();
    /**
     * 整个Layout
     */
    private View mSceneView;
    private View mSunView;
    private View mSkyView;
    /**
     * TODO add sea view
     */
    private View mSeaView;
    private View mSeaSunView;

    private int mBlueSkyColor;
    private int mSunsetSkyColor;
    private int mNightSkyColor;
    /**
     * TODO add sea color
     */
    private int mBlueSeaColor;
    private int mSunsetSeaColor;
    private int mNightSeaColor;

    private float mSunViewTop;

    private boolean mRise = false;
    private Handler mHandler;


    public static Fragment newInstance() {
        return new SeaSunsetFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();
        Resources resources = getResources();
        mBlueSkyColor = resources.getColor(R.color.blue_sky);
        mSunsetSkyColor = resources.getColor(R.color.sunset_sky);
        mNightSkyColor = resources.getColor(R.color.night_sky);
        mBlueSeaColor = resources.getColor(R.color.sea);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sea_sunset, container, false);
    }

    @CallSuper
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mSceneView = view;
        mSunView = view.findViewById(R.id.sun);
        mSkyView = view.findViewById(R.id.sky);
        mSeaView = view.findViewById(R.id.sea);
        mSeaSunView = view.findViewById(R.id.sea_sun);

        mSceneView.setOnClickListener(v -> {
            v.setEnabled(false);
            if (mRise) {
                startUpAnimator();
            } else {
                startDownAnimator();
            }
            mRise = !mRise;
            mHandler.postDelayed(() -> v.setEnabled(true), 4500);
        });


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    /**
     * 上升
     */
    private void startUpAnimator() {
        final float sunYStart = mSkyView.getHeight();
        final float sunYEnd = mSunViewTop;


        final ObjectAnimator sunYAnimator = ObjectAnimator
                .ofFloat(mSunView, "y", sunYStart, sunYEnd)
                .setDuration(3000);
        sunYAnimator.setInterpolator(new AccelerateInterpolator());

        final ObjectAnimator skyColorAnimator = ObjectAnimator
                .ofInt(mSkyView, "backgroundColor", mSunsetSkyColor, mBlueSkyColor)
                .setDuration(3000);
        skyColorAnimator.setEvaluator(new ArgbEvaluator());

        ObjectAnimator nightSkyAnimator = ObjectAnimator
                .ofInt(mSkyView, "backgroundColor", mNightSkyColor, mSunsetSkyColor)
                .setDuration(1500);
        nightSkyAnimator.setEvaluator(new ArgbEvaluator());


        final int seaSunYEnd = mSeaSunView.getTop();
        final int seaSunYStart = 0 - mSeaSunView.getHeight();

        final ObjectAnimator seaSunYAnimator = ObjectAnimator
                .ofFloat(mSeaSunView, "y", seaSunYStart, seaSunYEnd)
                .setDuration(3000);
        seaSunYAnimator.setInterpolator(new AccelerateInterpolator());


        final ObjectAnimator seaColorAnimator = ObjectAnimator
                .ofInt(mSeaView, "backgroundColor", mSunsetSkyColor, mBlueSeaColor)
                .setDuration(3000);
        seaColorAnimator.setEvaluator(new ArgbEvaluator());

        final ObjectAnimator nightSeaAnimator = ObjectAnimator
                .ofInt(mSeaView, "backgroundColor", mNightSkyColor, mSunsetSkyColor)
                .setDuration(1500);
        nightSeaAnimator.setEvaluator(new ArgbEvaluator());


        AnimatorSet nightSet = new AnimatorSet();
        nightSet.play(nightSkyAnimator)
                .with(nightSeaAnimator)
        ;

        AnimatorSet daySet = new AnimatorSet();
        daySet.play(skyColorAnimator)
                .with(sunYAnimator)
                .with(seaSunYAnimator)
                .with(seaColorAnimator);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet
                .play(nightSet)
                .before(daySet);

        animatorSet.start();


    }

    /**
     * 下降
     */
    private void startDownAnimator() {

        final int sunYStart = mSunView.getTop();
        final int sunYEnd = mSkyView.getHeight();
        mSunViewTop = mSunView.getTop();

        final ObjectAnimator sunYAnimator = ObjectAnimator.ofFloat(mSunView, "y", sunYStart, sunYEnd)
                .setDuration(3000);
        // TODO: 2019/7/18 AccelerateInterpolator:先慢后快的变化
        sunYAnimator.setInterpolator(new AccelerateInterpolator());

        //sky backgroundColor animator
        final ObjectAnimator skyColorAnimator = ObjectAnimator
                .ofInt(mSkyView, "backgroundColor", mBlueSkyColor, mSunsetSkyColor)
                .setDuration(3000);
        skyColorAnimator.setEvaluator(new ArgbEvaluator());
        //after sunset sky backgroundColor -->night
        final ObjectAnimator nightSkyColorAnimator = ObjectAnimator
                .ofInt(mSkyView, "backgroundColor", mSunsetSkyColor, mNightSkyColor)
                .setDuration(1500);
        nightSkyColorAnimator.setEvaluator(new ArgbEvaluator());

        //


        final int seaSunYStart = mSeaSunView.getTop();
        final int seaSunYEnd = 0 - mSeaSunView.getHeight();

        final ObjectAnimator seaSunYAnimator = ObjectAnimator
                .ofFloat(mSeaSunView, "y", seaSunYStart, seaSunYEnd)
                .setDuration(3000);
        seaSunYAnimator.setInterpolator(new AccelerateInterpolator());

        final ObjectAnimator seaColorAnimator = ObjectAnimator
                .ofInt(mSeaView, "backgroundColor", mBlueSeaColor, mSunsetSkyColor)
                .setDuration(3000);
        seaColorAnimator.setEvaluator(new ArgbEvaluator());

        // TODO: 2019/7/19 对2种颜色稍微一些差别 ，不能完全一致
        final ObjectAnimator seaNightAnimator = ObjectAnimator
                .ofInt(mSeaView, "backgroundColor", mSunsetSkyColor, mNightSkyColor)
                .setDuration(1500);
        seaNightAnimator.setEvaluator(new ArgbEvaluator());
        final AnimatorSet nightSet = new AnimatorSet();
//
        nightSet.play(seaNightAnimator)
                .with(nightSkyColorAnimator)
        ;
        AnimatorSet animatorSet = new AnimatorSet();

        animatorSet.play(sunYAnimator)
                .with(seaSunYAnimator)
                .with(skyColorAnimator)
                .with(seaColorAnimator)
                .before(nightSet)
        ;
        animatorSet.start();

    }

}
