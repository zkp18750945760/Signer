package com.zhoukp.signer.module.login;

/**
 * @author zhoukp
 * @time 2018/3/15 20:18
 * @email 275557625@qq.com
 * @function LoginView
 */

public interface LoginView {
    //隐藏loading view
    void hideLoadingView();

    //显示loading view
    void showLoadingView();

    //用户名或密码为空
    void nullNameOrPsd();

    //登陆成功
    void loginSuccess(LoginBean loginBean);

    //登陆事变
    void loginError();
}
