package com.zhoukp.signer.module.course.view;

import android.view.View;

/**
 * @author zhoukp
 * @time 2018/4/3 20:40
 * @email 275557625@qq.com
 * @function 课表项长按的监听
 */
public interface OnSubjectItemLongClickListener {
    void onItemLongClick(View view, int day, int start);
}
