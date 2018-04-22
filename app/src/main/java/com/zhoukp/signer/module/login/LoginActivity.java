package com.zhoukp.signer.module.login;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.makeramen.roundedimageview.RoundedImageView;
import com.zhoukp.signer.R;
import com.zhoukp.signer.utils.ToastUtil;
import com.zhoukp.signer.utils.WindowUtils;
import com.zhoukp.signer.utils.aes.AesUtil;
import com.zhoukp.signer.utils.aes.MD5;
import com.zhoukp.signer.view.ThreePointLoadingView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @BindView(R.id.ivHead)
    RoundedImageView ivHead;
    @BindView(R.id.etUsername)
    TextInputLayout etUsername;
    @BindView(R.id.etPassword)
    TextInputLayout etPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.ThreePointLoadingView)
    ThreePointLoadingView threePointLoadingView;

    private EditText username;
    private EditText password;

    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtils.getStatusBarHeight(this);
        WindowUtils.setTransluteWindow(this);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        presenter = new LoginPresenter();
        presenter.attachView(this);

        username = etUsername.getEditText();
        password = etPassword.getEditText();
    }

    @OnClick(R.id.btnLogin)
    public void onClick() {
        try {
            String key = MD5.encode("hqu");
            String passwordStr = AesUtil.encrypt(password.getText().toString(), key);
            presenter.login(username.getText().toString(), passwordStr, presenter.getSerialNo(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void hideLoadingView() {
        threePointLoadingView.setVisibility(View.GONE);

    }

    @Override
    public void showLoadingView() {
        threePointLoadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void nullNameOrPsd() {
        ToastUtil.showToast(this, "用户名或密码不能为空");
    }

    @Override
    public void loginSuccess(LoginBean loginBean) {
        ToastUtil.showToast(this, loginBean.getUser().getUserName() + "登陆成功");
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void loginError() {
        ToastUtil.showToast(LoginActivity.this, "登陆失败");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
