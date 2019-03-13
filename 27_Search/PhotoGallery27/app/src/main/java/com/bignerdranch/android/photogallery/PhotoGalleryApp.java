package com.bignerdranch.android.photogallery;

import android.app.Application;
import android.os.StrictMode;

/**
 * @author admin  on 2019/3/13.
 */
public class PhotoGalleryApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //TODO 开启严格检测模式：将检测以下的问题
        // 在主线程上发起网络请求
        // 在主线程上做了磁盘读写
        // Activity未及时销毁（又称为activity泄露）
        // SQLite数据库游标未关闭
        // 网络通信使用了明文（未使用SSL/TLS加密）
        StrictMode.enableDefaults();
    }
}
