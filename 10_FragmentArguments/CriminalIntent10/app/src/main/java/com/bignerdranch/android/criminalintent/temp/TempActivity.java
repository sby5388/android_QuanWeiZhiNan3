package com.bignerdranch.android.criminalintent.temp;

import android.support.v4.app.Fragment;

import com.bignerdranch.android.criminalintent.SingleFragmentActivity;

/**
 * @author Administrator  on 2019/4/18.
 */
public class TempActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return TempFragment.newInstance();
    }
}
