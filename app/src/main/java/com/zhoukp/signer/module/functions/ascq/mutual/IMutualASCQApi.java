package com.zhoukp.signer.module.functions.ascq.mutual;

import com.zhoukp.signer.module.managedevice.DeviceBean;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author zhoukp
 * @time 2018/4/13 14:24
 * @email 275557625@qq.com
 * @function
 */
public interface IMutualASCQApi {

    /**
     * 判断当前用户是不是互评小组成员
     *
     * @param userId    用户ID
     * @param userGrade 年级
     * @param userMajor 专业
     * @param userClazz 班级
     * @return 200->成功
     */
    @POST("IsMutualMembers?")
    Observable<IsMutualMemberBean> isMutualMembers(
            @Query("userId") String userId,
            @Query("userGrade") String userGrade,
            @Query("userMajor") String userMajor,
            @Query("userClazz") String userClazz
    );

    /**
     * 判断分配的任务是不是完成了
     *
     * @param userId    用户ID
     * @param userGrade 年级
     * @param userMajor 专业
     * @param userClazz 班级
     * @return 200->成功
     */
    @POST("IsMutualFinish?")
    Observable<IsMutualFinishBean> isMutualFinish(
            @Query("userId") String userId,
            @Query("userGrade") String userGrade,
            @Query("userMajor") String userMajor,
            @Query("userClazz") String userClazz
    );

    /**
     * 获取当前用户未完成的任务列表
     *
     * @param userId    用户ID
     * @param userGrade 年级
     * @param userMajor 专业
     * @param userClazz 班级
     * @return 200->成功
     */
    @POST("GetMutualTask?")
    Observable<MutualTaskBean> getMutualTask(
            @Query("userId") String userId,
            @Query("userGrade") String userGrade,
            @Query("userMajor") String userMajor,
            @Query("userClazz") String userClazz
    );

    /**
     * 上传审核的综测数据
     *
     * @param ASCQ      ASCQ
     * @param userId    用户ID
     * @param userGrade 年级
     * @param userMajor 专业
     * @param userClazz 班级
     * @return 200->成功
     */
    @POST("UploadMutual?")
    Observable<DeviceBean> uploadMutual(
            @Query("ASCQ") String ASCQ,
            @Query("userId") String userId,
            @Query("userGrade") String userGrade,
            @Query("userMajor") String userMajor,
            @Query("userClazz") String userClazz
    );

}
