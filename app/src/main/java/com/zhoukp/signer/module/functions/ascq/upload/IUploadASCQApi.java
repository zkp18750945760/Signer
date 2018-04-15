package com.zhoukp.signer.module.functions.ascq.upload;

import com.zhoukp.signer.module.managedevice.DeviceBean;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author zhoukp
 * @time 2018/4/11 21:35
 * @email 275557625@qq.com
 * @function
 */
public interface IUploadASCQApi {

    /**
     * 上传综测成绩
     *
     * @param ASCQ      ASCQBean
     * @param userId    用户ID
     * @param userGrade 年级
     * @param userMajor 专业
     * @param userClazz 班级
     * @return 200->成功
     */
    @POST("UploadASQC?")
    Observable<DeviceBean> uploadASQC(
            @Query("ASCQ") String ASCQ,
            @Query("userId") String userId,
            @Query("userGrade") String userGrade,
            @Query("userMajor") String userMajor,
            @Query("userClazz") String userClazz
    );

}
