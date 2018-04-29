package com.zhoukp.signer.module.functions.ascq.record;

import android.util.Log;

import com.zhoukp.signer.module.functions.ascq.mutual.IMutualASCQApi;
import com.zhoukp.signer.module.functions.ascq.mutual.IsMutualMemberBean;
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
     * 判断当前用户是不是互评小组成员
     *
     * @param userId    用户ID
     * @param userGrade 年级
     * @param userMajor 专业
     * @param userClazz 班级
     */
    public void isMutualMembers(String userId, String userGrade, String userMajor, String userClazz) {

        if (mutualRecordView != null) {
            mutualRecordView.showLoadingView();
        }

        BaseApi.request(BaseApi.createApi(IMutualASCQApi.class).isMutualMembers(userId, userGrade, userMajor, userClazz),
                new BaseApi.IResponseListener<IsMutualMemberBean>() {
                    @Override
                    public void onSuccess(IsMutualMemberBean data) {
                        Log.e("zkp", "isMutualMembers==" + data.getStatus());
                        if (mutualRecordView != null) {
                            if (data.getStatus() == 200) {
                                mutualRecordView.isMemberSuccess(data);
                            } else {
                                mutualRecordView.isMemberError(data.getStatus());
                            }
                            mutualRecordView.hideLoadingView();
                        }
                    }

                    @Override
                    public void onFail() {
                        if (mutualRecordView != null) {
                            mutualRecordView.isMemberError(100);
                            mutualRecordView.hideLoadingView();
                        }
                    }
                });

    }

    /**
     * 获取某个学年的互评记录
     *
     * @param userId     用户ID
     * @param schoolYear 学年
     */
    public void getMutualRecord(String userId, String schoolYear) {

        if (mutualRecordView != null) {
            mutualRecordView.showLoadingView();
        }

        BaseApi.request(BaseApi.createApi(IMutualRecordApi.class).getMutualRecord(userId, schoolYear),
                new BaseApi.IResponseListener<MutualRecordBean>() {
                    @Override
                    public void onSuccess(MutualRecordBean data) {
                        Log.e("zkp", "getMutualRecord==" + data.getStatus());
                        if (mutualRecordView != null) {
                            if (data.getStatus() == 200) {
                                mutualRecordView.getRecordSuccess(data);
                            } else {
                                mutualRecordView.getRecordError(data.getStatus());
                            }

                            mutualRecordView.hideLoadingView();
                        }
                    }

                    @Override
                    public void onFail() {
                        if (mutualRecordView != null) {
                            mutualRecordView.getRecordError(100);
                            mutualRecordView.hideLoadingView();
                        }
                    }
                });

    }

    public void detachView() {
        if (this.mutualRecordView != null) {
            this.mutualRecordView = null;
        }
    }

}
