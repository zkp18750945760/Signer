package com.zhoukp.signer.module.functions.meetings;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author zhoukp
 * @time 2018/4/9 22:13
 * @email 275557625@qq.com
 * @function
 */
public class MeetBean {


    /**
     * data : [{"date":"2018-02-03","pdfUrl":"/pdf/【境内生】1425122042-周开平.pdf","read":false,"theme":"关于综测评分工作的总结会议","abstract":"本次综测评分过程中存在有些班级没有落实到位","browse":"0"},{"date":"2018-02-17","pdfUrl":"/pdf/简历_170914.pdf","read":false,"theme":"就业推荐函填写注意点","abstract":"就业推荐函是毕业生就业的重要材料，希望各班","browse":"0"},{"date":"2018-02-10","pdfUrl":"/pdf/就业展望.pdf","read":false,"theme":"年级大会注意事项","abstract":"本次年级大会可能涉及到毕业涉及，希望能让全班","browse":"0"}]
     * time : 2018-04-09 22:13:20
     * status : 200
     */

    private String time;
    private int status;
    /**
     * date : 2018-02-03
     * pdfUrl : /pdf/【境内生】1425122042-周开平.pdf
     * read : false
     * theme : 关于综测评分工作的总结会议
     * abstract : 本次综测评分过程中存在有些班级没有落实到位
     * browse : 0
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
        private String date;
        private String pdfUrl;
        private boolean read;
        private String theme;
        @SerializedName("abstract")
        private String abstractX;
        private String browse;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getPdfUrl() {
            return pdfUrl;
        }

        public void setPdfUrl(String pdfUrl) {
            this.pdfUrl = pdfUrl;
        }

        public boolean isRead() {
            return read;
        }

        public void setRead(boolean read) {
            this.read = read;
        }

        public String getTheme() {
            return theme;
        }

        public void setTheme(String theme) {
            this.theme = theme;
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
