package com.bignerdranch.android.photogallery;

import android.support.v4.app.Fragment;

/**
 * @author Administrator
 */
public class PhotoGalleryActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return PhotoGalleryFragment.newInstance();
    }
}
