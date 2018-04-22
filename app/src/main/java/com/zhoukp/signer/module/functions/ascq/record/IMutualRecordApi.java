package com.zhoukp.signer.module.functions.ascq.record;

import com.zhoukp.signer.module.functions.ascq.mutual.IsMutualMemberBean;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author zhoukp
 * @time 2018/4/15 14:01
 * @email 275557625@qq.com
 * @function
 */
public interface IMutualRecordApi {

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
     * 获取某个学年的互评记录
     *
     * @param userId     用户ID
     * @param schoolYear 学年
     * @return 200->成功 101->还没有审核记录 102->数据库IO错误
     */
    @POST("GetMutualRecord?")
    Observable<MutualRecordBean> getMutualRecord(
            @Query("userId") String userId,
            @Query("schoolYear") String schoolYear
    );

}
