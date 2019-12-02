package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * @author Administrator
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
        {
            // TODO 通过findFragmentById 控件Id找到绑定在这个组件上的Fragment,如果有多个Fragment，只会出现第一个
            FragmentManager manager = getSupportFragmentManager();
            Fragment fragment1 = manager.findFragmentById(R.id.fragment_container);
            if (null == fragment1) {
                fragment1 = createFragment();
                fm.beginTransaction()
                        .add(R.id.fragment_container, fragment1)
                        .commit();
            }

        }
    }
}
