package com.zhoukp.signer.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhoukp.signer.R;
import com.zhoukp.signer.fragment.BaseFragment;
import com.zhoukp.signer.fragment.FirstLedgerFragemnt;
import com.zhoukp.signer.utils.WindowUtils;

import java.util.ArrayList;

/**
 * @author zhoukp
 * @time 2018/2/4 17:48
 * @email 275557625@qq.com
 * @function 台账页面
 */

public class LedgerActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivBack, ivUpload;
    private TextView tvFirst, tvSecond;

    private ArrayList<BaseFragment> baseFragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtils.getStatusBarHeight(this);
        WindowUtils.setTransluteWindow(this);
        setContentView(R.layout.activity_ledger);

        initViews();

        initVariates();

        initEvents();

        tvFirst.setSelected(true);
        tvSecond.setSelected(false);
        setFragment(0);
    }

    private void initViews() {
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivUpload = (ImageView) findViewById(R.id.ivUpload);
        tvFirst = (TextView) findViewById(R.id.tvFirst);
        tvSecond = (TextView) findViewById(R.id.tvSecond);
    }

    private void initVariates() {
        baseFragments = new ArrayList<>();
        baseFragments.add(new FirstLedgerFragemnt());
        baseFragments.add(new FirstLedgerFragemnt());
    }

    private void initEvents() {
        ivBack.setOnClickListener(this);
        tvFirst.setOnClickListener(this);
        tvSecond.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.tvFirst:
                Log.e("zkp", tvFirst.isSelected() + "");
                if (!tvFirst.isSelected()) {
                    tvFirst.setSelected(true);
                    tvSecond.setSelected(false);
                    setFragment(0);
                }
                break;
            case R.id.tvSecond:
                Log.e("zkp", tvSecond.isSelected() + "");
                if (!tvSecond.isSelected()) {
                    tvFirst.setSelected(false);
                    tvSecond.setSelected(true);
                    setFragment(1);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 把页面添加到Fragment中
     */
    private void setFragment(int position) {
        //1.得到FragmentManger
        FragmentManager manager = getSupportFragmentManager();
        //2.开启事务
        FragmentTransaction ft = manager.beginTransaction();
        //3.替换
        ft.replace(R.id.flLedgerContent, baseFragments.get(position));
        //4.提交事务
        ft.commit();
    }
}
