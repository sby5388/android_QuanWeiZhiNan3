package com.by5388.photogallery.zhuang.bi;

import android.support.v4.app.Fragment;

import com.by5388.photogallery.SingleFragmentActivity;

/**
 * @author admin  on 2019/3/13.
 */
public class ZhuangbiActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return ZhuangbiFragment.newInstance();
    }


}
