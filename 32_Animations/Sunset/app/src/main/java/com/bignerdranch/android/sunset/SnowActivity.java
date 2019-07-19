package com.bignerdranch.android.sunset;

import android.support.v4.app.Fragment;

/**
 * @author Administrator  on 2019/4/1.
 */
public class SnowActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return SnowFragment.newInstance();
    }

}
