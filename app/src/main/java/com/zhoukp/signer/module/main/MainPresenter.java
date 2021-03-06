package com.zhoukp.signer.module.main;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

import com.zhoukp.signer.module.functions.ledgers.scanxls.XlsBean;
import com.zhoukp.signer.module.functions.sign.SponsorSignBean;
import com.zhoukp.signer.utils.BaseApi;
import com.zhoukp.signer.utils.Constant;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author zhoukp
 * @time 2018/3/30 15:37
 * @email 275557625@qq.com
 * @function
 */
public class MainPresenter {

    private MainView mainView;

    /**
     * 绑定视图
     *
     * @param mainView MainView
     */
    public void attachView(MainView mainView) {
        if (this.mainView == null) {
            this.mainView = mainView;
        }
    }

    /**
     * 获取版本更新信息
     */
    public void getUpdateInfo() {
        if (mainView != null) {
            mainView.showLoadingView();
        }
        BaseApi.request(BaseApi.createApi(IMainApi.class).getUpdateInfo(),
                new BaseApi.IResponseListener<UpdateBean>() {
                    @Override
                    public void onSuccess(UpdateBean data) {
                        Log.e("zkp", "getUpdateInfo==" + data.getStatus());
                        if (mainView != null) {
                            if (data.getStatus() == 200) {
                                mainView.getUpdateInfoSuccess(data);
                            } else {
                                mainView.getUpdateInfoError(data.getStatus());
                            }

                            mainView.hideLoadingView();
                        }
                    }

                    @Override
                    public void onFail() {
                        if (mainView != null) {
                            mainView.getUpdateInfoError(100);
                            mainView.hideLoadingView();
                        }
                    }
                });
    }

    /**
     * 搜索指定目录下特定后缀名的所有文件
     *
     * @param Path        搜索目录
     * @param Extension   扩展名
     * @param IsIterative 是否进入子文件夹
     */
    public void getCrashFile(String Path, String Extension, boolean IsIterative) {
        XlsBean lstFile = new XlsBean();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long crachTime = 0;
        File[] files = new File(Path).listFiles();
        if (files == null) {
            return;
        }
        for (File f : files) {
            if (f.isFile()) {
                //判断扩展名
                if (f.getPath().substring(f.getPath().length() - Extension.length()).equals(Extension)) {
                    try {
                        if (crachTime < simpleFormat.parse(f.getName().substring(6, 25)).getTime()) {
                            crachTime = simpleFormat.parse(f.getName().substring(6, 25)).getTime();

                            lstFile.setPath(f.getPath());
                            lstFile.setName(f.getName());
                            lstFile.setSize(f.length() + "");
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (!IsIterative)
                    break;
            } else if (f.isDirectory() && !f.getPath().contains("/.")) //忽略点文件（隐藏文件/文件夹）
                getCrashFile(f.getPath(), Extension, IsIterative);
        }
        if (!TextUtils.isEmpty(lstFile.getName())) {
            if (mainView != null) {
                mainView.getCrashLogcatSuccess(lstFile);
                mainView.hideLoadingView();
            }
        } else {
            if (mainView != null) {
                mainView.getCrashLogcatError();
                mainView.hideLoadingView();
            }
        }
    }

    /**
     * @param file File
     */
    public void uploadCrashLogcat(File file) {

        if (mainView != null) {
            mainView.showLoadingView();
        }

        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("uploadFile", file.getName(),
                        RequestBody.create(MediaType.parse("multipart/form-data"), file))
                .build();
        //将错误日志上传到服务器
        BaseApi.request(BaseApi.createApi(IMainApi.class).uploadCrashLogcat(requestBody),
                new BaseApi.IResponseListener<SponsorSignBean>() {
                    @Override
                    public void onSuccess(SponsorSignBean data) {
                        Log.e("zkp", "uploadCrashLogcat==" + data.getStatus());
                        if (mainView != null) {
                            if (data.getStatus() == 200) {
                                //上传错误日志成功
                                Log.e("zkp", "错误日志上传成功");
                                mainView.uploadCrashSuccess();
                                deleteFile(new File(Constant.appCrashPath));
                            } else {
                                mainView.uploadCrashError();
                            }
                            mainView.hideLoadingView();
                        }
                    }

                    @Override
                    public void onFail() {
                        if (mainView != null) {
                            Log.e("zkp", "错误日志上传失败");
                            mainView.uploadCrashError();
                            mainView.hideLoadingView();
                        }
                    }
                });
    }

    /**
     * 清空文件夹
     *
     * @param file File
     */
    private void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                deleteFile(f);
            }
            //删除文件夹
//            file.delete();
        } else if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 文件大小的转换
     *
     * @param size long
     * @return String
     */
    public String getSize(long size) {
        //如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        if (size < 1024) {
            return String.valueOf(size) + "B";
        } else {
            size = size / 1024;
        }
        //如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        //因为还没有到达要使用另一个单位的时候
        //接下去以此类推
        if (size < 1024) {
            return String.valueOf(size) + "KB";
        } else {
            size = size / 1024;
        }
        if (size < 1024) {
            //因为如果以MB为单位的话，要保留最后1位小数，
            //因此，把此数乘以100之后再取余
            size = size * 100;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "MB";
        } else {
            //否则如果要以GB为单位的，先除于1024再作同样的处理
            size = size * 100 / 1024;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "GB";
        }
    }

    /**
     * 解绑视图
     */
    public void detachView() {
        if (this.mainView != null) {
            this.mainView = null;
        }
    }
}
