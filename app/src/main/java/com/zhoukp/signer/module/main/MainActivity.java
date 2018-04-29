package com.zhoukp.signer.module.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.zhoukp.signer.R;
import com.zhoukp.signer.fragment.BaseFragment;
import com.zhoukp.signer.module.activity.ActivityPager;
import com.zhoukp.signer.view.dialog.ProgressDialog;
import com.zhoukp.signer.module.functions.ledgers.scanxls.XlsBean;
import com.zhoukp.signer.module.home.HomePager;
import com.zhoukp.signer.module.me.MePager;
import com.zhoukp.signer.module.update.DownloadManager;
import com.zhoukp.signer.utils.CacheUtils;
import com.zhoukp.signer.utils.Constant;
import com.zhoukp.signer.utils.PermissionUtils;
import com.zhoukp.signer.utils.ToastUtil;
import com.zhoukp.signer.utils.WindowUtils;
import com.zhoukp.signer.utils.permission.DefaultRationale;
import com.zhoukp.signer.utils.permission.PermissionSetting;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.view.KeyEvent.KEYCODE_BACK;

/**
 * @author zhoukp
 * @time 2018/1/28 18:34
 * @email 275557625@qq.com
 * @function 主页面
 */
public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, MainView {

    protected RadioGroup rgTag;
    protected FrameLayout flMainContent;

    private ProgressDialog dialog;
    private MainPresenter presenter;

    /**
     * 选中的位置
     */
    private int position;
    /**
     * 页面的集合
     */
    private ArrayList<BaseFragment> baseFragments;

    /**
     * 是否要退出
     */
    private boolean isExit = false;
    //记录选中的fragment
    private Fragment content;
    private String[] tags = new String[]{"home", "activities", "me"};

    private PermissionSetting setting;
    private Rationale rationale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtils.getStatusBarHeight(this);
        WindowUtils.setTransluteWindow(this);

        setContentView(R.layout.activity_main);

        initViews();

        initVariates(savedInstanceState);
    }

    private void initViews() {
        rgTag = findViewById(R.id.rgTag);
        rgTag.setOnCheckedChangeListener(this);
        flMainContent = findViewById(R.id.flMainContent);
    }

    private void initVariates(Bundle savedInstanceState) {
        setting = new PermissionSetting(this);
        rationale = new DefaultRationale();

        requestPermission(PermissionUtils.PERMISSIONS);

        //1.得到FragmentManger
        FragmentManager manager = getSupportFragmentManager();
        baseFragments = new ArrayList<>();
        if (savedInstanceState != null) {
            baseFragments.add((HomePager) manager.findFragmentByTag(tags[0]));
            baseFragments.add((ActivityPager) manager.findFragmentByTag(tags[1]));
            baseFragments.add((MePager) manager.findFragmentByTag(tags[2]));
        } else {
            //首页
            baseFragments.add(new HomePager());
            baseFragments.add(new ActivityPager());
            baseFragments.add(new MePager());
        }
        position = 0;
        content = baseFragments.get(0);
        //2.开启事务
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(R.id.flMainContent, content).commit();

        if (presenter == null) {
            presenter = new MainPresenter();
            presenter.attachView(this);
            presenter.getCrashFile(Constant.appCrashPath, ".log", true);
            presenter.getUpdateInfo();
        }
    }

    /**
     * 请求权限
     *
     * @param permissions 权限列表
     */
    private void requestPermission(String... permissions) {
        AndPermission.with(this)
                .permission(permissions)
                .rationale(rationale)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        ToastUtil.showToast(MainActivity.this, getResources().getString(R.string.successfully));
                    }
                })
                .onDenied(new Action() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                        ToastUtil.showToast(MainActivity.this, getResources().getString(R.string.failure));
                        if (AndPermission.hasAlwaysDeniedPermission(MainActivity.this, permissions)) {
                            setting.showSetting(permissions);
                        }
                    }
                })
                .start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("zkp", "homePosition == " + position);
        if (position == 0) {
            rgTag.check(R.id.rbHome);
        } else if (position == 1) {
            rgTag.check(R.id.rbActivity);
        } else {
            rgTag.check(R.id.rbMe);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    }

    /**
     * 再按一次退出应用
     *
     * @param keyCode keyCode
     * @param event   event
     * @return boolean
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KEYCODE_BACK) {
            if (!isExit) {
                isExit = true;
                ToastUtil.showToast(getApplicationContext(), "再按一次退出应用");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isExit = false;
                    }
                }, 2000);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rbHome:
                position = 0;
                break;
            case R.id.rbActivity:
                position = 1;
                break;
            case R.id.rbMe:
                position = 2;
                break;
            default:
                break;
        }
        CacheUtils.putInteger(MainActivity.this, "homePosition", position);
        switchContent(content, baseFragments.get(position), position);
    }

    /**
     * fragment 切换
     *
     * @param from 当前页面
     * @param to   要显示的页面
     */
    public void switchContent(Fragment from, Fragment to, int position) {
        if (content != to) {
            content = to;
            //1.得到FragmentManger
            FragmentManager manager = getSupportFragmentManager();
            //2.开启事务
            FragmentTransaction ft = manager.beginTransaction();
            if (!to.isAdded()) { // 先判断是否被add过
                // 隐藏当前的fragment，add下一个到Activity中
                ft.hide(from).add(R.id.flMainContent, to, tags[position]).commit();
            } else {
                // 隐藏当前的fragment，显示下一个
                ft.hide(from).show(to).commit();
            }
        }
    }

    /**
     * 根据位置得到对应的页面
     *
     * @return ReplaceFragment
     */
    private BaseFragment getFragment() {
        return baseFragments.get(position);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constant.Login:
                    //登陆回调
                    getSharedPreferences("Signer", MODE_PRIVATE).edit().putBoolean("login", true).apply();
                    ((MePager) baseFragments.get(2)).refreshUI();
                    break;
                case PictureConfig.CHOOSE_REQUEST:
                    //更新头像回调
                    // 图片选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    if (selectList.get(0).isCompressed()) {
                        String compressPath = selectList.get(0).getCompressPath();
                        ((MePager) baseFragments.get(2)).presenter.upLoadHeadIcon(compressPath);
                        rgTag.check(R.id.rbMe);
                    }
                    break;
                default:
                    position = CacheUtils.getInteger(MainActivity.this, "homePosition", 0);
                    break;
            }
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
                .setApkDescription("发布时间：" + bean.getData().get(0).getUpdateTime() + "\n" + bean.getData().get(0).getDescription())
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

    @Override
    public void getCrashLogcatSuccess(XlsBean bean) {
        presenter.uploadCrashLogcat(new File(bean.getPath()));
    }

    @Override
    public void getCrashLogcatError() {
        ToastUtil.showToast(this, "还没有错误日志哦");
    }

    @Override
    public void uploadCrashSuccess() {
        ToastUtil.showToast(this, "错误日志上传成功");
    }

    @Override
    public void uploadCrashError() {
        ToastUtil.showToast(this, "错误日志上传失败");
    }
}
