package com.bignerdranch.android.photogallery.zhuangbi.bean;

import com.bignerdranch.android.photogallery.zhuangbi.ZhuangbiItem;
import com.google.gson.annotations.SerializedName;

/**
 * @author admin  on 2019/3/13.
 */
public class QueryResult implements ZhuangbiItem {

    /**
     * id : 2474
     * description : 猫拍金馆长头
     * path :
     * size : 0
     * width : 100
     * height : 77
     * created_at : 2016-07-25 15:08:12
     * updated_at : 2016-07-25 15:08:12
     * user_id : 1
     * permitted_at : 2016-07-25 15:08:12
     * disk :
     * hotpoint : 227
     * channel : admin
     * upload_id : 5795
     * content :
     * provider_name :
     * image_url : https://ws1.sinaimg.cn/large/54d358dbly1fvbwv9jce6g202s025ta4.gif
     * file_size : 60.83 KB
     * upload : {"id":5795,"name":null,"description":"猫拍金馆长头","disk":"qiniu","path":"i/2016-07-25-fff143e216fec00ff58dc5e4672bd3ad.gif","size":62289,"user_id":1,"created_at":"2016-07-25 15:08:12","updated_at":"2018-09-17 01:49:22","uploadable_id":null,"uploadable_type":null,"weibo_url":"https://ws1.sinaimg.cn/large/54d358dbly1fvbwv9jce6g202s025ta4.gif","url":"https://ws1.sinaimg.cn/large/54d358dbly1fvbwv9jce6g202s025ta4.gif"}
     */

    private int id;
    private String description;
    private String path;
    private int size;
    private int width;
    private int height;
    @SerializedName("created_at")
    private String createdTime;
    @SerializedName("updated_at")
    private String updatedTime;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("permitted_at")
    private String permittedTime;
    private String disk;
    private int hotpoint;
    private String channel;
    @SerializedName("upload_id")
    private int uploadId;
    private String content;
    @SerializedName("provider_name")
    private String providerName;
    @SerializedName("image_url")
    private String imageUrl;
    @SerializedName("file_size")
    private String fileSize;
    private UploadBean upload;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPermittedTime() {
        return permittedTime;
    }

    public void setPermittedTime(String permittedTime) {
        this.permittedTime = permittedTime;
    }

    public String getDisk() {
        return disk;
    }

    public void setDisk(String disk) {
        this.disk = disk;
    }

    public int getHotpoint() {
        return hotpoint;
    }

    public void setHotpoint(int hotpoint) {
        this.hotpoint = hotpoint;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public int getUploadId() {
        return uploadId;
    }

    public void setUploadId(int uploadId) {
        this.uploadId = uploadId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public UploadBean getUpload() {
        return upload;
    }

    public void setUpload(UploadBean upload) {
        this.upload = upload;
    }

    @Override
    public String getSHowText() {
        return description;
    }
}
