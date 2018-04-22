package com.zhoukp.signer.module.functions.meetings.upload;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.zhoukp.signer.R;
import com.zhoukp.signer.view.dialog.ProgressDialog;
import com.zhoukp.signer.module.functions.ledgers.scanxls.XlsAdapter;
import com.zhoukp.signer.module.functions.ledgers.scanxls.XlsBean;
import com.zhoukp.signer.module.functions.meetings.read.ReadPdfActivity;
import com.zhoukp.signer.utils.ToastUtil;
import com.zhoukp.signer.utils.WindowUtils;
import com.zhoukp.signer.view.dialog.CommonDialog;
import com.zhoukp.signer.view.pop.FloatMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhoukp
 * @time 2018/4/20 20:04
 * @email 275557625@qq.com
 * @function
 */
public class UploadPdfActivity extends AppCompatActivity implements UploadPdfView, View.OnClickListener, AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.ivSearch)
    ImageView ivSearch;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private ProgressDialog dialog;
    private UploadPdfPresenter presenter;
    private XlsAdapter adapter;

    private ArrayList<XlsBean> datas;
    private Point point;
    private FloatMenu floatMenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtils.setTransluteWindow(this);

        setContentView(R.layout.activity_upload_pdf);
        ButterKnife.bind(this);

        initVariates();

        initEvents();
    }

    private void initVariates() {

        point = new Point();

        presenter = new UploadPdfPresenter();
        presenter.attachView(this);
        swipeRefreshLayout.setRefreshing(true);
        presenter.queryFiles(this, new String[]{"%.pdf%", "%.doc%", "%.docx%"});
    }

    private void initEvents() {
        ivBack.setOnClickListener(this);
        listView.setOnItemLongClickListener(this);
        listView.setOnItemClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
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
    public void scanSuccess(ArrayList<XlsBean> datas) {
        swipeRefreshLayout.setRefreshing(false);
        if (datas.size() == 0) {
            presenter.GetFiles("/storage/emulated/0/tencent/QQfile_recv/",
                    new String[]{"%.pdf", "%.doc", "%.docx"}, true);
            return;
        }
        this.datas = datas;
        adapter = new XlsAdapter(this, datas);
        listView.setAdapter(adapter);
    }

    @Override
    public void scanError() {
        swipeRefreshLayout.setRefreshing(false);
        ToastUtil.showToast(this, "扫描失败");
        presenter.GetFiles("/storage/emulated/0/tencent/QQfile_recv/",
                new String[]{"%.pdf", "%.doc", "%.docx"}, true);
    }

    @Override
    public void scanQQSuccess(ArrayList<XlsBean> datas) {
        swipeRefreshLayout.setRefreshing(false);
        this.datas = datas;
        adapter = new XlsAdapter(this, datas);
        listView.setAdapter(adapter);
    }

    @Override
    public void scanQQError() {
        swipeRefreshLayout.setRefreshing(false);
        ToastUtil.showToast(this, "扫描失败");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                //弹出对话框
                new CommonDialog(UploadPdfActivity.this, "确认退出吗？", R.style.dialog, new CommonDialog.OnCloseListener() {
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
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
        popWindow(view, position);
        return true;
    }

    private void popWindow(View view, final int index) {
        floatMenu = new FloatMenu(this);
        floatMenu.items("预览", "上传", "删除");
        floatMenu.show(point);

        floatMenu.setOnItemClickListener(new FloatMenu.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                if (position == 0) {
                    Intent intent = new Intent(UploadPdfActivity.this, ReadPdfActivity.class);
                    intent.putExtra("path", datas.get(index).getPath());
                    intent.putExtra("fileName", datas.get(index).getName());
                    startActivity(intent);
                } else if (position == 1) {
                    //上传文件到服务器
                    Intent intent = new Intent(UploadPdfActivity.this, UploadActivity.class);
                    intent.putExtra("path", datas.get(index).getPath());
                    intent.putExtra("fileName", datas.get(index).getName());
                    startActivity(intent);
                } else {
                    //从列表中删除
                    datas.remove(index);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            point.x = (int) ev.getRawX();
            point.y = (int) ev.getRawY();
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        if (isTopActivity()) {
            Intent intent = new Intent();
            intent.putExtra("path", datas.get(position).getPath());
            intent.putExtra("fileName", datas.get(position).getName());
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }

    /**
     * 返回当前的应用是否处于前台显示状态
     *
     * @return boolean
     */
    private boolean isTopActivity() {
        //_context是一个保存的上下文
        ActivityManager manager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> list = manager.getRunningAppProcesses();
        if (list.size() == 0) return false;
        for (ActivityManager.RunningAppProcessInfo process : list) {
            Log.d("zkp", Integer.toString(process.importance));
            Log.d("zkp", process.processName);
            if (process.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND &&
                    process.processName.equals("com.zhoukp.signer.module.functions.meetings.upload.UploadActivity")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onRefresh() {
        presenter.queryFiles(this, new String[]{"%.pdf%", "%.doc%", "%.docx%"});
    }
}
