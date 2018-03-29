package com.zhoukp.signer.utils.lrucache;

import android.graphics.Bitmap;

/**
 * @author zhoukp
 * @time 2018/3/29 14:53
 * @email 275557625@qq.com
 * @function
 */

public class ImageBean {

    private String url;
    private Bitmap bitmap;

    public ImageBean(Bitmap bitmap, String url) {
        this.bitmap = bitmap;
        this.url = url;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
