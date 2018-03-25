package com.zhoukp.signer.bean;

import java.util.List;

/**
 * @author zhoukp
 * @time 2018/2/2 18:47
 * @email 275557625@qq.com
 * @function
 */

public class MeetingSignBean {

    /**
     * data : [{"name":"应老师","signstatus":"true","signtime":"18:44:33","headurl":"icon_signed.png"},{"name":"应老师","signstatus":"false","signtime":"18:44:35","headurl":"icon_signed.png"},{"name":"应老师","signstatus":"true","signtime":"18:44:38","headurl":"icon_signed.png"}]
     * error : 0
     */

    private int error;
    /**
     * name : 应老师
     * signstatus : true
     * signtime : 18:44:33
     * headurl : icon_signed.png
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
        private String name;
        private String signstatus;
        private String signtime;
        private String headurl;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSignstatus() {
            return signstatus;
        }

        public void setSignstatus(String signstatus) {
            this.signstatus = signstatus;
        }

        public String getSigntime() {
            return signtime;
        }

        public void setSigntime(String signtime) {
            this.signtime = signtime;
        }

        public String getHeadurl() {
            return headurl;
        }

        public void setHeadurl(String headurl) {
            this.headurl = headurl;
        }
    }
}
