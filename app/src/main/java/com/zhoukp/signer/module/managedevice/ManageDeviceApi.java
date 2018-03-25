package com.zhoukp.signer.module.managedevice;

import com.zhoukp.signer.module.login.LoginBean;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author zhoukp
 * @time 2018/3/18 19:27
 * @email 275557625@qq.com
 * @function
 */

public interface ManageDeviceApi {

    //解绑设备
    @POST("UnBindDevice?")
    Observable<DeviceBean> unBindDevice(
            @Query("userId") String userId
    );

    //绑定设备
    @POST("BindDevice?")
    Observable<DeviceBean> bindDevice(
            @Query("userId") String userId,
            @Query("stuSerialNo") String stuSerialNo
    );

    //修改密码
    @POST("ModifyPassword?")
    Observable<LoginBean> modifyPassword(
            @Query("userId") String userId,
            @Query("userPassword") String userPassword,
            @Query("newPassword") String newPassword
    );
}
