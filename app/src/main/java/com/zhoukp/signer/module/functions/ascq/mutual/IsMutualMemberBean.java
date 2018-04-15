package com.zhoukp.signer.module.functions.ascq.mutual;

/**
 * @author zhoukp
 * @time 2018/4/13 14:23
 * @email 275557625@qq.com
 * @function
 */
public class IsMutualMemberBean {

    /**
     * isMember : true
     * time : 2018-04-13 14:22:43
     * status : 200
     */

    private boolean isMember;
    private String time;
    private int status;

    public boolean isIsMember() {
        return isMember;
    }

    public void setIsMember(boolean isMember) {
        this.isMember = isMember;
    }

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
}
