package com.zhoukp.signer.module.welcome;

import android.view.View;
import android.widget.TextView;
/**
 * @author zhoukp
 * @time 2018/4/1 14:53
 * @email 275557625@qq.com
 * @function
 */
class SkipButton extends WelcomeViewWrapper {

    private boolean enabled = true;
    private boolean onlyShowOnFirstPage = false;

    public SkipButton(View button) {
        super(button);
    }

    @Override
    public void setup(WelcomeConfiguration config) {
        super.setup(config);
        onlyShowOnFirstPage = config.getShowPrevButton();
        this.enabled = config.getCanSkip();
        setVisibility(enabled, false);
        if (getView() instanceof TextView) {
            WelcomeUtils.setTypeface((TextView) this.getView(), config.getSkipButtonTypefacePath(), config.getContext());
        }
    }

    @Override
    public void onPageSelected(int pageIndex, int firstPageIndex, int lastPageIndex) {
        if (onlyShowOnFirstPage)
            setVisibility(enabled && pageIndex == firstPageIndex);
        else
            setVisibility(enabled && WelcomeUtils.isIndexBeforeLastPage(pageIndex, lastPageIndex, isRtl));
    }

}
