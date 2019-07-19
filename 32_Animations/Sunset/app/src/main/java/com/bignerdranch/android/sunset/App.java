package com.bignerdranch.android.sunset;

import android.app.Application;
import android.os.StrictMode;

/**
 * @author Administrator  on 2019/7/19.
 */
class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        StrictMode.enableDefaults();
    }
}
