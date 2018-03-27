package com.zhoukp.signer.module.functions.ledgers.scanxls;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;

/**
 * @author zhoukp
 * @time 2018/3/27 21:03
 * @email 275557625@qq.com
 * @function
 */

public class ScanXlsPrestener {

    /**
     * 扫描文件视图
     */
    private ScanXlsView scanXlsView;

    /**
     * 绑定视图
     *
     * @param scanXlsView scanXlsView
     */
    public void attachView(ScanXlsView scanXlsView) {
        this.scanXlsView = scanXlsView;
    }

    /**
     * 扫描手机上特定后缀名的文件
     *
     * @param suffix 后缀名
     */
    public void queryFiles(Context context, String[] suffix) {

        scanXlsView.showDialog();

        String[] projection = new String[]{MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.SIZE
        };
        Cursor cursor = context.getContentResolver().query(
                Uri.parse("content://media/external/file"),
                projection,
                MediaStore.Files.FileColumns.DATA + " like ?",
                new String[]{"%.xlsx"},
                null);

        ArrayList<XlsBean> xlsBeanList = new ArrayList<>();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int idindex = cursor.getColumnIndex(MediaStore.Files.FileColumns._ID);
                int dataindex = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA);
                int sizeindex = cursor.getColumnIndex(MediaStore.Files.FileColumns.SIZE);
                do {
                    String id = cursor.getString(idindex);
                    String path = cursor.getString(dataindex);
                    String size = cursor.getString(sizeindex);
                    int dot = path.lastIndexOf("/");
                    String name = path.substring(dot + 1);

                    XlsBean xlsBean = new XlsBean();
                    xlsBean.setId(id);
                    xlsBean.setPath(path);
                    xlsBean.setSize(size);
                    xlsBean.setName(name);

                    xlsBeanList.add(xlsBean);
                    Log.e("test", name);
                } while (cursor.moveToNext());
            }
            cursor.close();
            scanXlsView.scanXlsSuccess(xlsBeanList);
            scanXlsView.hideDialog();
            return;
        }
        scanXlsView.scanXlsError();
        scanXlsView.hideDialog();
        return;
    }

    /**
     * 解绑视图
     */
    public void detachView() {
        this.scanXlsView = null;
    }
}
