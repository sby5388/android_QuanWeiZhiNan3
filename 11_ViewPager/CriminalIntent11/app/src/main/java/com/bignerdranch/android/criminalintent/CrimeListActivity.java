package com.bignerdranch.android.criminalintent;

import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import com.bignerdranch.android.criminalintent.temp.ViewPagerTempActivity;

public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.temp, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int itemId = item.getItemId();
        if (itemId == R.id.menu_to_viewPager) {
            startActivity(ViewPagerTempActivity.newIntent(this));
            return true;
        }
        return super.onContextItemSelected(item);
    }
}
