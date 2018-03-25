package com.zhoukp.signer.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.zhoukp.signer.R;
import com.zhouwei.indicatorview.CircleIndicatorView;

import java.util.List;

/**
 * @author zhoukp
 * @time 2018/2/3 19:19
 * @email 275557625@qq.com
 * @function
 */

public class CommonViewPager<T> extends RelativeLayout {

    private ViewPager viewPager;
    private ViewPagerAdapter<T> adapter;
    private CircleIndicatorView circleIndicatorView;

    public CommonViewPager(Context context) {
        this(context, null);
    }

    public CommonViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.viewpager_layout, this, true);
        viewPager = view.findViewById(R.id.viewPager);
        circleIndicatorView = view.findViewById(R.id.viewPagerIndicator);
    }

    /**
     * 设置数据
     *
     * @param data    data
     * @param creator creator
     */
    public void setPages(List<T> data, ViewPagerHolderCreator<ViewPagerHolder> creator) {
        adapter = new ViewPagerAdapter<>(data, creator);
        viewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        circleIndicatorView.setUpWithViewPager(viewPager);
    }

    public void setCurrentItem(int currentItem) {
        viewPager.setCurrentItem(currentItem);
    }

    public int getCurrentItem() {
        return viewPager.getCurrentItem();
    }

    public void setOffscreenPageLimit(int limit) {
        viewPager.setOffscreenPageLimit(limit);
    }

    /**
     * 设置切换动画，see {@link ViewPager#setPageTransformer(boolean, ViewPager.PageTransformer)}
     *
     * @param reverseDrawingOrder reverseDrawingOrder
     * @param transformer         transformer
     */
    public void setPageTransformer(boolean reverseDrawingOrder, ViewPager.PageTransformer transformer) {
        viewPager.setPageTransformer(reverseDrawingOrder, transformer);
    }

    /**
     * see {@link ViewPager#addOnPageChangeListener(ViewPager.OnPageChangeListener)}
     *
     * @param listener listener
     */
    public void addOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        viewPager.addOnPageChangeListener(listener);
    }

    /**
     * 设置是否显示Indicator
     *
     * @param visible 是否可见
     */
    public void setIndicatorVisible(boolean visible) {
        if (visible) {
            circleIndicatorView.setVisibility(VISIBLE);
        } else {
            circleIndicatorView.setVisibility(GONE);
        }
    }

    public ViewPager getViewPager() {
        return viewPager;
    }
}
