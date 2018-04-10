package com.zhoukp.signer.module.functions.ledgers.first;

import java.util.List;

/**
 * @author zhoukp
 * @time 2018/4/9 13:19
 * @email 275557625@qq.com
 * @function
 */
public class FirstLedgerBean {


    /**
     * data : [{"date":"2018-03-26 星期一","time":"2018-03-26 14:26:00","content":"#高等数学C(一)","contentLong":"2学时","status":"1"},{"date":"2018-04-03 星期二","time":"2018-04-03 17:00:00","content":"#高等数学C(一)","contentLong":"2学时","status":"1"},{"date":"2018-04-02 星期一","time":"2018-04-02 14:26:00","content":"#高等数学C(一)","contentLong":"2学时","status":"2"},{"date":"2018-04-06 星期五","time":"2018-04-06 14:26:00","content":"#高等数学C(一)","contentLong":"2学时","status":"3"},{"date":"2018-04-07 星期六","time":"2018-04-07 14:26:00","content":"#高等数学C(一)","contentLong":"2学时","status":"3"}]
     * time : 2018-04-09 14:32:41
     * status : 200
     */

    private String time;
    private int status;
    /**
     * date : 2018-03-26 星期一
     * time : 2018-03-26 14:26:00
     * content : #高等数学C(一)
     * contentLong : 2学时
     * status : 1
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
        private String time;
        private String content;
        private String contentLong;
        private String status;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getContentLong() {
            return contentLong;
        }

        public void setContentLong(String contentLong) {
            this.contentLong = contentLong;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
