package com.zhoukp.signer.module.welcome;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author zhoukp
 * @time 2018/4/1 14:52
 * @email 275557625@qq.com
 * @function
 */
class WelcomeItemList extends ArrayList<OnWelcomeScreenPageChangeListener> implements OnWelcomeScreenPageChangeListener {

    WelcomeItemList(OnWelcomeScreenPageChangeListener... items) {
        super(Arrays.asList(items));
    }

    void addAll(OnWelcomeScreenPageChangeListener... items) {
        super.addAll(Arrays.asList(items));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        for (OnWelcomeScreenPageChangeListener changeListener : this) {
            changeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        for (OnWelcomeScreenPageChangeListener changeListener : this) {
            changeListener.onPageSelected(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        for (OnWelcomeScreenPageChangeListener changeListener : this) {
            changeListener.onPageScrollStateChanged(state);
        }
    }

    @Override
    public void setup(WelcomeConfiguration config) {
        for (OnWelcomeScreenPageChangeListener changeListener : this) {
            changeListener.setup(config);
        }
    }
}
