package com.zhoukp.signer.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;

import com.zhoukp.signer.utils.aes.MD5Encoder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by 周开平 on 2017/3/25 16:11.
 * qq 275557625@qq.com
 * 作用：缓存数据
 */

public class CacheUtils {
    /**
     * 得到缓存值
     *
     * @param context context
     * @param key     key
     * @return boolean
     */
    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("HQUZkP", Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }

    /**
     * @param context context
     * @param key     key
     * @param value   value
     */
    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences("HQUZkP", Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).apply();
    }

    /**
     * 缓存文本数据
     *
     * @param context context
     * @param key     key
     * @param value   value
     */
    public static void putString(Context context, String key, String value) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                String fileName = MD5Encoder.encode(key);
                File file = new File(Constant.appPath, fileName);
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    //创建目录
                    parentFile.mkdirs();
                }
                if (!file.exists()) {
                    file.createNewFile();
                }
                //保存文本数据
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(value.getBytes());
                fileOutputStream.close();

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("zkp", "文本数据缓存失败");
            }
        } else {
            SharedPreferences sp = context.getSharedPreferences("HQUZkP", Context.MODE_PRIVATE);
            sp.edit().putString(key, value).apply();
        }
    }

    /**
     * 获取缓存的文本信息
     *
     * @param context context
     * @param key     key
     * @return String
     */
    public static String getString(Context context, String key, int defValue) {
        String result = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                String fileName = MD5Encoder.encode(key);
                File file = new File(Constant.appPath, fileName);
                if (file.exists()) {
                    FileInputStream is = new FileInputStream(file);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = is.read(buffer)) != -1) {
                        stream.write(buffer, 0, length);
                    }
                    is.close();
                    stream.close();
                    result = stream.toString();
                } else {
                    result = defValue + "";
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("zkp", "图片获取失败");
            }
        } else {
            SharedPreferences sp = context.getSharedPreferences("HQUZkP", Context.MODE_PRIVATE);
            result = sp.getString(key, "");
        }
        return result;
    }

    /**
     * 根据key删除相应的SharedPreferences数据
     *
     * @param keys keys
     */
    public static void clearSp(String[] keys) {
        for (String key : keys) {
            String fileName;
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                try {
                    fileName = MD5Encoder.encode(key);
                    File file = new File(Constant.appPath, fileName);
                    if (file.exists()) {
                        file.delete();
                        Log.e("zkp", key + "删除成功");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
