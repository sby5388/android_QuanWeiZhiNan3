package com.android.alarmmanager;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 */
public class MyIntentService extends IntentService {
    public static final String TAG = "MyIntentService";
    private static final long TEST_INTERVAL_MS = TimeUnit.MINUTES.toMillis(30);


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Log.d(TAG, "onHandleIntent: " + "开始打印");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        Log.d(TAG, "onHandleIntent: " + dateFormat.format(new Date(calendar.getTimeInMillis())));
    }


    public static Intent newIntent(Context context) {
        return new Intent(context, MyIntentService.class);
    }

    public MyIntentService() {
        super(TAG);
    }

    public static void start(Context context, boolean run) {
        Intent intent = newIntent(context);
        PendingIntent pendingIntent =
                PendingIntent.getService(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager == null) {
            return;
        }
        if (run) {
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime(), TEST_INTERVAL_MS, pendingIntent);
        } else {
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }
    }
}
