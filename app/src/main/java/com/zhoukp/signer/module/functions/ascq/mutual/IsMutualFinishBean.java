package com.zhoukp.signer.module.functions.ascq.mutual;

/**
 * @author zhoukp
 * @time 2018/4/13 14:57
 * @email 275557625@qq.com
 * @function
 */
public class IsMutualFinishBean {

    /**
     * hasTask : true
     * isFinish : false
     * time : 2018-04-13 14:57:22
     * status : 200
     */

    private boolean hasTask;
    private boolean isFinish;
    private String time;
    private int status;

    public boolean isHasTask() {
        return hasTask;
    }

    public void setHasTask(boolean hasTask) {
        this.hasTask = hasTask;
    }

    public boolean isIsFinish() {
        return isFinish;
    }

    public void setIsFinish(boolean isFinish) {
        this.isFinish = isFinish;
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
