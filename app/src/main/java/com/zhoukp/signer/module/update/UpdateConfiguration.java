package com.zhoukp.signer.module.update;

import android.app.NotificationChannel;

/**
 * @author zhoukp
 * @time 2018/3/30 14:02
 * @email 275557625@qq.com
 * @function 版本更新的一些配置信息, 配置一些必须要的默认信息
 */

public class UpdateConfiguration {
    /**
     * 通知栏id
     */
    private int notifyId = 1011;
    /**
     * 适配Android O的渠道通知
     */
    private NotificationChannel notificationChannel;
    /**
     * 用户自定义的下载管理
     */
    private BaseHttpDownloadManager httpManager;
    /**
     * 是否需要支持断点下载 (默认是支持的)
     */
    private boolean breakpointDownload = true;
    /**
     * 是否需要日志输出（错误日志）
     */
    private boolean enableLog = true;
    /**
     * 是否需要显示通知栏进度
     */
    private boolean showNotification = true;
    /**
     * 下载过程回调
     */
    private OnDownloadListener onDownloadListener;
    /**
     * 下载完成是否自动弹出安装页面 (默认为true)
     */
    private boolean jumpInstallPage = true;


    public int getNotifyId() {
        return notifyId;
    }

    public UpdateConfiguration setNotifyId(int notifyId) {
        this.notifyId = notifyId;
        return this;
    }

    public UpdateConfiguration setHttpManager(BaseHttpDownloadManager httpManager) {
        this.httpManager = httpManager;
        return this;
    }

    public BaseHttpDownloadManager getHttpManager() {
        return httpManager;
    }

    public boolean isBreakpointDownload() {
        return breakpointDownload;
    }

    public UpdateConfiguration setBreakpointDownload(boolean breakpointDownload) {
        this.breakpointDownload = breakpointDownload;
        return this;
    }

    public boolean isEnableLog() {
        return enableLog;
    }

    public UpdateConfiguration setEnableLog(boolean enableLog) {
        this.enableLog = enableLog;
        return this;
    }

    public OnDownloadListener getOnDownloadListener() {
        return onDownloadListener;
    }

    public UpdateConfiguration setOnDownloadListener(OnDownloadListener onDownloadListener) {
        this.onDownloadListener = onDownloadListener;
        return this;
    }

    public boolean isJumpInstallPage() {
        return jumpInstallPage;
    }

    public UpdateConfiguration setJumpInstallPage(boolean jumpInstallPage) {
        this.jumpInstallPage = jumpInstallPage;
        return this;
    }

    public NotificationChannel getNotificationChannel() {
        return notificationChannel;
    }

    public boolean isShowNotification() {
        return showNotification;
    }

    public UpdateConfiguration setShowNotification(boolean showNotification) {
        this.showNotification = showNotification;
        return this;
    }

    public UpdateConfiguration setNotificationChannel(NotificationChannel notificationChannel) {
        this.notificationChannel = notificationChannel;
        return this;
    }
}
