package com.android.copy.view;

/**
 * @author Administrator  on 2021/2/2.
 */
class Temp {

    /**
     * appName : FaceApp
     * appVersion : 2020.10.21.0
     * code : FyHttp
     * data : {"DeviceType":"TZH-L2Y","DeviceMode":"StandAlone"}
     * deviceId : FPAL22010011999TZH00001
     * level : debug
     * message : HTTP POST https://zhenjian.3.shanfupai.com/api/sfp/get_display_info OK
     {"code":"1","msg":"成功！","data":{"current_dept":"诊间演示","current_dept_no":"124","current_doctor_no":"0301","current_doctor_name":"闪付派","user_no":"10011051","user_name":"闪小派","total_price":0.01,"order":[{"dept":"演示门诊","dept_no":"124","doctor_no":"0301","doctor_name":"闪小派","order_price":0.01,"create_time":"1572019709","item_id":"yp103","order_detail":[{"item_id":"yp103","detail_id":"10020","group_name":"演示分类","title":"演示药品","price":0.01,"count":1.0,"detail_price":0.01,"CARDNO":"00011051"}]}]}}
     * timestamp : 1612251661219
     * userId : 2500
     */

    @com.google.gson.annotations.SerializedName("appName")
    public String mAppName;
    @com.google.gson.annotations.SerializedName("appVersion")
    public String mAppVersion;
    @com.google.gson.annotations.SerializedName("code")
    public String mCode;
    @com.google.gson.annotations.SerializedName("data")
    public DataBean mData;
    @com.google.gson.annotations.SerializedName("deviceId")
    public String mDeviceId;
    @com.google.gson.annotations.SerializedName("level")
    public String mLevel;
    @com.google.gson.annotations.SerializedName("message")
    public String mMessage;
    @com.google.gson.annotations.SerializedName("timestamp")
    public long mTimestamp;
    @com.google.gson.annotations.SerializedName("userId")
    public String mUserId;

    public static class DataBean {
        /**
         * DeviceType : TZH-L2Y
         * DeviceMode : StandAlone
         */

        @com.google.gson.annotations.SerializedName("DeviceType")
        public String mDeviceType;
        @com.google.gson.annotations.SerializedName("DeviceMode")
        public String mDeviceMode;
    }
}
