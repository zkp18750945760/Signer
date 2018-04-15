package com.zhoukp.signer.module.functions.ascq.record;

import android.util.Log;

import com.zhoukp.signer.utils.BaseApi;

/**
 * @author zhoukp
 * @time 2018/4/15 14:02
 * @email 275557625@qq.com
 * @function
 */
public class MutualRecordPresenter {

    private MutualRecordView mutualRecordView;

    public void attachView(MutualRecordView mutualRecordView) {
        if (this.mutualRecordView == null) {
            this.mutualRecordView = mutualRecordView;
        }
    }

    /**
     * 获取某个学年的互评记录
     *
     * @param userId     用户ID
     * @param schoolYear 学年
     */
    public void getMutualRecord(String userId, String schoolYear) {

        mutualRecordView.showLoadingView();

        BaseApi.request(BaseApi.createApi(IMutualRecordApi.class).getMutualRecord(userId, schoolYear),
                new BaseApi.IResponseListener<MutualRecordBean>() {
                    @Override
                    public void onSuccess(MutualRecordBean data) {
                        Log.e("zkp", "getMutualRecord==" + data.getStatus());

                        if (data.getStatus() == 200) {
                            mutualRecordView.getRecordSuccess(data);
                        } else {
                            mutualRecordView.getRecordError(data.getStatus());
                        }

                        mutualRecordView.hideLoadingView();
                    }

                    @Override
                    public void onFail() {
                        mutualRecordView.getRecordError(100);
                        mutualRecordView.hideLoadingView();
                    }
                });

    }

    public void detachView() {
        if (this.mutualRecordView != null) {
            this.mutualRecordView = null;
        }
    }

}
