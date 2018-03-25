package com.zhoukp.signer.module.me;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.zhoukp.signer.R;
import com.zhoukp.signer.fragment.BaseFragment;
import com.zhoukp.signer.module.managedevice.ManageDeviceActivity;
import com.zhoukp.signer.imageloader.GlideImageLoader;
import com.zhoukp.signer.module.login.LoginActivity;
import com.zhoukp.signer.module.login.LoginBean;
import com.zhoukp.signer.utils.CacheUtils;
import com.zhoukp.signer.utils.Constant;
import com.zhoukp.signer.utils.ToastUtil;
import com.zhoukp.signer.module.login.UserUtil;
import com.zhoukp.signer.view.ThreePointLoadingView;


/**
 * @author zhoukp
 * @time 2018/1/30 21:18
 * @email 275557625@qq.com
 * @function 我的页面
 */

public class MePager extends BaseFragment implements View.OnClickListener, MePagerView {

    private RoundedImageView ivHead;
    private TextView tvName, tvDuty;
    private RelativeLayout rlID, rlClass;
    private TextView tvID, tvClass;
    private Button btnExit, btnEditData;
    private TextView tvGoLogin;
    private LinearLayout llPersonInfo;
    private ThreePointLoadingView threePointLoadingView;

    public MePagerPresenter presenter;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_me, null);
        ivHead = view.findViewById(R.id.ivHead);
        tvName = view.findViewById(R.id.tvName);
        tvDuty = view.findViewById(R.id.tvDuty);
        rlID = view.findViewById(R.id.rlID);
        tvID = view.findViewById(R.id.tvID);
        rlClass = view.findViewById(R.id.rlClass);
        tvClass = view.findViewById(R.id.tvClass);
        btnExit = view.findViewById(R.id.btnExit);
        btnEditData = view.findViewById(R.id.btnEditData);
        tvGoLogin = view.findViewById(R.id.tvGoLogin);
        llPersonInfo = view.findViewById(R.id.llPersonInfo);
        threePointLoadingView = view.findViewById(R.id.ThreePointLoadingView);

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
        String headIconPath = CacheUtils.getString(context, "headIcon", 0);
        if (headIconPath.equals("0")) {
            headIconPath = CacheUtils.getString(context, "headIconUrl", 0);
            if (headIconPath.equals("0")) {
                refreshHeadIcon(Constant.BaseUrl + "/imgs/default.jpg");
            } else {
                refreshHeadIcon(Constant.BaseUrl + headIconPath);
            }
        } else {
            refreshHeadIcon(headIconPath);
        }
        initEvent();
    }

    private void initEvent() {
        btnExit.setOnClickListener(this);
        btnEditData.setOnClickListener(this);
        rlID.setOnClickListener(this);
        rlClass.setOnClickListener(this);
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
            case R.id.btnEditData:
                context.startActivityForResult(new Intent(context, ManageDeviceActivity.class), Constant.EditData);
                break;
            case R.id.rlID:
                ToastUtil.showToast(context, "学号");
                break;
            case R.id.rlClass:
                ToastUtil.showToast(context, "班级");
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
            tvID.setText(userBean.getUserId());
            tvClass.setText(userBean.getUserGrade() + userBean.getUserMajor() + userBean.getUserClass());
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
        GlideImageLoader loader = new GlideImageLoader();
        loader.displayImage(context, path, ivHead);
    }

    @Override
    public void uploadHeadIconSuccess() {
        ToastUtil.showToast(context, "上传头像成功");
    }

    @Override
    public void uploadHeadIconError() {
        ToastUtil.showToast(context, "上传失败");
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
