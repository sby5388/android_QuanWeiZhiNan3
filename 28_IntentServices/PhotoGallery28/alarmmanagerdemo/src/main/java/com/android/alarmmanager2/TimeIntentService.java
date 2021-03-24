package com.android.alarmmanager2;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

/**
 * @author Administrator  on 2020/11/6.
 */
public class TimeIntentService extends IntentService {
    public static final String TAG = TimeIntentService.class.getSimpleName();

    public TimeIntentService() {
        super(TAG);
    }

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private int mNumber;

    public static Intent newIntent(Context context, int number) {
        final Intent intent = new Intent(context, TimeIntentService.class);
        intent.putExtra("number", number);
        return intent;
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
        if (intent == null) {
            Log.d(TAG, "onStart: ");
            return;
        }
        final int number = intent.getIntExtra("number", -1);
        Log.d(TAG, "onStart: number = " + number);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null) {
            return;
        }
        mNumber = intent.getIntExtra("number", -1);
        Log.d(TAG, "onHandleIntent: start number = " + mNumber);
        SystemClock.sleep(TimeUnit.SECONDS.toMillis(5));
        Log.d(TAG, "onHandleIntent: end number = " + mNumber);
        mHandler.post(() -> Toast.makeText(TimeIntentService.this, "号码 = " + mNumber, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: number = " + mNumber);
        mHandler.post(() -> Toast.makeText(TimeIntentService.this, "号码1 = " + mNumber, Toast.LENGTH_SHORT).show());
        super.onDestroy();
    }
}
