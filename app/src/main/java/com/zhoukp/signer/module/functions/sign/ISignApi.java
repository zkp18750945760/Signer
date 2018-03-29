package com.zhoukp.signer.module.functions.sign;


import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ISignApi {

    /**
     * 获取课程
     */
    @POST("GetSignEvents?")
    Observable<SignEventsBean> getSignEvents(
            @Query("userId") String userId
    );

    /**
     * 获取事项的发起签到状态
     */
    @POST("GetEventsSponsorSignStatus?")
    Observable<SponsorSignBean> GetEventsSponsorSignStatus(
            @Query("userId") String userId,
            @Query("content") String content
    );

    /**
     * 发起签到
     */
    @POST("SponsorSign?")
    Observable<SponsorSignBean> sponsorSign(
            @Query("userId") String userId,
            @Query("userGrade") String userGrade,
            @Query("userMajor") String userMajor,
            @Query("userClazz") String userClazz,
            @Query("type") int type,
            @Query("time") String time,
            @Query("content") String content,
            @Query("longitude") double longitude,
            @Query("latitude") double latitude,
            @Query("radius") float radius
    );

    /**
     * 签到
     */
    @POST("Sign?")
    Observable<SponsorSignBean> sign(
            @Query("userId") String userId,
            @Query("userGrade") String userGrade,
            @Query("userMajor") String userMajor,
            @Query("userClazz") String userClazz,
            @Query("type") int type,
            @Query("time") String time,
            @Query("content") String content,
            @Query("longitude") double longitude,
            @Query("latitude") double latitude,
            @Query("radius") float radius
    );

    /**
     * 获取某个事项已签到人的头像
     */
    @POST("GetSignedHeadIcons?")
    Observable<SignedHeadIconsBean> getSignedHeadIcons(
            @Query("userId") String userId,
            @Query("userGrade") String userGrade,
            @Query("userMajor") String userMajor,
            @Query("userClazz") String userClazz,
            @Query("type") int type,
            @Query("time") String time,
            @Query("content") String content
    );

}
