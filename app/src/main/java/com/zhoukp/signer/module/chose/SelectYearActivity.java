package com.zhoukp.signer.module.chose;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.zhoukp.signer.R;
import com.zhoukp.signer.utils.TimeUtils;
import com.zhoukp.signer.view.CommonDialog;
import com.zhoukp.signer.view.picker.PickerScrollView;
import com.zhoukp.signer.view.picker.Pickers;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author zhoukp
 * @time 2018/3/29 21:38
 * @email 275557625@qq.com
 * @function
 */

public class SelectYearActivity extends Activity implements PickerScrollView.onSelectListener, View.OnClickListener {

    @Bind(R.id.pickerScrollView)
    PickerScrollView pickerScrollView;
    @Bind(R.id.tvCancel)
    TextView tvCancel;
    @Bind(R.id.tvTheme)
    TextView tvTheme;
    @Bind(R.id.tvSubmit)
    TextView tvSubmit;

    private String type;
    private String data;
    private String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_year);

        ButterKnife.bind(this);

        initVariables();

        initEvents();
    }

    private void initVariables() {
        ArrayList<Pickers> datas = new ArrayList<>();
        type = getIntent().getStringExtra("type");
        userId = getIntent().getStringExtra("userId");

        if (type.equals("year")) {
            tvTheme.setText("请选择年份");
            int entrance = Integer.parseInt("20" + userId.substring(0, 2));
            int current = Integer.parseInt(TimeUtils.getCurrentYear());
            for (int i = 0; i <= current - entrance; i++) {
                datas.add(new Pickers(entrance + i + "", i + ""));
            }
        }

        //设置数据，默认选择第一条
        pickerScrollView.setData(datas);
        pickerScrollView.setSelected(TimeUtils.getCurrentYear());
    }

    private void initEvents() {
        pickerScrollView.setOnSelectListener(this);
        tvCancel.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
    }

    @Override
    public void onSelect(Pickers pickers) {
        data = pickers.getShowConetnt();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvCancel:
                //关闭当前页面
                new CommonDialog(SelectYearActivity.this, "确认退出吗？", R.style.dialog, new CommonDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if (confirm) {
                            finish();
                        }
                        dialog.dismiss();
                    }
                }).show();
                break;
            case R.id.tvSubmit:
                //点击确定，关闭当前页面并带回结果
                Intent intent = new Intent();
                intent.putExtra(type, data);
                setResult(Activity.RESULT_OK, intent);
                finish();
                break;
            default:
                break;
        }
    }
}
