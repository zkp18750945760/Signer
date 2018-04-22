package com.zhoukp.signer.module.functions.meetings;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author zhoukp
 * @time 2018/4/9 22:16
 * @email 275557625@qq.com
 * @function
 */
public interface IMeetApi {

    /**
     * 获取支书会议列表
     *
     * @param userId     用户ID
     * @param userGrade  年级
     * @param userMajor  专业
     * @param userClazz  班级
     * @param schoolYear 学年
     * @param term       学期
     * @return 200->成功 101->还没有支书会议记录 102->数据库IO错误
     */
    @POST("GetDiscussion?")
    Observable<MeetBean> getDiscussion(
            @Query("userId") String userId,
            @Query("userGrade") String userGrade,
            @Query("userMajor") String userMajor,
            @Query("userClazz") String userClazz,
            @Query("schoolYear") String schoolYear,
            @Query("term") String term
    );

    /**
     * 标记支书会议已读
     *
     * @param userId 用户ID
     * @param theme  theme
     * @param date   date
     * @return 200->成功
     */
    @POST("UpdateDiscussionRead?")
    Observable<UpdateReadBean> updateDiscussionRead(
            @Query("userId") String userId,
            @Query("theme") String theme,
            @Query("date") String date
    );
}
