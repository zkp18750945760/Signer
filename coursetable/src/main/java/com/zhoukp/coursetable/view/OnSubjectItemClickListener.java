package com.zhoukp.coursetable.view;

import java.util.List;

import android.view.View;

/**
 * @author zhoukp
 * @time 2018/4/3 20:39
 * @email 275557625@qq.com
 * @function 可表象点击的监听
 */
public interface OnSubjectItemClickListener {

    /**
     * 课表项被点击时触发
     *
     * @param view        view
     * @param subjectList List
     */
    void onItemClick(View view, List<SubjectBean> subjectList);

}
