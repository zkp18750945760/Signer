package com.zhoukp.signer.bean;

import java.util.List;

/**
 * @author zhoukp
 * @time 2018/1/31 20:43
 * @email 275557625@qq.com
 * @function
 */

public class AwardsBean {

    /**
     * data : [{"item":"福建省挑战杯一等奖","date":"2017-6"},{"item":"计算机设计大赛一等奖","date":"2017-7"},{"item":"应老师杯一等奖","date":"2017-9"}]
     * error : 0
     */

    private int error;
    /**
     * item : 福建省挑战杯一等奖
     * date : 2017-6
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
        private String item;
        private String date;

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
