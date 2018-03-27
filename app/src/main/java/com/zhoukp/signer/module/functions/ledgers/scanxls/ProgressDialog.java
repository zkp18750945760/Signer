package com.zhoukp.signer.module.functions.ledgers.scanxls;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhoukp.signer.R;

/**
 * @author zhoukp
 * @time 2018/3/26 21:17
 * @email 275557625@qq.com
 * @function 加载中dialog
 */

public class ProgressDialog extends Dialog {
    private ImageView mLoadingImg;

    private TextView mMesssageTV;

    public ProgressDialog(Context context) {
        super(context, R.style.transparent_Dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.process_dialog);
        mLoadingImg = findViewById(R.id.loadingimg);
        mMesssageTV = findViewById(R.id.messagetv);
    }

    /**
     * 当没有消息时只展示菊花
     *
     * @param message
     */
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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
        mLoadingImg.startAnimation(animation);
    }
}