package com.zhoukp.signer.module.managedevice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhoukp.signer.R;
import com.zhoukp.signer.module.login.LoginActivity;
import com.zhoukp.signer.module.login.LoginBean;
import com.zhoukp.signer.module.login.UserUtil;
import com.zhoukp.signer.utils.ToastUtil;
import com.zhoukp.signer.utils.WindowUtils;
import com.zhoukp.signer.utils.aes.AesUtil;
import com.zhoukp.signer.utils.aes.MD5;
import com.zhoukp.signer.view.ThreePointLoadingView;
import com.zhoukp.signer.view.captcha.Captcha;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhoukp
 * @time 2018/3/18 16:47
 * @email 275557625@qq.com
 * @function
 */

public class ManageDeviceActivity extends AppCompatActivity implements View.OnClickListener, ManageDeviceView {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.btnUnBind)
    Button btnUnBind;
    @BindView(R.id.btnBind)
    Button btnBind;
    @BindView(R.id.btnModifyPsd)
    Button btnModifyPsd;
    @BindView(R.id.threePointLoadingView)
    ThreePointLoadingView threePointLoadingView;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.ivShowPassword)
    ImageView ivShowPassword;
    @BindView(R.id.etNewPassword)
    EditText etNewPassword;
    @BindView(R.id.ivShowNewPassword)
    ImageView ivShowNewPassword;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.rlManageDevice)
    LinearLayout rlManageDevice;
    @BindView(R.id.rlModifyPassword)
    LinearLayout rlModifyPassword;
    @BindView(R.id.captCha)
    Captcha captCha;
    @BindView(R.id.llModifyPassword)
    LinearLayout llModifyPassword;

    private ManagerDevicePresenter presenter;
    //默认不显示密码
    private boolean showPassword = false;
    private boolean showNewPassword = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtils.getStatusBarHeight(this);
        WindowUtils.setTransluteWindow(this);
        setContentView(R.layout.activity_manage_device);
        ButterKnife.bind(this);

        initData();

        initEvents();
    }

    private void initData() {
        presenter = new ManagerDevicePresenter();
        presenter.attachView(this);
    }

    private void initEvents() {
        ivBack.setOnClickListener(this);
        btnBind.setOnClickListener(this);
        btnUnBind.setOnClickListener(this);
        btnModifyPsd.setOnClickListener(this);
        ivShowPassword.setOnClickListener(this);
        ivShowNewPassword.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

        captCha.setCaptchaListener(new Captcha.CaptchaListener() {
            @Override
            public String onAccess(long time) {
                ToastUtil.showToast(ManageDeviceActivity.this, "验证成功");
                captCha.setVisibility(View.GONE);
                llModifyPassword.setVisibility(View.VISIBLE);
                return "验证通过";
            }

            @Override
            public String onFailed(int count) {
                ToastUtil.showToast(ManageDeviceActivity.this, "验证失败,失败次数" + count);
                return "验证失败";
            }

            @Override
            public String onMaxFailed() {
                ToastUtil.showToast(ManageDeviceActivity.this, "验证超过次数，你的帐号被封锁");
                return "可以走了";
            }

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void onClick(View view) {
        LoginBean.UserBean userBean = UserUtil.getInstance().getUser();
        switch (view.getId()) {
            case R.id.ivBack:
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.btnBind:
                presenter.bindDevice(userBean.getUserId(), presenter.getSerialNo(ManageDeviceActivity.this));
                break;
            case R.id.btnUnBind:
                presenter.unBindDevice(userBean.getUserId());
                break;
            case R.id.btnModifyPsd:
                rlManageDevice.setVisibility(View.GONE);
                rlModifyPassword.setVisibility(View.VISIBLE);
                break;
            case R.id.ivShowPassword:
                if (showPassword) {
                    //隐藏密码
                    ivShowPassword.setBackgroundResource(R.drawable.icon_hide_password);
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    //显示密码
                    ivShowPassword.setBackgroundResource(R.drawable.icon_show_password);
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                showPassword = !showPassword;
                break;
            case R.id.ivShowNewPassword:
                if (showNewPassword) {
                    //隐藏密码
                    ivShowNewPassword.setBackgroundResource(R.drawable.icon_hide_password);
                    etNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    //显示密码
                    ivShowNewPassword.setBackgroundResource(R.drawable.icon_show_password);
                    etNewPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                showNewPassword = !showNewPassword;
                break;
            case R.id.btnSubmit:
                String key;
                try {
                    key = MD5.encode("hqu");
                    String passwordStr = AesUtil.encrypt(etPassword.getText().toString(), key);
                    String newPasswordStr = AesUtil.encrypt(etNewPassword.getText().toString(), key);
                    presenter.modifyPassword(userBean.getUserId(), passwordStr, newPasswordStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void showLoadingView() {
        threePointLoadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        threePointLoadingView.setVisibility(View.GONE);
    }

    @Override
    public void unBindSuccess() {
        ToastUtil.showToast(ManageDeviceActivity.this, "解绑成功");
    }

    @Override
    public void unBindLater() {
        ToastUtil.showToast(ManageDeviceActivity.this, "距离上一次解绑还没有30天");
    }

    @Override
    public void unBindError() {
        ToastUtil.showToast(ManageDeviceActivity.this, "解绑失败");
    }

    @Override
    public void bindSuccess() {
        ToastUtil.showToast(ManageDeviceActivity.this, "绑定成功");
    }

    @Override
    public void bindError() {
        ToastUtil.showToast(ManageDeviceActivity.this, "绑定失败");
    }

    @Override
    public void unBindFirst() {
        ToastUtil.showToast(ManageDeviceActivity.this, "请先解绑");
    }

    @Override
    public void inputPassword() {
        ToastUtil.showToast(ManageDeviceActivity.this, "新旧密码不能为空");
    }

    @Override
    public void modifySuccess() {
        ToastUtil.showToast(ManageDeviceActivity.this, "修改成功");
    }

    @Override
    public void reLogin() {
        //重新登录
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void passWordError() {
        ToastUtil.showToast(ManageDeviceActivity.this, "旧密码错误");
    }

    @Override
    public void modifyError() {
        ToastUtil.showToast(ManageDeviceActivity.this, "修改失败");
    }
}
