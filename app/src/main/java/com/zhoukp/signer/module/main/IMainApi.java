package com.zhoukp.signer.module.main;

import com.zhoukp.signer.module.functions.sign.SponsorSignBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author zhoukp
 * @time 2018/3/30 15:33
 * @email 275557625@qq.com
 * @function
 */
public interface IMainApi {

    /**
     * 获取版本更新信息
     *
     * @return
     */
    @POST("GetUpdateInfo?")
    Observable<UpdateBean> getUpdateInfo();

    /**
     * 上传错误日志
     *
     * @param body RequestBody
     * @return
     */
    @POST("UploadCrashLogcat?")
    Observable<SponsorSignBean> uploadCrashLogcat(
            @Body RequestBody body
    );
}
