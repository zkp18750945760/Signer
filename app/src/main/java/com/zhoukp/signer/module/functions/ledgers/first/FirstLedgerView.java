package com.zhoukp.signer.module.functions.ledgers.first;

/**
 * @author zhoukp
 * @time 2018/4/9 13:18
 * @email 275557625@qq.com
 * @function
 */
public interface FirstLedgerView {

    //显示LoadingView
    void showLoadingView();

    //隐藏LoadingView
    void hideLoadingView();

    //获取第一台账成功
    void getFirstLedgerSuccess(FirstLedgerBean bean);

    //获取第一台账失败
    void getFirstLedgerError(int status);
}
