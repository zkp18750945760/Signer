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
        if (this.scanXlsView == null) {
            this.scanXlsView = scanXlsView;
        }
    }

    /**
     * 扫描手机上特定后缀名的文件
     *
     * @param suffix 后缀名
     */
    public void queryFiles(Context context, String[] suffix) {
        if (scanXlsView != null) {
            scanXlsView.showDialog();
        }

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
                if (scanXlsView != null) {
                    scanXlsView.scanXlsError();
                    scanXlsView.hideDialog();
                }
                return;
            }
        }

        if (scanXlsView != null) {
            scanXlsView.scanXlsSuccess(xlsBeanList);
            scanXlsView.hideDialog();
        }
    }

    /**
     * 搜索指定目录下特定后缀名的所有文件
     *
     * @param Path        搜索目录
     * @param Extension   扩展名
     * @param IsIterative 是否进入子文件夹
     */
    public void GetFiles(String Path, String[] Extension, boolean IsIterative) {
        if (scanXlsView != null) {
            scanXlsView.hideDialog();
        }
        ArrayList<XlsBean> lstFile = new ArrayList<>();
        File[] files = new File(Path).listFiles();
        if (files == null) {
            if (scanXlsView != null) {
                scanXlsView.scanXlsInQQError();
                scanXlsView.hideDialog();
            }
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

            } else if (f.isDirectory() && !f.getPath().contains("/.")) //忽略点文件（隐藏文件/文件夹）
                GetFiles(f.getPath(), Extension, IsIterative);
        }
        if (lstFile.size() > 0) {
            if (scanXlsView != null) {
                scanXlsView.scanXlsInQQSucccess(lstFile);
                scanXlsView.hideDialog();
            }
        } else {
            if (scanXlsView != null) {
                scanXlsView.scanXlsInQQError();
                scanXlsView.hideDialog();
            }
        }
    }

    /**
     * 上传某个月份的第二台账xls表格文件到服务器
     *
     * @param month 月份
     * @param xls   xls文件路径
     */
    public void uploadLedger(int month, String xls) {
        if (scanXlsView != null) {
            scanXlsView.showDialog();
        }

        File file = new File(xls);
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("uploadFile", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file))
                .build();

        BaseApi.request(BaseApi.createApi(IScanXlsApi.class).uploadLedger(month, requestBody),
                new BaseApi.IResponseListener<SponsorSignBean>() {
                    @Override
                    public void onSuccess(SponsorSignBean data) {
                        Log.e("zkp", "uploadLedger==" + data.getStatus());
                        if (scanXlsView != null) {
                            if (data.getStatus() == 200) {
                                //上传文件成功
                                scanXlsView.uploadSuccess();
                            } else {
                                scanXlsView.uploadError(data.getStatus());
                            }
                            scanXlsView.hideDialog();
                        }
                    }

                    @Override
                    public void onFail() {
                        if (scanXlsView != null) {
                            scanXlsView.uploadError(100);
                            scanXlsView.hideDialog();
                        }
                    }
                });
    }

    /**
     * 解绑视图
     */
    public void detachView() {
        if (this.scanXlsView != null) {
            this.scanXlsView = null;
        }
    }
}
