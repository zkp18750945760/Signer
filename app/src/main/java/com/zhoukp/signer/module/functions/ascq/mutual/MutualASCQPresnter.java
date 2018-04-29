package com.zhoukp.signer.module.functions.ascq.mutual;

import android.util.Log;

import com.zhoukp.signer.module.managedevice.DeviceBean;
import com.zhoukp.signer.utils.BaseApi;

/**
 * @author zhoukp
 * @time 2018/4/13 14:26
 * @email 275557625@qq.com
 * @function
 */
public class MutualASCQPresnter {

    private MutualASCQView mutualASCQView;

    public void attachView(MutualASCQView mutualASCQView) {
        if (this.mutualASCQView == null) {
            this.mutualASCQView = mutualASCQView;
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

        if (mutualASCQView != null){
            mutualASCQView.showLoadingView();
        }

        BaseApi.request(BaseApi.createApi(IMutualASCQApi.class).isMutualMembers(userId, userGrade, userMajor, userClazz),
                new BaseApi.IResponseListener<IsMutualMemberBean>() {
                    @Override
                    public void onSuccess(IsMutualMemberBean data) {
                        Log.e("zkp", "isMutualMembers==" + data.getStatus());
                        if (mutualASCQView != null) {
                            if (data.getStatus() == 200) {
                                mutualASCQView.isMemberSuccess(data);
                            } else {
                                mutualASCQView.isMemberError(data.getStatus());
                            }
                            mutualASCQView.hideLoadingView();
                        }
                    }

                    @Override
                    public void onFail() {
                        if (mutualASCQView != null){
                            mutualASCQView.isMemberError(100);
                            mutualASCQView.hideLoadingView();
                        }
                    }
                });

    }

    /**
     * 判断分配的任务是不是都完成了
     *
     * @param userId    用户ID
     * @param userGrade 年级
     * @param userMajor 专业
     * @param userClazz 班级
     */
    public void isMutualFinish(String userId, String userGrade, String userMajor, String userClazz) {

        if (mutualASCQView != null){
            mutualASCQView.showLoadingView();
        }

        BaseApi.request(BaseApi.createApi(IMutualASCQApi.class).isMutualFinish(userId, userGrade, userMajor, userClazz),
                new BaseApi.IResponseListener<IsMutualFinishBean>() {
                    @Override
                    public void onSuccess(IsMutualFinishBean data) {
                        Log.e("zkp", "isMutualFinish==" + data.getStatus());
                        if (mutualASCQView != null){
                            if (data.getStatus() == 200) {
                                mutualASCQView.isFinishSuccess(data);
                            } else {
                                mutualASCQView.isFinishError(data.getStatus());
                            }
                            mutualASCQView.hideLoadingView();
                        }
                    }

                    @Override
                    public void onFail() {
                        if (mutualASCQView != null){
                            mutualASCQView.isFinishError(100);
                            mutualASCQView.hideLoadingView();
                        }
                    }
                });

    }


    /**
     * 获取当前用户未完成的任务列表
     *
     * @param userId    用户ID
     * @param userGrade 年级
     * @param userMajor 专业
     * @param userClazz 班级
     */
    public void getMutualTask(String userId, String userGrade, String userMajor, String userClazz) {

        if (mutualASCQView != null){
            mutualASCQView.showLoadingView();
        }

        BaseApi.request(BaseApi.createApi(IMutualASCQApi.class).getMutualTask(userId, userGrade, userMajor, userClazz),
                new BaseApi.IResponseListener<MutualTaskBean>() {
                    @Override
                    public void onSuccess(MutualTaskBean data) {
                        Log.e("zkp", "getMutualTask==" + data.getStatus());
                        if (mutualASCQView != null){
                            if (data.getStatus() == 200) {
                                mutualASCQView.getTaskSuccess(data);
                            } else if (data.getStatus() == 102) {
                                mutualASCQView.ASQCNotExist();
                            } else {
                                mutualASCQView.getTaskError(data.getStatus());
                            }
                            mutualASCQView.hideLoadingView();
                        }
                    }

                    @Override
                    public void onFail() {
                        if (mutualASCQView != null){
                            mutualASCQView.getTaskError(100);
                            mutualASCQView.hideLoadingView();
                        }
                    }
                });

    }

    /**
     * 上传审核的综测成绩
     *
     * @param ASCQ      ASCQ
     * @param userId    用户ID
     * @param userGrade 年级
     * @param userMajor 专业
     * @param userClazz 班级
     */
    public void uploadMutual(String ASCQ, String userId, String userGrade, String userMajor, String userClazz) {

        if (mutualASCQView != null){
            mutualASCQView.showLoadingView();
        }

        BaseApi.request(BaseApi.createApi(IMutualASCQApi.class).uploadMutual(ASCQ, userId, userGrade, userMajor, userClazz),
                new BaseApi.IResponseListener<DeviceBean>() {
                    @Override
                    public void onSuccess(DeviceBean data) {
                        Log.e("zkp", "uploadMutual==" + data.getStatus());
                        if (mutualASCQView != null){
                            if (data.getStatus() == 200) {
                                mutualASCQView.uploadSuccess();
                            } else {
                                mutualASCQView.uploadError(data.getStatus());
                            }
                            mutualASCQView.hideLoadingView();
                        }
                    }

                    @Override
                    public void onFail() {
                        if (mutualASCQView != null){
                            mutualASCQView.uploadError(100);
                            mutualASCQView.hideLoadingView();
                        }
                    }
                });

    }

    public void detachView() {
        if (this.mutualASCQView != null) {
            this.mutualASCQView = null;
        }
    }

}
