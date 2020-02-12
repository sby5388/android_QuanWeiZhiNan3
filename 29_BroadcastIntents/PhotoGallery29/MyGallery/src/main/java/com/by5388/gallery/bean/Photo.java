package com.by5388.gallery.bean;

import com.google.gson.annotations.SerializedName;

/**
 * dataStruct create from gson
 * @author Administrator  on 2019/12/12.
 */
public final class Photo {
    /**
     * id : 49206111903
     * owner : 54432981@N00
     * secret : ef3becc519
     * server : 65535
     * farm : 66
     * title : L1008560.jpg
     * ispublic : 1
     * isfriend : 0
     * isfamily : 0
     * url_s : https://live.staticflickr.com/65535/49206111903_ef3becc519_m.jpg
     * height_s : 160
     * width_s : 240
     */

    @SerializedName("id")
    public String mId;
    @SerializedName("owner")
    public String mOwner;
    @SerializedName("secret")
    public String mSecret;
    @SerializedName("server")
    public String mServer;
    @SerializedName("farm")
    public int mFarm;
    @SerializedName("title")
    public String mTitle;
    @SerializedName("ispublic")
    public int mIsPublic;
    @SerializedName("isfriend")
    public int mIsfriend;
    @SerializedName("isfamily")
    public int mIsfamily;
    @SerializedName("url_s")
    public String mUrl;
    @SerializedName("height_s")
    public int mHeight;
    @SerializedName("width_s")
    public int mWidth;
}
