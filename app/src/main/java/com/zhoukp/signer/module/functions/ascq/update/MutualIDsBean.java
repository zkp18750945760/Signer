package com.zhoukp.signer.module.functions.ascq.update;

import java.util.List;

/**
 * @author zhoukp
 * @time 2018/4/15 15:13
 * @email 275557625@qq.com
 * @function
 */
public class MutualIDsBean {

    /**
     * data : [{"stuId":"1425122002"}]
     * time : 2018-04-15 15:13:09
     * status : 200
     */

    private String time;
    private int status;
    /**
     * stuId : 1425122002
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
        private String stuId;

        public String getStuId() {
            return stuId;
        }

        public void setStuId(String stuId) {
            this.stuId = stuId;
        }
    }
}
