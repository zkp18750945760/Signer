package com.zhoukp.signer.module.functions.ledgers.first;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.zhoukp.signer.R;
import com.zhoukp.signer.fragment.BaseFragment;
import com.zhoukp.signer.module.chose.SelectSchoolYearActivity;
import com.zhoukp.signer.module.chose.SelectTermActivity;
import com.zhoukp.signer.module.chose.SelectWeekActivity;
import com.zhoukp.signer.module.functions.ledgers.scanxls.ProgressDialog;
import com.zhoukp.signer.module.login.LoginBean;
import com.zhoukp.signer.module.login.UserUtil;
import com.zhoukp.signer.utils.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @author zhoukp
 * @time 2018/2/4 17:56
 * @email 275557625@qq.com
 * @function 第一台账页面
 */

public class FirstLedgerFragemnt extends BaseFragment implements View.OnClickListener, FirstLedgerView {

    private static final int YEAR = 1;
    private static final int TERM = 2;
    private static final int WEEK = 3;

    private TextView tvYear, tvTerm, tvWeek;
    private RecyclerView recyclerView;
    private NoFooterAdapter adapter;
    private ArrayList<GroupEntity> groups;

    private FirstLedgerPresenter presenter;
    private ProgressDialog dialog;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.pager_firstledger, null);
        tvYear = view.findViewById(R.id.tvYear);
        tvTerm = view.findViewById(R.id.tvTerm);
        tvWeek = view.findViewById(R.id.tvWeek);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        presenter = new FirstLedgerPresenter();
        presenter.attachView(this);
        LoginBean.UserBean user = UserUtil.getInstance().getUser();
        presenter.getFirstLedger(user.getUserId(), user.getUserGrade(), user.getUserMajor(), user.getUserClass());

        initEvents();
    }

    private void initEvents() {
        tvYear.setOnClickListener(this);
        tvTerm.setOnClickListener(this);
        tvWeek.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tvYear:
                intent = new Intent(context, SelectSchoolYearActivity.class);
                intent.putExtra("type", "schoolYear");
                intent.putExtra("userId", UserUtil.getInstance().getUser().getUserId());
                startActivityForResult(intent, YEAR);
                break;
            case R.id.tvTerm:
                intent = new Intent(context, SelectTermActivity.class);
                intent.putExtra("type", "term");
                startActivityForResult(intent, TERM);
                break;
            case R.id.tvWeek:
                intent = new Intent(context, SelectWeekActivity.class);
                intent.putExtra("type", "week");
                startActivityForResult(intent, WEEK);
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            switch (requestCode) {
                case YEAR:
                    String schoolYear = data.getStringExtra("schoolYear");
                    if (!TextUtils.isEmpty(schoolYear)) {
                        tvYear.setText(schoolYear);
                    }
                    break;
                case TERM:
                    String term = data.getStringExtra("term");
                    if (!TextUtils.isEmpty(term)) {
                        tvTerm.setText(term);
                    }
                    break;
                case WEEK:
                    String week = data.getStringExtra("week");
                    if (!TextUtils.isEmpty(week)) {
                        tvWeek.setText(week);
                    }
                    break;
                default:
                    break;
            }
        }
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
    public void getFirstLedgerSuccess(FirstLedgerBean bean) {
        final LayoutInflater layoutInflater = LayoutInflater.from(context);
        groups = new ArrayList<>();

        listSort(bean.getData());

        ArrayList<ChildEntity> children = null;

        for (int i = 0; i < bean.getData().size(); i++) {

            if (i < bean.getData().size() - 1) {
                if (!bean.getData().get(i).getTime().equals(bean.getData().get(i + 1).getTime())) {
                    children = new ArrayList<>();

                    children.add(new ChildEntity(bean.getData().get(i).getContent(),
                            bean.getData().get(i).getContentLong(),
                            Integer.parseInt(bean.getData().get(i).getStatus())));

                    groups.add(new GroupEntity(bean.getData().get(i).getDate(),
                            bean.getData().get(i).getDate(),
                            children));

                    if (i == bean.getData().size() - 2) {
                        children = new ArrayList<>();

                        children.add(new ChildEntity(bean.getData().get(i + 1).getContent(),
                                bean.getData().get(i + 1).getContentLong(),
                                Integer.parseInt(bean.getData().get(i + 1).getStatus())));

                        groups.add(new GroupEntity(bean.getData().get(i + 1).getDate(),
                                bean.getData().get(i + 1).getDate(),
                                children));
                    }
                } else {
                    if (children == null) {
                        children = new ArrayList<>();
                    }

                    children.add(new ChildEntity(bean.getData().get(i).getContent(),
                            bean.getData().get(i).getContentLong(),
                            Integer.parseInt(bean.getData().get(i).getStatus())));

                    if (i == bean.getData().size() - 2) {
                        children.add(new ChildEntity(bean.getData().get(i + 1).getContent(),
                                bean.getData().get(i + 1).getContentLong(),
                                Integer.parseInt(bean.getData().get(i + 1).getStatus())));
                    }
                }
            }
        }

        adapter = new NoFooterAdapter(context, groups);

        GroupItemDecoration decoration = new GroupItemDecoration(adapter);
        decoration.setGroupDivider(ResourcesCompat.getDrawable(getResources(), R.drawable.divider_height_16_dp, null));
        decoration.setTitleDivider(ResourcesCompat.getDrawable(getResources(), R.drawable.divider_height_1_px, null));
        decoration.setChildDivider(ResourcesCompat.getDrawable(getResources(), R.drawable.divider_white_header, null));
        recyclerView.addItemDecoration(decoration);

        adapter.setOnHeaderClickListener(new GroupedRecyclerViewAdapter.OnHeaderClickListener() {
            @Override
            public void onHeaderClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder, int groupPosition) {
                ToastUtil.showToast(context, "组头：groupPosition = " + groupPosition);
                Log.e("eee", adapter.toString() + "  " + holder.toString());
            }
        });

        adapter.setOnChildClickListener(new GroupedRecyclerViewAdapter.OnChildClickListener() {
            @Override
            public void onChildClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder, int groupPosition, int childPosition) {
                ToastUtil.showToast(context, "子项：groupPosition = " + groupPosition + ", childPosition = " + childPosition);
            }
        });

        recyclerView.setAdapter(adapter);

    }

    /**
     * 对时间做排序，越接近现在的越靠前
     *
     * @param list list
     */
    private static void listSort(List<FirstLedgerBean.DataBean> list) {
        Collections.sort(list, new Comparator<FirstLedgerBean.DataBean>() {
            @Override
            public int compare(FirstLedgerBean.DataBean o1, FirstLedgerBean.DataBean o2) {
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date dt1 = format.parse(o1.getTime());
                    Date dt2 = format.parse(o2.getTime());
                    if (dt1.getTime() < dt2.getTime()) {
                        return 1;
                    } else if (dt1.getTime() > dt2.getTime()) {
                        return -1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }

    @Override
    public void getFirstLedgerError(int status) {
        switch (status) {
            case 100:
                ToastUtil.showToast(context, "获取第一台账失败");
                break;
            case 101:
                ToastUtil.showToast(context, "管理员还没有上传课表");
                break;
            case 102:
                ToastUtil.showToast(context, "数据库IO错误");
                break;
        }
    }
}
