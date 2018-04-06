package com.zhoukp.signer.module.welcome;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;

/**
 * @author zhoukp
 * @time 2018/4/1 14:28
 * @email 275557625@qq.com
 * @function
 */
public abstract class WelcomePage<T extends WelcomePage> implements OnWelcomeScreenPageChangeListener {

    private Integer backgroundColorResId = null;
    private BackgroundColor backgroundColor = null;
    protected int index = -2;
    protected boolean isRtl = false;
    protected int totalPages = 0;

    private Fragment fragment;

    /**
     * 接口是由一些片段实现的，这些片段是welcomeActivity的一部分
     */
    public interface OnChangeListener {

        /**
         * 当这个页面进入视图时调用
         *
         * @param pageIndex    index
         * @param offset       这个页面的%偏移，如果页面不在屏幕右边，则为负，左边是正的
         * @param offsetPixels 这一页的偏移量为像素，如果页面不在屏幕右边，则为负，左边为正
         */
        void onWelcomeScreenPageScrolled(int pageIndex, float offset, int offsetPixels);

        /**
         * 当选定的页面改变时调用
         *
         * @param pageIndex         index
         * @param selectedPageIndex 被选中的页面的索引
         */
        void onWelcomeScreenPageSelected(int pageIndex, int selectedPageIndex);

        /**
         * 当ViewPager的滚动状态改变时调用
         *
         * @param pageIndex index
         * @param state     新的滚动状态
         */
        void onWelcomeScreenPageScrollStateChanged(int pageIndex, int state);
    }

    void setIndex(int index) {
        this.index = index;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public Fragment createFragment() {
        this.fragment = fragment();
        return fragment;
    }

    protected abstract Fragment fragment();

    boolean backgroundIsSet() {
        return backgroundColorResId != null || backgroundColor != null;
    }

    public T background(@ColorRes int colorResId) {
        this.backgroundColorResId = colorResId;
        this.backgroundColor = null;
        return (T) this;
    }

    public T background(BackgroundColor backgroundColor) {
        this.backgroundColor = backgroundColor;
        this.backgroundColorResId = null;
        return (T) this;
    }

    BackgroundColor getBackground(Context context) {
        if (backgroundColor == null) {
            backgroundColor = new BackgroundColor(ColorHelper.getColor(context, backgroundColorResId));
        }
        return backgroundColor;
    }

    @Override
    public void setup(WelcomeConfiguration config) {
        isRtl = config.isRtl();
        totalPages = config.pageCount();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        //RTL的正确位置。一个被减去，使它为零索引
        int realPosition = isRtl ? totalPages - 1 - position : position;

        if (getFragment() != null && getFragment() instanceof OnChangeListener && realPosition - index <= 1) {
            Fragment fragment = getFragment();

            int fragmentWidth = 0;
            if (fragment.getView() != null) {
                fragmentWidth = fragment.getView().getWidth();
            }

            boolean lowerPosition = isRtl ? realPosition > index : realPosition < index;
            float offset = lowerPosition ? -(1 - positionOffset) : positionOffset;
            int offsetPixels = lowerPosition ? -(fragmentWidth - positionOffsetPixels) : positionOffsetPixels;

            ((OnChangeListener) fragment).onWelcomeScreenPageScrolled(index, offset, offsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (getFragment() != null && getFragment() instanceof OnChangeListener) {
            ((OnChangeListener) getFragment()).onWelcomeScreenPageSelected(index, position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (getFragment() != null && getFragment() instanceof OnChangeListener) {
            ((OnChangeListener) getFragment()).onWelcomeScreenPageScrollStateChanged(index, state);
        }
    }
}
