package com.zhoukp.signer.module.functions.meetings.upload;

import com.zhoukp.signer.module.functions.ledgers.scanxls.XlsBean;

import java.util.ArrayList;

/**
 * @author zhoukp
 * @time 2018/4/20 20:07
 * @email 275557625@qq.com
 * @function
 */
public interface UploadPdfView {

    void showLoadingView();

    void hideLoadingView();

    void scanSuccess(ArrayList<XlsBean> datas);

    void scanError();

    void scanQQSuccess(ArrayList<XlsBean> datas);

    void scanQQError();
}
