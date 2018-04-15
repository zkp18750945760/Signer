package com.zhoukp.signer.module.functions.ascq.upload;

import android.util.Log;

import com.zhoukp.signer.module.managedevice.DeviceBean;
import com.zhoukp.signer.utils.BaseApi;

/**
 * @author zhoukp
 * @time 2018/4/11 21:38
 * @email 275557625@qq.com
 * @function
 */
public class UploadASCQPresenter {

    private UploadASCQView uploadASCQView;

    public void attachView(UploadASCQView uploadASCQView) {
        this.uploadASCQView = uploadASCQView;
    }

    /**
     * 上传综测成绩
     *
     * @param ASCQ      ASCQ
     * @param userId    用户ID
     * @param userGrade 年级
     * @param userMajor 专业
     * @param userClazz 班级
     */
    public void uploadASCQ(String ASCQ, String userId, String userGrade, String userMajor, String userClazz) {

        uploadASCQView.showLoadingView();

        BaseApi.request(BaseApi.createApi(IUploadASCQApi.class).uploadASQC(ASCQ, userId, userGrade, userMajor, userClazz),
                new BaseApi.IResponseListener<DeviceBean>() {
                    @Override
                    public void onSuccess(DeviceBean data) {
                        Log.e("zkp", "uploadASQC==" + data.getStatus());
                        if (data.getStatus() == 200) {
                            uploadASCQView.uploadASCQSuccess(data);
                        } else {
                            uploadASCQView.uploadASCQError(data.getStatus());
                        }
                        uploadASCQView.hideLoadingView();
                    }

                    @Override
                    public void onFail() {
                        uploadASCQView.uploadASCQError(100);
                        uploadASCQView.hideLoadingView();
                    }
                });

    }

    public void detachView() {
        if (uploadASCQView != null) {
            this.uploadASCQView = null;
        }
    }
}
