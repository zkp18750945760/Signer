package com.zhoukp.signer.module.login;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.zhoukp.signer.utils.BaseApi;
import com.zhoukp.signer.utils.DeviceUuidFactory;

/**
 * @author zhoukp
 * @time 2018/3/15 21:17
 * @email 275557625@qq.com
 * @function LoginPresenter
 */

public class LoginPresenter {

    private LoginView loginView;

    /**
     * 绑定视图
     *
     * @param loginView LoginView
     */
    public void attachView(LoginView loginView) {
        this.loginView = loginView;
    }

    /**
     * 登陆
     *
     * @param userId       用户名
     * @param userPassword 密码
     */
    public void login(String userId, String userPassword, String serialNo) {
        loginView.showLoadingView();
        if (TextUtils.isEmpty(userId) || TextUtils.isEmpty(userPassword)) {
            loginView.nullNameOrPsd();
            loginView.hideLoadingView();
            return;
        }

        BaseApi.request(BaseApi.createApi(ILoginApi.class).login(userId, userPassword, serialNo), new BaseApi.IResponseListener<LoginBean>() {
            @Override
            public void onSuccess(LoginBean loginBean) {
                Log.e("zkp", loginBean.getStatus() + "");
                if (loginBean.getStatus() == 200) {
                    UserUtil.getInstance().setUser(loginBean.getUser());
                    loginView.loginSuccess(loginBean);
                } else {
                    loginView.loginError();
                }
                loginView.hideLoadingView();
            }

            @Override
            public void onFail() {
                loginView.loginError();
                loginView.hideLoadingView();
            }
        });
    }

    /**
     * 获取设备序列号
     *
     * @return 序列号
     */
    public String getSerialNo(Context context) {
        return DeviceUuidFactory.getUDID(context);
    }

    /**
     * 解绑视图
     */
    public void detachView() {
        this.loginView = null;
    }
}
