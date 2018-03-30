package com.zhoukp.signer.module.main;

import com.zhoukp.signer.module.functions.ledgers.scanxls.XlsBean;

/**
 * @author zhoukp
 * @time 2018/3/30 15:35
 * @email 275557625@qq.com
 * @function
 */
public interface MainView {

    //显示LoadingView
    void showLoadingView();

    //隐藏LoadingView
    void hideLoadingView();

    //获取更新信息成功
    void getUpdateInfoSuccess(UpdateBean bean);

    //获取更新信息失败
    void getUpdateInfoError(int status);

    //获取错误日志成功
    void getCrashLogcatSuccess(XlsBean bean);

    //获取错误日志失败
    void getCrashLogcatError();

    //上传错误日志成功
    void uploadCrashSuccess();

    //上传错误日志失败
    void uploadCrashError();
}
