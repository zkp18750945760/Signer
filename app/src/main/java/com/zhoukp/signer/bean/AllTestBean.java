package com.zhoukp.signer.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author zhoukp
 * @time 2018/2/4 19:55
 * @email 275557625@qq.com
 * @function
 */

public class AllTestBean {

    /**
     * data : [{"item":"德育素质","abstract":"无重大过失给满分","full":9.5,"score":10,"detail":"德育素质测评采用扣分方法，无下列行为者应给满分。"},{"item":"智育素质","abstract":"(当学年绩点 + 2.5) * 8","full":50.5,"score":60,"detail":"计算方法为：智育素质分 =（当学年学业绩点+2.5）×8。"},{"item":"体育素质","abstract":"按优秀、良好、及格、不及格分别计5、4.5、4、1分","full":4.5,"score":5,"detail":"体质健康标准按优秀、良好、及格、不及格分别计5、4.5、4、1分。"},{"item":"科技创新与社会实践","abstract":"分为基本分和奖励分","full":9.5,"score":12,"detail":"基本分为6分，奖励分为6分。"},{"item":"文体艺术与身心发展","abstract":"分为基本分和奖励分","full":5.5,"score":7,"detail":"基本分为3分，奖励分为4分。"},{"item":"团体活动与社会工作","abstract":"分为基本分和奖励分","full":4.5,"score":6,"detail":"基本分为3分，奖励分为3分。"}]
     * score : 85.5
     * rank : 2
     * total : 55
     * time : 2016-2017学年
     * error : 0
     */

    private double score;
    private int rank;
    private int total;
    private String time;
    private int error;
    /**
     * item : 德育素质
     * abstract : 无重大过失给满分
     * full : 9.5
     * score : 10
     * detail : 德育素质测评采用扣分方法，无下列行为者应给满分。
     */

    private List<DataBean> data;

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

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
        @SerializedName("abstract")
        private String abstractX;
        private int full;
        private double score;
        private String detail;

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public String getAbstractX() {
            return abstractX;
        }

        public void setAbstractX(String abstractX) {
            this.abstractX = abstractX;
        }

        public int getFull() {
            return full;
        }

        public void setFull(int full) {
            this.full = full;
        }

        public double getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }
    }
}
