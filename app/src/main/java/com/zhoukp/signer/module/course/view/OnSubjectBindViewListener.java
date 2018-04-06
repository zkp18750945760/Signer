package com.zhoukp.signer.module.course.view;

import android.widget.TextView;

import java.util.List;

/**
 * @author zhoukp
 * @time 2018/4/3 20:57
 * @email 275557625@qq.com
 * @function
 */
public interface OnSubjectBindViewListener {
    void onBindTitleView(TextView titleTextView, int curWeek, String curTerm, List<SubjectBean> subjectBeans);
}
