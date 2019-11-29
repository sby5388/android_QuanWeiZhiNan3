package com.bignerdranch.android.criminalintent.temp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * @author Administrator  on 2019/11/29.
 */
class MyAdapter extends FragmentStatePagerAdapter {
    private static final int COUNT = 10;

    MyAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return MyFragment.newInstance(i);
    }

    @Override
    public int getCount() {
        return COUNT;
    }
}
