package com.zhoukp.signer.module.functions.meetings.upload;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhoukp.signer.R;
import com.zhoukp.signer.view.dialog.ProgressDialog;
import com.zhoukp.signer.module.login.LoginBean;
import com.zhoukp.signer.module.login.UserUtil;
import com.zhoukp.signer.utils.SchoolYearUtils;
import com.zhoukp.signer.utils.TimeUtils;
import com.zhoukp.signer.utils.ToastUtil;
import com.zhoukp.signer.utils.WindowUtils;
import com.zhoukp.signer.view.ClearEditText;
import com.zhoukp.signer.view.dialog.CommonDialog;
import com.zhoukp.signer.view.dialog.SelectSchoolYearDialog;
import com.zhoukp.signer.view.dialog.SelectTermDialog;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhoukp
 * @time 2018/4/21 15:56
 * @email 275557625@qq.com
 * @function
 */
public class UploadActivity extends AppCompatActivity implements View.OnClickListener, UploadView {

    private final int CHANGE_FILE = 1;

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvYear)
    TextView tvYear;
    @BindView(R.id.tvTerm)
    TextView tvTerm;
    @BindView(R.id.tvFileName)
    TextView tvFileName;
    @BindView(R.id.EtContent)
    ClearEditText EtContent;
    @BindView(R.id.EtAbstract)
    ClearEditText EtAbstract;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.btnChange)
    Button btnChange;
    private ProgressDialog dialog;
    private UploadPresenter presenter;

    private String path;
    private String fileName;

    private LoginBean.UserBean user;
    private String schoolYear;
    private String term;
    private String content;
    private String Abstract;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtils.setTransluteWindow(this);
        setContentView(R.layout.activity_upload_discussion);
        ButterKnife.bind(this);

        initVariates();

        initEvents();
    }

    private void initVariates() {
        path = getIntent().getStringExtra("path");
        fileName = getIntent().getStringExtra("fileName");
        System.out.println("path==" + path + ", fileName==" + fileName);
        tvFileName.setText(fileName);

        EtContent.etText.setHint("请填写主题");
        EtAbstract.etText.setHint("请填写摘要");

        user = UserUtil.getInstance().getUser();

        String termCode = SchoolYearUtils.getTermCodeByMonth(user.getUserId(),
                Integer.parseInt(TimeUtils.getCurrentYear()), TimeUtils.getMonthOfYear());

        HashMap map = SchoolYearUtils.getSchoolYear(SchoolYearUtils.getGradeCode(user.getUserGrade()), termCode);

        schoolYear = (String) map.get("schoolYear");
        term = (String) map.get("term");

        tvYear.setText(schoolYear);
        tvTerm.setText(getTerm(term));


        presenter = new UploadPresenter();
        presenter.attachView(this);
    }

    private void initEvents() {
        ivBack.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        btnChange.setOnClickListener(this);
        tvYear.setOnClickListener(this);
        tvTerm.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                //弹出对话框
                new CommonDialog(UploadActivity.this, "确认退出吗？\n数据不会保存哦！", R.style.dialog, new CommonDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if (confirm) {
                            finish();
                        }
                        dialog.dismiss();
                    }
                }).show();
                break;
            case R.id.btnSubmit:
                content = EtContent.etText.getText().toString();
                Abstract = EtAbstract.etText.getText().toString();

                if (TextUtils.isEmpty(content) || TextUtils.isEmpty(Abstract)) {
                    ToastUtil.showToast(UploadActivity.this, "主题或摘要不能为空");
                    return;
                }

                if (Abstract.length() > 30) {
                    ToastUtil.showToast(UploadActivity.this, "摘要不能超过30个字符");
                    return;
                }

                presenter.uploadDiscussion(path, user.getUserId(), schoolYear, term, content,
                        Abstract, user.getUserGrade(), user.getUserMajor(), user.getUserClass());

                break;
            case R.id.btnChange:
                Intent intent = new Intent(UploadActivity.this, UploadPdfActivity.class);
                startActivityForResult(intent, CHANGE_FILE);
                break;
            case R.id.tvYear:
                new SelectSchoolYearDialog(UploadActivity.this, "请选择学年", R.style.dialog, new SelectSchoolYearDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm, String data) {
                        if (confirm) {
                            schoolYear = data;
                            tvYear.setText(schoolYear);
                        }
                        dialog.dismiss();
                    }
                }).show();
                break;
            case R.id.tvTerm:
                new SelectTermDialog(UploadActivity.this, "请选择学期", R.style.dialog, new SelectTermDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm, String data) {
                        if (confirm) {
                            term = getTerm(data);
                            tvTerm.setText(data);
                        }
                        dialog.dismiss();
                    }
                }).show();
                break;
        }
    }

    private String getTerm(String term) {
        String result = "1";
        switch (term) {
            case "上":
                result = "1";
                break;
            case "下":
                result = "2";
                break;
            case "1":
                result = "上";
                break;
            case "2":
                result = "下";
                break;
        }
        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CHANGE_FILE:
                    if (data != null) {
                        path = data.getStringExtra("path");
                        fileName = data.getStringExtra("fileName");
                        System.out.println("path==" + path + ", fileName==" + fileName);
                        tvFileName.setText(fileName);
                    }
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
    public void uploadSuccess() {
        ToastUtil.showToast(this, "上传成功");
        EtContent.etText.setText("");
        EtAbstract.etText.setText("");
        tvFileName.setText("");
    }

    @Override
    public void uploadError(int status) {
        switch (status) {
            case 100:
                ToastUtil.showToast(this, "上传失败");
                break;
            case 101:
                ToastUtil.showToast(this, "服务器IO错误");
                break;
            case 102:
                ToastUtil.showToast(this, "文件转换失败");
                break;
        }
    }
}
