package com.zhoukp.signer.module.me;

/**
 * @author zhoukp
 * @time 2018/3/18 16:23
 * @email 275557625@qq.com
 * @function
 */

public interface MePagerView {
    //显示loadingView
    void showLoadingView();

    //隐藏loadingView
    void hideLoadingView();

    //刷新头像
    void refreshHeadIcon(String headIconPath);

    //上传头像成功
    void uploadHeadIconSuccess(UploadHeadIconBean data);

    //上传头像失败
    void uploadHeadIconError();

    //获取用户头像成功
    void getHeadIconSuccess(UploadHeadIconBean data);

    //获取用户头像失败
    void getHeadIconError(int status);
}
