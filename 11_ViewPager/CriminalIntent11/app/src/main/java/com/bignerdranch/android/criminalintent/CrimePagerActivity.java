package com.bignerdranch.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity implements CrimeFragment.IndexChangeCallback {
    public static final String TAG = "MyViewPager";

    private static final String EXTRA_CRIME_ID =
            "com.bignerdranch.android.criminalintent.crime_id";

    private ViewPager mViewPager;
    private List<Crime> mCrimes;

    private Button mButtonToFirst;
    private Button mButtonToLast;

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        UUID crimeId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_CRIME_ID);

        mViewPager = (ViewPager) findViewById(R.id.crime_view_pager);
        mButtonToFirst = findViewById(R.id.button_jump_first);
        mButtonToLast = findViewById(R.id.button_jump_last);

        mButtonToFirst.setOnClickListener(v -> toFirst());
        mButtonToLast.setOnClickListener(v -> toLast());

        mCrimes = CrimeLab.get(this).getCrimes();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        for (int i = 0; i < mCrimes.size(); i++) {
            //匹配相同UUUid的那一个对象
            if (mCrimes.get(i).getId().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    /**
     * 外部的ViewPager+Fragment适配器
     */
    class FragmentAdapter extends FragmentStatePagerAdapter {
        private List<Crime> mCrimes;

        FragmentAdapter(FragmentManager fm, List<Crime> crimes) {
            super(fm);
            mCrimes = crimes;
        }

        @Override
        public Fragment getItem(int i) {
            Crime crime = mCrimes.get(i);
            return CrimeFragment.newInstance(crime.getId());
        }

        @Override
        public int getCount() {
            return mCrimes.size();
        }
    }

    private void toLast() {
        Log.d(TAG, "toLast: ");
        mViewPager.setCurrentItem(getLastPosition());
    }

    private void toFirst() {
        Log.d(TAG, "toFirst: ");
        mViewPager.setCurrentItem(0);
    }

    @Override
    public void onUpdatePosition(int position) {
        Log.d(TAG, "onUpdatePosition: position = " + position);
        if (position == 0) {
            mButtonToFirst.setEnabled(false);
            mButtonToLast.setEnabled(true);
        } else if (isLabLast(position)) {
            mButtonToFirst.setEnabled(true);
            mButtonToLast.setEnabled(false);
        } else {
            mButtonToFirst.setEnabled(true);
            mButtonToLast.setEnabled(true);
        }

    }

    private boolean isLabLast(int position) {
        return position == getLastPosition();
    }

    private int getLastPosition() {
        return CrimeLab.get(this).getCrimes().size() - 1;
    }
}
