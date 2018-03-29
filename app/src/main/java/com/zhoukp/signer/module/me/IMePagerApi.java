package com.zhoukp.signer.module.me;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * @author zhoukp
 * @time 2018/3/18 16:03
 * @email 275557625@qq.com
 * @function
 */

public interface IMePagerApi {
    /**
     * 上传头像
     *
     * @param userId       用户ID
     * @param body         图片
     * @param headIconName 图片名
     * @return
     */
    @POST("UploadHeadIcon?")
    Observable<UploadHeadIconBean> uploadHeadIcon(
            @Query("userId") String userId,
            @Body RequestBody body,
            @Query("headIconName") String headIconName
    );

    /**
     * 获取用户头像连接
     *
     * @param uesrId
     * @return
     */
    @POST("GetHeadIcons?")
    Observable<UploadHeadIconBean> getHeadIcon(
            @Query("userId") String uesrId
    );

    /**
     * 下载头像
     *
     * @param fileUrl url
     * @return
     */
    @GET
    Observable<ResponseBody> downloadHeadIcon(
            @Url String fileUrl);
}
