package com.by5388.demo.beatbox2;

import android.app.Application;
import android.os.StrictMode;

/**
 * @author Administrator  on 2019/12/4.
 */
public class BeatBoxApplication extends Application {
    private static Application sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
//        StrictMode.enableDefaults();
        sInstance = this;
    }

    public static Application getInstance() {
        return sInstance;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        sInstance = null;
    }
}
