package com.zhoukp.signer.module.main;

import java.util.List;

/**
 * @author zhoukp
 * @time 2018/3/30 15:34
 * @email 275557625@qq.com
 * @function
 */
public class UpdateBean {


    /**
     * data : [{"appName":"IamHere.apk","downloadUrl":"/files/IamHere.apk","description":"新版本狂拽炫酷吊炸天，快下载吧！！","updateTime":"2018-03-30 10:47:50","apkSize":56910070,"versionName":"1.0.10","versionCode":2}]
     * time : 2018-03-30 16:01:27
     * status : 200
     */

    private String time;
    private int status;
    /**
     * appName : IamHere.apk
     * downloadUrl : /files/IamHere.apk
     * description : 新版本狂拽炫酷吊炸天，快下载吧！！
     * updateTime : 2018-03-30 10:47:50
     * apkSize : 56910070
     * versionName : 1.0.10
     * versionCode : 2
     */

    private List<DataBean> data;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String appName;
        private String downloadUrl;
        private String description;
        private String updateTime;
        private int apkSize;
        private String versionName;
        private int versionCode;

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public int getApkSize() {
            return apkSize;
        }

        public void setApkSize(int apkSize) {
            this.apkSize = apkSize;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }
    }
}
