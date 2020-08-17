package com.bignerdranch.android.photogallery.v2;

import com.bignerdranch.android.photogallery.GalleryItem;
import com.bignerdranch.android.photogallery.PhotoAdapter;

import java.util.List;

/**
 * @author Administrator  on 2020/4/28.
 */
public interface IGalleryTool {
    void loadImage();

    void onDestroy();

    void bindData(PhotoAdapter.BaseHolder photoHolder, String url);

    interface Callback {
        void onLoadFinish(List<GalleryItem> list);
    }
}
