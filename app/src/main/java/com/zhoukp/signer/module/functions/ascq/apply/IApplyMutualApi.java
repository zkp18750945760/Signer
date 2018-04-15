package com.zhoukp.signer.module.functions.ascq.apply;

import com.zhoukp.signer.module.managedevice.DeviceBean;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author zhoukp
 * @time 2018/4/12 16:16
 * @email 275557625@qq.com
 * @function
 */
public interface IApplyMutualApi {

    /**
     * 获取某个班的所有学生信息
     *
     * @param userId    用户ID
     * @param userGrade 年级
     * @param userMajor 专业
     * @param userClazz 班级
     * @return 200->成功
     */
    @POST("GetClazzStudents?")
    Observable<ClazzStudentsBean> getClazzStudents(
            @Query("userId") String userId,
            @Query("userGrade") String userGrade,
            @Query("userMajor") String userMajor,
            @Query("userClazz") String userClazz
    );

    /**
     * 邀请某个学生进入互评小组
     *
     * @param userId    用户ID
     * @param userGrade 年级
     * @param userMajor 专业
     * @param userClazz 班级
     * @param mutualId  被邀请人ID
     * @return 200->成功
     */
    @POST("ApplyMutual?")
    Observable<DeviceBean> applyMutual(
            @Query("userId") String userId,
            @Query("userGrade") String userGrade,
            @Query("userMajor") String userMajor,
            @Query("userClazz") String userClazz,
            @Query("mutualId") String mutualId
    );

    /**
     * 取消邀请某个学生进入互评小组
     *
     * @param userId    用户ID
     * @param userGrade 年级
     * @param userMajor 专业
     * @param userClazz 班级
     * @param mutualId  被邀请人ID
     * @return 200->成功
     */
    @POST("CancelMutual?")
    Observable<DeviceBean> cancelMutual(
            @Query("userId") String userId,
            @Query("userGrade") String userGrade,
            @Query("userMajor") String userMajor,
            @Query("userClazz") String userClazz,
            @Query("mutualId") String mutualId
    );

    /**
     * 获取互评小组成员数量
     *
     * @param userId    用户ID
     * @param userGrade 年级
     * @param userMajor 专业
     * @param userClazz 班级
     * @return 200->成功
     */
    @POST("GetMutualNum?")
    Observable<MutualNumBean> getMutualNum(
            @Query("userId") String userId,
            @Query("userGrade") String userGrade,
            @Query("userMajor") String userMajor,
            @Query("userClazz") String userClazz
    );

    /**
     * 分配某个班级所有学生的综测表给互评小组审核
     *
     * @param userId    用户ID
     * @param userGrade 年级
     * @param userMajor 专业
     * @param userClazz 班级
     * @return 200->成功
     */
    @POST("GroupMutual?")
    Observable<DeviceBean> groupMutual(
            @Query("userId") String userId,
            @Query("userGrade") String userGrade,
            @Query("userMajor") String userMajor,
            @Query("userClazz") String userClazz
    );

    /**
     * 获取综测表分配状态
     *
     * @param userId    用户ID
     * @param userGrade 年级
     * @param userMajor 专业
     * @param userClazz 班级
     * @return 200->成功
     */
    @POST("GetGroupMutualStatus?")
    Observable<GroupStatusBean> getGroupMutualStatus(
            @Query("userId") String userId,
            @Query("userGrade") String userGrade,
            @Query("userMajor") String userMajor,
            @Query("userClazz") String userClazz
    );
}
