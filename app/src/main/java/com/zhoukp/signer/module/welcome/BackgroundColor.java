package com.zhoukp.signer.module.welcome;

import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
/**
 * @author zhoukp
 * @time 2018/4/1 14:17
 * @email 275557625@qq.com
 * @function
 */
public class BackgroundColor {

    private int color = 0;

    public BackgroundColor(@ColorInt int color) {
        this.color = color;
    }

    public BackgroundColor(@Nullable @ColorInt Integer color, @ColorInt int fallbackColor) {
        this.color = color != null ? color : fallbackColor;
    }

    public int value() {
        return this.color;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof BackgroundColor) {
            BackgroundColor otherColor = (BackgroundColor) obj;
            if (otherColor.value() == this.value()) {
                return true;
            }
        }
        return false;
    }

}