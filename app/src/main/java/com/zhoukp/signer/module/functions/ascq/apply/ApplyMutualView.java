package com.zhoukp.signer.module.functions.ascq.apply;


/**
 * @author zhoukp
 * @time 2018/4/12 16:12
 * @email 275557625@qq.com
 * @function
 */
public interface ApplyMutualView {

    void showLoadingView();

    void hideLoadingView();

    void getClazzStudentsSuccess(ClazzStudentsBean bean);

    void getClazzStudentsError(int status);

    void applyMutualSuccess(int position);

    void applyMutualError(int status);

    void cancelMutualSuccess(int position);

    void cancelMutualError(int status);

    void getMutualNumSuccess(MutualNumBean bean);

    void getMutualNumError(int status);

    void groupMutualSuccess();

    void groupMutualError(int status);

    void getGroupMutualStatusSuccess(GroupStatusBean bean);

    void getGroupMutualStatusError(int status);
}
