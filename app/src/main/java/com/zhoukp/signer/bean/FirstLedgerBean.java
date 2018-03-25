package com.zhoukp.signer.bean;

import java.util.List;

/**
 * @author zhoukp
 * @time 2018/2/4 19:07
 * @email 275557625@qq.com
 * @function
 */

public class FirstLedgerBean {

    /**
     * data : [{"course":"Java语言程序设计","date":"2018-02-04","week":"星期日","period":"2学时","signstatus":"已签到"},{"course":"应老师开车","date":"2018-02-04","week":"星期日","period":"2学时","signstatus":"迟到"},{"course":"应老师开车","date":"2018-02-04","week":"星期日","period":"3学时","signstatus":"旷课"}]
     * error : 0
     */

    private int error;
    /**
     * course : Java语言程序设计
     * date : 2018-02-04
     * week : 星期日
     * period : 2学时
     * signstatus : 已签到
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
        private String course;
        private String date;
        private String week;
        private String period;
        private String signstatus;

        public String getCourse() {
            return course;
        }

        public void setCourse(String course) {
            this.course = course;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public String getPeriod() {
            return period;
        }

        public void setPeriod(String period) {
            this.period = period;
        }

        public String getSignstatus() {
            return signstatus;
        }

        public void setSignstatus(String signstatus) {
            this.signstatus = signstatus;
        }
    }
}
