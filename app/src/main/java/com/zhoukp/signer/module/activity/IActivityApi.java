package com.zhoukp.signer.module.activity;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author zhoukp
 * @time 2018/3/19 15:11
 * @email 275557625@qq.com
 * @function
 */

public interface IActivityApi {
    /**
     * 获取所有文体活动信息
     */
    @POST("GetActivities?")
    Observable<ActivityBean> getActivities(
            @Query("userId") String userId
    );

    /**
     * 报名文体活动
     */
    @POST("ApplyActivities?")
    Observable<ActivityBean> applyActivities(
            @Query("userId") String userId,
            @Query("actName") String actName,
            @Query("actTime") String actTime
    );

    /**
     * 取消报名文体活动
     */
    @POST("CancelApplyActivities?")
    Observable<ActivityBean> cancelApplyActivities(
            @Query("userId") String userId,
            @Query("actName") String actName,
            @Query("actTime") String actTime
    );

    /**
     * 获取所有志愿活动信息
     */
    @POST("GetVolunteers?")
    Observable<VolunteerBean> getVolunteers(
            @Query("userId") String userId
    );

    /**
     * 报名志愿活动
     */
    @POST("ApplyVolunteers?")
    Observable<VolunteerBean> applyVolunteers(
            @Query("userId") String userId,
            @Query("volName") String volName,
            @Query("volTime") String volTime
    );

    /**
     * 取消报名志愿活动
     */
    @POST("CancelApplyVolunteers?")
    Observable<VolunteerBean> cancelApplyVolunteers(
            @Query("userId") String userId,
            @Query("volName") String volName,
            @Query("volTime") String volTime
    );
}
