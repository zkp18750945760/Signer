package com.zhoukp.signer.module.course;

import android.util.Log;

import com.zhoukp.signer.utils.BaseApi;

/**
 * @author zhoukp
 * @time 2018/4/5 18:48
 * @email 275557625@qq.com
 * @function
 */
public class CoursePresenter {

    private CourseView courseView;

    public void attachView(CourseView courseView) {
        this.courseView = courseView;
    }

    /**
     * 获取课程表
     *
     * @param userId    用户ID
     * @param userGrade 年级
     * @param userMajor 专业
     * @param userClazz 班级
     */
    public void getCourse(String userId, String userGrade, String userMajor, String userClazz) {

        courseView.showLoadingView();

        BaseApi.request(BaseApi.createApi(ICourseApi.class).getCourse(userId, userGrade, userMajor, userClazz),
                new BaseApi.IResponseListener<CourseBean>() {
                    @Override
                    public void onSuccess(CourseBean data) {
                        Log.e("zkp", "getCourse==" + data.getStatus());
                        if (data.getStatus() == 200) {
                            courseView.getCourseSuccess(data);
                        } else {
                            courseView.getCourseError(data.getStatus());
                        }
                        courseView.hideLoadingView();
                    }

                    @Override
                    public void onFail() {
                        courseView.getCourseError(100);
                        courseView.hideLoadingView();
                    }
                });

    }

    public void detachView() {
        this.courseView = null;
    }
}
