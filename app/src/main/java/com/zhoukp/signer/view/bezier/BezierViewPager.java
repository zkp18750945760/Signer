package com.zhoukp.signer.view.bezier;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author zhoukp
 * @time 2018/4/22 14:59
 * @email 275557625@qq.com
 * @function
 */
public class BezierViewPager extends ViewPager {
    private boolean touchable = true;
    private ShadowTransformer cardShadowTransformer;

    public BezierViewPager(Context context) {
        super(context);
    }

    public BezierViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setTouchable(boolean isCanScroll) {
        this.touchable = isCanScroll;
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (touchable) {
            return super.onInterceptTouchEvent(arg0);
        } else {
            return false;
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (touchable) {
            return super.onTouchEvent(arg0);
        } else {
            return false;
        }

    }

    public void showTransformer(float zoomIn) {
        if (CardAdapter.class.isInstance(getAdapter())) {
            if (cardShadowTransformer == null) {
                cardShadowTransformer = new ShadowTransformer();
                cardShadowTransformer.attachViewPager(this, (CardAdapter) getAdapter());
            }
            cardShadowTransformer.setZoomIn(zoomIn);
        }
    }
}
