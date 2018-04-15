package com.zhoukp.signer.module.functions.ascq.browse;

/**
 * @author zhoukp
 * @time 2018/4/14 22:11
 * @email 275557625@qq.com
 * @function
 */
public interface BrowseASCQView {

    void showLoadingView();

    void hideLoadingView();

    void getASCQSuccess(ASCQScoreBean bean);

    void getASCQUnChecked(ASCQScoreBean bean);

    void getASCQError(int status);

    void confirmSuccess();

    void confirmError(int status);

    void modifySuccess();

    void modifyError(int status);
}
