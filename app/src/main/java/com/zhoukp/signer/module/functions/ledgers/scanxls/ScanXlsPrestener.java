package com.zhoukp.signer.module.functions.ledgers.scanxls;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.zhoukp.signer.module.functions.sign.SponsorSignBean;
import com.zhoukp.signer.utils.BaseApi;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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
     * 搜索指定目录下特定后缀名的所有文件
     *
     * @param Path        搜索目录
     * @param Extension   扩展名
     * @param IsIterative 是否进入子文件夹
     */
    public void GetFiles(String Path, String Extension, boolean IsIterative) {
        scanXlsView.showDialog();
        ArrayList<XlsBean> lstFile = new ArrayList<>();
        File[] files = new File(Path).listFiles();
        if (files == null) {
            scanXlsView.scanXlsInQQError();
            scanXlsView.hideDialog();
            return;
        }
        for (File f : files) {
            if (f.isFile()) {
                //判断扩展名
                if (f.getPath().substring(f.getPath().length() - Extension.length()).equals(Extension)) {
                    XlsBean bean = new XlsBean();
                    bean.setPath(f.getPath());
                    bean.setName(f.getName());
                    bean.setSize(f.length() + "");
                    lstFile.add(bean);
                }
                if (!IsIterative)
                    break;
            } else if (f.isDirectory() && f.getPath().indexOf("/.") == -1) //忽略点文件（隐藏文件/文件夹）
                GetFiles(f.getPath(), Extension, IsIterative);
        }
        if (lstFile.size() > 0) {
            scanXlsView.scanXlsInQQSucccess(lstFile);
            scanXlsView.hideDialog();
        } else {
            scanXlsView.scanXlsInQQError();
            scanXlsView.hideDialog();
        }
    }

    /**
     * 上传某个月份的第二台账xls表格文件到服务器
     *
     * @param month 月份
     * @param xls   xls文件路径
     */
    public void uploadLedger(int month, String xls) {
        scanXlsView.showDialog();
        File file = new File(xls);
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("xls", file.getName(), RequestBody.create(MediaType.parse("application/otcet-stream"), file))
                .build();

        BaseApi.request(BaseApi.createApi(IScanXlsApi.class).uploadLedger(month, requestBody),
                new BaseApi.IResponseListener<SponsorSignBean>() {
                    @Override
                    public void onSuccess(SponsorSignBean data) {
                        Log.e("zkp", "uploadLedger==" + data.getStatus());

                        if (data.getStatus() == 200) {
                            //上传文件成功
                            scanXlsView.uploadSuccess();
                        } else {
                            scanXlsView.uploadError(data.getStatus());
                        }
                        scanXlsView.hideDialog();
                    }

                    @Override
                    public void onFail() {
                        scanXlsView.uploadError(100);
                        scanXlsView.hideDialog();
                    }
                });
    }

    /**
     * 解绑视图
     */
    public void detachView() {
        this.scanXlsView = null;
    }
}
