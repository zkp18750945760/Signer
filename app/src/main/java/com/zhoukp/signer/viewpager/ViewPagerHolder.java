package com.zhoukp.signer.viewpager;

import android.content.Context;
import android.view.View;

/**
 * @author zhoukp
 * @time 2018/2/3 19:00
 * @email 275557625@qq.com
 * @function 提供布局和绑定数据
 */

public interface ViewPagerHolder<T> {
    /**
     * 创建View
     *
     * @param context 上下文
     * @return view
     */
    View createView(Context context);

    /**
     * 绑定数据
     *
     * @param context  上下文
     * @param position position
     * @param data     data
     */
    void onBind(Context context, int position, T data);
}
