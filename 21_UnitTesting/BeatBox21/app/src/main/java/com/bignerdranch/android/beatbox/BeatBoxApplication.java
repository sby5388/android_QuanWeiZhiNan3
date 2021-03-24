package com.bignerdranch.android.beatbox;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;

/**
 * @author Administrator  on 2019/6/14.
 */
public class BeatBoxApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            //开启默认的检测策略以及输出策略
            //StrictMode.enableDefaults();
            //
            final StrictMode.ThreadPolicy.Builder tpBuilder =
                    new StrictMode.ThreadPolicy.Builder()
                            //检测所有可疑的行为
                            //.detectAll()
                            //检测网络
                            .detectNetwork()
                            //检测读磁盘
                            .detectDiskReads()
                            //检测写磁盘
                            .detectDiskWrites();

            final int targetSdk = Build.VERSION.SDK_INT;
            if (targetSdk >= Build.VERSION_CODES.HONEYCOMB) {
                //检测缓慢的调用
                tpBuilder.detectCustomSlowCalls();
            }
            if (targetSdk >= Build.VERSION_CODES.M) {
                //检测资源不匹配，如没有相对应大小的图片资源
                tpBuilder.detectResourceMismatches();
            }
            if (targetSdk >= Build.VERSION_CODES.O) {
                //检测 没有使用缓冲的输入输出操作
                tpBuilder.detectUnbufferedIo();
            }
            tpBuilder
                    //日志
                    .penaltyLog()
                    //闪屏提示
                    .penaltyFlashScreen()
                    //弹框提示
                    .penaltyDialog()
                    //直接杀死应用
                    .penaltyDeath()
                    //当检测到问题时，相关日志会被发送到DropBoxManager，DropBoxManager收集这些日志，用于调试
                    .penaltyDropBox();
            StrictMode.setThreadPolicy(tpBuilder.build());

            final StrictMode.VmPolicy.Builder vmBuilder = new StrictMode.VmPolicy.Builder()
                    //检测所有
                    .detectAll()
                    //检测数据库的cursor没有关闭造成的泄露
                    //检查是否有SQLite对象在使用后，未被关闭
                    .detectLeakedSqlLiteObjects()
                    //检测Activity内存泄露
                    .detectActivityLeaks()
                    //检测需要关闭的Closeable没有被关闭造成的泄露
                    .detectLeakedClosableObjects()
                    //检测绑定服务的未解除绑定、注册广播接收者未解除接收造成的内存
                    //检查当Context被销毁时，ServiceConnection或BroadcastReceiver是否存在泄漏
                    .detectLeakedRegistrationObjects()
                    //检测文件未使用FileProvider造成文件权限失败的问题
                    .detectFileUriExposure();
            if (targetSdk >= Build.VERSION_CODES.M) {
                //检测网络明文传输
                vmBuilder.detectCleartextNetwork();
            }

            if (targetSdk >= Build.VERSION_CODES.O) {
                //检测Uri缺乏权限的问题
                vmBuilder.detectContentUriWithoutPermission();
                //检测socket端口
                vmBuilder.detectUntaggedSockets();
            }
            vmBuilder
                    //当问题发生时，日志会被记录到LogCat
                    .penaltyLog()
                    //当检测到问题时，相关日志会被发送到DropBoxManager，DropBoxManager收集这些日志，用于调试
                    .penaltyDropBox();
            StrictMode.setVmPolicy(vmBuilder.build());

        }
    }
}
