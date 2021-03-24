package com.bignerdranch.android.photogallery;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * @author Administrator
 */
public class PhotoPageActivity extends SingleFragmentActivity {
    public static final String TAG = "PhotoPageActivity";

    public static Intent newIntent(Context context, Uri photoPageUri) {
        Intent i = new Intent(context, PhotoPageActivity.class);
        i.setData(photoPageUri);
        return i;
    }



    @Override
    protected Fragment createFragment() {
        return PhotoPageFragment.newInstance(getIntent().getData());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged: orientation " + newConfig.orientation);

        Log.d(TAG, "onConfigurationChanged: densityDpi " + newConfig.densityDpi);
        Log.d(TAG, "onConfigurationChanged: fontScale " + newConfig.fontScale);
        Log.d(TAG, "onConfigurationChanged: hardKeyboardHidden " + newConfig.hardKeyboardHidden);
        Log.d(TAG, "onConfigurationChanged: mcc " + newConfig.mcc);
        Log.d(TAG, "onConfigurationChanged: mnc " + newConfig.mnc);
        Log.d(TAG, "onConfigurationChanged: navigation " + newConfig.navigation);
        Log.d(TAG, "onConfigurationChanged: screenHeightDp " + newConfig.screenHeightDp);
        Log.d(TAG, "onConfigurationChanged: screenLayout " + newConfig.screenLayout);
        Log.d(TAG, "onConfigurationChanged: screenWidthDp " + newConfig.screenWidthDp);
        Log.d(TAG, "onConfigurationChanged: smallestScreenWidthDp " + newConfig.smallestScreenWidthDp);
        Log.d(TAG, "onConfigurationChanged: touchscreen " + newConfig.touchscreen);
        Log.d(TAG, "onConfigurationChanged: uiMode " + newConfig.uiMode);
        Log.d(TAG, "onConfigurationChanged: locale " + newConfig.locale);
        Log.d(TAG, "onConfigurationChanged: keyboard " + newConfig.keyboard);
        Log.d(TAG, "onConfigurationChanged: keyboardHidden =" + newConfig.keyboardHidden);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d(TAG, "onConfigurationChanged: colorMode =" + newConfig.colorMode);
        }

    }
}
