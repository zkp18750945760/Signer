package com.zhoukp.signer.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zhoukp.signer.R;
import com.zhoukp.signer.view.picker.PickerScrollView;
import com.zhoukp.signer.view.picker.Pickers;

import java.util.ArrayList;

/**
 * @author zhoukp
 * @time 2018/3/27 22:42
 * @email 275557625@qq.com
 * @function 自定义选择体测等级对话框
 */

public class SelectLevelDialog extends Dialog implements View.OnClickListener, PickerScrollView.onSelectListener {

    /**
     * 取消按钮
     */
    private TextView tvCancel;
    /**
     * 确认按钮
     */
    private TextView tvSubmit;
    /**
     * 确认文字
     */
    private TextView tvTheme;

    private PickerScrollView pickerScrollView;

    private Context mContext;
    private String theme;
    /**
     * 关闭自身监听
     */
    private OnCloseListener listener;

    private String data;


    public SelectLevelDialog(Context context, String theme) {
        super(context);
        this.mContext = context;
        this.theme = theme;
    }

    public SelectLevelDialog(Context context, String theme, int themeResId, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.theme = theme;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select_level);
        setCanceledOnTouchOutside(false);
        initView();

        initData();

        initEvents();
    }

    private void initView() {
        tvTheme = findViewById(R.id.tvTheme);
        tvTheme.setText(theme);
        tvCancel = findViewById(R.id.tvCancel);
        tvSubmit = findViewById(R.id.tvSubmit);
        pickerScrollView = findViewById(R.id.pickerScrollView);
    }

    private void initData() {
        ArrayList<Pickers> datas = new ArrayList<>();
        datas.add(new Pickers("优秀", 0 + ""));
        datas.add(new Pickers("良好", 1 + ""));
        datas.add(new Pickers("及格", 2 + ""));
        datas.add(new Pickers("不及格", 3 + ""));

        //设置数据，默认选择第一条
        pickerScrollView.setData(datas);
        pickerScrollView.setSelected(0);
    }

    private void initEvents() {
        tvCancel.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
        pickerScrollView.setOnSelectListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCancel:
                if (listener != null) {
                    listener.onClick(this, false, "");
                }
                this.dismiss();
                break;
            case R.id.tvSubmit:
                if (listener != null) {
                    listener.onClick(this, true, data);
                }
                this.dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    public void onSelect(Pickers pickers) {
        data = pickers.getShowConetnt();
    }

    public interface OnCloseListener {
        void onClick(Dialog dialog, boolean confirm, String data);
    }
}
