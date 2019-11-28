package com.bignerdranch.android.criminalintent.temp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.bignerdranch.android.criminalintent.R;

/**
 * @author Administrator  on 2019/10/22.
 */
public abstract class TempSingleFragmentActivity extends AppCompatActivity {

    public abstract Fragment createFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp_single_fragment);

        final FragmentManager fragmentManager = getSupportFragmentManager();
        final Fragment fragment = fragmentManager.findFragmentById(R.id.temp_fragment_container);

        if (fragment == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.temp_fragment_container, createFragment())
                    .commit();
        }


    }

}
