package com.zhoukp.signer.module.functions.ascq.upload;

import com.zhoukp.signer.module.managedevice.DeviceBean;

/**
 * @author zhoukp
 * @time 2018/4/11 21:32
 * @email 275557625@qq.com
 * @function
 */
public interface UploadASCQView {

    //显示LoadingView
    void showLoadingView();

    //隐藏LoadingView
    void hideLoadingView();

    //上传综测成绩成功
    void uploadASCQSuccess(DeviceBean bean);

    //上传综测成绩失败
    void uploadASCQError(int status);
}
