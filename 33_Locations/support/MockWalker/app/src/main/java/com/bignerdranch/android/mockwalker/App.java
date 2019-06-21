package com.bignerdranch.android.mockwalker;

import android.app.Application;
import android.os.StrictMode;

/**
 * @author Administrator  on 2019/6/20.
 */
public class App extends Application {
    private static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        StrictMode.enableDefaults();
    }
}
