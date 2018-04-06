package com.zhoukp.signer.view.picker;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zhoukp.signer.R;

/**
 * @author zhoukp
 * @time 2018/4/2 16:02
 * @email 275557625@qq.com
 * @function
 */
public class KpAlertDialog extends AlertDialog implements View.OnClickListener {

    /**
     * 取消按钮
     */
    private Button btnCancle;
    /**
     * 确认按钮
     */
    private Button btnSumbit;
    /**
     * 确认文字
     */
    private TextView tvTheme;

    private Context mContext;
    private String theme;
    /**
     * 关闭自身监听
     */
    private OnCloseListener listener;


    public KpAlertDialog(Context context, String theme) {
        super(context);
        this.mContext = context;
        this.theme = theme;
    }

    public KpAlertDialog(Context context, String theme, int themeResId, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.theme = theme;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_common);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        tvTheme = findViewById(R.id.tvTheme);
        tvTheme.setText(theme);
        btnCancle = findViewById(R.id.btnCancle);
        btnCancle.setOnClickListener(this);
        btnSumbit = findViewById(R.id.btnSumbit);
        btnSumbit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancle:
                if (listener != null) {
                    listener.onClick(this, false);
                }
                this.dismiss();
                break;
            case R.id.btnSumbit:
                if (listener != null) {
                    listener.onClick(this, true);
                }
                this.dismiss();
                break;
            default:
                break;
        }
    }

    public interface OnCloseListener {
        void onClick(AlertDialog dialog, boolean confirm);
    }
}
