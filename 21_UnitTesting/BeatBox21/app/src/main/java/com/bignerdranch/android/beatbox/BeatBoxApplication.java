package com.bignerdranch.android.beatbox;

import android.app.Application;
import android.os.StrictMode;

/**
 * @author Administrator  on 2019/6/14.
 */
public class BeatBoxApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        StrictMode.enableDefaults();
        final StrictMode.ThreadPolicy threadPolicy = StrictMode.allowThreadDiskWrites();
    }
}
