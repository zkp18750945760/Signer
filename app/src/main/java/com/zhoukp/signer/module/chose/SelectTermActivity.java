package com.zhoukp.signer.module.chose;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.zhoukp.signer.R;
import com.zhoukp.signer.view.dialog.CommonDialog;
import com.zhoukp.signer.view.picker.PickerScrollView;
import com.zhoukp.signer.view.picker.Pickers;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhoukp
 * @time 2018/3/29 21:54
 * @email 275557625@qq.com
 * @function
 */

public class SelectTermActivity extends Activity implements PickerScrollView.onSelectListener, View.OnClickListener {

    @BindView(R.id.pickerScrollView)
    PickerScrollView pickerScrollView;
    @BindView(R.id.tvCancel)
    TextView tvCancel;
    @BindView(R.id.tvTheme)
    TextView tvTheme;
    @BindView(R.id.tvSubmit)
    TextView tvSubmit;

    private String type;
    private String data;
    private String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_select_term);
        ButterKnife.bind(this);

        initVariables();

        initEvents();
    }

    private void initVariables() {
        ArrayList<Pickers> datas = new ArrayList<>();
        type = getIntent().getStringExtra("type");

        if (type.equals("term")) {
            tvTheme.setText("请选择学期");
            datas.add(new Pickers("上", 0 + ""));
            datas.add(new Pickers("下", 1 + ""));
        }

        //设置数据，默认选择第一条
        data = datas.get(0).getShowConetnt();
        pickerScrollView.setData(datas);
        pickerScrollView.setSelected(0);
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
                new CommonDialog(SelectTermActivity.this, "确认退出吗？", R.style.dialog, new CommonDialog.OnCloseListener() {
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
