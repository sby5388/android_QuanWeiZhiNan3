package com.bignerdranch.android.mockwalker;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

public class MockWalkerService extends Service {
    private static final int NOTIFICATION_ID = 1;
    private static final int PENDING_SHUTDOWN_ID = 1;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //一种严格的模式，只能显式地启动、暂停；相应及时，如适用于后台的音乐播放
        return Service.START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Intent shutdownIntent = new Intent(this, ShutdownReceiver.class);
        PendingIntent shutdownPI = PendingIntent.getBroadcast(
                this, PENDING_SHUTDOWN_ID, shutdownIntent, 0
        );
        Notification notification = new NotificationCompat.Builder(this, getApplicationContext().getPackageName())
                .setSmallIcon(android.R.drawable.ic_dialog_map)
                .setContentTitle(getString(R.string.notification_title))
                .setContentText(getString(R.string.notification_text))
                .setTicker(getString(R.string.app_name))
                .setContentIntent(shutdownPI)
                .build();

        // TODO: 2020/1/7 这里出现了权限禁止-->闪退
        startForeground(NOTIFICATION_ID, notification);
        MockWalker.getInstance().setStarted(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        stopForeground(true);
        MockWalker.getInstance().setStarted(false);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("" + getClass().getName() + " is not a bindable service");
    }


    public static Intent newIntent(Context context) {
        return new Intent(context, MockWalkerService.class);
    }

}
