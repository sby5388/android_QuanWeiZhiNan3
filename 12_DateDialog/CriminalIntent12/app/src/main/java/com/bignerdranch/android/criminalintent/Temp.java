package com.bignerdranch.android.criminalintent;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

/**
 * @author Administrator  on 2019/9/21.
 */
public class Temp {
    private void temp(ViewPager viewPager, FragmentManager fragmentManager) {
        viewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {
            // TODO: 2019/9/21  需要保存Fragment状态的，一般用于首页固定的几个Fragment
            @Override
            public Fragment getItem(int i) {
                return null;
            }

            @Override
            public int getCount() {
                return 0;
            }
        });

        viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            // TODO: 2019/9/21 不需要Fragment保存状态的，一般是临时创建的，
            @Override
            public Fragment getItem(int i) {
                return null;
            }

            @Override
            public int getCount() {
                return 0;
            }
        });
    }
}
