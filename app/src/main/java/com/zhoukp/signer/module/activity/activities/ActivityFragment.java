package com.zhoukp.signer.module.activity.activities;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;

import com.zhoukp.signer.R;
import com.zhoukp.signer.fragment.BaseFragment;
import com.zhoukp.signer.module.activity.ActivityBean;
import com.zhoukp.signer.module.login.UserUtil;
import com.zhoukp.signer.utils.ToastUtil;
import com.zhoukp.signer.view.ThreePointLoadingView;

/**
 * @author zhoukp
 * @time 2018/1/31 13:19
 * @email 275557625@qq.com
 * @function 文体活动页面
 */

public class ActivityFragment extends BaseFragment implements ActivityFragmentView, SwipeRefreshLayout.OnRefreshListener {

    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ThreePointLoadingView threePointLoadingView;

    private ActivityListViewAdapter adapter;
    private ActivityFragmentPresenter presenter;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_sports_activity, null);
        listView = view.findViewById(R.id.listView);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        //设置颜色
        swipeRefreshLayout.setColorSchemeResources(R.color.colorBtnPressed);


        threePointLoadingView = view.findViewById(R.id.threePointLoadingView);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setRefreshing(true);

        presenter = new ActivityFragmentPresenter();
        presenter.attachView(this);
        presenter.getActivities(UserUtil.getInstance().getUser().getUserId());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
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
    public void getActivitiesSuccess() {
        ToastUtil.showToast(context, "加载数据成功");
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void getData(ActivityBean bean) {
        adapter = new ActivityListViewAdapter(context, bean, presenter);
        listView.setAdapter(adapter);
    }

    @Override
    public void getActivitiesError() {
        ToastUtil.showToast(context, "加载数据失败");
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void applyActivitiesSuccess() {
        ToastUtil.showToast(context, "报名成功");
    }

    @Override
    public void applyActivitiesError() {
        ToastUtil.showToast(context, "报名失败");
    }

    @Override
    public void cancelApplySuccess() {
        ToastUtil.showToast(context, "取消报名成功");
    }

    @Override
    public void cancelApplyError() {
        ToastUtil.showToast(context, "取消报名失败");
    }

    @Override
    public void setData(ActivityBean data) {
        adapter.setBean(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        presenter.getActivities(UserUtil.getInstance().getUser().getUserId());
    }
}
