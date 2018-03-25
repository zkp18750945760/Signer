package com.zhoukp.signer.utils;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * @author zhoukp
 * @time 2018/2/2 19:10
 * @email 275557625@qq.com
 * @function
 */

public class ImageHelper {

    /**
     * 黑白滤镜
     *
     * @param bitmap bitmap
     * @return bitmap
     */
    public static Bitmap BlackWhite(Bitmap bitmap) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Bitmap resultBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
        int color = 0;
        int a, r, g, b, r1, g1, b1;
        int[] oldPx = new int[w * h];
        int[] newPx = new int[w * h];

        bitmap.getPixels(oldPx, 0, w, 0, 0, w, h);
        for (int i = 0; i < w * h; i++) {
            color = oldPx[i];
            r = Color.red(color);
            g = Color.green(color);
            b = Color.blue(color);
            a = Color.alpha(color);
            //黑白矩阵
            r1 = (int) (0.33 * r + 0.59 * g + 0.11 * b);
            g1 = (int) (0.33 * r + 0.59 * g + 0.11 * b);
            b1 = (int) (0.33 * r + 0.59 * g + 0.11 * b);
            //检查各像素值是否超出范围
            if (r1 > 255) {
                r1 = 255;
            }
            if (g1 > 255) {
                g1 = 255;
            }
            if (b1 > 255) {
                b1 = 255;
            }
            newPx[i] = Color.argb(a, r1, g1, b1);
        }
        resultBitmap.setPixels(newPx, 0, w, 0, 0, w, h);
        return resultBitmap;
    }
}
