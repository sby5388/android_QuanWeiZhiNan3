package com.by5388.demo.beatbox2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;


/**
 * @author Administrator  on 2019/12/4.
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {
    private static final int CONTAINER_LAYOUT_ID = R.id.fragment_container;

    public abstract Fragment createFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final Fragment fragment = fragmentManager.findFragmentById(CONTAINER_LAYOUT_ID);
        if (fragment == null) {
            fragmentManager.beginTransaction()
                    .add(CONTAINER_LAYOUT_ID, createFragment())
                    .commit();
        }
    }
}
