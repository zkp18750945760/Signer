package com.zhoukp.signer.module.welcome;

import android.view.View;

/**
 * @author zhoukp
 * @time 2018/4/1 14:55
 * @email 275557625@qq.com
 * @function
 */
class PreviousButton extends WelcomeViewWrapper {

    private boolean shouldShow = false;

    public PreviousButton(View button) {
        super(button);
    }

    @Override
    public void setup(WelcomeConfiguration config) {
        super.setup(config);
        this.shouldShow = config.getShowPrevButton();
    }

    @Override
    public void onPageSelected(int pageIndex, int firstPageIndex, int lastPageIndex) {
        setVisibility(shouldShow && pageIndex != firstPageIndex);
    }
}