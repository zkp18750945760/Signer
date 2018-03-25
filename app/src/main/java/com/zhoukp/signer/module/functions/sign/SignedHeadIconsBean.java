package com.zhoukp.signer.module.functions.sign;

import java.util.List;

/**
 * @author zhoukp
 * @time 2018/3/25 17:00
 * @email 275557625@qq.com
 * @function
 */

public class SignedHeadIconsBean {

    /**
     * data : [{"headIconUrl":"/imgs/1425122042.jpg","userId":"1425122042","userName":"周开平"},{"headIconUrl":"/imgs/1425122040.jpg","userId":"1425122040","userName":"张洋铭"}]
     * status : 200
     * time : 2018-03-17 10:47:50
     */

    private int status;
    private String time;
    /**
     * headIconUrl : /imgs/1425122042.jpg
     * userId : 1425122042
     * userName : 周开平
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
        private String headIconUrl;
        private String userId;
        private String userName;

        public String getHeadIconUrl() {
            return headIconUrl;
        }

        public void setHeadIconUrl(String headIconUrl) {
            this.headIconUrl = headIconUrl;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
