package com.zhoukp.signer.module.home;

import java.util.List;

/**
 * @author zhoukp
 * @time 2018/3/22 20:54
 * @email 275557625@qq.com
 * @function
 */

public class ScheduleBean {

    /**
     * data : [{"date":22,"week":"星期四","address":"实验室A434","time":"11-13节","type":1,"content":"#软件集成开发环境(Java)"},{"date":22,"week":"星期四","address":"C1-401","time":"7-8节","type":1,"content":"#高等数学C(一)"}]
     * time : 2018-03-22 20:54:03
     * status : 200
     */

    private String time;
    private int status;
    /**
     * date : 22
     * week : 星期四
     * address : 实验室A434
     * time : 11-13节
     * type : 1
     * content : #软件集成开发环境(Java)
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
        private int date;
        private String week;
        private String address;
        private String time;
        private int type;
        private String content;

        public int getDate() {
            return date;
        }

        public void setDate(int date) {
            this.date = date;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
