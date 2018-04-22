package com.zhoukp.signer.module.functions.meetings.upload;

/**
 * @author zhoukp
 * @time 2018/4/21 16:26
 * @email 275557625@qq.com
 * @function
 */
public interface UploadView {

    void showLoadingView();


    void hideLoadingView();


    void uploadSuccess();

    void uploadError(int status);
}
