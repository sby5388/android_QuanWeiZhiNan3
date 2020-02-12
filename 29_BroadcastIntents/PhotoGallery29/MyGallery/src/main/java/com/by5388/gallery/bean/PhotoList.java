package com.by5388.gallery.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * dataStruct create from gson
 * @author Administrator  on 2019/12/12.
 */
public final class PhotoList {

    /**
     * page : 1
     * pages : 10
     * perpage : 100
     * total : 1000
     * photo : [{"id":"49206111903","owner":"54432981@N00","secret":"ef3becc519","server":"65535","farm":66,"title":"L1008560.jpg","ispublic":1,"isfriend":0,"isfamily":0,"url_s":"https://live.staticflickr.com/65535/49206111903_ef3becc519_m.jpg","height_s":160,"width_s":240},{"id":"49206112508","owner":"26532149@N04","secret":"2b293c07e1","server":"65535","farm":66,"title":"La neige est toujours présente dans les sentiers du parc national de la Jacques-Cartier // Snow is still present in the trails of Jacques Cartier National Park #raquettes #snowshoeing #paysage #winterlandscape #neige #snow #hiver #winter #tourguide #guide","ispublic":1,"isfriend":0,"isfamily":0,"url_s":"https://live.staticflickr.com/65535/49206112508_2b293c07e1_m.jpg","height_s":240,"width_s":240}]
     */

    @SerializedName("page")
    public int mPage;
    @SerializedName("pages")
    public int mPages;
    @SerializedName("perpage")
    public int mPerpage;
    @SerializedName("total")
    public int mTotal;
    @SerializedName("photo")
    public List<Photo> mPhoto;
}
