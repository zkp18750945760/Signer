package com.zhoukp.signer.module.functions.ledgers.second;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author zhoukp
 * @time 2018/3/28 14:27
 * @email 275557625@qq.com
 * @function
 */

public interface ISecondLedgerApi {

    /**
     * 获取某个学生对应年份的所有第二台账数据
     *
     * @param userId 用户ID
     * @param year   年份
     * @return
     */
    @POST("GetSecondLedger?")
    Observable<SecondLedgerBean> getSecondLedger(
            @Query("userId") String userId,
            @Query("year") int year
    );
}
