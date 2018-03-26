package com.zhoukp.signer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.zhoukp.signer.R;
import com.zhoukp.signer.fragment.BaseFragment;
import com.zhoukp.signer.module.activity.ActivityPager;
import com.zhoukp.signer.module.home.HomePager;
import com.zhoukp.signer.module.me.MePager;
import com.zhoukp.signer.utils.Constant;
import com.zhoukp.signer.utils.ToastUtil;
import com.zhoukp.signer.utils.WindowUtils;

import java.util.ArrayList;
import java.util.List;

import static android.view.KeyEvent.KEYCODE_BACK;

/**
 * @author zhoukp
 * @time 2018/1/28 18:34
 * @email 275557625@qq.com
 * @function
 */
public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    protected RadioGroup rgTag;
    protected FrameLayout flMainContent;

    /**
     * 选中的位置
     */
    private int position;
    /**
     * 页面的集合
     */
    private ArrayList<BaseFragment> baseFragments;

    /**
     * 是否要退出
     */
    private boolean isExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtils.getStatusBarHeight(this);
        WindowUtils.setTransluteWindow(this);

        setContentView(R.layout.activity_main);

        initViews();

        initVariates();
    }

    /**
     * 再按一次退出应用
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KEYCODE_BACK) {
            if (!isExit) {
                isExit = true;
                ToastUtil.showToast(getApplicationContext(), "再按一次退出应用");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isExit = false;
                    }
                }, 2000);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initViews() {
        rgTag = (RadioGroup) findViewById(R.id.rgTag);
        rgTag.setOnCheckedChangeListener(this);
        flMainContent = (FrameLayout) findViewById(R.id.flMainContent);
    }

    private void initVariates() {
        baseFragments = new ArrayList<>();
        //首页
        baseFragments.add(new HomePager());
        baseFragments.add(new ActivityPager());
        baseFragments.add(new MePager());
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rbHome:
                position = 0;
                break;
            case R.id.rbActivity:
                position = 1;
                break;
            case R.id.rbMe:
                position = 2;
                break;
            default:
                break;
        }
        setFragment();
    }

    /**
     * 把页面添加到Fragment中
     */
    private void setFragment() {
        //1.得到FragmentManger
        FragmentManager manager = getSupportFragmentManager();
        //2.开启事务
        FragmentTransaction ft = manager.beginTransaction();
        //3.替换
        ft.replace(R.id.flMainContent, getFragment());
        //4.提交事务
        ft.commit();
    }

    /**
     * 根据位置得到对应的页面
     *
     * @return ReplaceFragment
     */
    private BaseFragment getFragment() {
        return baseFragments.get(position);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initVariates();
        rgTag.check(R.id.rbHome);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constant.Login:
                    //登陆回调
                    getSharedPreferences("Signer", MODE_PRIVATE).edit().putBoolean("login", true).apply();
                    ((MePager) baseFragments.get(2)).refreshUI();
                    break;
                case PictureConfig.CHOOSE_REQUEST:
                    //更新头像回调
                    // 图片选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    if (selectList.get(0).isCompressed()) {
                        String compressPath = selectList.get(0).getCompressPath();
                        ((MePager) baseFragments.get(2)).presenter.upLoadHeadIcon(MainActivity.this, compressPath);
                        rgTag.check(R.id.rbMe);
                    }
                    break;
            }
        }
    }
}
