package com.zhoukp.signer.module.functions.ledgers.scanxls;

import java.util.ArrayList;

/**
 * @author zhoukp
 * @time 2018/3/27 21:02
 * @email 275557625@qq.com
 * @function
 */

public interface ScanXlsView {

    //显示LoadingView
    void showDialog();

    //隐藏LoadingView
    void hideDialog();

    //扫描文件成功
    void scanXlsSuccess(ArrayList<XlsBean> datas);

    //扫描文件失败
    void scanXlsError();
}
