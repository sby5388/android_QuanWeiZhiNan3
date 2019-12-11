package com.by5388.demo.beatbox2;


import android.support.v4.app.Fragment;

/**
 * @author Administrator  on 2019/12/4.
 */
public class BeatBoxActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return BeatBoxFragment.newInstance();
    }
}
