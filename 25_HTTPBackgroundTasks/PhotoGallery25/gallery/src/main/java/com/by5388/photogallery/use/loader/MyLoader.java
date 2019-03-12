package com.by5388.photogallery.use.loader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

/**
 * @author admin  on 2019/3/12.
 */
public class MyLoader extends AsyncTaskLoader<Man> {

    public MyLoader(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public Man loadInBackground() {
        return null;
    }
}
