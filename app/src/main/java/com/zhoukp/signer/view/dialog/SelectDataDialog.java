package com.zhoukp.signer.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.zhoukp.signer.R;
import com.zhoukp.signer.module.functions.ascq.update.MutualIDsBean;
import com.zhoukp.signer.view.picker.PickerScrollView;
import com.zhoukp.signer.view.picker.Pickers;

import java.util.ArrayList;

import static android.view.KeyEvent.KEYCODE_BACK;

/**
 * @author zhoukp
 * @time 2018/3/27 22:42
 * @email 275557625@qq.com
 * @function 自定义选择数据对话框
 */

public class SelectDataDialog extends Dialog implements View.OnClickListener, PickerScrollView.onSelectListener {

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

    private MutualIDsBean datas;

    public SelectDataDialog(Context context, String theme) {
        super(context);
        this.mContext = context;
        this.theme = theme;
    }

    public SelectDataDialog(Context context, String theme, int themeResId, MutualIDsBean datas, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.theme = theme;
        this.datas = datas;
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
        ArrayList<Pickers> pickers = new ArrayList<>();

        for (int i = 0; i < datas.getData().size(); i++) {
            pickers.add(new Pickers(datas.getData().get(i).getStuId(), i + ""));
        }

        //设置数据，默认选择第一条
        data = pickers.get(0).getShowConetnt();
        pickerScrollView.setData(pickers);
        pickerScrollView.setSelected(0);
    }

    private void initEvents() {
        tvCancel.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
        pickerScrollView.setOnSelectListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
