package com.zhoukp.signer.module.welcome;

import android.view.View;
import android.widget.TextView;

/**
 * @author zhoukp
 * @time 2018/4/1 14:56
 * @email 275557625@qq.com
 * @function
 */
class DoneButton extends WelcomeViewWrapper {

    private boolean shouldShow = true;

    public DoneButton(View button) {
        super(button);
        if (button != null) hideImmediately();
    }

    @Override
    public void setup(WelcomeConfiguration config) {
        super.setup(config);
        shouldShow = !config.getUseCustomDoneButton();
        if (this.getView() instanceof TextView) {
            WelcomeUtils.setTypeface((TextView) this.getView(), config.getDoneButtonTypefacePath(), config.getContext());
        }
    }

    @Override
    public void onPageSelected(int pageIndex, int firstPageIndex, int lastPageIndex) {
        setVisibility(shouldShow && !WelcomeUtils.isIndexBeforeLastPage(pageIndex, lastPageIndex, isRtl));
    }
}
