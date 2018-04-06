package com.zhoukp.signer.module.welcome;

import android.view.View;

/**
 * @author zhoukp
 * @time 2018/4/1 14:56
 * @email 275557625@qq.com
 * @function
 */
class NextButton extends WelcomeViewWrapper {

    private boolean shouldShow = true;

    public NextButton(View button) {
        super(button);
    }

    @Override
    public void setup(WelcomeConfiguration config) {
        super.setup(config);
        this.shouldShow = config.getShowNextButton();
    }

    @Override
    public void onPageSelected(int pageIndex, int firstPageIndex, int lastPageIndex) {
        setVisibility(shouldShow && WelcomeUtils.isIndexBeforeLastPage(pageIndex, lastPageIndex, isRtl));
    }
}
