package com.zhoukp.signer.module.welcome;

import android.support.v4.view.ViewPager;

/**
 * @author zhoukp
 * @time 2018/4/1 14:22
 * @email 275557625@qq.com
 * @function
 */
interface OnWelcomeScreenPageChangeListener extends ViewPager.OnPageChangeListener {
    void setup(WelcomeConfiguration config);
}
