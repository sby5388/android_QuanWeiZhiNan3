package com.by5388.photogallery.zhuang.bi.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author admin  on 2019/3/13.
 */
class UploadBean {
    /**
     * id : 5795
     * name : null
     * description : 猫拍金馆长头
     * disk : qiniu
     * path : i/2016-07-25-fff143e216fec00ff58dc5e4672bd3ad.gif
     * size : 62289
     * user_id : 1
     * created_at : 2016-07-25 15:08:12
     * updated_at : 2018-09-17 01:49:22
     * uploadable_id : null
     * uploadable_type : null
     * weibo_url : https://ws1.sinaimg.cn/large/54d358dbly1fvbwv9jce6g202s025ta4.gif
     * url : https://ws1.sinaimg.cn/large/54d358dbly1fvbwv9jce6g202s025ta4.gif
     */

    private int id;
    private Object name;
    private String description;
    private String disk;
    private String path;
    private int size;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("created_at")
    private String createdTime;
    @SerializedName("updated_at")
    private String updatedTime;
    @SerializedName("weibo_url")
    private String weiboUrl;
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisk() {
        return disk;
    }

    public void setDisk(String disk) {
        this.disk = disk;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }


    public String getWeiboUrl() {
        return weiboUrl;
    }

    public void setWeiboUrl(String weiboUrl) {
        this.weiboUrl = weiboUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
