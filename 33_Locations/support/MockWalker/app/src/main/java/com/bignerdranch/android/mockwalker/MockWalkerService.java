package com.bignerdranch.android.mockwalker;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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

        {
            // TODO: 2020/2/25
            //新增---------------------------------------------
            String CHANNEL_ID = MockWalkerService.class.getName();
            String CHANNEL_NAME = MockWalkerService.class.getSimpleName();
//            NotificationChannel notificationChannel = null;
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                notificationChannel = new NotificationChannel(CHANNEL_ID,
//                        CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
//                notificationChannel.enableLights(true);
//                notificationChannel.setLightColor(Color.RED);
//                notificationChannel.setShowBadge(true);
//                notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
//                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                manager.createNotificationChannel(notificationChannel);
//            }

            NotificationChannel notificationChannel = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.createNotificationChannel(notificationChannel);
            }

//--------------------------------------------------------新增
//                ————————————————
//            版权声明：本文为CSDN博主「BigSweetee」的原创文章，遵循 CC 4.0 BY - SA 版权协议，转载请附上原文出处链接及本声明。
//            原文链接：https:
//                //blog.csdn.net/qq_15527709/article/details/78853048
        }

        if (false) {
            final Intent shutdownIntent = new Intent(this, ShutdownReceiver.class);
            PendingIntent shutdownPI = PendingIntent.getBroadcast(
                    this, PENDING_SHUTDOWN_ID, shutdownIntent, 0
            );
            final Notification notification = new NotificationCompat.Builder(this, getApplicationContext().getPackageName())
                    .setSmallIcon(android.R.drawable.ic_dialog_map)
                    .setContentTitle(getString(R.string.notification_title))
                    .setContentText(getString(R.string.notification_text))
                    .setTicker(getString(R.string.app_name))
                    .setContentIntent(shutdownPI)
                    .build();

            // TODO: 2020/1/7 这里出现了权限禁止-->闪退
            startForeground(NOTIFICATION_ID, notification);
        }
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
        //throw new UnsupportedOperationException("" + getClass().getName() + " is not a bindable service");
        return null;
    }


    public static Intent newIntent(Context context) {
        return new Intent(context, MockWalkerService.class);
    }

}
