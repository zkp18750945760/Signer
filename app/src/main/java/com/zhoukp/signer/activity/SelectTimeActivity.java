package com.zhoukp.signer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zhoukp.signer.R;
import com.zhoukp.signer.view.picker.PickerScrollView;
import com.zhoukp.signer.view.picker.Pickers;
import com.zhoukp.signer.view.picker.YearObject;

import java.util.ArrayList;

/**
 * @author zhoukp
 * @time 2018/2/1 19:49
 * @email 275557625@qq.com
 * @function 选择年份页面
 */

public class SelectTimeActivity extends Activity implements View.OnClickListener, PickerScrollView.onSelectListener {

    private PickerScrollView pickerScrollView;
    private TextView tvCancel, tvSubmit;

    private String type;
    private String data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecttime);

        initViews();

        initvariables();

        initEvents();
    }

    private void initViews() {
        pickerScrollView = findViewById(R.id.pickerScrollView);
        tvCancel = findViewById(R.id.tvCancel);
        tvSubmit = findViewById(R.id.tvSubmit);
    }

    private void initvariables() {
        ArrayList<Pickers> datas = new ArrayList<>();
        type = getIntent().getStringExtra("type");
        if (type.equals("year")) {
            YearObject object = new YearObject("1425122042", "2018");
            for (int i = 0; i < object.getYears().size(); i++) {
                datas.add(new Pickers(object.getYears().get(i), i + ""));
            }
        } else if (type.equals("month")) {
            YearObject object = new YearObject();
            for (int i = 0; i < object.getMonths().size(); i++) {
                datas.add(new Pickers(object.getMonths().get(i), i + ""));
            }
        } else if (type.equals("schoolYear")) {
            YearObject object = new YearObject("1425122042", "2018");
            for (int i = 0; i < object.getYears().size(); i++) {
                datas.add(new Pickers(object.getSchoolYears().get(i), i + ""));
            }
        } else if (type.equals("term")) {
            datas.add(new Pickers("上", 0 + ""));
            datas.add(new Pickers("下", 1 + ""));
        } else if (type.equals("week")) {
            for (int i = 0; i < 20; i++) {
                datas.add(new Pickers(i + 1 + "", i + ""));
            }
        }

        //设置数据，默认选择第一条
        pickerScrollView.setData(datas);
        pickerScrollView.setSelected(0);
    }

    private void initEvents() {
        pickerScrollView.setOnSelectListener(this);
        tvCancel.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvCancel:
                //关闭当前页面
                finish();
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

    @Override
    public void onSelect(Pickers pickers) {
        Log.e("zkp", pickers.getShowId() + "-->" + pickers.getShowConetnt());
        data = pickers.getShowConetnt();
    }
}
