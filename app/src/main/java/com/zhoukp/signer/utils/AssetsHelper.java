package com.zhoukp.signer.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author zhoukp
 * @time 2018/1/30 20:52
 * @email 275557625@qq.com
 * @function 读取assets文件夹下的文件
 */

public class AssetsHelper {
    /**
     * 读取assets下的txt文件，返回utf-8 String
     *
     * @param context  上下文
     * @param fileName 不包括后缀
     * @return String
     */
    public static String readAssetsTxt(Context context, String fileName) {
        try {
            InputStream is = context.getAssets().open(fileName + ".txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String text = new String(buffer, "utf-8");
            return text;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "读取错误，请检查文件名";
    }

    /**
     * 读取asstes下的bitmap文件
     *
     * @param context  上下文
     * @param fileName 文件名
     * @return bitmap
     */
    public static Bitmap readAssetsBitmap(Context context, String fileName) {
        Bitmap image = null;
        AssetManager am = context.getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
