package com.zhoukp.signer.module.functions.ledgers.first;

import java.util.ArrayList;

/**
 * @author zhoukp
 * @time 2018/4/9 15:31
 * @email 275557625@qq.com
 * @function
 */
public class GroupEntity {
    private String header;
    private String footer;
    private ArrayList<ChildEntity> children;

    public GroupEntity(String header, String footer, ArrayList<ChildEntity> children) {
        this.header = header;
        this.footer = footer;
        this.children = children;
    }

    public String getHeader() {
        return header == null ? "" : header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getFooter() {
        return footer == null ? "" : footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public ArrayList<ChildEntity> getChildren() {
        if (children == null) {
            return new ArrayList<>();
        }
        return children;
    }

    public void setChildren(ArrayList<ChildEntity> children) {
        this.children = children;
    }
}
