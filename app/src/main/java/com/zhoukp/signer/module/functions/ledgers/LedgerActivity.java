package com.zhoukp.signer.module.functions.ledgers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.github.czy1121.view.SegmentedView;
import com.zhoukp.signer.R;
import com.zhoukp.signer.fragment.BaseFragment;
import com.zhoukp.signer.module.functions.ledgers.first.FirstLedgerFragemnt;
import com.zhoukp.signer.module.functions.ledgers.scanxls.ScanXlsActivity;
import com.zhoukp.signer.module.functions.ledgers.second.SecondLedgerFragemnt;
import com.zhoukp.signer.module.login.UserUtil;
import com.zhoukp.signer.utils.ToastUtil;
import com.zhoukp.signer.utils.WindowUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author zhoukp
 * @time 2018/2/4 17:48
 * @email 275557625@qq.com
 * @function 台账页面
 */
public class LedgerActivity extends AppCompatActivity implements View.OnClickListener, SegmentedView.OnItemSelectedListener {


    @Bind(R.id.ivUpload)
    ImageView ivUpload;
    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.segmentView)
    SegmentedView segmentView;
    @Bind(R.id.flLedgerContent)
    FrameLayout flLedgerContent;

    private ArrayList<BaseFragment> baseFragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtils.getStatusBarHeight(this);
        WindowUtils.setTransluteWindow(this);

        setContentView(R.layout.activity_ledger);

        ButterKnife.bind(this);

        initVariates();

        initEvents();

        setFragment(0);
    }

    private void initVariates() {
        baseFragments = new ArrayList<>();
        baseFragments.add(new FirstLedgerFragemnt());
        baseFragments.add(new SecondLedgerFragemnt());
    }

    private void initEvents() {
        ivBack.setOnClickListener(this);
        ivUpload.setOnClickListener(this);
        segmentView.setOnItemSelectedListener(this);
    }

    @Override
    public void onSelected(int i, String s) {
        ToastUtil.showToast(this, i + "==" + s);
        setFragment(i);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.ivUpload:
                //上传第二台账xls表格页面
                if (!UserUtil.getInstance().getUser().getUserDuty().equals("学生")) {
                    startActivity(new Intent(this, ScanXlsActivity.class));
                } else {
                    ToastUtil.showToast(LedgerActivity.this, "你没有权限上传本班的第二台账哦");
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
