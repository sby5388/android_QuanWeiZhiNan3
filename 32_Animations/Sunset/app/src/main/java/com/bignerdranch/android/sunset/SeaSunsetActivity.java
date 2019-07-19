package com.bignerdranch.android.sunset;

import android.support.v4.app.Fragment;

/**
 * 参照Sunset，可以制作一个有趣的海上日出日落的动画
 *
 * @author Administrator  on 2019/7/16.
 */
public class SeaSunsetActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return SeaSunsetFragment.newInstance();
    }
}
