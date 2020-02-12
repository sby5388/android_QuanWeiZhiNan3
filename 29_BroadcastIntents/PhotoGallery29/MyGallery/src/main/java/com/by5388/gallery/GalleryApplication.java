package com.by5388.gallery;

import android.app.Application;
import android.os.Handler;
import android.os.StrictMode;

/**
 * @author Administrator  on 2019/12/12.
 */
public class GalleryApplication extends Application {
    private static GalleryApplication sApplication;
    private Handler mHandler;
    private boolean mEnableStrictMode = false;

    public static GalleryApplication getApplication() {
        return sApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // TODO: 2019/12/13 自定义检测行为 假如应用违反了防御策略， 你想定制应对行为， 可使用ThreadPolicy.Builder 和
        //  VmPolicy.Builder类定制。你可以定制的应对行为有：控制是否抛出异常，弹出对话框或是日
        //  志记录违反策略警示信息
        if (mEnableStrictMode) {
            StrictMode.enableDefaults();
        } else {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .build());
        }
        sApplication = this;
        mHandler = new Handler();
    }

    public void runOnUiThread(Runnable runnable) {
        try {
            mHandler.post(runnable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
