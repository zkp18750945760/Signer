package com.zhoukp.signer.module.welcome;

import android.content.Context;
import android.os.Build;
import android.support.annotation.AnimRes;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;

import com.zhoukp.signer.R;

/**
 * @author zhoukp
 * @time 2018/4/1 14:18
 * @email 275557625@qq.com
 * @function
 */
public class WelcomeConfiguration {

    public enum BottomLayout {

        /**
         * 标准的布局，有跳过/以前的按钮，页面指示符，以及next/done按钮
         */
        STANDARD(R.layout.wel_bottom_standard),

        /**
         * 与标准布局相同，但已完成按钮使用图像而不是文本
         */
        STANDARD_DONE_IMAGE(R.layout.wel_bottom_done_image),

        /**
         * 有两个按钮并排显示，上面有一个指示器。默认的按钮文本是“登录”和“注册”
         */
        BUTTON_BAR(R.layout.wel_bottom_button_bar),

        /**
         * 在上面有一个带有指示器的按钮。默认的按钮文本是“登录”
         */
        BUTTON_BAR_SINGLE(R.layout.wel_bottom_single_button),

        /**
         * 没有按钮，只是当前页面指示器
         */
        INDICATOR_ONLY(R.layout.wel_bottom_indicator),

        /**
         * 没有底的布局;没有按钮,没有指标
         */
        NONE(R.layout.wel_bottom_none);

        @LayoutRes
        final int resId;

        BottomLayout(@LayoutRes int resId) {
            this.resId = resId;
        }

    }

    public static final int NO_ANIMATION_SET = -1;

    private Builder builder;

    private WelcomePageList pages;

    public WelcomeConfiguration(Builder builder) {
        this.builder = builder;

        this.pages = new WelcomePageList();
        pages.addAll(builder.pages);

        if (pageCount() == 0) {
            throw new IllegalStateException("0 pages; at least one page must be added");
        }

        if (getSwipeToDismiss()) {
            pages.add(new FragmentWelcomePage() {
                @Override
                protected Fragment fragment() {
                    return new Fragment();
                }
            }.background(pages.getBackgroundColor(getContext(), pageCount() - 1)));
        }

        if (isRtl()) {
            pages.reversePageOrder();
        }

    }

    /**
     * 获取上下文
     *
     * @return 构造器初始化的上下文
     */
    public Context getContext() {
        return builder.context;
    }

    /**
     * 获取指定索引的fragment
     *
     * @param index index
     * @return fragment
     */
    public Fragment getFragment(int index) {
        return pages.getFragment(index);
    }

    /**
     * 在指定的索引中创建fragment
     *
     * @param index index
     * @return fragment
     */
    public Fragment createFragment(int index) {
        return pages.get(index).createFragment();
    }

    /**
     * 获得默认的背景颜色
     *
     * @return 默认颜色
     */
    public BackgroundColor getDefaultBackgroundColor() {
        return builder.defaultBackgroundColor;
    }

    /**
     * 获取页面的背景颜色
     *
     * @return 背景颜色[]
     */
    public BackgroundColor[] getBackgroundColors() {
        return pages.getBackgroundColors(getContext());
    }

    /**
     * 获得页面的总数，如果启用了swipeToDismiss解散，将是+1
     *
     * @return 页面总数
     */
    public int pageCount() {
        return pages.size();
    }

    /**
     * 获取可见页面的总数
     *
     * @return number of viewable pages
     */
    public int viewablePageCount() {
        return getSwipeToDismiss() ? pageCount() - 1 : pageCount();
    }

    /**
     * @return 页面列表
     */
    public WelcomePageList getPages() {
        return pages;
    }

    /**
     * 跳过按钮是否应该跳过
     *
     * @return backButtonSkips
     */
    public boolean getBackButtonSkips() {
        return builder.backButtonSkips;
    }

    /**
     * back按钮是否应该在页面中显示
     *
     * @return backButtonNavigatesPages
     */
    public boolean getBackButtonNavigatesPages() {
        return builder.backButtonNavigatesPages;
    }

    /**
     * 跳过按钮的字体路径
     *
     * @return 资源文件夹中的字体路径
     */
    public String getSkipButtonTypefacePath() {
        return builder.skipButtonTypefacePath;
    }

    /**
     * 完成按钮的字体路径
     *
     * @return 资源文件夹中的字体路径
     */
    public String getDoneButtonTypefacePath() {
        return builder.doneButtonTypefacePath;
    }

