package com.by5388.gallery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * TODO 显式广播/Intent：指定了ComponentName 只能被指定的对象接收
 * TODO 隐式广播/Intent：只包括了Action,可以被多个对象接收
 * 有序广播：按照优先级依次传递，可以被阻断
 * 无序广播：无序
 * 不能在广播接收者中执行耗时操作、异步操作，但可以用于启动Activity 与 Service
 *
 * @author Administrator  on 2019/12/31.
 */
public class StartupReceiver extends BroadcastReceiver {
    public static final String TAG = StartupReceiver.class.getSimpleName();


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Received broadcast intent: " + intent.getAction());

        boolean isOn = QueryPreferences.isAlarmOn(context);
        PollService.setServiceAlarm(context, isOn);
    }
}
