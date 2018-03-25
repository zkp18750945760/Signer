package com.zhoukp.signer.module.login;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ILoginApi {

    /**
     * 登录
     */
    @POST("Login?")
    Observable<LoginBean> login(
            @Query("userId") String userId,
            @Query("userPassword") String userPassword,
            @Query("stuSerialNo") String serialNo
    );
}
