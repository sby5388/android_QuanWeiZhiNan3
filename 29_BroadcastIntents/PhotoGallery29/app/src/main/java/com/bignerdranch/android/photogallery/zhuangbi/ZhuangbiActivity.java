package com.bignerdranch.android.photogallery.zhuangbi;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.bignerdranch.android.photogallery.SingleFragmentActivity;

/**
 * @author admin  on 2019/3/13.
 */
public class ZhuangbiActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return ZhuangbiFragment.newInstance();
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, ZhuangbiActivity.class);
    }

}
