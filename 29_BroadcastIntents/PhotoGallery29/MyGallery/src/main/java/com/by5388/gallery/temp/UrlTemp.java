package com.by5388.gallery.temp;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Toast;

/**
 * @author Administrator  on 2019/12/31.
 */
public class UrlTemp {
    /**
     * webView 打开链接时 对链接类型进行处理
     *
     * @param context
     * @param path
     */
    void tempTest(@NonNull final Context context, final String path) {
        final Uri parse = Uri.parse(path);
        final String scheme = parse.getScheme();
        System.out.println(scheme);
        if ("http".equals(scheme) || "https".equals(scheme)) {
            System.err.println("网址类型");
            return;
        }
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(parse);
        try {
            context.getApplicationContext().startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "没有合适的应用", Toast.LENGTH_SHORT).show();
        }

    }
}
