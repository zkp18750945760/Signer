package com.zhoukp.signer.pager;

import android.content.Context;
import android.view.View;

/**
 * @author zhoukp
 * @time 2018/1/29 19:15
 * @email 275557625@qq.com
 * @function
 */

public abstract class BasePager {
    /**
     * 上下文
     */
    public static Context context;
    /**
     * 接收各个页面的实例
     */
    public View rootView;

    public boolean isInitData;

    public BasePager(Context context) {
        BasePager.context = context;
        rootView = initView();
    }

    /**
     * 强制子页面实现该方法，实现想要的特定的效果
     *
     * @return view
     */
    public abstract View initView();


    /**
     * 当子页面，需要绑定数据，或者联网请求数据并且绑定的时候，重写该方法
     */
    public void initData() {

    }
}
