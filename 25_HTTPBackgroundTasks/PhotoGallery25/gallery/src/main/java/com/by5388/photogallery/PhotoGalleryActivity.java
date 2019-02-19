package com.by5388.photogallery;

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
