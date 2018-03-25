package com.zhoukp.signer.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author zhoukp
 * @time 2018/2/3 20:47
 * @email 275557625@qq.com
 * @function
 */

public class ConvertionBean {

    /**
     * data : [{"theme":"关于综测评分工作的总结会议","date":"2018-02-03","abstract":"本次综测评分过程中存在有些班级没有落实到位","browse":"45"},{"theme":"年级大会注意事项","date":"2018-02-10","abstract":"本次年级大会可能涉及到毕业涉及，希望能让全班","browse":"22"},{"theme":"就业推荐函填写注意点","date":"2018-02-17","abstract":"就业推荐函是毕业生就业的重要材料，希望各班","browse":"55"}]
     * error : 0
     */

    private int error;
    /**
     * theme : 关于综测评分工作的总结会议
     * date : 2018-02-03
     * abstract : 本次综测评分过程中存在有些班级没有落实到位
     * browse : 45
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
        @SerializedName("abstract")
        private String abstractX;
        private String browse;

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

        public String getAbstractX() {
            return abstractX;
        }

        public void setAbstractX(String abstractX) {
            this.abstractX = abstractX;
        }

        public String getBrowse() {
            return browse;
        }

        public void setBrowse(String browse) {
            this.browse = browse;
        }
    }
}
