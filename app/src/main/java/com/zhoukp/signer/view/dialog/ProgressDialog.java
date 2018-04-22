package com.zhoukp.signer.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zhoukp.signer.R;

/**
 * @author zhoukp
 * @time 2018/3/26 21:17
 * @email 275557625@qq.com
 * @function 加载中dialog
 */

public class ProgressDialog extends Dialog {
    private TextView mMesssageTV;

    public ProgressDialog(Context context) {
        super(context, R.style.transparent_Dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.process_dialog);
        mMesssageTV = findViewById(R.id.messagetv);
    }

    public void showMessage(String message) {
        try {
            super.show();
            if (!TextUtils.isEmpty(message)) {
                mMesssageTV.setText(message);
                mMesssageTV.setVisibility(View.VISIBLE);
            } else {
                mMesssageTV.setText("");
                mMesssageTV.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}