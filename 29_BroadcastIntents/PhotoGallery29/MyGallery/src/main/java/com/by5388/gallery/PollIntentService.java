package com.by5388.gallery;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator  on 2019/12/13.
 */
public class PollIntentService extends IntentService {
    private static final String TAG = "PollIntentService";
    private static final long POLL_INTERVAL_MS = TimeUnit.MINUTES.toMillis(15);
    public static final String REQUEST_CODE = "REQUEST_CODE";
    public static final String NOTIFICATION = "NOTIFICATION";

    public static Intent newIntent(Context context) {
        return new Intent(context, PollIntentService.class);
    }

    public PollIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null) {
            return;
        }
        if (!PollService.isNetworkAvailableAndConnected(this)) {
            Toast.makeText(this, "网络不可用", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d(TAG, "onHandleIntent: intent = " + intent.toString());
        Log.d(TAG, "onHandleIntent: thread = " + Thread.currentThread().getName());

        final String storedQuery = QueryPreferences.getStoredQuery();
        final String lastResultId = QueryPreferences.getLastResultId();
        List<GalleryItem> itemList;
        if (TextUtils.isEmpty(storedQuery)) {
            itemList = new FlickrFetchr().fetchItems(1);
        } else {
            itemList = new FlickrFetchr().searchPhotos(storedQuery);
        }
        if (itemList == null || itemList.size() == 0) {
            return;
        }
        final String resultId = itemList.get(0).getId();
        if (lastResultId.equals(resultId)) {
            Log.i(TAG, "Got an old result: " + resultId);
        } else {
            Log.i(TAG, "Got an old result: " + resultId);
            showNotification();
        }
        QueryPreferences.setLastResultId(resultId);

    }

    private void showNotification() {
        PollService.showBackgroundNotification(this, 0);
    }



    /**
     * 开启或者关闭闹钟服务
     *
     * @param context
     * @param isOn
     */
    public static void setServiceAlarm(@NonNull Context context, boolean isOn) {
        final Intent intent = newIntent(context);
        // TODO: 2019/12/24 最后一个参数的意思
        final int flags = 0;
        final PendingIntent pi = PendingIntent.getService(context, 0, intent, flags);

        final AlarmManager alarmManager = Objects.requireNonNull((AlarmManager) context.getSystemService(Context.ALARM_SERVICE));

        if (isOn) {
            //类型：相对时间 ,AlarmManager.ELAPSED_REALTIME使用自最近一次设备重启（包括睡眠时间）开始走过的时间量作为间隔计算基准
            final int type = AlarmManager.ELAPSED_REALTIME;
            //经过一段指定的时间，就启动定时器
            final long triggerAtMillis = SystemClock.elapsedRealtime();
            //  间隔触发时间，单位毫秒.api19 之后最低为1分钟
            final long intervalMillis = POLL_INTERVAL_MS;
            //alarmManager 执行的intent
            final PendingIntent operation = pi;
            alarmManager.setRepeating(type, triggerAtMillis, intervalMillis, operation);
        } else {
            //取消定时器
            alarmManager.cancel(pi);
            //取消任务
            pi.cancel();

        }
    }


    /**
     * 定时器任务是否已经开启
     *
     * @return
     */
    public static boolean isServiceAlarmOn(Context context) {
        final Intent intent = newIntent(context);
        //TODO  FLAG_NO_CREATE : 如果 intent 对应的pendingIntent 不存在的话，就不创建它
        int flags = PendingIntent.FLAG_NO_CREATE;
        final PendingIntent pi = PendingIntent.getService(context, 0, intent, flags);
        return pi != null;
    }

}
