package com.zhoukp.signer.module.welcome;

import android.os.Build;
import android.view.View;
/**
 * @author zhoukp
 * @time 2018/4/1 14:57
 * @email 275557625@qq.com
 * @function
 */
class WelcomeViewHider implements OnWelcomeScreenPageChangeListener {

    private View view;
    private Integer lastPage = null;
    private boolean isRtl = false;
    private OnViewHiddenListener listener = null;
    private boolean enabled = false;

    public WelcomeViewHider(View viewToHide) {
        view = viewToHide;
    }

    public interface OnViewHiddenListener {
        void onViewHidden();
    }

    public void setOnViewHiddenListener(OnViewHiddenListener listener) {
        this.listener = listener;
    }

    @Override
    public void setup(WelcomeConfiguration config) {
        enabled = config.getSwipeToDismiss();
        lastPage = config.lastPageIndex();
        isRtl = config.isRtl();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        if (!enabled) {
            return;
        }

        if (position == lastPage && positionOffset == 0 && listener != null) {
            listener.onViewHidden();
        }

        if (Build.VERSION.SDK_INT < 11) {
            return;
        }

        boolean shouldSetAlpha = position == (isRtl ? lastPage : lastPage - 1);
        float alpha = isRtl ? positionOffset : 1f - positionOffset;

        boolean beforeLastPage = isRtl ? position > lastPage : position < lastPage - 1;

        if (shouldSetAlpha) {
            view.setAlpha(alpha);
        } else if (beforeLastPage && view.getAlpha() != 1f) {
            view.setAlpha(1f);
        }

    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}