package com.zhoukp.signer.module.functions.sign.record;

import android.util.Log;

import com.zhoukp.signer.utils.BaseApi;

/**
 * @author zhoukp
 * @time 2018/4/6 13:36
 * @email 275557625@qq.com
 * @function
 */
public class SignRecordPresenter {

    private SignRecordView signRecordView;

    public void attachView(SignRecordView signRecordView) {
        this.signRecordView = signRecordView;
    }

    /**
     * 获取签到记录
     *
     * @param userId    用户ID
     * @param usetGrade 年级
     * @param userMajor 专业
     * @param userClazz 班级
     */
    public void getSignRecord(String userId, String usetGrade, String userMajor, String userClazz) {
        signRecordView.showLoadingView();

        BaseApi.request(BaseApi.createApi(ISignRecordApi.class).getSignRecord(userId, usetGrade, userMajor, userClazz),
                new BaseApi.IResponseListener<SignRecordBean>() {
                    @Override
                    public void onSuccess(SignRecordBean data) {
                        Log.e("zkp", "getSignRecord==" + data.getStatus());
                        if (data.getStatus() == 200) {
                            signRecordView.getRecordSuccess(data);
                        } else {
                            signRecordView.getRecordError(data.getStatus());
                        }
                        signRecordView.hideLoadingView();
                    }

                    @Override
                    public void onFail() {
                        signRecordView.getRecordError(100);
                        signRecordView.hideLoadingView();
                    }
                });
    }

    public void detachView() {
        this.signRecordView = null;
    }
}
