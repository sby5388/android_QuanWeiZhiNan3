package com.bignerdranch.android.mockwalker;

import android.app.Application;
import android.app.NotificationChannel;
import android.os.Build;

import androidx.core.app.NotificationCompat;

/**
 * @author Administrator  on 2019/6/20.
 */
public class App extends Application {
    private static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        registerNotificationChannelId();
    }

    /**
     * 注册通知渠道
     */
    private void registerNotificationChannelId() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return;
        }
//        NotificationCompat.getChannelId()

    }
}
