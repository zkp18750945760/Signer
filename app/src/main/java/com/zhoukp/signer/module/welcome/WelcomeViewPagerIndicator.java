package com.zhoukp.signer.module.welcome;

import android.content.Context;
import android.util.AttributeSet;
/**
 * @author zhoukp
 * @time 2018/4/1 14:20
 * @email 275557625@qq.com
 * @function
 */
public class WelcomeViewPagerIndicator extends SimpleViewPagerIndicator implements OnWelcomeScreenPageChangeListener {

    public WelcomeViewPagerIndicator(Context context) {
        super(context);
    }

    public WelcomeViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WelcomeViewPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setup(WelcomeConfiguration config) {
        setTotalPages(config.viewablePageCount());
        if (config.isRtl()) {
            setRtl(true);
            if (config.getSwipeToDismiss()) {
                setPageIndexOffset(-1);
            }
        }
    }
}