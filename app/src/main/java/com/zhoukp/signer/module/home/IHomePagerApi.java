package com.zhoukp.signer.module.home;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author zhoukp
 * @time 2018/3/22 20:52
 * @email 275557625@qq.com
 * @function
 */

public interface IHomePagerApi {
    /**
     * 获取今天所有的日程安排
     *
     * @param userId 用户ID
     * @return ScheduleBean
     */
    @POST("GetAllSchedule?")
    Observable<ScheduleBean> getAllSchedule(
            @Query("userId") String userId
    );

    /**
     * 获取所有banner图片信息
     *
     * @return BannerBean
     */
    @POST("GetBanners?")
    Observable<BannerBean> getBanners();

}
