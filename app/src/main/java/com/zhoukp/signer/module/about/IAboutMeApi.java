package com.zhoukp.signer.module.about;

import com.zhoukp.signer.module.main.UpdateBean;

import io.reactivex.Observable;
import retrofit2.http.POST;

/**
 * @author zhoukp
 * @time 2018/4/6 23:29
 * @email 275557625@qq.com
 * @function
 */
public interface IAboutMeApi {
    /**
     * 获取版本更新信息
     *
     * @return
     */
    @POST("GetUpdateInfo?")
    Observable<UpdateBean> getUpdateInfo();
}
