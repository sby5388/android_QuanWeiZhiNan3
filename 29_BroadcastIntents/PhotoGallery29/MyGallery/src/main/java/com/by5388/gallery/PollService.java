package com.by5388.gallery;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.annotation.NonNull;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator  on 2019/12/13.
 */
public class PollService {
    private static final String TAG = "PollService";
    private static final long POLL_INTERVAL_MS = TimeUnit.MINUTES.toMillis(15);
    public static final String SHOW_NOTIFICATION = "com.by5388.gallery.SHOW_NOTIFICATION";
    //给广播带上权限
    public static final String PERM_PRIVATE = "com.by5388.gallery.PRIVATE";
    public static final String REQUEST_CODE = "REQUEST_CODE";
    public static final String NOTIFICATION = "NOTIFICATION";


    public static Intent newIntent(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return PollJobService.newIntent(context);
        }
        return PollIntentService.newIntent(context);
    }


    public static void showBackgroundNotification(Context context, int requestCode) {
        final Intent broadcast = new Intent(SHOW_NOTIFICATION);
        broadcast.putExtra(REQUEST_CODE, requestCode);
        broadcast.putExtra(NOTIFICATION, createNotification(context));
        context.sendOrderedBroadcast(broadcast, PERM_PRIVATE, null, null,
                Activity.RESULT_OK, null, null);
    }

    private static Notification createNotification(Context context) {
        final Intent intent = PhotoGalleryActivity.newIntent(context);
        final PendingIntent activity = PendingIntent.getActivity(context, 0, intent, 0);

        final Notification notification = new Notification.Builder(context)
                .setTicker("发现新照片")
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle("新照片")
                .setContentText("这里发现了一张   新照片")
                .setContentIntent(activity)
                .setAutoCancel(true)
                .build();
        return notification;
    }


    /**
     * 开启或者关闭闹钟服务
     *
     * @param context
     * @param isOn
     */
    public static void setServiceAlarm(@NonNull Context context, boolean isOn) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            PollJobService.setServiceAlarm(context, isOn);
        } else {
            PollIntentService.setServiceAlarm(context, isOn);
        }
        QueryPreferences.setAlarmOn(context, isOn);
    }


    /**
     * 定时器任务是否已经开启
     *
     * @return
     */
    public static boolean isServiceAlarmOn(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return PollJobService.isServiceAlarmOn(context);
        }
        return PollIntentService.isServiceAlarmOn(context);
    }

    /**
     * 网络是否可用及可连接网络
     * TODO  网络状态的信息
     *
     * @return
     */
    public static boolean isNetworkAvailableAndConnected(Context context) {
        final ConnectivityManager cm = Objects.requireNonNull((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        //可用
        final boolean available = cm.getActiveNetworkInfo() != null;
        if (!available) {
            return false;
        }
        //可联网
        final boolean connected = cm.getActiveNetworkInfo().isConnected();
        return connected;

    }

}
