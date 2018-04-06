package com.zhoukp.signer.module.about;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.zhoukp.signer.R;
import com.zhoukp.signer.utils.WindowUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author zhoukp
 * @time 2018/4/6 23:00
 * @email 275557625@qq.com
 * @function
 */
public class AboutMeActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.ivBack)
    ImageView ivBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtils.setTransluteWindow(this);

        setContentView(R.layout.activity_aboutme);
        ButterKnife.bind(this);

        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                //关闭当前页面
                finish();
                break;
        }
    }
}
