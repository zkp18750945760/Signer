package com.zhoukp.signer.module.functions.meetings.upload;

import android.util.Log;

import com.zhoukp.signer.module.managedevice.DeviceBean;
import com.zhoukp.signer.utils.BaseApi;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author zhoukp
 * @time 2018/4/21 16:27
 * @email 275557625@qq.com
 * @function
 */
public class UploadPresenter {

    private UploadView uploadView;

    public void attachView(UploadView uploadView) {
        if (this.uploadView == null) {
            this.uploadView = uploadView;
        }
    }

    public void uploadDiscussion(String filePath, String userId, String schoolYear,
                                 String term, String content, String Abstract,
                                 String userGrade, String userMajor, String userClazz) {

        uploadView.showLoadingView();

        File file = new File(filePath);

        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("uploadFile", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file))
                .build();

        BaseApi.request(BaseApi.createApi(IUploadApi.class).uploadDiscussion(requestBody, userId, schoolYear,
                term, content, Abstract, userGrade, userMajor, userClazz), new BaseApi.IResponseListener<DeviceBean>() {
            @Override
            public void onSuccess(DeviceBean data) {
                Log.e("zkp", "uploadDiscussion==" + data.getStatus());
                if (data.getStatus() == 200) {
                    uploadView.uploadSuccess();
                } else {
                    uploadView.uploadError(data.getStatus());
                }
                uploadView.hideLoadingView();
            }

            @Override
            public void onFail() {
                uploadView.uploadError(100);
                uploadView.hideLoadingView();
            }
        });

    }


    public void detachView() {
        if (this.uploadView != null) {
            this.uploadView = null;
        }
    }
}
