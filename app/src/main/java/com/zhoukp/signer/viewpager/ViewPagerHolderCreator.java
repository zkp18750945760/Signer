package com.zhoukp.signer.viewpager;

/**
 * @author zhoukp
 * @time 2018/2/3 19:01
 * @email 275557625@qq.com
 * @function ViewHolder生成器
 */

public interface ViewPagerHolderCreator<VH extends ViewPagerHolder> {
    /**
     * 创建ViewHolder
     *
     * @return VH
     */
    public VH createViewHolder();
}
