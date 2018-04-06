package com.zhoukp.signer.module.network;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.view.WindowManager;

import com.zhoukp.signer.R;
import com.zhoukp.signer.application.SignApplication;
import com.zhoukp.signer.view.picker.KpAlertDialog;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * @author zhoukp
 * @time 2018/4/2 15:23
 * @email 275557625@qq.com
 * @function 提示用户打开网络工具类
 */
public class OpenNetHelper {

    private static KpAlertDialog wifiDlg;

    /**
     * 提示用户打开wifi
     *
     * @param context 上下文
     */
    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void showWifiDlg(final Context context) {
        if (!Settings.canDrawOverlays(context)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            context.startActivity(intent);
            return;
        }
        if (wifiDlg == null) {
            wifiDlg = new KpAlertDialog(context, "是否要连接至wifi？", R.style.dialog, new KpAlertDialog.OnCloseListener() {
                @SuppressLint("ObsoleteSdkInt")
                @Override
                public void onClick(AlertDialog dialog, boolean confirm) {
                    if (confirm) {
                        // 跳转到系统的网络设置界面
                        Intent intent = null;
                        // 先判断当前系统版本
                        if (Build.VERSION.SDK_INT > 10) {//3.0以上
                            intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                        } else {
                            intent = new Intent();
                            intent.setClassName("com.android.settings", Settings.ACTION_WIFI_SETTINGS);
                        }
                        if ((context instanceof Application)) {
                            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                        }
                        context.startActivity(intent);
                    }
                    dialog.dismiss();
                }
            });
            //设置为系统的dialog,避免报错
            wifiDlg.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            //点击屏幕不消失
            wifiDlg.setCanceledOnTouchOutside(false);
        }
        if (!wifiDlg.isShowing() && !SignApplication.getInstance().isEnableWifi()) {
            wifiDlg.show();
        }
    }
}
