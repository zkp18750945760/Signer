package com.zhoukp.signer.utils.lrucache;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

/**
 * @author zhoukp
 * @time 2018/3/29 14:54
 * @email 275557625@qq.com
 * @function
 */

public class MemoryCacheObservable extends CacheObservable {

    private int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    private int cacheSize = maxMemory / 4;
    private LruCache<String, Bitmap> mLruCache = new LruCache<String, Bitmap>(cacheSize) {
        @Override
        protected int sizeOf(String key, Bitmap bitmap) {
            return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
        }
    };

    @Override
    public ImageBean getDataFromCache(String url) {
        Log.e("getDataFromCache", "getDataFromMemoryCache");
        Bitmap bitmap = mLruCache.get(url);
        return new ImageBean(bitmap, url);
    }

    @Override
    public void putDataToCache(ImageBean image) {
        mLruCache.put(image.getUrl(), image.getBitmap());
    }
}
