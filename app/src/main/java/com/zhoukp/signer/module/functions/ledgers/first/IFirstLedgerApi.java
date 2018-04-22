package com.zhoukp.signer.module.functions.ledgers.first;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author zhoukp
 * @time 2018/4/9 13:23
 * @email 275557625@qq.com
 * @function
 */
public interface IFirstLedgerApi {
    /**
     * 获取第一台账
     *
     * @param userId    用户ID
     * @param userGrade 年级
     * @param userMajor 专业
     * @param userClazz 班级
     * @return 200->成功 101->还没有上传课程表 102->数据库IO错误
     */
    @POST("GetFirstLedger?")
    Observable<FirstLedgerBean> getFirstLedger(
            @Query("userId") String userId,
            @Query("userGrade") String userGrade,
            @Query("userMajor") String userMajor,
            @Query("userClazz") String userClazz,
            @Query("schoolYear") String schoolYear,
            @Query("term") String term

    );
}
