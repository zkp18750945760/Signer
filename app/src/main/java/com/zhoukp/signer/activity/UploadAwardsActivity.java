package com.zhoukp.signer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhoukp.signer.R;
import com.zhoukp.signer.module.chose.SelectMonthActivity;
import com.zhoukp.signer.module.chose.SelectYearActivity;
import com.zhoukp.signer.utils.WindowUtils;

/**
 * @author zhoukp
 * @time 2018/2/1 18:48
 * @email 275557625@qq.com
 * @function 上传科创获奖
 */

public class UploadAwardsActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int YEAR = 1;
    public static final int MONTH = 2;

    private ImageView ivBack;
    private EditText etAwardsName;
    private TextView tvYear, tvMonth;
    private Button btnUpload;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtils.getStatusBarHeight(this);
        WindowUtils.setTransluteWindow(this);
        setContentView(R.layout.activity_uploadawards);

        initViews();

        initEvents();
    }

    private void initViews() {
        ivBack = (ImageView) findViewById(R.id.ivBack);
        etAwardsName = (EditText) findViewById(R.id.etAwardsName);
        tvYear = (TextView) findViewById(R.id.tvYear);
        tvMonth = (TextView) findViewById(R.id.tvMonth);
        btnUpload = (Button) findViewById(R.id.btnUpload);
    }

    private void initEvents() {
        ivBack.setOnClickListener(this);
        tvYear.setOnClickListener(this);
        tvMonth.setOnClickListener(this);
        btnUpload.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.tvYear:
                //年份选择器
                intent = new Intent(UploadAwardsActivity.this, SelectYearActivity.class);
                intent.putExtra("type", "year");
                startActivityForResult(intent, YEAR);
                break;
            case R.id.tvMonth:
                intent = new Intent(UploadAwardsActivity.this, SelectMonthActivity.class);
                intent.putExtra("type", "month");
                startActivityForResult(intent, MONTH);
                break;
            case R.id.btnUpload:

                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case YEAR:
                    String year = data.getStringExtra("year");
                    if (!TextUtils.isEmpty(year)) {
                        tvYear.setText(year);
                    }
                    break;
                case MONTH:
                    String month = data.getStringExtra("month");
                    if (!TextUtils.isEmpty(month)) {
                        tvMonth.setText(month);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
