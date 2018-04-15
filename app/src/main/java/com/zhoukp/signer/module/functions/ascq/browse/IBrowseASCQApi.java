package com.zhoukp.signer.module.functions.ascq.browse;

import com.zhoukp.signer.module.managedevice.DeviceBean;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author zhoukp
 * @time 2018/4/14 22:13
 * @email 275557625@qq.com
 * @function
 */
public interface IBrowseASCQApi {

    /**
     * 获取综测成绩
     *
     * @param userId     用户ID
     * @param schoolYear 学年
     * @param userGrade  年级
     * @param userMajor  专业
     * @param userClazz  班级
     * @return 200->成功(已审核成绩) 101->未审核成绩 102->待审核综测表还不存在 103->该用户还没有上传对应学年的综测成绩 104->数据库IO错误
     */
    @POST("GetASCQScore?")
    Observable<ASCQScoreBean> getASCQScore(
            @Query("userId") String userId,
            @Query("schoolYear") String schoolYear,
            @Query("userGrade") String userGrade,
            @Query("userMajor") String userMajor,
            @Query("userClazz") String userClazz
    );

    /**
     * 确认综测成绩
     *
     * @param userId     用户ID
     * @param schoolYear 学年
     * @return 200->成功 101->还没有审核记录 102->数据库IO错误 103->已经确认过了
     */
    @POST("ConfirmASCQ?")
    Observable<DeviceBean> confirmASCQ(
            @Query("userId") String userId,
            @Query("schoolYear") String schoolYear
    );

    /**
     * 请求修改综测成绩
     *
     * @param userId     用户ID
     * @param schoolYear 学年
     * @return 200->成功 101->还没有审核记录 102->数据库IO错误 103->已经请求修改过了 104->已经确认过得分了，不能再请求修改
     */
    @POST("ModifyASCQ?")
    Observable<DeviceBean> modifyASCQ(
            @Query("userId") String userId,
            @Query("schoolYear") String schoolYear
    );
}
