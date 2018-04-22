package com.zhoukp.signer.view.pop;

import android.view.View;

/**
 * @author zhoukp
 * @time 2018/4/21 0:40
 * @email 275557625@qq.com
 * @function
 */
public class MenuItem {

    private String item;
    private int itemResId = View.NO_ID;


    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getItemResId() {
        return itemResId;
    }

    public void setItemResId(int itemResId) {
        this.itemResId = itemResId;
    }
}
