package com.zhoukp.signer.module.functions.meetings.upload;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.zhoukp.signer.module.functions.ledgers.scanxls.XlsBean;

import java.io.File;
import java.util.ArrayList;

/**
 * @author zhoukp
 * @time 2018/4/20 20:08
 * @email 275557625@qq.com
 * @function
 */
public class UploadPdfPresenter {

    private UploadPdfView uploadPdfView;

    public void attachView(UploadPdfView uploadPdfView) {
        if (this.uploadPdfView == null) {
            this.uploadPdfView = uploadPdfView;
        }
    }

    /**
     * 扫描手机上特定后缀名的文件
     *
     * @param suffix 后缀名
     */
    public void queryFiles(Context context, String[] suffix) {
        uploadPdfView.showLoadingView();

        String[] projection = new String[]{
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.SIZE
        };

        ArrayList<XlsBean> xlsBeanList = new ArrayList<>();

        for (int i = 0; i < suffix.length; i++) {
            Cursor cursor = context.getContentResolver().query(
                    Uri.parse("content://media/external/file"),
                    projection,
                    MediaStore.Files.FileColumns.DATA + " like ? ",
                    new String[]{suffix[i]},
                    null);

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
                        Log.e("test", name + "," + path);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            } else {
                uploadPdfView.scanError();
                uploadPdfView.hideLoadingView();
                return;
            }
        }

        uploadPdfView.scanSuccess(xlsBeanList);
        uploadPdfView.hideLoadingView();
    }

    /**
     * 搜索指定目录下特定后缀名的所有文件
     *
     * @param Path        搜索目录
     * @param Extension   扩展名
     * @param IsIterative 是否进入子文件夹
     */
    public void GetFiles(String Path, String[] Extension, boolean IsIterative) {
        uploadPdfView.showLoadingView();
        ArrayList<XlsBean> lstFile = new ArrayList<>();
        File[] files = new File(Path).listFiles();
        if (files == null) {
            uploadPdfView.scanQQError();
            uploadPdfView.hideLoadingView();
            return;
        }
        for (File f : files) {
            if (f.isFile()) {
                //判断扩展名
                for (int i = 0; i < Extension.length; i++) {
                    if (f.getPath().substring(f.getPath().length() - Extension[i].length()).equals(Extension)) {
                        XlsBean bean = new XlsBean();
                        bean.setPath(f.getPath());
                        bean.setName(f.getName());
                        bean.setSize(f.length() + "");
                        lstFile.add(bean);
                    }
                }
                if (!IsIterative)
                    break;

            } else if (f.isDirectory() && f.getPath().indexOf("/.") == -1) //忽略点文件（隐藏文件/文件夹）
                GetFiles(f.getPath(), Extension, IsIterative);
        }
        if (lstFile.size() > 0) {
            uploadPdfView.scanQQSuccess(lstFile);
            uploadPdfView.hideLoadingView();
        } else {
            uploadPdfView.scanQQError();
            uploadPdfView.hideLoadingView();
        }
    }

    public void detachView() {
        if (this.uploadPdfView != null) {
            this.uploadPdfView = null;
        }
    }

}
