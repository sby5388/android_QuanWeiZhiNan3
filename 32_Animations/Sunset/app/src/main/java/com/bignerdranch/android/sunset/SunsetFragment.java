package com.bignerdranch.android.sunset;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;

/**
 * @author Administrator
 */
public class SunsetFragment extends Fragment {
    public static final String TAG = "SunsetFragment";

    private View mSceneView;
    private View mSunView;
    private View mSkyView;

    private int mBlueSkyColor;
    private int mSunsetSkyColor;
    private int mNightSkyColor;

    private float mSunViewTop;


    private boolean mRise = false;

    public static SunsetFragment newInstance() {
        return new SunsetFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sunset, container, false);

        mSceneView = view;
        mSunView = view.findViewById(R.id.sun);
        mSkyView = view.findViewById(R.id.sky);


        Resources resources = getResources();
        mBlueSkyColor = resources.getColor(R.color.blue_sky);
        mSunsetSkyColor = resources.getColor(R.color.sunset_sky);
        mNightSkyColor = resources.getColor(R.color.night_sky);

        mSceneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSceneView.setEnabled(false);
                if (mRise) {
                    startAnimationRise();
                } else {
                    startAnimation();
                }
                mRise = !mRise;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                        } catch (Exception e) {

                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mSceneView.setEnabled(true);
                            }
                        });
                    }
                }).start();
            }
        });

        return view;
    }

    /**
     * TODO 2种动画之间的差异
     * Animator:
     * Animation:
     */
    private void startAnimationRise() {
        //垂直方向：上升
        float sunYStart = mSkyView.getHeight();
        float sunYEnd = mSunViewTop;
        Log.d(TAG, "startAnimationRise: mSunViewTop = " + mSunViewTop);
        ObjectAnimator heightAnimator = ObjectAnimator
                .ofFloat(mSunView, "y", sunYStart, sunYEnd)
                .setDuration(3000);

        //TODO 几个子类的含义？
        heightAnimator.setInterpolator(new AccelerateInterpolator());

        //TODO 太阳颜色变化
        ObjectAnimator sunsetSkyAnimator = ObjectAnimator
                .ofInt(mSkyView, "backgroundColor", mSunsetSkyColor, mBlueSkyColor)
                .setDuration(3000);
        //TODO ??
        sunsetSkyAnimator.setEvaluator(new ArgbEvaluator());
        //TODO 背景颜色变化
        ObjectAnimator nightSkyAnimator = ObjectAnimator
                .ofInt(mSkyView, "backgroundColor", mNightSkyColor, mSunsetSkyColor)
                .setDuration(1500);
        nightSkyAnimator.setEvaluator(new ArgbEvaluator());

        //动画集合
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(nightSkyAnimator)
                .with(heightAnimator)
                .before(sunsetSkyAnimator);
        animatorSet.start();

    }

    private void startAnimation() {
        float sunYStart = mSunView.getTop();
        float sunYEnd = mSkyView.getHeight();

        mSunViewTop = mSunView.getTop();
        Log.d(TAG, "startAnimation:  mSunView.getTop() = " + sunYStart);

        ObjectAnimator heightAnimator = ObjectAnimator
                .ofFloat(mSunView, "y", sunYStart, sunYEnd)
                .setDuration(3000);
        heightAnimator.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator sunsetSkyAnimator = ObjectAnimator
                .ofInt(mSkyView, "backgroundColor", mBlueSkyColor, mSunsetSkyColor)
                .setDuration(3000);
        sunsetSkyAnimator.setEvaluator(new ArgbEvaluator());

        ObjectAnimator nightSkyAnimator = ObjectAnimator
                .ofInt(mSkyView, "backgroundColor", mSunsetSkyColor, mNightSkyColor)
                .setDuration(1500);
        nightSkyAnimator.setEvaluator(new ArgbEvaluator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet
                .play(heightAnimator)
                .with(sunsetSkyAnimator)
                .before(nightSkyAnimator);
        animatorSet.start();
    }


}
