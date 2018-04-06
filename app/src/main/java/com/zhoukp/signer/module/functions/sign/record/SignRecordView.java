package com.zhoukp.signer.module.functions.sign.record;

/**
 * @author zhoukp
 * @time 2018/4/5 20:06
 * @email 275557625@qq.com
 * @function
 */
public interface SignRecordView {

    void showLoadingView();

    void hideLoadingView();

    void getRecordSuccess(SignRecordBean bean);

    void getRecordError(int status);
}
