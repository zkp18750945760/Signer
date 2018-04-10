package com.zhoukp.signer.module.functions.sign.record;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.zhoukp.signer.R;
import com.zhoukp.signer.module.functions.ledgers.scanxls.ProgressDialog;
import com.zhoukp.signer.module.login.LoginBean;
import com.zhoukp.signer.module.login.UserUtil;
import com.zhoukp.signer.utils.ToastUtil;
import com.zhoukp.signer.utils.WindowUtils;
import com.zhoukp.signer.view.listview.SearchListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author zhoukp
 * @time 2018/4/5 19:20
 * @email 275557625@qq.com
 * @function 签到记录页面
 */
public class SignRecordActivity extends AppCompatActivity implements View.OnClickListener, SignRecordView {

    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.listView)
    SearchListView listView;

    private SignRecordPresenter presenter;
    private ProgressDialog dialog;
    private SignRecordAdapter adapter;

    private ArrayList<SignRecordBean.DataBean> dataBeans;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtils.setTransluteWindow(this);

        setContentView(R.layout.activity_sign_record);
        ButterKnife.bind(this);

        listView.setEnableRefresh(true);
        dataBeans = new ArrayList<>();

        presenter = new SignRecordPresenter();
        presenter.attachView(this);
        LoginBean.UserBean user = UserUtil.getInstance().getUser();
        presenter.getSignRecord(user.getUserId(), user.getUserGrade(), user.getUserMajor(), user.getUserClass());

        initEvents();
    }

    private void initEvents() {
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                finish();
                break;
        }
    }

    @Override
    public void showLoadingView() {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.showMessage("加载中...");
        }
        dialog.show();
    }

    @Override
    public void hideLoadingView() {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.showMessage("加载中...");
        }
        dialog.dismiss();
    }

    @Override
    public void getRecordSuccess(final SignRecordBean bean) {
        if (bean.getData().size() == 0) {
            ToastUtil.showToast(this, "还没有签到记录哦");
            return;
        }

        listSort(bean.getData());
        dataBeans.clear();

        if (bean.getData().size() <= 10) {
            for (SignRecordBean.DataBean dataBean : bean.getData()) {
                dataBeans.add(dataBean);
            }
        } else {
            for (int i = 0; i < 10; i++) {
                dataBeans.add(bean.getData().get(i));
            }
        }

        adapter = new SignRecordAdapter(this, dataBeans);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        listView.showHeader(false);
        //下拉刷新
        listView.pullRefreshEnable(true);
        //自动加载更多
        listView.setAutoFetchMore(false);
        listView.onRefreshComplete();

        listView.setOnRefreshListener(new SearchListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoginBean.UserBean user = UserUtil.getInstance().getUser();
                presenter.getSignRecord(user.getUserId(), user.getUserGrade(), user.getUserMajor(), user.getUserClass());
                listView.onRefreshComplete();
            }
        });

        listView.setOnLastItemVisibleListener(new SearchListView.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                int start = listView.getLastVisiblePosition();
                int end;
                if (bean.getData().size() >= start + 10) {
                    end = start + 10;
                } else {
                    end = bean.getData().size();
                }
                for (int i = start; i < end; i++) {
                    dataBeans.add(bean.getData().get(i));
                }
                adapter.notifyDataSetChanged();
                listView.onRefreshComplete();

                if (bean.getData().size() > 30) {
                    listView.setLoadAll();
                }
            }
        });
    }

    /**
     * 对时间做排序，越接近现在的越靠前
     *
     * @param list list
     */
    private static void listSort(List<SignRecordBean.DataBean> list) {
        Collections.sort(list, new Comparator<SignRecordBean.DataBean>() {
            @Override
            public int compare(SignRecordBean.DataBean o1, SignRecordBean.DataBean o2) {
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
    public void getRecordError(int status) {
        switch (status) {
            case 100:
                ToastUtil.showToast(this, "获取签到记录失败");
                break;
            case 101:
                ToastUtil.showToast(this, "数据库IO错误");
                break;
        }
    }
}
