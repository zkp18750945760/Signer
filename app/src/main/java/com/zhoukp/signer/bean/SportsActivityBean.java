package com.zhoukp.signer.bean;

import java.util.List;

/**
 * @author zhoukp
 * @time 2018/1/31 13:30
 * @email 275557625@qq.com
 * @function
 */

public class SportsActivityBean {

    /**
     * data : [{"theme":"\u201c3v3\u201d篮球赛","date":"时间:5月5日-7日 16:30","address":"地点:灯光篮球场","imgurl":"1.png"},{"theme":"学院排球赛","date":"时间:5月8日-9日 15:00","address":"地点:排球场","imgurl":"2.png"},{"theme":"新生杯篮球赛","date":"时间:5月10日-12日 16:00","address":"地点:篮球场","imgurl":"3.png"}]
     * error : 0
     */

    private int error;
    /**
     * theme : “3v3”篮球赛
     * date : 时间:5月5日-7日 16:30
     * address : 地点:灯光篮球场
     * imgurl : 1.png
     */

    private List<DataBean> data;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String theme;
        private String date;
        private String address;
        private String imgurl;

        public String getTheme() {
            return theme;
        }

        public void setTheme(String theme) {
            this.theme = theme;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }
    }
}
