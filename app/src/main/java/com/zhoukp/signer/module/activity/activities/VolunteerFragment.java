package com.zhoukp.signer.module.activity.activities;

import android.view.View;
import android.widget.ListView;

import com.zhoukp.signer.R;
import com.zhoukp.signer.fragment.BaseFragment;
import com.zhoukp.signer.module.activity.VolunteerBean;
import com.zhoukp.signer.module.login.UserUtil;
import com.zhoukp.signer.utils.ToastUtil;
import com.zhoukp.signer.view.ThreePointLoadingView;

/**
 * @author zhoukp
 * @time 2018/1/31 13:19
 * @email 275557625@qq.com
 * @function 文体活动页面
 */

public class VolunteerFragment extends BaseFragment implements VolunteerFragmentView {

    private ListView listView;
    private ThreePointLoadingView threePointLoadingView;

    private VolunteerListViewAdapter adapter;
    private VolunteerFragmentPresenter presenter;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_sports_activity, null);
        listView = view.findViewById(R.id.listView);
        threePointLoadingView = view.findViewById(R.id.threePointLoadingView);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        presenter = new VolunteerFragmentPresenter();
        presenter.attachView(this);

        presenter.getVolunteers(UserUtil.getInstance().getUser().getUserId());
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
    public void getVolunteersSuccess() {
        ToastUtil.showToast(context, "加载数据成功");
    }

    @Override
    public void getData(VolunteerBean bean) {
        adapter = new VolunteerListViewAdapter(context, bean, presenter);
        listView.setAdapter(adapter);
    }

    @Override
    public void getVolunteersError() {
        ToastUtil.showToast(context, "加载数据失败");
    }

    @Override
    public void applyVolunteersSuccess() {
        ToastUtil.showToast(context, "报名成功");
    }

    @Override
    public void applyVolunteersError() {
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
    public void setData(VolunteerBean data) {
        adapter.setBean(data);
        adapter.notifyDataSetChanged();
    }
}
