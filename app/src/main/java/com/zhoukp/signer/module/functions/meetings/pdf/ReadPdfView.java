package com.zhoukp.signer.module.functions.meetings.pdf;

import java.io.File;

/**
 * @author zhoukp
 * @time 2018/4/10 0:59
 * @email 275557625@qq.com
 * @function
 */
public interface ReadPdfView {

    void showLoadingView();

    void hideLoadingView();

    void downloadSuccess(File file);

    void downloadError();
}
