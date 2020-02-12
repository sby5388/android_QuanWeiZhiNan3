package com.by5388.gallery;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import java.util.Objects;

/**
 * @author Administrator  on 2019/12/31.
 */
public class VisibleFragment extends Fragment {
    private static final String TAG = "VisibleFragment";

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(getContext(), "新的图片", Toast.LENGTH_SHORT).show();
            //Activity.RESULT_CANCELED:如果是有序广播，就会阻止继续传播
            final int resultCode = getResultCode();
            Log.d(TAG, "onReceive: resultCode = " + resultCode);
            if (resultCode != Activity.RESULT_OK) {
                return;
            }
            //TODO 页面打开时，取消显示通知栏，阻断传播
            setResultCode(Activity.RESULT_CANCELED);
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        final IntentFilter intentFilter = new IntentFilter(PollService.SHOW_NOTIFICATION);
        //PollService.PERM_PRIVATE :注册广播时带上的自定义权限
        Objects.requireNonNull(getContext()).registerReceiver(mReceiver, intentFilter, PollService.PERM_PRIVATE, null);
    }

    @Override
    public void onStop() {
        super.onStop();
        Objects.requireNonNull(getContext()).unregisterReceiver(mReceiver);
    }
}
