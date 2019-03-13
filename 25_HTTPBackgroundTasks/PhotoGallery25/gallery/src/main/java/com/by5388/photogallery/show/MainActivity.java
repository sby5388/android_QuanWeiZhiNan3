package com.by5388.photogallery.show;

import android.support.v4.app.Fragment;

import com.by5388.photogallery.SingleFragmentActivity;

/**
 * @author Administrator
 */
public class MainActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return ShowFragment.newInstance();
    }
}
