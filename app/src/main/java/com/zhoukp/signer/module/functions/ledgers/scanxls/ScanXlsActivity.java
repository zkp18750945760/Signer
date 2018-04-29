package com.zhoukp.signer.module.functions.ledgers.scanxls;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.zhoukp.signer.R;
import com.zhoukp.signer.utils.PermissionUtils;
import com.zhoukp.signer.utils.TimeUtils;
import com.zhoukp.signer.utils.ToastUtil;
import com.zhoukp.signer.utils.WindowUtils;
import com.zhoukp.signer.view.dialog.CommonDialog;
import com.zhoukp.signer.view.dialog.ProgressDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhoukp
 * @time 2018/3/27 20:55
 * @email 275557625@qq.com
 * @function 扫描手机上的xls文件
 */

public class ScanXlsActivity extends AppCompatActivity implements ScanXlsView, View.OnClickListener, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.ivBack)
    ImageView ivBack;

    private ProgressDialog dialog;
    private ScanXlsPrestener prestener;
    private XlsAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtils.getStatusBarHeight(this);
        WindowUtils.setTransluteWindow(this);

        setContentView(R.layout.activity_scanxls);

        ButterKnife.bind(this);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setRefreshing(true);

        prestener = new ScanXlsPrestener();
        prestener.attachView(this);
        prestener.queryFiles(this, new String[]{"%.xls", "%.xlsx"});

        initEvents();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        prestener.detachView();
    }

    private void initEvents() {
        ivBack.setOnClickListener(this);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                new CommonDialog(ScanXlsActivity.this, "确认退出上传本月第二台账吗？", R.style.dialog, new CommonDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if (confirm) {
                            finish();
                        }
                        dialog.dismiss();
                    }
                }).show();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e("zkp", adapter.getItem(position).getName());
        prestener.uploadLedger(TimeUtils.getMonthOfYear(), adapter.getItem(position).getPath());
    }

    @Override
    public void showDialog() {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
        }
        dialog.showMessage("正在加载");
    }

    @Override
    public void hideDialog() {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
        }
        dialog.dismiss();
    }

    @Override
    public void scanXlsSuccess(ArrayList<XlsBean> datas) {
        if (datas.size() == 0) {
            prestener.GetFiles("/storage/emulated/0/tencent/QQfile_recv/", new String[]{"%.xls", "%.xlsx"}, true);
            return;
        }
        adapter = new XlsAdapter(this, datas);
        listView.setAdapter(adapter);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void scanXlsError() {
//        ToastUtil.showToast(this, "扫描失败");
        prestener.GetFiles("/storage/emulated/0/tencent/QQfile_recv/", new String[]{"%.xls", "%.xlsx"}, true);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void scanXlsInQQSucccess(ArrayList<XlsBean> datas) {
        adapter = new XlsAdapter(this, datas);
        listView.setAdapter(adapter);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void scanXlsInQQError() {
        ToastUtil.showToast(this, "扫描失败");
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void uploadSuccess() {
        ToastUtil.showToast(this, "上传本月第二台账成功");
    }

    @Override
    public void uploadError(int status) {
        switch (status) {
            case 100:
                ToastUtil.showToast(this, "上传失败");
                break;
            case 101:
                ToastUtil.showToast(this, "您选择的是空文件");
                break;
            case 102:
                ToastUtil.showToast(this, "文件IO错误");
                break;
            case 103:
                ToastUtil.showToast(this, "数据库IO错误");
                break;
        }
    }

    @Override
    public void onRefresh() {
        prestener.queryFiles(this, new String[]{"%.xls", "%.xlsx"});
    }
}
