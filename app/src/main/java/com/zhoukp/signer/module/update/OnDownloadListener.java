package com.zhoukp.signer.module.update;

import java.io.File;

/**
 * @author zhoukp
 * @time 2018/3/30 13:52
 * @email 275557625@qq.com
 * @function 下载回调
 */

public interface OnDownloadListener {

    /**
     * 开始下载
     */
    void start();

    /**
     * 下载中
     *
     * @param max      总进度
     * @param progress 当前进度
     */
    void downloading(int max, int progress);

    /**
     * 下载完成
     *
     * @param apk 下载好的apk
     */
    void done(File apk);

    /**
     * 下载出错
     *
     * @param e 错误信息
     */
    void error(Exception e);
}
