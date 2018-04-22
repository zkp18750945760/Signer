package com.zhoukp.signer.module.functions.ascq;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhoukp.signer.R;
import com.zhoukp.signer.fragment.BaseFragment;
import com.zhoukp.signer.module.functions.ascq.apply.ApplyMutualFragment;
import com.zhoukp.signer.module.functions.ascq.browse.BrowseASCQFragment;
import com.zhoukp.signer.module.functions.ascq.mutual.MutualASCQFragment;
import com.zhoukp.signer.module.functions.ascq.record.MutualRecordFragment;
import com.zhoukp.signer.module.functions.ascq.update.UpdateASCQFragment;
import com.zhoukp.signer.module.functions.ascq.upload.UploadASCQFragment;
import com.zhoukp.signer.utils.WindowUtils;
import com.zhoukp.signer.view.dialog.CommonDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhoukp
 * @time 2018/4/12 14:05
 * @email 275557625@qq.comy
 * @function
 */
public class ASCQFragmentManager extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.tvContent)
    TextView tvContent;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rlTitle)
    RelativeLayout rlTitle;

    private int actionIndex;
    private String content;

    private ArrayList<BaseFragment> baseFragments;
    private Fragment fragment;//记录选中的fragment

    private String[] tags = new String[]{
            "uploadASCQ",
            "browseASCQ",
            "applyASCQ",
            "startASCQ",
            "mutualRecord",
            "updateASCQ"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtils.setTransluteWindow(this);
        setContentView(R.layout.activity_ascq_manager);
        ButterKnife.bind(this);

        initVariates(savedInstanceState);

        initEvents();
    }

    private void initVariates(Bundle savedInstanceState) {
        actionIndex = getIntent().getIntExtra("actionIndex", 0);

        if (actionIndex == 3 || actionIndex == 1 || actionIndex == 4) {
            //互评
            rlTitle.setVisibility(View.GONE);
        }

        content = getIntent().getStringExtra("content");
        tvContent.setText(content);

        //1.得到FragmentManger
        FragmentManager manager = getSupportFragmentManager();
        baseFragments = new ArrayList<>();
        if (savedInstanceState != null) {
            baseFragments.add((UploadASCQFragment) manager.findFragmentByTag(tags[0]));
            baseFragments.add((BrowseASCQFragment) manager.findFragmentByTag(tags[1]));
            baseFragments.add((ApplyMutualFragment) manager.findFragmentByTag(tags[2]));
            baseFragments.add((MutualASCQFragment) manager.findFragmentByTag(tags[3]));
            baseFragments.add((MutualRecordFragment) manager.findFragmentByTag(tags[4]));
            baseFragments.add((UpdateASCQFragment) manager.findFragmentByTag(tags[5]));
        } else {
            baseFragments.add(new UploadASCQFragment());
            baseFragments.add(new BrowseASCQFragment());
            baseFragments.add(new ApplyMutualFragment());
            baseFragments.add(new MutualASCQFragment());
            baseFragments.add(new MutualRecordFragment());
            baseFragments.add(new UpdateASCQFragment());
        }
        fragment = baseFragments.get(actionIndex);
        //2.开启事务
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(R.id.flASCQMamager, fragment).commit();
    }

    private void initEvents() {
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                switch (actionIndex) {
                    case 0:
                        //弹出对话框
                        new CommonDialog(ASCQFragmentManager.this, "确认退出成绩上报吗？\n数据不会保留哦！", R.style.dialog, new CommonDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                if (confirm) {
                                    finish();
                                }
                                dialog.dismiss();
                            }
                        }).show();
                        break;
                    default:
                        finish();
                        break;
                }
                break;
        }
    }
}
