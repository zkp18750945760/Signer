package com.zhoukp.signer.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author zhoukp
 * @time 2018/1/29 19:22
 * @email 275557625@qq.com
 * @function
 */

public class WindowUtils {
    /**
     * 设置状态栏透明
     *
     * @param activity activity
     */
    public static void setTransluteWindow(Activity activity) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 获取状态栏高度
     *
     * @param context 上下文
     * @return height
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
