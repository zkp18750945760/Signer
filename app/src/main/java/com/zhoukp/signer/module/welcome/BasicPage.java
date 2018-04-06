package com.zhoukp.signer.module.welcome;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
/**
 * @author zhoukp
 * @time 2018/4/1 15:10
 * @email 275557625@qq.com
 * @function
 */
public class BasicPage extends WelcomePage<BasicPage> {

    private int drawableResId;
    private String title;
    private String description;
    private boolean showParallax = true;
    private String headerTypefacePath = null;
    private String descriptionTypefacePath = null;
    private int headerColor = WelcomeUtils.NO_COLOR_SET;
    private int descriptionColor = WelcomeUtils.NO_COLOR_SET;


    public BasicPage(@DrawableRes int drawableResId, String title, String description) {
        this.drawableResId = drawableResId;
        this.title = title;
        this.description = description;
    }


    public BasicPage parallax(boolean showParallax) {
        this.showParallax = showParallax;
        return this;
    }


    public BasicPage headerTypeface(String typefacePath) {
        this.headerTypefacePath = typefacePath;
        return this;
    }


    public BasicPage descriptionTypeface(String typefacePath) {
        this.descriptionTypefacePath = typefacePath;
        return this;
    }


    public BasicPage headerColor(@ColorInt int color) {
        this.headerColor = color;
        return this;
    }


    public BasicPage headerColorResource(Context context, @ColorRes int colorRes) {
        this.headerColor = ContextCompat.getColor(context, colorRes);
        return this;
    }


    public BasicPage descriptionColor(@ColorInt int color) {
        this.descriptionColor = color;
        return this;
    }


    public BasicPage descriptionColorResource(Context context, @ColorRes int colorRes) {
        this.descriptionColor = ContextCompat.getColor(context, colorRes);
        return this;
    }


    int getDrawableResId() {
        return drawableResId;
    }

    String getTitle() {
        return title;
    }

    String getDescription() {
        return description;
    }

    boolean getShowParallax() {
        return showParallax;
    }

    String getHeaderTypefacePath() {
        return headerTypefacePath;
    }

    String getDescriptionTypefacePath() {
        return descriptionTypefacePath;
    }

    int getHeaderColor() {
        return headerColor;
    }

    int getDescriptionColor() {
        return descriptionColor;
    }

    @Override
    public void setup(WelcomeConfiguration config) {
        super.setup(config);

        if (this.headerTypefacePath == null) {
            headerTypeface(config.getDefaultHeaderTypefacePath());
        }

        if (this.descriptionTypefacePath == null) {
            descriptionTypeface(config.getDefaultDescriptionTypefacePath());
        }

    }

    @Override
    public Fragment fragment() {
        return WelcomeBasicFragment.newInstance(drawableResId,
                title,
                description,
                showParallax,
                headerTypefacePath,
                descriptionTypefacePath,
                headerColor,
                descriptionColor);
    }
}
