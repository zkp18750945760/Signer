package com.zhoukp.signer.module.course;

/**
 * @author zhoukp
 * @time 2018/4/5 18:46
 * @email 275557625@qq.com
 * @function
 */
public interface CourseView {

    //显示LoadingView
    void showLoadingView();

    //隐藏LoadingView
    void hideLoadingView();

    //获取课程成功
    void getCourseSuccess(CourseBean bean);

    //获取课程失败
    void getCourseError(int status);
}
