package com.zhoukp.signer.module.welcome;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
/**
 * @author zhoukp
 * @time 2018/4/1 14:28
 * @email 275557625@qq.com
 * @function
 */
class ColorHelper {

    static int getColor(Context context, @ColorRes int resId) {
        return ContextCompat.getColor(context, resId);
    }

    static int resolveColorAttribute(Context context, @AttrRes int resId, int fallback) {
        TypedValue value = new TypedValue();
        boolean colorFound = context.getTheme().resolveAttribute(resId, value, true);
        return colorFound ? value.data : fallback;
    }

}