package com.zhoukp.signer.module.functions.meetings;

import com.zhoukp.signer.module.managedevice.DeviceBean;

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
     * 获取支书会议记录
     *
     * @param userId 用户ID
     * @return 200->成功
     */
    @POST("GetDiscussion?")
    Observable<MeetBean> getDiscussion(
            @Query("userId") String userId
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