    /**
     * 标题的默认字体路径
     *
     * @return 资源文件夹中的字体路径
     */
    public String getDefaultTitleTypefacePath() {
        return builder.defaultTitleTypefacePath;
    }

    /**
     * 标题的默认字体路径
     *
     * @return 资源文件夹中的字体路径
     */
    public String getDefaultHeaderTypefacePath() {
        return builder.defaultHeaderTypefacePath;
    }

    /**
     * 描述的默认字体路径
     *
     * @return 资源文件夹中的字体路径
     */
    public String getDefaultDescriptionTypefacePath() {
        return builder.defaultDescriptionTypefacePath;
    }

    /**
     * 能否跳过
     *
     * @return canSkip
     */
    public boolean getCanSkip() {
        return builder.canSkip;
    }

    /**
     * 检查是否启用了swipeToDismiss,如果sdk int小于11，返回false
     *
     * @return swipeToDismiss
     */
    public boolean getSwipeToDismiss() {
        return builder.swipeToDismiss && Build.VERSION.SDK_INT >= 11;
    }

    /**
     * 检查布局是否为RTL
     *
     * @return true if RTL, false if not
     */
    public boolean isRtl() {
        return builder.context.getResources().getBoolean(R.bool.wel_is_rtl);
    }

    /**
     * 获取第一个页面的索引，依赖如果布局是RTL
     *
     * @return first page index
     */
    public int firstPageIndex() {
        return isRtl() ? pages.size() - 1 : 0;
    }

    /**
     * 得到最后一个页面的索引如果布局是RTL
     *
     * @return last page index
     */
    public int lastPageIndex() {
        return isRtl() ? 0 : pages.size() - 1;
    }

    /**
     * 获取最后一个可视页面的索引，取决于布局是否为RTL
     *
     * @return last viewable page index
     */
    public int lastViewablePageIndex() {
        return getSwipeToDismiss() ? Math.abs(lastPageIndex() - 1) : lastPageIndex();
    }

    /**
     * 获取退出动画的资源id
     *
     * @return animation resource id
     */
    public @AnimRes
    int getExitAnimation() {
        return builder.exitAnimationResId;
    }

    /**
     * 按钮是否应该有动画
     *
     * @return animate buttons
     */
    public boolean getAnimateButtons() {
        return builder.animateButtons;
    }

    /**
     * 是否应该使用定制的按钮。如果是真的，默认的done按钮应该被隐藏起来
     *
     * @return use custom done button
     */
    public boolean getUseCustomDoneButton() {
        return builder.useCustomDoneButton;
    }

    /**
     * 是否应该显示下一个按钮
     *
     * @return show next button
     */
    public boolean getShowNextButton() {
        return builder.showNextButton;
    }

    /**
     * 是否应该显示前一个按钮
     *
     * @return show previous button
     */
    public boolean getShowPrevButton() {
        return builder.showPrevButton;
    }

    /**
     * 是否应该显示动作栏后退按钮
     *
     * @return show action bar back button
     */
    public boolean getShowActionBarBackButton() {
        return builder.showActionBarBackButton;
    }

    /**
     * 在欢迎界面的底部使用布局
     *
     * @return layout resource id
     */
    public @LayoutRes
    int getBottomLayoutResId() {
        return builder.bottomLayoutRes;
    }

    public static class Builder {

        private WelcomePageList pages = new WelcomePageList();
        private boolean canSkip = true;
        private boolean backButtonSkips = true;
        private boolean backButtonNavigatesPages = true;
        private BackgroundColor defaultBackgroundColor;
        private Context context;
        private boolean swipeToDismiss = false;
        private int exitAnimationResId = NO_ANIMATION_SET;
        private String skipButtonTypefacePath = null;
        private String doneButtonTypefacePath = null;
        private String defaultTitleTypefacePath = null;
        private String defaultHeaderTypefacePath = null;
        private String defaultDescriptionTypefacePath = null;
        private boolean animateButtons = true;
        private boolean useCustomDoneButton = false;
        private boolean showNextButton = true;
        private boolean showPrevButton = false;
        private boolean showActionBarBackButton = false;
        private int bottomLayoutRes = BottomLayout.STANDARD.resId;


        public Builder(Context context) {
            this.context = context;
            initDefaultBackgroundColor(this.context);
        }

