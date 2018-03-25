package com.zhoukp.signer.module.home;

import java.util.List;

/**
 * @author zhoukp
 * @time 2018/3/22 22:24
 * @email 275557625@qq.com
 * @function
 */

public class BannerBean {

    /**
     * data : [{"bannerIndex":1,"bannerUrl":"/banner/1.png","uploadTime":"2018-03-22 10:47:50"},{"bannerIndex":2,"bannerUrl":"/banner/2.png","uploadTime":"2018-03-22 10:47:52"},{"bannerIndex":3,"bannerUrl":"/banner/3.png","uploadTime":"2018-03-22 10:47:54"},{"bannerIndex":4,"bannerUrl":"/banner/4.png","uploadTime":"2018-03-22 10:47:58"}]
     * status : 200
     * time : 2018-03-22 22:22:27
     */

    private int status;
    private String time;
    /**
     * bannerIndex : 1
     * bannerUrl : /banner/1.png
     * uploadTime : 2018-03-22 10:47:50
     */

    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private int bannerIndex;
        private String bannerUrl;
        private String uploadTime;

        public int getBannerIndex() {
            return bannerIndex;
        }

        public void setBannerIndex(int bannerIndex) {
            this.bannerIndex = bannerIndex;
        }

        public String getBannerUrl() {
            return bannerUrl;
        }

        public void setBannerUrl(String bannerUrl) {
            this.bannerUrl = bannerUrl;
        }

        public String getUploadTime() {
            return uploadTime;
        }

        public void setUploadTime(String uploadTime) {
            this.uploadTime = uploadTime;
        }
    }
}
