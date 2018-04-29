package com.zhoukp.signer.module.about;

import android.util.Log;

import com.zhoukp.signer.module.main.UpdateBean;
import com.zhoukp.signer.utils.BaseApi;

/**
 * @author zhoukp
 * @time 2018/4/6 23:30
 * @email 275557625@qq.com
 * @function
 */
public class AboutMePresenter {

    private AboutMeView aboutMeView;

    public void attachView(AboutMeView aboutMeView) {
        if (this.aboutMeView == null) {
            this.aboutMeView = aboutMeView;
        }
    }

    public void getUpdateInfo() {

        if (aboutMeView != null){
            aboutMeView.showLoadingView();
        }

        BaseApi.request(BaseApi.createApi(IAboutMeApi.class).getUpdateInfo(),
                new BaseApi.IResponseListener<UpdateBean>() {
                    @Override
                    public void onSuccess(UpdateBean data) {
                        Log.e("zkp", "getUpdateInfo==" + data.getStatus());
                        if (aboutMeView != null) {
                            if (data.getStatus() == 200) {
                                aboutMeView.getUpdateInfoSuccess(data);
                            } else {
                                aboutMeView.getUpdateInfoError(data.getStatus());
                            }
                            aboutMeView.hideLoadingView();
                        }
                    }

                    @Override
                    public void onFail() {
                        if (aboutMeView != null) {
                            aboutMeView.getUpdateInfoError(100);
                            aboutMeView.hideLoadingView();
                        }
                    }
                });

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

    public void detachView() {
        if (this.aboutMeView != null) {
            this.aboutMeView = null;
        }
    }
}
