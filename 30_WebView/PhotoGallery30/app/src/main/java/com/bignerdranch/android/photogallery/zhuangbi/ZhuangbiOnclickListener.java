package com.bignerdranch.android.photogallery.zhuangbi;

import android.net.Uri;

/**
 * @author admin  on 2019/3/15.
 */
public interface ZhuangbiOnclickListener {
    /**
     * 获取图片链接
     *
     * @param uri 链接
     */
    void pushUri(Uri uri);
}
