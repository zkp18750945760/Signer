package com.zhoukp.signer.module.functions.ledgers.second;

/**
 * @author zhoukp
 * @time 2018/3/28 14:27
 * @email 275557625@qq.com
 * @function
 */

public interface SecondLedgerView {

    //显示LoadingView
    void showLoadingView();

    //隐藏LoadingView
    void hideLoadingView();

    //获取台账数据成功
    void getLedgerSuccess(SecondLedgerBean bean);

    //获取台账数据失败
    void getLedgerError(int status);

}
