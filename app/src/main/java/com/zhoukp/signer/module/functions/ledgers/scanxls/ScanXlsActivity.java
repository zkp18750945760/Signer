package com.zhoukp.signer.module.functions.ledgers.scanxls;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
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
import com.zhoukp.signer.view.CommonDialog;

import java.io.File;
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
    @Bind(R.id.ivSearch)
    ImageView ivSearch;

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
        ivSearch.setOnClickListener(this);
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
            case R.id.ivSearch:
                //打开文件管理器选择文件
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                try {
                    startActivityForResult(Intent.createChooser(intent, "选择文件"), 200);
                } catch (android.content.ActivityNotFoundException ex) {
                    ToastUtil.showToast(this, "亲，木有文件管理器啊-_-!!");
                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 200:
                    String path;
                    Uri uri = data.getData();
                    if ("file".equalsIgnoreCase(uri.getScheme())) {
                        //使用第三方应用打开
                        path = uri.getPath();
                        return;
                    }
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                        //4.4以后
                        path = getPath(this, uri);
                    } else {
                        //4.4以下下系统调用方法
                        path = getRealPathFromURI(uri);
                    }

                    if (path != null && new File(path).getName().contains(".xls")) {
                        prestener.uploadLedger(TimeUtils.getMonthOfYear(), path);
                    } else {
                        ToastUtil.showToast(ScanXlsActivity.this, "请选择xls文件");
                    }
                    break;
            }
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (null != cursor && cursor.moveToFirst()) {
            ;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
            cursor.close();
        }
        return res;
    }

    /**
     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
     */
    @SuppressLint("NewApi")
    public String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * 获取此 Uri 的数据列的值。这对于 MediaStore uri 和其他基于文件的 ContentProviders 很有用
     *
     * @param context       context
     * @param uri           uri
     * @param selection     查询中使用的筛选器
     * @param selectionArgs 查询中使用的选择参数
     * @return _data列的值, 通常是文件路径
     */
    public String getDataColumn(Context context, Uri uri, String selection,
                                String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri uri
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri uri
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri uri
     * @return Whether the Uri authority is MediaProvider.
     */
    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e("zkp", adapter.getItem(position).getName());
        prestener.uploadLedger(TimeUtils.getMonthOfYear(), adapter.getItem(position).getPath());
    }

    //权限请求回调
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    prestener.queryFiles(this, new String[]{"%.xls", "%.xlsx"});
                } else {
                    ToastUtil.showToast(getApplicationContext(), "请开启读取手机内存权限");
                }
                break;
            case 2:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    prestener.GetFiles("/storage/emulated/0/tencent/QQfile_recv/", ".xls", true);
                } else {
                    ToastUtil.showToast(getApplicationContext(), "请开启读取手机内存权限");
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
        if (datas.size() == 0) {
            if (PermissionUtils.isGrantExternalRW(this, 2)) {
                prestener.GetFiles("/storage/emulated/0/tencent/QQfile_recv/", ".xls", true);
            }
            return;
        }
        adapter = new XlsAdapter(this, datas);
        listView.setAdapter(adapter);
    }

    @Override
    public void scanXlsError() {
        ToastUtil.showToast(this, "扫描失败");
        if (PermissionUtils.isGrantExternalRW(this, 2)) {
            prestener.GetFiles("/storage/emulated/0/tencent/QQfile_recv/", ".xls", true);
        }
    }

    @Override
    public void scanXlsInQQSucccess(ArrayList<XlsBean> datas) {
        adapter = new XlsAdapter(this, datas);
        listView.setAdapter(adapter);
    }

    @Override
    public void scanXlsInQQError() {
        ToastUtil.showToast(this, "扫描失败");
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
}
