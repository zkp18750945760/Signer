package com.zhoukp.signer.module.functions.ledgers.scanxls;

import java.io.Serializable;

/**
 * @author zhoukp
 * @time 2018/3/26 22:08
 * @email 275557625@qq.com
 * @function
 */

public class XlsBean implements Serializable {

    private String id;
    private String path;
    private String size;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
