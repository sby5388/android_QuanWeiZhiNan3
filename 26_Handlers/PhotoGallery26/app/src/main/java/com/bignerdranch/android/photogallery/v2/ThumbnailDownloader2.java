package com.bignerdranch.android.photogallery.v2;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.util.LruCache;

import com.bignerdranch.android.photogallery.FlickrFetchr;
import com.bignerdranch.android.photogallery.GalleryItem;
import com.bignerdranch.android.photogallery.PhotoAdapter;

import java.io.IOException;
import java.util.List;

/**
 * @author Administrator  on 2020/4/28.
 */
public class ThumbnailDownloader2 implements IGalleryTool {
    private static final String TAG = "ThumbnailDownloader2";

    private Handler mUiHandler;
    private Handler mWorkHandler;
    private Callback mCallback;
    private static final int CACHE_SIZE = (int) (Runtime.getRuntime().maxMemory() / 100);
    private LruCache<String, Drawable> mCache = new LruCache<>(CACHE_SIZE);

    public ThumbnailDownloader2(Callback callback) {
        mCallback = callback;
        mUiHandler = new Handler(Looper.getMainLooper());
        final HandlerThread thread = new HandlerThread(TAG);
        thread.start();
        mWorkHandler = new Handler(thread.getLooper());
    }

    @Override
    public void loadImage() {
        if (mCallback == null) {
            return;
        }
        mWorkHandler.post(new Runnable() {
            @Override
            public void run() {
                final FlickrFetchr flickrFetchr = new FlickrFetchr();
                final List<GalleryItem> galleryItems = flickrFetchr.fetchItems();
                if (galleryItems != null && mCallback != null) {
                    mUiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mCallback.onLoadFinish(galleryItems);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void bindData(final PhotoAdapter.BaseHolder photoHolder, final String url) {
        final Drawable drawable = mCache.get(url);
        if (drawable != null) {
            photoHolder.bindDrawable(drawable);
            return;
        }
        mWorkHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    byte[] bitmapBytes = new FlickrFetchr().getUrlBytes(url);
                    final Bitmap bitmap = BitmapFactory
                            .decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
                    final BitmapDrawable result = new BitmapDrawable(bitmap);
                    mUiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            photoHolder.bindDrawable(result);
                            mCache.put(url, result);
                        }
                    });
                } catch (IOException e) {
                    Log.e(TAG, "run: ", e);
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onDestroy() {
        mCallback = null;
        mWorkHandler.removeCallbacksAndMessages(null);
        mUiHandler.removeCallbacksAndMessages(null);
    }
}
