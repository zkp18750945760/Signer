package com.zhoukp.signer.module.functions.sign.record;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author zhoukp
 * @time 2018/4/6 13:38
 * @email 275557625@qq.com
 * @function
 */
public interface ISignRecordApi {

    /**
     * 获取签到记录
     *
     * @param userId    用户ID
     * @param userGrade 年级
     * @param userMajor 专业
     * @param userClazz 班级
     * @return 200->成功 101->数据库IO错误
     */
    @POST("GetSignRecord?")
    Observable<SignRecordBean> getSignRecord(
            @Query("userId") String userId,
            @Query("userGrade") String userGrade,
            @Query("userMajor") String userMajor,
            @Query("userClazz") String userClazz
    );
}
