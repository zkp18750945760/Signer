package com.zhoukp.signer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.github.czy1121.view.SegmentedView;
import com.zhoukp.signer.R;
import com.zhoukp.signer.fragment.ReplaceFragment;
import com.zhoukp.signer.pager.AwardsPager;
import com.zhoukp.signer.pager.BasePager;
import com.zhoukp.signer.utils.ToastUtil;
import com.zhoukp.signer.utils.WindowUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author zhoukp
 * @time 2018/1/31 18:13
 * @email 275557625@qq.com
 * @function 科创页面
 */

public class CotrunActivity extends AppCompatActivity implements View.OnClickListener, SegmentedView.OnItemSelectedListener {


    @Bind(R.id.ivUpload)
    ImageView ivUpload;
    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.rl_title)
    RelativeLayout rlTitle;
    @Bind(R.id.segmentView)
    SegmentedView segmentView;
    @Bind(R.id.flCotrunContent)
    FrameLayout flCotrunContent;

    private ArrayList<BasePager> basePagers;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtils.getStatusBarHeight(this);
        WindowUtils.setTransluteWindow(this);
        setContentView(R.layout.activity_cotrun);
        ButterKnife.bind(this);

        initVariates();

        initEvent();

        setFragment(0);
    }

    private void initVariates() {
        basePagers = new ArrayList<>();
        basePagers.add(new AwardsPager(this));
        basePagers.add(new AwardsPager(this));
    }

    private void initEvent() {
        ivBack.setOnClickListener(this);
        ivUpload.setOnClickListener(this);
        segmentView.setOnItemSelectedListener(this);
    }


    @Override
    public void onSelected(int i, String s) {
        setFragment(i);
        ToastUtil.showToast(this, i + "==" + s);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                //关闭当前页面
                finish();
                break;
            case R.id.ivUpload:
                Intent intent = new Intent(CotrunActivity.this, UploadAwardsActivity.class);
                startActivity(intent);
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
        ft.replace(R.id.flCotrunContent, new ReplaceFragment(getBasePager(position)));
        //4.提交事务
        ft.commit();
    }

    /**
     * 根据位置得到对应的页面
     *
     * @param position position
     * @return basePager
     */
    private BasePager getBasePager(int position) {
        BasePager basePager = basePagers.get(position);
        if (basePager != null && !basePager.isInitData) {
            basePager.initData();//联网请求或者绑定数据
            //使得界面只初始化一次
            basePager.isInitData = true;
        }
        return basePager;
    }
}
