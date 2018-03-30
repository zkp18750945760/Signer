package com.zhoukp.signer.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * @author zhoukp
 * @time 2018/3/30 13:48
 * @email 275557625@qq.com
 * @function
 */

public class ScreenUtil {
    /**
     * 获取屏幕宽度（像素）
     *
     * @param context 上下文
     * @return px
     */
    public static int getWith(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度（像素）
     *
     * @param context 上下文
     * @return px
     */
    public static int getHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }
}
