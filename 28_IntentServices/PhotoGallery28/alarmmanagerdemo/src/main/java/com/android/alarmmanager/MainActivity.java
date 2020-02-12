package com.android.alarmmanager;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.File;

import static android.app.DownloadManager.ACTION_VIEW_DOWNLOADS;

public class MainActivity extends AppCompatActivity {
    private long mLong = -1L;
    private static final int REQUEST_CODE = 100;
    private DownloadManager mDownloadManager;
    private static final String[] PERMISSION_ARRAY = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private int temp = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.registerReceiver(mReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        mDownloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        ToggleButton toggleButton = findViewById(R.id.toggle_button);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MyIntentService.start(MainActivity.this, isChecked);
            }
        });


        findViewById(R.id.button_test_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2019/12/24 下载东西应该使用DownloadManager
//             final   Intent intent = new Intent(ACTION_VIEW_DOWNLOADS);
                if (temp == 0) {
                    downloadTest();
                    temp++;
                } else {
                    openDownloadList();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    private void openDownloadList() {
        final Intent intent = new Intent(ACTION_VIEW_DOWNLOADS);
        if (false) {
            String uriString = "http://www.gd.gov.cn/gdwjfj/%E5%B9%BF%E4%B8%9C%E7%9C%81%E5%A4%9A%E5%BC%8F%E8%81%94%E8%BF%90%E5%9E%8B%E8%B4%A7%E8%BF%90%E6%9E%A2%E7%BA%BD%EF%BC%88%E7%89%A9%E6%B5%81%E5%9B%AD%E5%8C%BA%EF%BC%89%E9%A1%B9%E7%9B%AE%E8%A1%A8.doc";
            intent.putExtra("url", uriString);
            intent.putExtra("path", uriString);
            intent.putExtra("remotePath", uriString);
        }
        MainActivity.this.startActivity(intent);
    }

    private void downloadTest() {
        boolean getPermission = getStoragePermission();
        if (!getPermission) {
            Toast.makeText(this, "请授权", Toast.LENGTH_SHORT).show();
            requestPermissions(PERMISSION_ARRAY, REQUEST_CODE);
            return;
        }


        final String docUrl = "http://www.gd.gov.cn/gdwjfj/%E5%B9%BF%E4%B8%9C%E7%9C%81%E5%A4%9A%E5%BC%8F%E8%81%94%E8%BF%90%E5%9E%8B%E8%B4%A7%E8%BF%90%E6%9E%A2%E7%BA%BD%EF%BC%88%E7%89%A9%E6%B5%81%E5%9B%AD%E5%8C%BA%EF%BC%89%E9%A1%B9%E7%9B%AE%E8%A1%A8.doc";

        final String fileName = "Doc.doc";

        // TODO: 2019/12/24 增加一个下载队列
        final DownloadManager.Request request = new DownloadManager.Request(Uri.parse(docUrl));
        {
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            request.setTitle("下载文档");
            request.setDescription(fileName + "下载中...");
            request.setVisibleInDownloadsUi(true);
            File path = new File(Environment.getExternalStorageDirectory(), fileName);
            path = new File(path, fileName);
            request.setDestinationUri(Uri.fromFile(path));
        }
        mLong = mDownloadManager.enqueue(request);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkStatus();
        }
    };

    private boolean getStoragePermission() {
        boolean b = true;
        for (String s : PERMISSION_ARRAY) {
            if (checkSelfPermission(s) != PackageManager.PERMISSION_GRANTED) {
                b = false;
                break;
            }
        }
        return b;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            checkStatus();
        }
    }

    private void checkStatus() {
        final DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(mLong);
        final Cursor cursor = mDownloadManager.query(query);
        if (cursor == null) {
            return;
        }
        if (cursor.moveToFirst()) {
            final int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            if (DownloadManager.STATUS_SUCCESSFUL == status) {
                //下载成功
                final Intent intent = new Intent(ACTION_VIEW_DOWNLOADS);
                MainActivity.this.startActivity(intent);
            }
        }
        cursor.close();
    }


}
