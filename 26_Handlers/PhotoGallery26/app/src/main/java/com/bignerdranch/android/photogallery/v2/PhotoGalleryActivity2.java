package com.bignerdranch.android.photogallery.v2;

import android.support.v4.app.Fragment;

import com.bignerdranch.android.photogallery.SingleFragmentActivity;

/**
 * @author Administrator  on 2020/4/28.
 */
public class PhotoGalleryActivity2 extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return PhotoGalleryFragment2.newInstance();
    }
}
