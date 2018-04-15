package com.zhoukp.signer.module.functions.ascq.mutual;

/**
 * @author zhoukp
 * @time 2018/4/13 14:23
 * @email 275557625@qq.com
 * @function
 */
public interface MutualASCQView {

    void showLoadingView();

    void hideLoadingView();

    void isMemberSuccess(IsMutualMemberBean bean);

    void isMemberError(int status);

    void isFinishSuccess(IsMutualFinishBean bean);

    void isFinishError(int status);

    void getTaskSuccess(MutualTaskBean bean);

    void ASQCNotExist();

    void getTaskError(int status);

    void uploadSuccess();

    void uploadError(int status);

}
