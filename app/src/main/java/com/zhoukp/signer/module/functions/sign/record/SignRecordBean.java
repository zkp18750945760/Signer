package com.zhoukp.signer.module.functions.sign.record;

import java.util.List;

/**
 * @author zhoukp
 * @time 2018/4/6 13:35
 * @email 275557625@qq.com
 * @function
 */
public class SignRecordBean {

    /**
     * data : [{"time":"2018-03-26 14:26:00","content":"#高等数学C(一)","status":"1"},{"time":"2018-04-03 17:00:00","content":"#高等数学C(一)","status":"1"},{"time":"2018-03-25 15:56:42","content":"2014级年级大会","status":"1"}]
     * time : 2018-04-06 13:35:00
     * status : 200
     */

    private String time;
    private int status;
    /**
     * time : 2018-03-26 14:26:00
     * content : #高等数学C(一)
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
        private String time;
        private String content;
        private String status;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
