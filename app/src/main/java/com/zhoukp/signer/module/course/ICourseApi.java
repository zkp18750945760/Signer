package com.zhoukp.signer.module.course;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author zhoukp
 * @time 2018/4/5 18:49
 * @email 275557625@qq.com
 * @function
 */
public interface ICourseApi {

    /**
     * 获取课程表
     *
     * @param userId    用户ID
     * @param userGrade 年级
     * @param userMajor 专业
     * @param userClazz 班级
     * @return
     */
    @POST("GetCourse?")
    Observable<CourseBean> getCourse(
            @Query("userId") String userId,
            @Query("userGrade") String userGrade,
            @Query("userMajor") String userMajor,
            @Query("userClazz") String userClazz
    );
}
