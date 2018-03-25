package com.zhoukp.signer.module.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.zhoukp.signer.R;
import com.zhoukp.signer.adapter.ActivityPagerViewPagerAdapter;
import com.zhoukp.signer.fragment.BaseFragment;
import com.zhoukp.signer.module.activity.activities.ActivityFragment;
import com.zhoukp.signer.module.activity.activities.VolunteerFragment;
import com.zhoukp.signer.module.login.LoginActivity;
import com.zhoukp.signer.module.login.UserUtil;
import com.zhoukp.signer.utils.TabUtils;
import com.zhoukp.signer.utils.ToastUtil;

import java.util.ArrayList;

/**
 * @author zhoukp
 * @time 2018/1/31 12:57
 * @email 275557625@qq.com
 * @function 活动页面
 */

public class ActivityPager extends BaseFragment {

    private ImageView ivSearch, ivFiltrate;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ActivityPagerViewPagerAdapter adapter;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_activity, null);
        ivSearch = view.findViewById(R.id.ivSearch);
        ivFiltrate = view.findViewById(R.id.ivFiltrate);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);
        //关联viewpager
        tabLayout.setupWithViewPager(viewPager);
        //设置显示模式
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        if (UserUtil.getInstance().getUser() == null) {
            ToastUtil.showToast(context, "请先登录");
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
            return;
        }

        ArrayList<BaseFragment> fragments = new ArrayList<>();
        fragments.add(new ActivityFragment());
        fragments.add(new VolunteerFragment());

        ArrayList<String> titles = new ArrayList<>();
        titles.add("文体活动");
        titles.add("志愿活动");

        adapter = new ActivityPagerViewPagerAdapter(getChildFragmentManager(), fragments, titles);

        viewPager.setAdapter(adapter);
        TabUtils.reflex(tabLayout);

        initEvent();
    }

    private void initEvent() {

    }
}
