package com.zhoukp.signer.module.functions.ascq;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.zhoukp.signer.R;
import com.zhoukp.signer.utils.WindowUtils;
import com.zhoukp.signer.view.HoneycombView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author zhoukp
 * @time 2018/4/11 20:55
 * @email 275557625@qq.com
 * @function 综测页面
 */
public class ASCQActivity extends AppCompatActivity implements View.OnClickListener, HoneycombView.OnClickListener {

    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.honeycombView)
    HoneycombView honeycombView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtils.setTransluteWindow(this);
        setContentView(R.layout.activity_ascq);
        ButterKnife.bind(this);

        initEvents();
    }

    private void initEvents() {
        ivBack.setOnClickListener(this);
        honeycombView.setOnClickListener((HoneycombView.OnClickListener) this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                //关闭当前页
                finish();
                break;
        }
    }

    @Override
    public void onClickListener(int actionIndex) {
        Intent intent = new Intent(ASCQActivity.this, ASCQFragmentManager.class);
        intent.putExtra("actionIndex", actionIndex);
        switch (actionIndex) {
            case 0:
                //成绩上报模块
                intent.putExtra("content", "成绩上报");
                break;
            case 1:
                //成绩查询模块
                intent.putExtra("content", "成绩查询");
                break;
            case 2:
                //申报互评小组模块
                intent.putExtra("content", "互评小组申报");
                break;
            case 3:
                //开始互评模块
                intent.putExtra("content", "综测互评");
                break;
            case 4:
                //开始互评模块
                intent.putExtra("content", "互评记录");
                break;
            case 5:
                //开始互评模块
                intent.putExtra("content", "成绩修改");
                break;
        }
        startActivity(intent);
    }
}
