package com.zhoukp.signer.module.home;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.zhoukp.signer.R;
import com.zhoukp.signer.SignApplication;
import com.zhoukp.signer.activity.AllTestActivity;
import com.zhoukp.signer.activity.ConventionActivity;
import com.zhoukp.signer.activity.CotrunActivity;
import com.zhoukp.signer.activity.LedgerActivity;
import com.zhoukp.signer.fragment.BaseFragment;
import com.zhoukp.signer.imageloader.GlideImageLoader;
import com.zhoukp.signer.module.login.LoginActivity;
import com.zhoukp.signer.module.login.UserUtil;
import com.zhoukp.signer.module.functions.sign.SignActivity;
import com.zhoukp.signer.utils.Constant;
import com.zhoukp.signer.utils.DividerItemDecoration;
import com.zhoukp.signer.utils.ToastUtil;
import com.zhoukp.signer.view.ThreePointLoadingView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoukp
 * @time 2018/1/28 20:48
 * @email 275557625@qq.com
 * @function 首页
 */

public class HomePager extends BaseFragment implements HomePagerView {

    private RecyclerView recyclerView;
    private ThreePointLoadingView threePointLoadingView;
    private View headerView;
    private static Banner banner;

    private HomeRecyclerViewAdapter adapter;
    private HomePagerPresenter presenter;

    private List<String> banners;

    public static Banner getBanner() {
        return banner;
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_home_scroll, null);
        recyclerView = view.findViewById(R.id.recyclerView);
        //设置recyclerView布局方式
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayout.VERTICAL, false));
        //设置recyclerView动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置recyclerView分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
        threePointLoadingView = view.findViewById(R.id.threePointLoadingView);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        presenter = new HomePagerPresenter();
        presenter.attachView(this);
        presenter.getBanners();
    }

    /**
     * 初始化头部布局
     */
    private void initHeaderView() {
        headerView = View.inflate(context, R.layout.fragment_home_header, null);
        banner = headerView.findViewById(R.id.banner);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(banners);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(4000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        banner.startAutoPlay();
        RecyclerView rvFunction = headerView.findViewById(R.id.rvFunction);
        //recyclerView
        RecyclerView.LayoutManager manager = new GridLayoutManager(context, 5, GridLayout.VERTICAL, false);
        rvFunction.setLayoutManager(manager);
        //设置recyclerView动画为默认动画
        rvFunction.setItemAnimator(new DefaultItemAnimator());
        HomeFunctionAdapter functionAdapter = new HomeFunctionAdapter(context);
        rvFunction.setAdapter(functionAdapter);

        functionAdapter.setOnItemClickListener(new HomeFunctionAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                Intent intent;
                switch (position) {
                    case 0:
                        intent = new Intent(context, CotrunActivity.class);
                        context.startActivity(intent);
                        break;
                    case 1:
                        if (UserUtil.getInstance().getUser() == null) {
                            ToastUtil.showToast(context, "请先登录");
                            intent = new Intent(context, LoginActivity.class);
                            context.startActivity(intent);
                            return;
                        }
                        intent = new Intent(context, SignActivity.class);
                        context.startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(context, AllTestActivity.class);
                        context.startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(context, ConventionActivity.class);
                        context.startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(context, LedgerActivity.class);
                        context.startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (banner != null) {
            banner.stopAutoPlay();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (banner != null) {
            banner.startAutoPlay();
        }
    }

    @Override
    public void showLoadingView() {
        threePointLoadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        threePointLoadingView.setVisibility(View.GONE);
    }

    @Override
    public void getScheduleSuccess(com.zhoukp.signer.module.home.ScheduleBean bean) {
        ToastUtil.showToast(context, "获取日程成功");
        initHeaderView();
        adapter = new HomeRecyclerViewAdapter(context, bean);
        adapter.setHeaderView(headerView);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void getScheduleError(int status) {
        switch (status) {
            case 100:
                ToastUtil.showToast(context, "获取日程失败");
                break;
            case 101:
                ToastUtil.showToast(context, "数据库IO错误");
                break;
        }
        initHeaderView();
        adapter = new HomeRecyclerViewAdapter(context, null);
        adapter.setHeaderView(headerView);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void getBannersSuccess(BannerBean bean) {
        banners = new ArrayList<>();
        for (int i = 0; i < bean.getData().size(); i++) {
            banners.add(Constant.BaseUrl + bean.getData().get(i).getBannerUrl());
        }
        if (UserUtil.getInstance().getUser() == null) {
            initHeaderView();
            adapter = new HomeRecyclerViewAdapter(context, null);
            adapter.setHeaderView(headerView);
            recyclerView.setAdapter(adapter);
            return;
        }
        presenter.getAllSchedule(UserUtil.getInstance().getUser().getUserId());
    }

    @Override
    public void getBannersError(int status) {
        banners = SignApplication.banners;
        switch (status) {
            case 100:
                ToastUtil.showToast(context, "获取Banner失败");
                break;
            case 101:
                ToastUtil.showToast(context, "数据库IO错误");
                break;
        }

        if (UserUtil.getInstance().getUser() == null) {
            initHeaderView();
            adapter = new HomeRecyclerViewAdapter(context, null);
            adapter.setHeaderView(headerView);
            recyclerView.setAdapter(adapter);
            return;
        }

        presenter.getAllSchedule(UserUtil.getInstance().getUser().getUserId());
    }
}
