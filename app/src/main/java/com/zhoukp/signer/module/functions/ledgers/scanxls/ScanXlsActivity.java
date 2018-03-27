package com.zhoukp.signer.module.functions.ledgers.scanxls;

import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.zhoukp.signer.R;
import com.zhoukp.signer.utils.PermissionUtils;
import com.zhoukp.signer.utils.ToastUtil;
import com.zhoukp.signer.utils.WindowUtils;
import com.zhoukp.signer.view.CommonDialog;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author zhoukp
 * @time 2018/3/27 20:55
 * @email 275557625@qq.com
 * @function 扫描手机上的xls文件
 */

public class ScanXlsActivity extends AppCompatActivity implements ScanXlsView, View.OnClickListener, AdapterView.OnItemClickListener {

    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.ivBack)
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

        prestener = new ScanXlsPrestener();
        prestener.attachView(this);

        if (PermissionUtils.isGrantExternalRW(this, 1)) {
            //登陆
            prestener.queryFiles(this, new String[]{"%.xls", "%.xlsx"});
        }

        initEvents();
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
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    prestener.queryFiles(this, new String[]{"%.xls", "%.xlsx"});
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showToast(getApplicationContext(), "请开启读取手机内存权限");
                        }
                    });
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
        adapter = new XlsAdapter(this, datas);
        listView.setAdapter(adapter);
    }

    @Override
    public void scanXlsError() {
        ToastUtil.showToast(this, "扫描失败");
    }
}
