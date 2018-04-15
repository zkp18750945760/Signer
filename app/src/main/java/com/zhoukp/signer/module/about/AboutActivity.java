package com.zhoukp.signer.module.about;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.zhoukp.signer.R;
import com.zhoukp.signer.module.functions.ledgers.scanxls.ProgressDialog;
import com.zhoukp.signer.module.main.UpdateBean;
import com.zhoukp.signer.module.update.ApkUtil;
import com.zhoukp.signer.module.update.DownloadManager;
import com.zhoukp.signer.utils.Constant;
import com.zhoukp.signer.utils.ToastUtil;
import com.zhoukp.signer.utils.WindowUtils;
import com.zhoukp.signer.view.LSettingItem;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author zhoukp
 * @time 2018/4/6 22:34
 * @email 275557625@qq.com
 * @function
 */
public class AboutActivity extends AppCompatActivity implements View.OnClickListener, AboutMeView {

    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.itemVersion)
    LSettingItem itemVersion;
    @Bind(R.id.itemUpdate)
    LSettingItem itemUpdate;
    @Bind(R.id.itemCopyright)
    LSettingItem itemCopyright;
    @Bind(R.id.itemAbout)
    LSettingItem itemAbout;

    private ProgressDialog dialog;
    private AboutMePresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtils.setTransluteWindow(this);

        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        presenter = new AboutMePresenter();
        presenter.attachView(this);

        itemVersion.setmRightStyle(1);
        itemVersion.setRightText(ApkUtil.getVersionName(this));

        initEvents();
    }

    private void initEvents() {
        ivBack.setOnClickListener(this);

        itemAbout.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                startActivity(new Intent(AboutActivity.this, AboutMeActivity.class));
            }
        });

        itemUpdate.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                if (presenter != null) {
                    presenter.getUpdateInfo();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                //关闭当前页面
                finish();
                break;
        }
    }

    @Override
    public void showLoadingView() {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.showMessage("加载中...");
        }
        dialog.show();
    }

    @Override
    public void hideLoadingView() {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.showMessage("加载中...");
        }
        dialog.dismiss();
    }

    @Override
    public void getUpdateInfoSuccess(UpdateBean bean) {
        //获取版本更新信息成功
        //提示用户进行版本升级
        DownloadManager manager = DownloadManager.getInstance(this);
        manager.setApkName(bean.getData().get(0).getAppName())
                .setApkUrl(Constant.BaseUrl + bean.getData().get(0).getDownloadUrl())
                .setDownloadPath(Constant.appFilePath)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setApkVersionCode(bean.getData().get(0).getVersionCode())
                .setApkDescription(bean.getData().get(0).getDescription())
                .setApkVersionName(bean.getData().get(0).getVersionName())
                .setApkSize(presenter.getSize(bean.getData().get(0).getApkSize()))
                //可设置，可不设置
                .setConfiguration(null)
                .download();
    }

    @Override
    public void getUpdateInfoError(int status) {
        switch (status) {
            case 100:
                ToastUtil.showToast(this, "获取更新信息失败");
                break;
            case 101:
                ToastUtil.showToast(this, "数据库IO错误");
                break;
        }
    }
}
