package com.bignerdranch.android.mockwalker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class StartActivity extends AppCompatActivity {

    public static final String TAG = StartActivity.class.getSimpleName();
    private static final String[] permissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.FOREGROUND_SERVICE,
    };
    private static final int REQUEST_CODE = 100;
    private static final int REQUEST_SETTING = 200;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        findViewById(R.id.button_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toMain();
            }
        });
        toMain();
    }

    private void toMain() {
        boolean hasPermission = true;
        final List<String> needPermissions = new ArrayList<>();
        for (String s : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, s)) {
                needPermissions.add(s);
                hasPermission = false;
            }
        }

        if (!hasPermission) {
            boolean showPermissionDialog = false;
            for (String s : needPermissions) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, s)) {
                    Log.d(TAG, "toMain: true = " + s);
                    showPermissionDialog = true;
                } else {
                    Log.d(TAG, "toMain: false = " + s);
                }
            }
            if (!showPermissionDialog) {
                ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
            } else {
                Toast.makeText(this, "权限已被禁止,请打开设置授权", Toast.LENGTH_LONG).show();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toSetting();
                    }
                }, TimeUnit.SECONDS.toMillis(2));
            }
            return;
        }

        startActivity(new Intent(StartActivity.this, MockWalkerActivity.class));
        this.finish();
    }


    private void toSetting() {
        final Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        final Uri data = Uri.fromParts("package", getApplication().getPackageName(), null);
        intent.setData(data);
        startActivityForResult(intent, REQUEST_SETTING);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
         */
        if (requestCode == REQUEST_SETTING) {
            // TODO: 2020/1/3
            toMain();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            toMain();
        }
    }
}
