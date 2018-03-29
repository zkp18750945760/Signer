package com.zhoukp.signer.utils.lrucache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author zhoukp
 * @time 2018/3/29 15:01
 * @email 275557625@qq.com
 * @function
 */

public class NetworkCacheObservable extends CacheObservable {
    @Override
    public ImageBean getDataFromCache(String url) {
        Log.e("getDataFromCache", "getDataFromNetworkCache");
        Bitmap bitmap = downloadImage(url);
        return new ImageBean(bitmap, url);
    }

    @Override
    public void putDataToCache(ImageBean image) {

    }

    /**
     * 下载文件
     *
     * @param url
     * @return
     */
    public Bitmap downloadImage(String url) {
        Bitmap bitmap = null;
        InputStream inputStream = null;
        try {
            URL imageUrl = new URL(url);
            URLConnection urlConnection = imageUrl.openConnection();
            inputStream = urlConnection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }
}
