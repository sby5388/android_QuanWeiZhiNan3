package com.bignerdranch.android.photogallery.zhuangbi;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.text.TextUtils;
import android.util.Log;

import com.bignerdranch.android.photogallery.R;
import com.bignerdranch.android.photogallery.zhuangbi.bean.QueryResult;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author admin  on 2019/3/14.
 */
public class ZhuangbiService extends IntentService {
    public static final String TAG = "ZhuangbiService";
    private static final long ZB_INTERVAL_MS = TimeUnit.MINUTES.toMillis(15);


    public static Intent newIntent(Context context) {
        Log.d(TAG, "setServiceAlarm: 我又启动服务了");
        return new Intent(context, ZhuangbiService.class);
    }


    public ZhuangbiService() {
        super(TAG);
    }

    public static boolean isServiceAlarmOn(Context context) {
        Intent i = newIntent(context);
        PendingIntent pi = PendingIntent
                .getService(context, 0, i, PendingIntent.FLAG_NO_CREATE);
        return pi != null;
    }

    public static void setServiceAlarm(Context context, boolean isOn) {

        Intent intent = newIntent(context);
        PendingIntent pi = PendingIntent.getService(
                context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        if (alarmManager == null) {
            return;
        }
        if (isOn) {
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime(), ZB_INTERVAL_MS, pi);
        } else {
            alarmManager.cancel(pi);
            pi.cancel();
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //响应Service:处理Intent
        if (!isNetworkAvailabeAndConnected(this)) {
            return;
        }
        // TODO: 2019/3/14 应当在QueryPreferences之中增加Zb相关的“K-V”对
        String queryWork = ZbQueryPreferences.getStoredQuery(this);
        String queryLastId = ZbQueryPreferences.getLastResultId(this);

        List<QueryResult> results;

        if (queryWork == null) {
            results = new ZhuangbiQuery().getDefault();
        } else {
            results = new ZhuangbiQuery().fetchItems(queryLastId);
        }
        if (results.isEmpty()) {
            return;
        }
        int resultId = results.get(0).getId();
        if (TextUtils.equals(String.valueOf(resultId), queryLastId)) {
            Log.d(TAG, "onHandleIntent: resultId = " + resultId);
        } else {
            Resources resources = getResources();
            Intent i = ZhuangbiActivity.newIntent(this);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, 0);
            //生成一个通知栏
            Notification notification = new NotificationCompat.Builder(this)
                    .setTicker(getText(R.string.app_name))
                    .setSmallIcon(android.R.drawable.ic_menu_camera)
                    .setContentTitle("标题")
                    .setContentText("这是一个非常非常非常非常长的文本")
                    //绑定通知栏点击事件
                    .setContentIntent(pendingIntent)
                    //设置可以被清除
                    .setAutoCancel(true)
                    .build();
            //获取通知栏管理者
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
            //通知栏管理者发布通知
            notificationManagerCompat.notify(0, notification);
        }
        ZbQueryPreferences.setLastResultId(this, String.valueOf(resultId));
    }


    /**
     * 网络是否可用且已经连接上网络
     */
    private boolean isNetworkAvailabeAndConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        if (manager == null) {
            Log.e(TAG, "isNetworkAvailabeAndConnected: ", new Exception("ConnectivityManager manager == null"));
            return false;
        }
        //是否可用
        boolean isNetworkAvailabe = manager.getActiveNetworkInfo() != null;
        //是否能够连接上
        boolean isConnected = isNetworkAvailabe && manager.getActiveNetworkInfo().isConnected();
        return isConnected;
    }
}
