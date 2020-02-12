package com.by5388.gallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 下载工具
 *
 * @author Administrator  on 2019/12/12.
 */
public class ThumbnailDownloader<T> extends HandlerThread {
    public interface ThumbnailDownloadListener<T> {
        /**
         * 下载完成回调，更新UI
         */
        void onThumbnailDownloaded(T t, Bitmap bitmap);
    }

    private static final String TAG = ThumbnailDownloader.class.getSimpleName();
    private static final int MESSAGE_DOWNLOAD = 0;
    /**
     * 请求Handler，运行在WorkerThreader
     */
    private Handler mRequestHandler;
    /**
     * 响应Handler，即UIHandler，可以更新UI
     */
    private final Handler mResponseHandler;

    // TODO: 2019/12/13 26.9 使用LruCache 实现预加载和缓存

    private LruCache<String, Bitmap> mLruCache;

    /**
     * 支持并发的HashMap(线程安全),用于记录T与url的关系
     */
    private ConcurrentHashMap<T, String> mRequestMap = new ConcurrentHashMap<>();

    private ThumbnailDownloadListener<T> mDownloadListener;


    public ThumbnailDownloader(Handler responseHandler, ThumbnailDownloadListener<T> listener) {
        super(TAG);
        mResponseHandler = responseHandler;
        this.mDownloadListener = listener;
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cache = maxMemory / 8;
        mLruCache = new LruCache<String, Bitmap>(cache) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                // TODO: 2019/12/13
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };

    }

    private boolean mHasQuit = false;

    @Override
    public boolean quit() {
        mHasQuit = true;
        return super.quit();
    }


    public void queueThumbnail(final T target, final String url) {
        // TODO: 2019/12/12
        Log.d(TAG, "queueThumbnail: url = " + url);
        if (TextUtils.isEmpty(url)) {
            mRequestMap.remove(target);
        } else {
            mRequestMap.put(target, url);
            // TODO: 2019/12/12 通过target在HashMap中找到图片链接
            mRequestHandler.obtainMessage(MESSAGE_DOWNLOAD, target).sendToTarget();
        }

    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        //handler 在 哪个线程创建，就工作再那个线程，除了ui线程，都要进行Looper.prepare(),make Handler(),Looper.looper();
        // TODO: 2019/12/12 工作Handler要在这里生成
        mRequestHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (MESSAGE_DOWNLOAD == msg.what) {
                    final T t = (T) msg.obj;
                    handleRequest(t);
                }
            }


        };
    }

    private void handleRequest(final T t) {
        final String url = mRequestMap.get(t);
        if (url == null) {
            return;
        }
        final Bitmap cacheBitmap = mLruCache.get(url);
        if (cacheBitmap != null) {
            sendToUi(t, cacheBitmap);
            Log.d(TAG, "handleRequest: 使用缓存数据");
            return;
        }

        try {
            final byte[] result = new FlickrFetchr().getUrlBytes(url);
            Log.d(TAG, "handleRequest: 新下载数据");
            final Bitmap bitmap = BitmapFactory.decodeByteArray(result, 0, result.length);
            // TODO: 2019/12/12 检查连接是否匹配，HandlerThread 是否已经退出
            if (mHasQuit || !url.equals(mRequestMap.get(t))) {
                Log.d(TAG, "run: not match url");
                return;
            }
            sendToUi(t, bitmap);
            mLruCache.put(url, bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void sendToUi(final T t, final Bitmap bitmap) {
        if (t == null || bitmap == null) {
            return;
        }
        mRequestMap.remove(t);
        mResponseHandler.post(new Runnable() {
            @Override
            public void run() {
                mDownloadListener.onThumbnailDownloaded(t, bitmap);
            }
        });
    }

    /**
     * 清除下载任务；2个操作
     */
    public void clearQueue() {
        mRequestHandler.removeMessages(MESSAGE_DOWNLOAD);
        mRequestMap.clear();
    }
}