        private void initDefaultBackgroundColor(Context context) {
            // Default background color
            final int standardBackgroundColor = ColorHelper.getColor(context, R.color.wel_default_background_color);

            // AppCompat colorPrimary
            int defaultBackgroundColor = ColorHelper.resolveColorAttribute(context, R.attr.colorPrimary, standardBackgroundColor);

            // Android system colorPrimary
            if (defaultBackgroundColor == standardBackgroundColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                defaultBackgroundColor = ColorHelper.resolveColorAttribute(context, android.R.attr.colorPrimary, defaultBackgroundColor);

            this.defaultBackgroundColor = new BackgroundColor(defaultBackgroundColor, standardBackgroundColor);
        }

        /**
         * 构建配置
         *
         * @return
         */
        public WelcomeConfiguration build() {
            return new WelcomeConfiguration(this);
        }

        /**
         * 启用或禁用滑动来关闭（默认情况下禁用）
         *
         * @param swipeToDismiss True允许滑动关闭，false禁用它
         * @return 这个构建器对象允许将方法调用拴住
         */
        public Builder swipeToDismiss(boolean swipeToDismiss) {
            this.swipeToDismiss = swipeToDismiss;
            return this;
        }

        /**
         * 设置是否可以跳过欢迎屏幕。默认允许跳过
         *
         * @param canSkip 允许跳过，false，禁用它
         * @return 这个构建器对象允许将方法调用拴住
         */
        public Builder canSkip(boolean canSkip) {
            this.canSkip = canSkip;
            return this;
        }

        /**
         * 只适用于允许跳过。设置是否后退按钮可以跳过欢迎屏幕。这是默认启用的。
         *
         * @param backButtonSkips True允许后退按钮跳过欢迎界面，false禁用它
         * @return 这个构建器对象允许将方法调用拴住
         */
        public Builder backButtonSkips(boolean backButtonSkips) {
            this.backButtonSkips = backButtonSkips;
            return this;
        }

        /**
         * 设置是否按下后退按钮将移动到欢迎屏幕上的前一页。这在默认情况下是正确的
         *
         * @return
         */
        public Builder backButtonNavigatesPages(boolean navigatesPages) {
            this.backButtonNavigatesPages = navigatesPages;
            return this;
        }

        /**
         * 设置按钮是否会在改变可视的情况下消失
         *
         * @param animateButtons
         * @return
         */
        public Builder animateButtons(boolean animateButtons) {
            this.animateButtons = animateButtons;
            return this;
        }


        public Builder useCustomDoneButton(boolean useCustomDoneButton) {
            this.useCustomDoneButton = useCustomDoneButton;
            return this;
        }


        public Builder showNextButton(boolean showNextButton) {
            this.showNextButton = showNextButton;
            return this;
        }


        public Builder showPrevButton(boolean showPrevButton) {
            this.showPrevButton = showPrevButton;
            return this;
        }


        public Builder showActionBarBackButton(boolean showBackButton) {
            this.showActionBarBackButton = showBackButton;
            return this;
        }


        public Builder skipButtonTypefacePath(String typefacePath) {
            this.skipButtonTypefacePath = typefacePath;
            return this;
        }


        public Builder doneButtonTypefacePath(String typefacePath) {
            this.doneButtonTypefacePath = typefacePath;
            return this;
        }


        public Builder defaultTitleTypefacePath(String typefacePath) {
            this.defaultTitleTypefacePath = typefacePath;
            return this;
        }


        public Builder defaultHeaderTypefacePath(String typefacePath) {
            this.defaultHeaderTypefacePath = typefacePath;
            return this;
        }


        public Builder defaultDescriptionTypefacePath(String typefacePath) {
            this.defaultDescriptionTypefacePath = typefacePath;
            return this;
        }


        public Builder exitAnimation(@AnimRes int exitAnimationResId) {
            this.exitAnimationResId = exitAnimationResId;
            return this;
        }


        public Builder defaultBackgroundColor(@ColorRes int resId) {
            this.defaultBackgroundColor = new BackgroundColor(ColorHelper.getColor(context, resId));
            return this;
        }


        public Builder defaultBackgroundColor(BackgroundColor backgroundColor) {
            this.defaultBackgroundColor = backgroundColor;
            return this;
        }


        public Builder bottomLayout(BottomLayout layout) {
            this.bottomLayoutRes = layout.resId;
            return this;
        }

        public Builder page(WelcomePage page) {
            page.setIndex(pages.size());
            if (!page.backgroundIsSet()) {
                page.background(defaultBackgroundColor);
            }
            pages.add(page);
            return this;
        }

    }

}
