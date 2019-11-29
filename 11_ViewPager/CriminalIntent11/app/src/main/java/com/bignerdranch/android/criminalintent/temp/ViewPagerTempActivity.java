package com.bignerdranch.android.criminalintent.temp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.bignerdranch.android.criminalintent.R;

import java.util.Calendar;

public class ViewPagerTempActivity extends AppCompatActivity implements MyFragment.MyCallback {

    private ViewPager mViewPager;
    private Button mButtonLast;
    private Button mButtonFirst;
    private TextView mTextViewShow;

    private PagerAdapter mAdapter;

    public static Intent newIntent(Context context) {
        return new Intent(context, ViewPagerTempActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setContentView(R.layout.activity_view_pager_temp);
        mViewPager = findViewById(R.id.viewPager_temp);
        mButtonFirst = findViewById(R.id.button_temp_first);
        mButtonLast = findViewById(R.id.button_temp_last);
        mTextViewShow = findViewById(R.id.textView_show_index);


        mButtonLast.setOnClickListener(v -> toLast());
        mButtonFirst.setOnClickListener(v -> toFirst());

        mAdapter = new MyAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onUpdate(int index) {
        // TODO: 2019/11/29
        mTextViewShow.setText(String.valueOf(index));
        Log.d("MyFragment", "onUpdate: " + Calendar.getInstance().getTime().getTime());
    }

    private void toLast() {
        // TODO: 2019/11/29
        mViewPager.setCurrentItem(mAdapter.getCount() - 1);
    }

    private void toFirst() {
        // TODO: 2019/11/29
        mViewPager.setCurrentItem(0);
    }
}
