package com.zhoukp.signer.module.home;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhoukp.signer.R;
import com.zhoukp.signer.application.SignApplication;
import com.zhoukp.signer.module.functions.ascq.ASCQActivity;
import com.zhoukp.signer.module.main.MainActivity;
import com.zhoukp.signer.module.functions.meetings.MeetingActivity;
import com.zhoukp.signer.activity.CotrunActivity;
import com.zhoukp.signer.module.functions.ledgers.LedgerActivity;
import com.zhoukp.signer.fragment.BaseFragment;
import com.zhoukp.signer.module.home.banner.BannerPagerAdapter;
import com.zhoukp.signer.module.login.LoginActivity;
import com.zhoukp.signer.module.login.UserUtil;
import com.zhoukp.signer.module.functions.sign.SignActivity;
import com.zhoukp.signer.utils.Constant;
import com.zhoukp.signer.utils.DividerItemDecoration;
import com.zhoukp.signer.utils.ToastUtil;
import com.zhoukp.signer.utils.lrucache.RxImageLoader;
import com.zhoukp.signer.view.PaletteImageView;
import com.zhoukp.signer.view.ThreePointLoadingView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoukp
 * @time 2018/1/28 20:48
 * @email 275557625@qq.com
 * @function 首页
 */

public class HomePager extends BaseFragment implements HomePagerView, ViewPager.OnPageChangeListener, View.OnTouchListener {

    public static final int VIEW_PAGER_DELAY = 4000;

    private RecyclerView recyclerView;
    private ThreePointLoadingView threePointLoadingView;
    private View headerView;
    private ViewPager viewPager;
    private LinearLayout indicator;

    private HomeRecyclerViewAdapter adapter;
    private BannerPagerAdapter bannerAdapter;
    private HomePagerPresenter presenter;

    private List<String> banners;
    private ArrayList<PaletteImageView> imageViews;
    private ImageView[] indicators;
    private int currentViewPagerItem;
    //是否自动播放
    private boolean isAutoPlay;
    private BannerHandler bannerHandler;

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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentViewPagerItem = position;
        if (imageViews != null) {
            position %= indicators.length;
            int total = indicators.length;

            for (int i = 0; i < total; i++) {
                if (i == position) {
                    indicators[i].setImageResource(R.drawable.icon_dot_selected);
                } else {
                    indicators[i].setImageResource(R.drawable.icon_dot_normal);
                }
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isAutoPlay = false;
                break;
            case MotionEvent.ACTION_UP:
                isAutoPlay = true;
                break;
        }
        return false;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            //不可见
            isAutoPlay = false;
            if (bannerHandler != null) {
                bannerHandler.removeMessages(0);
            }
        } else {
            //可见
            isAutoPlay = true;
            if (UserUtil.getInstance().getUser() != null){
                presenter.getAllSchedule(UserUtil.getInstance().getUser().getUserId());
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        isAutoPlay = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isAutoPlay = false;
        if (bannerHandler != null) {
            bannerHandler.removeMessages(0);
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

    /**
     * 初始化头部布局
     */
    private void initHeaderView() {
        headerView = View.inflate(context, R.layout.fragment_home_header, null);
        indicator = headerView.findViewById(R.id.indicator);
        viewPager = headerView.findViewById(R.id.viewPager);

        addImageView();

        addPoints();

        isAutoPlay = true;

        bannerAdapter = new BannerPagerAdapter(imageViews, context);
        viewPager.setAdapter(bannerAdapter);
        viewPager.setOnTouchListener(this);
        viewPager.addOnPageChangeListener(this);

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
                        if (UserUtil.getInstance().getUser() == null) {
                            ToastUtil.showToast(context, "请先登录");
                            intent = new Intent(context, LoginActivity.class);
                            context.startActivity(intent);
                            return;
                        }
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
                        if (UserUtil.getInstance().getUser() == null) {
                            ToastUtil.showToast(context, "请先登录");
                            intent = new Intent(context, LoginActivity.class);
                            context.startActivity(intent);
                            return;
                        }
                        intent = new Intent(context, ASCQActivity.class);
                        context.startActivity(intent);
                        break;
                    case 3:
                        if (UserUtil.getInstance().getUser() == null) {
                            ToastUtil.showToast(context, "请先登录");
                            intent = new Intent(context, LoginActivity.class);
                            context.startActivity(intent);
                            return;
                        }
                        intent = new Intent(context, MeetingActivity.class);
                        context.startActivity(intent);
                        break;
                    case 4:
                        if (UserUtil.getInstance().getUser() == null) {
                            ToastUtil.showToast(context, "请先登录");
                            intent = new Intent(context, LoginActivity.class);
                            context.startActivity(intent);
                            return;
                        }
                        intent = new Intent(context, LedgerActivity.class);
                        context.startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void addImageView() {
        imageViews = new ArrayList<>();
        for (int i = 0; i < banners.size(); i++) {
            PaletteImageView imageView = new PaletteImageView(context);
            imageView.setPaletteShadowOffset(10, 10);
            imageView.setPaletteRadius(20);
            Log.e("banners", banners.get(i));
            RxImageLoader.with(context).load(banners.get(i)).into(imageView);
            imageViews.add(i, imageView);
        }
    }

    /**
     * 添加圆点
     */
    private void addPoints() {
        indicators = new ImageView[banners.size()];
        for (int i = 0; i < banners.size(); i++) {
            ImageView imageView = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            params.setMargins(10, 0, 10, 0);
            imageView.setLayoutParams(params);
            //如果当前是第一个 设置为选中状态
            if (i == 0) {
                imageView.setImageResource(R.drawable.icon_dot_selected);
            } else {
                imageView.setImageResource(R.drawable.icon_dot_normal);
            }
            indicators[i] = imageView;
            //添加到父容器
            indicator.addView(imageView);
        }

        //让其在最大值的中间开始滑动, 一定要在 mBottomImages初始化之前完成
        int mid = BannerPagerAdapter.MAX_SCROLL_VALUE / 2;
        viewPager.setCurrentItem(mid);
        currentViewPagerItem = mid;

        bannerHandler = new BannerHandler((MainActivity) context);

        //定时发送消息
        bannerHandler.sendEmptyMessageDelayed(0, VIEW_PAGER_DELAY);
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

    //为防止内存泄漏, 声明自己的Handler并弱引用Activity
    private class BannerHandler extends Handler {

        private WeakReference<MainActivity> mWeakReference;

        public BannerHandler(MainActivity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    MainActivity activity = mWeakReference.get();
                    if (isAutoPlay) {
                        viewPager.setCurrentItem(++currentViewPagerItem);
                        bannerHandler.removeMessages(0);
                        bannerHandler.sendEmptyMessageDelayed(0, VIEW_PAGER_DELAY);
                    }
                    break;
            }
        }
    }
}
