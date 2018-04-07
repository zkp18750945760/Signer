package com.zhoukp.signer.utils.permission;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.yanzhenjie.alertdialog.AlertDialog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.SettingService;
import com.zhoukp.signer.R;

import java.util.List;

/**
 * @author zhoukp
 * @time 2018/4/7 15:50
 * @email 275557625@qq.com
 * @function
 */
public class PermissionSetting {
    private final Context context;

    public PermissionSetting(Context context) {
        this.context = context;
    }

    public void showSetting(final List<String> permissions) {
        List<String> permissionNames = Permission.transformText(context, permissions);
        String message = context.getString(R.string.message_permission_always_failed, TextUtils.join("\n", permissionNames));

        final SettingService settingService = AndPermission.permissionSetting(context);
        AlertDialog.newBuilder(context)
                .setCancelable(false)
                .setTitle(R.string.title_dialog)
                .setMessage(message)
                .setPositiveButton(R.string.setting, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        settingService.execute();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        settingService.cancel();
                    }
                })
                .show();
    }
}
