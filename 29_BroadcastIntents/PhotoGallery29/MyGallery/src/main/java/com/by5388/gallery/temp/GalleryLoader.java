package com.by5388.gallery.temp;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;

import java.util.Map;

/**
 * @author Administrator  on 2019/12/12.
 * todo Loader 和 LoaderManager的使用
 * {@link android.support.v4.app.LoaderManager}
 * {@link AsyncTaskLoader}
 */
public class GalleryLoader extends AsyncTaskLoader {
    public GalleryLoader(Context context) {
        super(context);
    }

    @Override
    public Object loadInBackground() {
        return null;
    }


    /**
     * TODO  原生构建Uri的方式,拼装链接
     */
    private void temp(String server) {

        final Uri uri = Uri.parse(server)
                .buildUpon()
                .appendQueryParameter("key1", "value1")
                .appendQueryParameter("key2", "value2")
                .appendQueryParameter("key3", "value3")
                .build();
    }

    /**
     * TODO  原生构建Uri的方式,拼装链接，参数(K,V)
     */
    private void temp(String server, Map<String, String> param) {
        final Uri.Builder builder = Uri.parse(server)
                .buildUpon();
        for (String key : param.keySet()) {
            builder.appendQueryParameter(key, param.get(key));
        }
        final Uri uri = builder.build();
    }
}
