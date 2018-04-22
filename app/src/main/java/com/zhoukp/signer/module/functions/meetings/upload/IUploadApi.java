package com.zhoukp.signer.module.functions.meetings.upload;

import com.zhoukp.signer.module.managedevice.DeviceBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author zhoukp
 * @time 2018/4/21 15:51
 * @email 275557625@qq.com
 * @function
 */
public interface IUploadApi {

    /**
     * 上传支书会议记录到服务器
     *
     * @param body       文件
     * @param userId     用户ID
     * @param schoolYear 学年
     * @param term       学期
     * @param content    主题
     * @param Abstract   摘要
     * @param userGrade  年级
     * @param userMajor  专业
     * @param userClazz  班级
     * @return 200->成功 101->服务器IO错误 102->文件转换失败
     */
    @POST("UploadDiscussion?")
    Observable<DeviceBean> uploadDiscussion(
            @Body RequestBody body,
            @Query("userId") String userId,
            @Query("schoolYear") String schoolYear,
            @Query("term") String term,
            @Query("content") String content,
            @Query("Abstract") String Abstract,
            @Query("userGrade") String userGrade,
            @Query("userMajor") String userMajor,
            @Query("userClazz") String userClazz
    );
}
