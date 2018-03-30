package com.zhoukp.signer.module.update;

/**
 * @author zhoukp
 * @time 2018/3/30 13:53
 * @email 275557625@qq.com
 * @function 下载管理者
 */

public abstract class BaseHttpDownloadManager {

    /**
     * 下载apk
     *
     * @param apkUrl   apk下载地址
     * @param apkName  apk名字
     * @param listener 回调
     */
    public abstract void download(String apkUrl, String apkName, OnDownloadListener listener);
}
