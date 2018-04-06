package com.zhoukp.signer.module.welcome;

import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.view.View;

/**
 * @author zhoukp
 * @time 2018/4/1 14:53
 * @email 275557625@qq.com
 * @function
 */
abstract class WelcomeViewWrapper implements OnWelcomeScreenPageChangeListener {

    private View view;
    private int firstPageIndex = 0;
    private int lastPageIndex = 0;
    private boolean animated = true;
    protected boolean isRtl = false;

    WelcomeViewWrapper(View view) {
        this.view = view;
    }

    public abstract void onPageSelected(int pageIndex, int firstPageIndex, int lastPageIndex);

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        onPageSelected(position, firstPageIndex, lastPageIndex);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void setup(WelcomeConfiguration config) {
        firstPageIndex = config.firstPageIndex();
        lastPageIndex = config.lastViewablePageIndex();
        animated = config.getAnimateButtons();
        isRtl = config.isRtl();
    }

    protected View getView() {
        return this.view;
    }

    protected void setVisibility(boolean visible) {
        setVisibility(visible, animated);
    }

    protected void setVisibility(boolean visible, boolean animate) {
        view.setEnabled(visible);
        if (visible) {
            show(animate);
        } else {
            hide(animate);
        }
    }

    protected void hide(boolean animate) {
        if (animate) {
            hideWithAnimation();
        } else {
            hideImmediately();
        }
    }

    private void show(boolean animate) {
        if (animate) {
            showWithAnimation();
        } else {
            showImmediately();
        }
    }

    protected void hideImmediately() {
        view.setVisibility(View.INVISIBLE);
    }

    protected void showImmediately() {
        setAlpha(1f);
        view.setVisibility(View.VISIBLE);
    }

    protected void hideWithAnimation() {
        ViewPropertyAnimatorListener listener = new ViewPropertyAnimatorListener() {

            @Override
            public void onAnimationStart(View view) {
            }

            @Override
            public void onAnimationEnd(View view) {
                setAlpha(0f);
                WelcomeViewWrapper.this.view.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(View view) {
                setAlpha(0f);
                WelcomeViewWrapper.this.view.setVisibility(View.INVISIBLE);
            }
        };

        ViewCompat.animate(view).alpha(0f).setListener(listener).start();
    }

    protected void showWithAnimation() {

        ViewPropertyAnimatorListener listener = new ViewPropertyAnimatorListener() {

            @Override
            public void onAnimationStart(View view) {
                WelcomeViewWrapper.this.view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(View view) {
                setAlpha(1f);
            }

            @Override
            public void onAnimationCancel(View view) {
                setAlpha(1f);
            }
        };

        ViewCompat.animate(view).alpha(1f).setListener(listener).start();
    }

    void setOnClickListener(View.OnClickListener listener) {
        view.setOnClickListener(listener);
    }

    private void setAlpha(float alpha) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            view.setAlpha(alpha);
    }

}
