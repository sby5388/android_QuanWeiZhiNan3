package com.bignerdranch.android.photogallery.zhuangbi;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Handler之间相互通信：直接用另一个就好了
 *
 * @author admin  on 2019/3/13.
 */
public class ZbDownloadHandlerThread<T> extends HandlerThread {
    private static final String TAG = "ZbDownloadHandlerThread";
    private static final int MESSAGE_DOWNLOAD = 100;
    private boolean mHasQuit = false;

    private ZbThumbnailDownloadListener<T> mZbThumbnailDownloadListener;


    public void setZbThumbnailDownloadListener(ZbThumbnailDownloadListener<T> listener) {
        mZbThumbnailDownloadListener = listener;
    }

    /**
     * TODO 请求Handler:
     * 用于后台获取资源，工作于非UI线程
     */
    private Handler mRequestHandler;

    /**
     * TODO 响应Handler:
     * 当工作线程把事情完成之后，要更新UI时，把数据传给这个handler；工作于UI线程
     */
    private Handler mResponseHandler;
    /**
     * todo ?? 这是一个什么Map?
     * 这是一种线程安全的HashMap。这里，使用一个标记下载请求的T类型对象作为key，
     * 我们可以存取和请求关联的URL下载链接。（这个标记对象是PhotoHolder，
     * 下载结果就能很方便地发送给显示图片的UI元素。）
     */
    private ConcurrentMap<T, String> mRequestMap;


    /**
     * 下载监听：回调
     */
    public interface ZbThumbnailDownloadListener<T> {
        /**
         * 回调
         *
         * @param target    {@link T}
         * @param thumbnail 缩略图
         */
        void onThumbnailDownloaded(T target, Bitmap thumbnail);
    }


    public ZbDownloadHandlerThread(Handler responseHandler) {
        super(TAG);
        this.mResponseHandler = responseHandler;

        mRequestMap = new ConcurrentHashMap<>();
    }

    /**
     * TODO: 2019/3/13 把 mRequestHandler 初始化放在构造
     * 方法中，导致 mRequestHandler 运行在主线程之中，
     * 访问网络即刻闪退
     * todo:onLooperPrepared() 运行在
     * HandlerThread:Thread.run()之中，即运行在子线程的，可以进行耗时操作，而不阻塞主线程
     */
    @SuppressLint("HandlerLeak")
    @Override
    protected void onLooperPrepared() {
        mRequestHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MESSAGE_DOWNLOAD) {
                    T target = (T) msg.obj;
                    Log.d(TAG, "handleMessage: url = " + mRequestMap.get(target));
                    handleRequest(target);
                }
            }
        };
    }

    private void handleRequest(final T target) {
        try {
            final String url = mRequestMap.get(target);
            if (url == null) {
                return;
            }
            byte[] urlBytes = new ZhuangbiQuery().getUrlBytes(url);
            final Bitmap bitmap = BitmapFactory.decodeByteArray(urlBytes, 0, urlBytes.length);
            Log.d(TAG, "handleRequest: ");
            // TODO: 2019/3/13 这里能够加载到数据
            mResponseHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mRequestMap.get(target) == null) {
                        return;
                    }
                    if (!mRequestMap.get(target).equals(url) || mHasQuit) {
                        return;
                    }
                    mRequestMap.remove(target);
                    mZbThumbnailDownloadListener.onThumbnailDownloaded(target, bitmap);
                }
            });
        } catch (IOException e) {
            System.err.println(e);
        }

    }


    /**
     * 查询缩略图
     * query 查询
     * Thumbnail 缩略图
     *
     * @param target 目标，例如是显示图片的宿主：ImageView
     * @param url    地址
     */
    public void queryThumbnail(T target, String url) {
        Log.d(TAG, "queryThumbnail: url  = " + url);
        if (url == null) {
            mRequestMap.remove(target);
        } else {
            mRequestMap.put(target, url);
            mRequestHandler.obtainMessage(MESSAGE_DOWNLOAD, target).sendToTarget();
        }
    }

    public void clearQueue() {
        //移除下载任务
        mRequestHandler.removeMessages(MESSAGE_DOWNLOAD);
        mRequestMap.clear();
    }

    @Override
    public boolean quit() {
        mHasQuit = true;
        return super.quit();
    }
}
