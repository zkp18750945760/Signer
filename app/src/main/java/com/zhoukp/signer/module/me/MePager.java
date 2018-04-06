package com.zhoukp.signer.module.me;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.zhoukp.signer.R;
import com.zhoukp.signer.fragment.BaseFragment;
import com.zhoukp.signer.module.course.CourseActivity;
import com.zhoukp.signer.module.managedevice.ManageDeviceActivity;
import com.zhoukp.signer.module.login.LoginActivity;
import com.zhoukp.signer.module.login.LoginBean;
import com.zhoukp.signer.utils.Constant;
import com.zhoukp.signer.utils.PermissionUtils;
import com.zhoukp.signer.utils.ToastUtil;
import com.zhoukp.signer.module.login.UserUtil;
import com.zhoukp.signer.utils.lrucache.RxImageLoader;
import com.zhoukp.signer.view.LSettingItem;
import com.zhoukp.signer.view.ThreePointLoadingView;


/**
 * @author zhoukp
 * @time 2018/1/30 21:18
 * @email 275557625@qq.com
 * @function 我的页面
 */

public class MePager extends BaseFragment implements View.OnClickListener, MePagerView {

    /**
     * 请求图片成功
     */
    public static final int SUCCESS = 1;
    /**
     * 图片请求失败
     */
    public static final int FAIL = 2;

    private RoundedImageView ivHead;
    private TextView tvName, tvDuty;
    private Button btnExit;
    private TextView tvGoLogin;
    private LinearLayout llPersonInfo;
    private ThreePointLoadingView threePointLoadingView;

    private LSettingItem itemID;
    private LSettingItem itemClass;
    private LSettingItem itemCourse;
    private LSettingItem itemSetting;

    public MePagerPresenter presenter;
    private String path;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_me, null);
        ivHead = view.findViewById(R.id.ivHead);
        tvName = view.findViewById(R.id.tvName);
        tvDuty = view.findViewById(R.id.tvDuty);
        btnExit = view.findViewById(R.id.btnExit);
        tvGoLogin = view.findViewById(R.id.tvGoLogin);
        llPersonInfo = view.findViewById(R.id.llPersonInfo);
        threePointLoadingView = view.findViewById(R.id.ThreePointLoadingView);

        itemID = view.findViewById(R.id.itemID);
        itemClass = view.findViewById(R.id.itemClass);
        itemCourse = view.findViewById(R.id.itemCourse);
        itemSetting = view.findViewById(R.id.itemSetting);

        if (context.getSharedPreferences("Signer", Context.MODE_PRIVATE).getBoolean("login", false)) {
            llPersonInfo.setVisibility(View.VISIBLE);
            tvGoLogin.setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        presenter = new MePagerPresenter();
        presenter.attachView(this);

        refreshUI();

        initEvent();
    }

    private void initEvent() {
        btnExit.setOnClickListener(this);
        tvGoLogin.setOnClickListener(this);
        ivHead.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnExit:
                ToastUtil.showToast(context, "退出");
                UserUtil.getInstance().removeUser();
                refreshUI();
                break;
            case R.id.tvGoLogin:
                context.startActivityForResult(new Intent(context, LoginActivity.class), Constant.Login);
                break;
            case R.id.ivHead:
                //修改头像
                presenter.selectPicture(context);
                break;
            default:
                break;
        }
    }

    /**
     * 登陆成功后刷新本页面的用户信息
     */
    public void refreshUI() {
        LoginBean.UserBean userBean = UserUtil.getInstance().getUser();
        if (userBean == null) {
            llPersonInfo.setVisibility(View.GONE);
            tvGoLogin.setVisibility(View.VISIBLE);
        } else {
            llPersonInfo.setVisibility(View.VISIBLE);
            tvGoLogin.setVisibility(View.GONE);
            ivHead.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_head));
            tvName.setText(userBean.getUserName());
            tvDuty.setText(userBean.getUserDuty());

            presenter.getHeadIcon(UserUtil.getInstance().getUser().getUserId());
            itemID.setRightText(userBean.getUserId());
            itemID.setLeftText("学号");

            itemClass.setLeftText("班级");
            itemClass.setRightText(userBean.getUserGrade() + userBean.getUserMajor() + userBean.getUserClass());

            itemCourse.setLeftText("我的课表");
            itemCourse.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
                @Override
                public void click(boolean isChecked) {
                    //跳转到我的课表页面
                    context.startActivity(new Intent(context, CourseActivity.class));
                }
            });

            itemSetting.setLeftText("设置");
            itemSetting.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
                @Override
                public void click(boolean isChecked) {
                    //跳转到设备管理页面
                    context.startActivityForResult(new Intent(context, ManageDeviceActivity.class), Constant.EditData);
                }
            });
        }
    }

    /**
     * 刷新头像
     *
     * @param path 裁剪并压缩后的path
     */
    @Override
    public void refreshHeadIcon(String path) {
        Log.e("zkp", path);
        this.path = path;
        if (PermissionUtils.isGrantExternalRW(context, 1)) {
            RxImageLoader.with(context).load(path).into(ivHead);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    RxImageLoader.with(context).load(path).into(ivHead);
                } else {
                    ToastUtil.showToast(context, "请开启读取手机内存权限");
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void uploadHeadIconSuccess(UploadHeadIconBean data) {
        ToastUtil.showToast(context, "上传头像成功");
        this.path = Constant.BaseUrl + data.getHeadIconUrl();
        RxImageLoader.with(context).load(Constant.BaseUrl + data.getHeadIconUrl()).into(ivHead);
    }

    @Override
    public void uploadHeadIconError() {
        ToastUtil.showToast(context, "上传失败");
    }

    @Override
    public void getHeadIconSuccess(UploadHeadIconBean data) {
        this.path = Constant.BaseUrl + data.getHeadIconUrl();
        RxImageLoader.with(context).load(Constant.BaseUrl + data.getHeadIconUrl()).into(ivHead);
    }

    @Override
    public void getHeadIconError(int status) {
        ToastUtil.showToast(context, "获取头像失败");
    }

    @Override
    public void showLoadingView() {
        threePointLoadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        threePointLoadingView.setVisibility(View.GONE);
    }
}
