package com.zhoukp.coursetable;

import java.util.List;

/**
 * @author zhoukp
 * @time 2018/4/3 20:14
 * @email 275557625@qq.com
 * @function
 */
public class CourseBean {

    /**
     * data : [{"name":"高等数学A(一)","location":"D1-203","day":2,"start":1,"end":2,"weeks":"1-18周"},{"name":"高等数学A(一)","location":"D5-201","day":4,"start":7,"end":8,"weeks":"1-18周"},{"name":"Java语言程序设计","location":"C2-203","day":3,"start":3,"end":4,"weeks":"1-18周"},{"name":"思修","location":"C5-203","day":3,"start":11,"end":13,"weeks":"1-18周"},{"name":"毛概","location":"D5-203","day":5,"start":7,"end":9,"weeks":"1-18周"},{"name":"应老师请吃饭","location":"A408","day":4,"start":7,"end":9,"weeks":"1-18周"}]
     * status : 200
     * time : 2018-03-17 10:47:50
     */

    private int status;
    private String time;
    /**
     * name : 高等数学A(一)
     * location : D1-203
     * day : 2
     * start : 1
     * end : 2
     * weeks : 1-18周
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
        private String name;
        private String location;
        private int day;
        private int start;
        private int end;
        private String weeks;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }

        public String getWeeks() {
            return weeks;
        }

        public void setWeeks(String weeks) {
            this.weeks = weeks;
        }
    }
}
