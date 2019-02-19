package com.by5388.photogallery;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @author admin  on 2019/2/13.
 */
class FetchItemsTask extends AsyncTask<Void, Void, List<GalleryItem>> {
    private WeakReference<CallBack> mCallBack;

    FetchItemsTask(CallBack callBack) {
        mCallBack = new WeakReference<>(callBack);
    }

    @Override
    protected List<GalleryItem> doInBackground(Void... voids) {
        return new FlickrFetchr().fetchItems();
    }

    @Override
    protected void onPostExecute(List<GalleryItem> galleryItems) {
        CallBack callBack = mCallBack.get();
        callBack.updateData(galleryItems);
    }

    @SuppressWarnings("all")
    interface CallBack {
        void updateData(List<GalleryItem> items);
    }
}
