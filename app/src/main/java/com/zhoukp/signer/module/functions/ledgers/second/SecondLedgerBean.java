package com.zhoukp.signer.module.functions.ledgers.second;

import java.util.List;

/**
 * @author zhoukp
 * @time 2018/3/28 14:50
 * @email 275557625@qq.com
 * @function
 */

public class SecondLedgerBean {

    /**
     * data : [{"month":"3","count":2}]
     * time : 2018-03-28 14:49:42
     * status : 101
     */

    private String time;
    private int status;
    /**
     * month : 3
     * count : 2.0
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
        private String month;
        private double count;

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public double getCount() {
            return count;
        }

        public void setCount(double count) {
            this.count = count;
        }
    }
}
