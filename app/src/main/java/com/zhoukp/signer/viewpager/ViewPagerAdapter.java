package com.zhoukp.signer.viewpager;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.zhoukp.signer.R;

import java.util.List;

/**
 * @author zhoukp
 * @time 2018/2/3 19:02
 * @email 275557625@qq.com
 * @function ViewPager的Adapter
 */

public class ViewPagerAdapter<T> extends PagerAdapter {

    /**
     * 数据
     */
    private List<T> datas;
    /**
     * ViewHolder生成器
     */
    private ViewPagerHolderCreator creator;

    public ViewPagerAdapter(List<T> datas, ViewPagerHolderCreator creator) {
        this.datas = datas;
        this.creator = creator;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = getView(position, null, container);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    /**
     * 获取viewPager 页面展示View
     *
     * @param position  position
     * @param view      view
     * @param container viewgroup
     * @return view
     */
    private View getView(int position, View view, ViewGroup container) {
        ViewPagerHolder holder;
        if (view == null) {
            //创建Holder
            holder = creator.createViewHolder();
            view = holder.createView(container.getContext());
            view.setTag(R.id.commonViewPagerItemTag, holder);
        } else {
            holder = (ViewPagerHolder) view.getTag(R.id.commonViewPagerItemTag);
        }
        if (holder != null && datas != null && datas.size() > 0) {
            // 数据绑定
            holder.onBind(container.getContext(), position, datas.get(position));
        }
        return view;
    }
}
