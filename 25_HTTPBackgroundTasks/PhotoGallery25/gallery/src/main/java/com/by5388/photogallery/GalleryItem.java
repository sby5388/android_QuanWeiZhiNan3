package com.by5388.photogallery;

import com.by5388.photogallery.use.loader.ShowItem;

/**
 * @author Administrator
 */
public class GalleryItem implements ShowItem {
    private String mCaption;
    private String mId;
    private String mUrl;

    public String getCaption() {
        return mCaption;
    }

    public void setCaption(String caption) {
        mCaption = caption;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    @Override
    public String toString() {
        return mCaption;
    }

    @Override
    public String getSHowText() {
        return mCaption;
    }
}
