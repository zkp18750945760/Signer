package com.zhoukp.signer.module.welcome;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

/**
 * @author zhoukp
 * @time 2018/4/1 15:07
 * @email 275557625@qq.com
 * @function
 */
public class TitlePage extends WelcomePage<TitlePage> {

    private int drawableResId;
    private String title;
    private boolean showParallax = true;
    private String titleTypefacePath = null;
    private int titleColor = WelcomeUtils.NO_COLOR_SET;


    public TitlePage(@DrawableRes int drawableResId, String title) {
        this.drawableResId = drawableResId;
        this.title = title;
    }


    public TitlePage parallax(boolean showParallax) {
        this.showParallax = showParallax;
        return this;
    }


    public TitlePage titleTypeface(String typefacePath) {
        this.titleTypefacePath = typefacePath;
        return this;
    }


    public TitlePage titleColor(@ColorInt int color) {
        this.titleColor = color;
        return this;
    }


    public TitlePage titleColorResource(Context context, @ColorRes int colorRes) {
        this.titleColor = ContextCompat.getColor(context, colorRes);
        return this;
    }


    int getDrawableResId() {
        return drawableResId;
    }

    String getTitle() {
        return title;
    }

    boolean getShowParallax() {
        return showParallax;
    }

    String getTitleTypefacePath() {
        return titleTypefacePath;
    }

    int getTitleColor() {
        return titleColor;
    }

    @Override
    public void setup(WelcomeConfiguration config) {
        super.setup(config);

        if (this.titleTypefacePath == null) {
            titleTypeface(config.getDefaultTitleTypefacePath());
        }
    }

    @Override
    public Fragment fragment() {
        return WelcomeTitleFragment.newInstance(drawableResId, title, showParallax, titleTypefacePath, titleColor);
    }
}