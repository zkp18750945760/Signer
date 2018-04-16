package com.zhoukp.signer.module.functions.ascq.update;

import com.zhoukp.signer.module.functions.ascq.mutual.IsMutualMemberBean;
import com.zhoukp.signer.module.managedevice.DeviceBean;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author zhoukp
 * @time 2018/4/15 15:20
 * @email 275557625@qq.com
 * @function
 */
public interface IUpdateASCQApi {

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
     * 获取某个学年审核的所有学生学号
     *
     * @param userId     用户ID
     * @param schoolYear 学年
     * @return 200->成功 101->还没有审核记录 102->数据库IO错误
     */
    @POST("GetMutualIDs?")
    Observable<MutualIDsBean> getMutualIDs(
            @Query("userId") String userId,
            @Query("schoolYear") String schoolYear
    );

    /**
     * 获取申请修改综测成绩学生的互评成绩
     *
     * @param userId 用户ID
     * @param schoolYear 学年
     * @return 200->成功 101->还没有审核记录 102->成绩已被学生本人确定 103->数据库IO错误 104->该学生未申请修改成绩
     */
    @POST("GetModifyASCQ?")
    Observable<UpdateASCQBean> getModifyASCQ(
            @Query("userId") String userId,
            @Query("schoolYear") String schoolYear
    );

    /**
     * 更新某个学年某个学生的综测成绩
     *
     * @param ASCQ ASCQ
     * @return 200->成功 101->还没有审核记录 102->该条记录不存在数据库中 103->数据库IO错误
     */
    @POST("UpdateASCQ?")
    Observable<DeviceBean> updateASCQ(
            @Query("ASCQ") String ASCQ
    );

}
