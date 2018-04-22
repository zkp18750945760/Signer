package com.zhoukp.signer.view.pop;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;

/**
 * @author zhoukp
 * @time 2018/4/21 0:39
 * @email 275557625@qq.com
 * @function
 */
public class Display {

    public static Point getScreenMetrics(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
        int h_screen = dm.heightPixels;
        return new Point(w_screen, h_screen);
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
