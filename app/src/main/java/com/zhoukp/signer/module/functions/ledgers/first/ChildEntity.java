package com.zhoukp.signer.module.functions.ledgers.first;

/**
 * @author zhoukp
 * @time 2018/4/9 15:30
 * @email 275557625@qq.com
 * @function
 */
public class ChildEntity {
    private String content;
    private String contentLong;
    private int status;

    public ChildEntity(String content, String contentLong, int status) {
        this.content = content;
        this.contentLong = contentLong;
        this.status = status;
    }

    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentLong() {
        return contentLong == null ? "" : contentLong;
    }

    public void setContentLong(String contentLong) {
        this.contentLong = contentLong;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
