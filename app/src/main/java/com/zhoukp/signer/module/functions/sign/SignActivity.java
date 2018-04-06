package com.zhoukp.signer.module.functions.sign;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.zhoukp.signer.R;
import com.zhoukp.signer.adapter.MeetingRecyclerViewAdapter;
import com.zhoukp.signer.module.functions.sign.record.SignRecordActivity;
import com.zhoukp.signer.module.login.LoginBean;
import com.zhoukp.signer.utils.LocationUtils;
import com.zhoukp.signer.utils.SchoolYearUtils;
import com.zhoukp.signer.utils.TimeUtils;
import com.zhoukp.signer.utils.ToastUtil;
import com.zhoukp.signer.module.login.UserUtil;
import com.zhoukp.signer.utils.WindowUtils;
import com.zhoukp.signer.view.ThreePointLoadingView;
import com.zhoukp.signer.viewpager.CommonViewPager;
import com.zhoukp.signer.viewpager.ViewPagerHolder;
import com.zhoukp.signer.viewpager.ViewPagerHolderCreator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;

/**
 * @author zhoukp
 * @time 2018/2/1 21:15
 * @email 275557625@qq.com
 * @function
 */

public class SignActivity extends AppCompatActivity implements View.OnClickListener, SignView {

    @Bind(R.id.tvStartSign)
    TextView tvStartSign;
    @Bind(R.id.ivSignRecord)
    ImageView ivSignRecord;
    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.pagerEvents)
    CommonViewPager<SignEventsBean.DataBean> pagerEvents;
    @Bind(R.id.tvSign)
    TextView tvSign;
    @Bind(R.id.tvNoEvents)
    TextView tvNoEvents;
    @Bind(R.id.rlMeetingTheme)
    RelativeLayout rlMeetingTheme;
    @Bind(R.id.ivSignSuccess)
    ImageView ivSignSuccess;
    @Bind(R.id.rlSignSuccess)
    RelativeLayout rlSignSuccess;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.threePointLoadingView)
    ThreePointLoadingView threePointLoadingView;

    private LoginBean.UserBean user;
    private List<SignEventsBean.DataBean> signEventsList;
    private SignPresenter presenter;

    private LocationUtils locationUtils;
    private KpLocationListener locationListener;
    private int flag;

    private MeetingRecyclerViewAdapter adapter;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public SignEventsBean.DataBean dataBean;

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    locationUtils.getLocationClient().stop();
                    locationUtils.getLocationClient().unRegisterLocationListener(locationListener);
                    dataBean = signEventsList.get(pagerEvents.getCurrentItem());
                    presenter.sponsorSign(user.getUserId(),
                            user.getUserGrade(),
                            user.getUserMajor(),
                            user.getUserClass(),
                            dataBean.getType(),
                            dataBean.getTime(),
                            dataBean.getContent(),
                            locationUtils.getLongitude(),
                            locationUtils.getLatitude(),
                            locationUtils.getRadius());
                    handler.removeMessages(flag);
                    break;
                case 2:
                    locationUtils.getLocationClient().stop();
                    locationUtils.getLocationClient().unRegisterLocationListener(locationListener);
                    dataBean = signEventsList.get(pagerEvents.getCurrentItem());
                    presenter.sign(user.getUserId(),
                            user.getUserGrade(),
                            user.getUserMajor(),
                            user.getUserClass(),
                            dataBean.getType(),
                            dataBean.getTime(),
                            dataBean.getContent(),
                            locationUtils.getLongitude(),
                            locationUtils.getLatitude(),
                            locationUtils.getRadius());
                    handler.removeMessages(flag);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtils.getStatusBarHeight(this);
        WindowUtils.setTransluteWindow(this);
        setContentView(R.layout.activity_sign);
        ButterKnife.bind(this);

        initViews();

        initVariates();

        initEvent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    private void initViews() {
        //recyclerView
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);
        //设置recyclerView动画为默认动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        rlSignSuccess = findViewById(R.id.rlSignSuccess);
        if (UserUtil.getInstance().getUser().getUserDuty() != null && UserUtil.getInstance().getUser().getUserDuty().equals("班长")) {
            tvStartSign.setVisibility(View.VISIBLE);
        } else {
            tvStartSign.setVisibility(View.GONE);
        }
    }

    private void initVariates() {

        user = UserUtil.getInstance().getUser();
        locationUtils = new LocationUtils(this);
        locationListener = new KpLocationListener();

        presenter = new SignPresenter();
        presenter.attachView(this);
        presenter.getSignEvents(user.getUserId());
    }

    private void initEvent() {
        ivBack.setOnClickListener(this);
        ivSignRecord.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.ivSignRecord:
                startActivity(new Intent(SignActivity.this, SignRecordActivity.class));
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.tvStartSign, R.id.tvSign})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvStartSign:
                //发起签到
                SignEventsBean.DataBean dataBean = signEventsList.get(pagerEvents.getCurrentItem());
                if (dataBean.getType() == 1) {
                    //课堂签到
                    presenter.getEventsSponsorSignStatus(user.getUserId(),
                            dataBean.getContent() + SchoolYearUtils.getGradeCode(user.getUserGrade())
                                    + SchoolYearUtils.getMajorCode(user.getUserMajor())
                                    + SchoolYearUtils.getClassCode(user.getUserClass())
                                    + SchoolYearUtils.getTermCodeByMonth(user.getUserId(),
                                    Integer.parseInt(TimeUtils.getCurrentYear()),
                                    TimeUtils.getMonthOfYear())
                                    + SchoolYearUtils.getClassBeginTime(dataBean.getTime()));
                } else {
                    //会议签到
                    presenter.getEventsSponsorSignStatus(user.getUserId(), dataBean.getContent() + dataBean.getTime());
                }
                break;
            case R.id.tvSign:
                locationUtils.getLocationClient().registerLocationListener(locationListener);
                locationUtils.getLocationClient().start();
                flag = 2;
                handler.sendEmptyMessageDelayed(flag, 2000);
                break;
        }
    }

    public class KpLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            //获取纬度信息
            locationUtils.setLatitude(location.getLatitude());
            //获取经度信息
            locationUtils.setLongitude(location.getLongitude());

            //获取定位精度，默认值为0.0f
            locationUtils.setRadius(location.getRadius());
        }
    }

    @Override
    public void hideLoadingView() {
        threePointLoadingView.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingView() {
        threePointLoadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void getSignEventsSuccess(SignEventsBean bean) {
        if (bean.getData().size() == 0) {
            rlMeetingTheme.setVisibility(View.GONE);
            tvNoEvents.setVisibility(View.VISIBLE);
            return;
        }

        signEventsList = new ArrayList<>();
        for (int i = 0; i < bean.getData().size(); i++) {
            signEventsList.add(bean.getData().get(i));
        }
        pagerEvents.setIndicatorVisible(false);
        pagerEvents.setPages(signEventsList, new ViewPagerHolderCreator<ViewPagerHolder>() {
            @Override
            public ViewPagerHolder createViewHolder() {
                return new SignEventsViewHolder();
            }
        });

        if (signEventsList.size() > 0) {
            SignEventsBean.DataBean dataBean = signEventsList.get(pagerEvents.getCurrentItem());
            presenter.getSignedHeadIcons(user.getUserId(), user.getUserGrade(),
                    user.getUserMajor(), user.getUserClass(), dataBean.getType(),
                    dataBean.getTime(), dataBean.getContent());
        }
    }

    @Override
    public void getSignEventsError(int status) {
        switch (status) {
            case 100:
                ToastUtil.showToast(this, "请求失败");
                break;
            case 200:
                ToastUtil.showToast(this, "获取签到事项成功");
                break;
            case 101:
                ToastUtil.showToast(this, "数据库IO错误");
                break;
        }
    }

    @Override
    public void sponsorSignSuccess() {
        ToastUtil.showToast(this, "发起签到成功");
    }

    @Override
    public void sponsorSignError(int status) {
        switch (status) {
            case 100:
                ToastUtil.showToast(this, "发起签到失败");
                break;
            case 101:
                ToastUtil.showToast(this, "数据库IO错误");
                break;
            case 102:
                ToastUtil.showToast(this, "服务器无响应");
                break;
            case 103:
                ToastUtil.showToast(this, "班长已经发起签到了哦~");
                break;
        }
    }

    @Override
    public void getEventsSponsorSignStatusSuccess(int status) {
        switch (status) {
            case 201:
                //还未发起签到
                locationUtils.getLocationClient().registerLocationListener(locationListener);
                locationUtils.getLocationClient().start();
                flag = 1;
                handler.sendEmptyMessageDelayed(flag, 2000);
                break;
            case 202:
                //已经发起签到
                ToastUtil.showToast(this, "已经发起签到了哦~");
                break;
        }
    }

    @Override
    public void getEventsSponsorSignStatusError(int status) {
        switch (status) {
            case 100:
                ToastUtil.showToast(this, "请求发起签到状态失败");
                break;
            case 101:
                ToastUtil.showToast(this, "数据库IO错误");
                break;
        }
    }

    @Override
    public void signSuccess() {
        rlMeetingTheme.setVisibility(View.GONE);
        rlSignSuccess.setVisibility(View.VISIBLE);
        SignEventsBean.DataBean dataBean = signEventsList.get(pagerEvents.getCurrentItem());
        presenter.getSignedHeadIcons(user.getUserId(), user.getUserGrade(),
                user.getUserMajor(), user.getUserClass(), dataBean.getType(),
                dataBean.getTime(), dataBean.getContent());
    }

    @Override
    public void signError(int status) {
        switch (status) {
            case 100:
                ToastUtil.showToast(this, "签到失败");
                break;
            case 101:
                ToastUtil.showToast(this, "班长还未发起签到哦~");
                break;
            case 102:
                ToastUtil.showToast(this, "距离班长太远啦");
                break;
            case 103:
                ToastUtil.showToast(this, "已经签到过了哦~");
                break;
            case 104:
                ToastUtil.showToast(this, "数据库IO错误");
                break;
            case 105:
                ToastUtil.showToast(this, "服务器无响应");
                break;
        }
    }

    @Override
    public void getSignedHeadIconsSuccess(SignedHeadIconsBean bean) {
        adapter = new MeetingRecyclerViewAdapter(this, bean);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getSignedHeadIconsError(int status) {
        switch (status) {
            case 100:
                ToastUtil.showToast(this, "获取头像失败");
                break;
            case 101:
                ToastUtil.showToast(this, "还未发起签到");
                break;
            case 102:
                ToastUtil.showToast(this, "数据库IO错误");
                break;
            case 103:
                ToastUtil.showToast(this, "服务器无响应");
                break;
        }
    }

    private class SignEventsViewHolder implements ViewPagerHolder<SignEventsBean.DataBean> {

        private TextView tvWeek, tvDate, tvTime, tvTheme;
        private CountdownView countdownView;

        @Override
        public View createView(Context context) {
            View view = View.inflate(context, R.layout.sign_events_holder, null);
            tvWeek = view.findViewById(R.id.tvWeek);
            tvDate = view.findViewById(R.id.tvDate);
            tvTime = view.findViewById(R.id.tvTime);
            tvTheme = view.findViewById(R.id.tvTheme);
            countdownView = view.findViewById(R.id.countdownView);
            return view;
        }

        @Override
        public void onBind(final Context context, int position, final SignEventsBean.DataBean data) {
            tvWeek.setText(data.getWeek());
            tvTheme.setText(data.getContent());
            tvDate.setText(TimeUtils.getCurrentDate());
            countdownView.start(data.getTimeLag());
            if (data.getType() == 1) {
                //课堂签到
                tvTime.setText(SchoolYearUtils.getClassBeginTime(data.getTime()));

            } else if (data.getType() == 2) {
                //会议签到
                tvTime.setText(data.getTime().substring(11));

            }


            countdownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
                @Override
                public void onEnd(CountdownView countdownView) {
                    String timeEnd = "";
                    if (data.getType() == 1) {
                        timeEnd = "5分钟";
                    } else if (data.getType() == 2) {
                        timeEnd = "10分钟";
                    }
                    ToastUtil.showToast(context, data.getContent() + "签到还有" + timeEnd + "截止,请尽快签到");
                }
            });
        }
    }
}
