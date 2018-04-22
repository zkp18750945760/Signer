package com.zhoukp.signer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhoukp.signer.R;
import com.zhoukp.signer.utils.TimeUtils;
import com.zhoukp.signer.view.picker.PickerScrollView;
import com.zhoukp.signer.view.picker.Pickers;
import com.zhoukp.signer.view.picker.YearObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhoukp
 * @time 2018/2/1 19:49
 * @email 275557625@qq.com
 * @function 选择年份页面
 */

public class SelectTimeActivity extends Activity implements View.OnClickListener, PickerScrollView.onSelectListener {

    @BindView(R.id.pickerScrollView)
    PickerScrollView pickerScrollView;
    @BindView(R.id.llYearPicker)
    LinearLayout llYearPicker;
    @BindView(R.id.tvCancel)
    TextView tvCancel;
    @BindView(R.id.tvSubmit)
    TextView tvSubmit;
    @BindView(R.id.tvTheme)
    TextView tvTheme;

    private String type;
    private String data;
    private String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_selecttime);

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
            YearObject object = new YearObject(userId, TimeUtils.getCurrentYear());
            for (int i = 0; i < object.getYears().size(); i++) {
                datas.add(new Pickers(object.getYears().get(i), i + ""));
            }
        } else if (type.equals("month")) {
            tvTheme.setText("请选择月份");
            YearObject object = new YearObject();
            for (int i = 0; i < object.getMonths().size(); i++) {
                datas.add(new Pickers(object.getMonths().get(i), i + ""));
            }
        } else if (type.equals("schoolYear")) {
            tvTheme.setText("请选择学年");
            YearObject object = new YearObject(userId, TimeUtils.getCurrentYear());
            for (int i = 0; i < object.getYears().size(); i++) {
                datas.add(new Pickers(object.getSchoolYears().get(i), i + ""));
            }
        } else if (type.equals("term")) {
            tvTheme.setText("请选择学期");
            datas.add(new Pickers("上", 0 + ""));
            datas.add(new Pickers("下", 1 + ""));
        } else if (type.equals("week")) {
            tvTheme.setText("请选择星期");
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
