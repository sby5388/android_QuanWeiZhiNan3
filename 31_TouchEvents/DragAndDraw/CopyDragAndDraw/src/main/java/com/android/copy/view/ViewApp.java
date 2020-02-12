package com.android.copy.view;

import android.app.Application;
import android.os.StrictMode;

/**
 * @author Administrator  on 2019/6/17.
 */
public class ViewApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        StrictMode.enableDefaults();
    }
}
