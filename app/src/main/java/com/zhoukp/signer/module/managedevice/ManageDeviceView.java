package com.zhoukp.signer.module.managedevice;

/**
 * @author zhoukp
 * @time 2018/3/18 19:27
 * @email 275557625@qq.com
 * @function ManageDeviceView
 */

public interface ManageDeviceView {

    //显示LoadingView
    void showLoadingView();

    //隐藏LoadingView
    void hideLoadingView();

    //解绑成功
    void unBindSuccess();

    //距离上一次解绑还没有30天
    void unBindLater();

    //解绑失败
    void unBindError();

    //绑定成功
    void bindSuccess();

    //绑定失败
    void bindError();

    //请先解绑
    void unBindFirst();

    //旧密码或新密码为空
    void inputPassword();

    //修改密码成功
    void modifySuccess();

    //重新登录
    void reLogin();

    //旧密码错误
    void passWordError();

    //修改失败
    void modifyError();
}
