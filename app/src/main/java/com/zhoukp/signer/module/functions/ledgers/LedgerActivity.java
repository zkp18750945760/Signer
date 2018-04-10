package com.zhoukp.signer.module.functions.ledgers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
    private Fragment content;//记录选中的fragment

    private String[] tags = new String[]{"first", "second"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtils.getStatusBarHeight(this);
        WindowUtils.setTransluteWindow(this);

        setContentView(R.layout.activity_ledger);

        ButterKnife.bind(this);

        initVariates(savedInstanceState);

        initEvents();
    }

    private void initVariates(Bundle savedInstanceState) {
        //1.得到FragmentManger
        FragmentManager manager = getSupportFragmentManager();
        baseFragments = new ArrayList<>();
        if (savedInstanceState != null) {
            baseFragments.add((FirstLedgerFragemnt) manager.findFragmentByTag(tags[0]));
            baseFragments.add((SecondLedgerFragemnt) manager.findFragmentByTag(tags[1]));
        } else {
            baseFragments.add(new FirstLedgerFragemnt());
            baseFragments.add(new SecondLedgerFragemnt());
        }
        content = baseFragments.get(0);
        //2.开启事务
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(R.id.flLedgerContent, content).commit();
    }

    private void initEvents() {
        ivBack.setOnClickListener(this);
        ivUpload.setOnClickListener(this);
        segmentView.setOnItemSelectedListener(this);
    }

    @Override
    public void onSelected(int i, String s) {
        ToastUtil.showToast(this, i + "==" + s);
        switchContent(content, baseFragments.get(i), i);
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
     * fragment 切换
     *
     * @param from 当前页面
     * @param to   要显示的页面
     */
    public void switchContent(Fragment from, Fragment to, int position) {
        if (content != to) {
            content = to;
            //1.得到FragmentManger
            FragmentManager manager = getSupportFragmentManager();
            //2.开启事务
            FragmentTransaction ft = manager.beginTransaction();
            if (!to.isAdded()) { // 先判断是否被add过
                // 隐藏当前的fragment，add下一个到Activity中
                ft.hide(from).add(R.id.flLedgerContent, to, tags[position]).commit();
            } else {
                // 隐藏当前的fragment，显示下一个
                ft.hide(from).show(to).commit();
            }
        }
    }
}
