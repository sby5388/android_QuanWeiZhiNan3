package com.bignerdranch.android.criminalintent.temp;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;

/**
 * @author Administrator  on 2019/10/22.
 */
@SuppressLint("Registered")
public class TempCrimeListActivity extends TempSingleFragmentActivity {
    @Override
    public Fragment createFragment() {
        return TempCrimeListFragment.newInstance();
    }
}
