package com.zhoukp.signer.module.functions.ascq.apply;

import android.app.Dialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.zhoukp.signer.R;
import com.zhoukp.signer.fragment.BaseFragment;
import com.zhoukp.signer.view.dialog.ProgressDialog;
import com.zhoukp.signer.module.login.LoginBean;
import com.zhoukp.signer.module.login.UserUtil;
import com.zhoukp.signer.utils.ToastUtil;
import com.zhoukp.signer.view.dialog.CommonDialog;

/**
 * @author zhoukp
 * @time 2018/4/12 16:08
 * @email 275557625@qq.com
 * @function 邀请同学进入互评小组Fragment
 */
public class ApplyMutualFragment extends BaseFragment implements ApplyMutualView, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private RelativeLayout rlGroupFinish, rlGroup;
    private ListView listView;
    private Button btnGroup;
    private SwipeRefreshLayout swipeRefreshLayout;

    private ProgressDialog dialog;
    private ApplyMutualPresenter presenter;

    private ApplyMutualAdapter adapter;
    private LoginBean.UserBean user;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_apply_mutual, null);
        listView = view.findViewById(R.id.listView);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        btnGroup = view.findViewById(R.id.btnGroup);
        rlGroup = view.findViewById(R.id.rlGroup);
        rlGroupFinish = view.findViewById(R.id.rlGroupFinish);
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        initVariates();

        initEvents();
    }

    private void initVariates() {


        presenter = new ApplyMutualPresenter();
        presenter.attachView(this);
        user = UserUtil.getInstance().getUser();

        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(this);

        presenter.getGroupMutualStatus(user.getUserId(), user.getUserGrade(),
                user.getUserMajor(), user.getUserClass());
    }

    private void initEvents() {
        btnGroup.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void showLoadingView() {
        if (dialog == null) {
            dialog = new ProgressDialog(context);
            dialog.showMessage("加载中...");
        }
        dialog.show();
    }

    @Override
    public void hideLoadingView() {
        if (dialog == null) {
            dialog = new ProgressDialog(context);
            dialog.showMessage("加载中...");
        }
        dialog.dismiss();
    }

    @Override
    public void getClazzStudentsSuccess(ClazzStudentsBean bean) {
        swipeRefreshLayout.setRefreshing(false);
        adapter = new ApplyMutualAdapter(context, bean, presenter);
        listView.setAdapter(adapter);
    }

    @Override
    public void getClazzStudentsError(int status) {
        swipeRefreshLayout.setRefreshing(false);
        switch (status) {
            case 100:
                ToastUtil.showToast(context, "获取学生名单失败");
                break;
            case 101:
                ToastUtil.showToast(context, "学生课表不存在");
                break;
            case 102:
                ToastUtil.showToast(context, "数据库IO错误");
                break;
        }
    }

    @Override
    public void applyMutualSuccess(int position) {
        ToastUtil.showToast(context, "邀请成功");
        adapter.getItem(position).setIsInvite(true);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void applyMutualError(int status) {
        switch (status) {
            case 100:
                ToastUtil.showToast(context, "邀请失败");
                break;
            case 101:
                ToastUtil.showToast(context, "已经邀请过该同学");
                break;
            case 102:
                ToastUtil.showToast(context, "数据库IO错误");
                break;
            case 103:
                ToastUtil.showToast(context, "互评小组最多6人");
                break;
        }
    }

    @Override
    public void cancelMutualSuccess(int position) {
        ToastUtil.showToast(context, "取消邀请成功");
        adapter.getItem(position).setIsInvite(false);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void cancelMutualError(int status) {
        switch (status) {
            case 100:
                ToastUtil.showToast(context, "取消邀请失败");
                break;
            case 101:
                ToastUtil.showToast(context, "互评表不存在");
                break;
            case 102:
                ToastUtil.showToast(context, "数据库IO错误");
                break;
        }
    }

    @Override
    public void getMutualNumSuccess(MutualNumBean bean) {
        if (bean.getMutualIds() < 6) {
            //人数不足6人，询问用户是否确定要分配综测表给互评小组
            new CommonDialog(context, "互评小组只有" + bean.getMutualIds() + "人，确定要分配吗？", R.style.dialog, new CommonDialog.OnCloseListener() {
                @Override
                public void onClick(Dialog dialog, boolean confirm) {
                    if (confirm) {
                        //分配综测表给互评小组
                        presenter.groupMutual(user.getUserId(), user.getUserGrade(),
                                user.getUserMajor(), user.getUserClass());
                    }
                    dialog.dismiss();
                }
            }).show();
        } else {
            presenter.groupMutual(user.getUserId(), user.getUserGrade(),
                    user.getUserMajor(), user.getUserClass());
        }
    }

    @Override
    public void getMutualNumError(int status) {
        switch (status) {
            case 100:
                ToastUtil.showToast(context, "获取互评小组人数失败");
                break;
            case 101:
                ToastUtil.showToast(context, "互评表不存在");
                break;
            case 102:
                ToastUtil.showToast(context, "数据库IO错误");
                break;
        }
    }

    @Override
    public void groupMutualSuccess() {
        ToastUtil.showToast(context, "分配成功");
    }

    @Override
    public void groupMutualError(int status) {
        switch (status) {
            case 100:
                ToastUtil.showToast(context, "分配失败");
                break;
            case 101:
                ToastUtil.showToast(context, "互评表不存在");
                break;
            case 102:
                ToastUtil.showToast(context, "数据库IO错误");
                break;
            case 103:
                ToastUtil.showToast(context, "已经分配过了");
                break;
        }
    }

    @Override
    public void getGroupMutualStatusSuccess(GroupStatusBean bean) {
        if (bean.getIsGroup().equals("true")) {
            swipeRefreshLayout.setRefreshing(false);
            ToastUtil.showToast(context, "分配过了");
            rlGroupFinish.setVisibility(View.VISIBLE);
            rlGroup.setVisibility(View.GONE);
        } else {
            rlGroupFinish.setVisibility(View.GONE);
            rlGroup.setVisibility(View.VISIBLE);
            presenter.getClazzStudents(user.getUserId(), user.getUserGrade(),
                    user.getUserMajor(), user.getUserClass());
        }
    }

    @Override
    public void getGroupMutualStatusError(int status) {
        swipeRefreshLayout.setRefreshing(false);
        switch (status) {
            case 100:
                ToastUtil.showToast(context, "获取分组记录失败");
                break;
            case 102:
                ToastUtil.showToast(context, "数据库IO错误");
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGroup:
                //随机分配综测表给互评小组
                presenter.getMutualNum(user.getUserId(), user.getUserGrade(), user.getUserMajor(), user.getUserClass());
                break;
        }
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        presenter.getGroupMutualStatus(user.getUserId(), user.getUserGrade(),
                user.getUserMajor(), user.getUserClass());
    }
}
