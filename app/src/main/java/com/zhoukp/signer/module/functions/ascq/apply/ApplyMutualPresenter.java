package com.zhoukp.signer.module.functions.ascq.apply;

import android.util.Log;

import com.zhoukp.signer.module.managedevice.DeviceBean;
import com.zhoukp.signer.utils.BaseApi;

/**
 * @author zhoukp
 * @time 2018/4/12 16:16
 * @email 275557625@qq.com
 * @function
 */
public class ApplyMutualPresenter {

    private ApplyMutualView applyMutualView;

    public void attachView(ApplyMutualView applyMutualView) {
        if (this.applyMutualView == null) {
            this.applyMutualView = applyMutualView;
        }
    }

    /**
     * 获取某个班级所有的学生信息
     *
     * @param userId 用户ID
     * @param grade  年级
     * @param major  专业
     * @param clazz  班级
     */
    public void getClazzStudents(String userId, String grade, String major, String clazz) {

        if (applyMutualView != null){
            applyMutualView.showLoadingView();
        }

        BaseApi.request(BaseApi.createApi(IApplyMutualApi.class).getClazzStudents(userId, grade, major, clazz),
                new BaseApi.IResponseListener<ClazzStudentsBean>() {
                    @Override
                    public void onSuccess(ClazzStudentsBean data) {
                        Log.e("zkp", "getClazzStudents==" + data.getStatus());
                        if (applyMutualView != null) {
                            if (data.getStatus() == 200) {
                                applyMutualView.getClazzStudentsSuccess(data);
                            } else {
                                applyMutualView.getClazzStudentsError(data.getStatus());
                            }
                            applyMutualView.hideLoadingView();
                        }
                    }

                    @Override
                    public void onFail() {
                        if (applyMutualView != null) {
                            applyMutualView.getClazzStudentsError(100);
                            applyMutualView.hideLoadingView();
                        }
                    }
                });
    }

    /**
     * 邀请某个学生进入互评小组
     *
     * @param userId    用户ID
     * @param userGrade 年级
     * @param userMajor 专业
     * @param userClazz 班级
     * @param mutualId  被邀请人ID
     */
    public void applyMutual(String userId, String userGrade, String userMajor,
                            String userClazz, String mutualId, final int position) {

        if (applyMutualView != null){
            applyMutualView.showLoadingView();
        }

        BaseApi.request(BaseApi.createApi(IApplyMutualApi.class).applyMutual(userId, userGrade, userMajor, userClazz, mutualId),
                new BaseApi.IResponseListener<DeviceBean>() {
                    @Override
                    public void onSuccess(DeviceBean data) {
                        Log.e("zkp", "applyMutual==" + data.getStatus());
                        if (applyMutualView != null) {
                            if (data.getStatus() == 200) {
                                applyMutualView.applyMutualSuccess(position);
                            } else {
                                applyMutualView.applyMutualError(data.getStatus());
                            }
                            applyMutualView.hideLoadingView();
                        }
                    }

                    @Override
                    public void onFail() {
                        if (applyMutualView != null) {
                            applyMutualView.applyMutualError(100);
                            applyMutualView.hideLoadingView();
                        }
                    }
                });
    }

    /**
     * 取消邀请某个学生进入互评小组
     *
     * @param userId    用户ID
     * @param userGrade 年级
     * @param userMajor 专业
     * @param userClazz 班级
     * @param mutualId  被邀请人ID
     */
    public void cancelMutual(String userId, String userGrade, String userMajor,
                             String userClazz, String mutualId, final int position) {

        if (applyMutualView != null){
            applyMutualView.showLoadingView();
        }

        BaseApi.request(BaseApi.createApi(IApplyMutualApi.class).cancelMutual(userId, userGrade, userMajor, userClazz, mutualId),
                new BaseApi.IResponseListener<DeviceBean>() {
                    @Override
                    public void onSuccess(DeviceBean data) {
                        Log.e("zkp", "cancelMutual==" + data.getStatus());
                        if (applyMutualView != null) {
                            if (data.getStatus() == 200) {
                                applyMutualView.cancelMutualSuccess(position);
                            } else {
                                applyMutualView.cancelMutualError(data.getStatus());
                            }
                            applyMutualView.hideLoadingView();
                        }
                    }

                    @Override
                    public void onFail() {
                        if (applyMutualView != null) {
                            applyMutualView.cancelMutualError(100);
                            applyMutualView.hideLoadingView();
                        }
                    }
                });
    }

    /**
     * 获取某个班级互评小组人数
     *
     * @param userId    用户ID
     * @param userGrade 年级
     * @param userMajor 专业
     * @param userClazz 班级
     */
    public void getMutualNum(String userId, String userGrade, String userMajor, String userClazz) {

        if (applyMutualView != null){
            applyMutualView.showLoadingView();
        }

        BaseApi.request(BaseApi.createApi(IApplyMutualApi.class).getMutualNum(userId, userGrade, userMajor, userClazz),
                new BaseApi.IResponseListener<MutualNumBean>() {
                    @Override
                    public void onSuccess(MutualNumBean data) {
                        Log.e("zkp", "getMutualNum==" + data.getStatus());
                        if (applyMutualView != null) {
                            if (data.getStatus() == 200) {
                                applyMutualView.getMutualNumSuccess(data);
                            } else {
                                applyMutualView.getMutualNumError(data.getStatus());
                            }
                            applyMutualView.hideLoadingView();
                        }
                    }

                    @Override
                    public void onFail() {
                        if (applyMutualView != null) {
                            applyMutualView.getMutualNumError(100);
                            applyMutualView.hideLoadingView();
                        }
                    }
                });

    }

    /**
     * 分配某个班级所有学生的综测表给互评小组审核
     *
     * @param userId    用户ID
     * @param userGrade 年级
     * @param userMajor 专业
     * @param userClazz 班级
     */
    public void groupMutual(String userId, String userGrade, String userMajor, String userClazz) {

        if (applyMutualView != null){
            applyMutualView.showLoadingView();
        }

        BaseApi.request(BaseApi.createApi(IApplyMutualApi.class).groupMutual(userId, userGrade, userMajor, userClazz),
                new BaseApi.IResponseListener<DeviceBean>() {
                    @Override
                    public void onSuccess(DeviceBean data) {
                        Log.e("zkp", "groupMutual==" + data.getStatus());
                        if (applyMutualView != null) {
                            if (data.getStatus() == 200) {
                                applyMutualView.groupMutualSuccess();
                            } else {
                                applyMutualView.groupMutualError(data.getStatus());
                            }
                            applyMutualView.hideLoadingView();
                        }
                    }

                    @Override
                    public void onFail() {
                        if (applyMutualView != null) {
                            applyMutualView.groupMutualError(100);
                            applyMutualView.hideLoadingView();
                        }
                    }
                });

    }

    /**
     * 获取综测表分配状态
     *
     * @param userId    用户ID
     * @param userGrade 年级
     * @param userMajor 专业
     * @param userClazz 班级
     */
    public void getGroupMutualStatus(String userId, String userGrade, String userMajor, String userClazz) {

        if (applyMutualView != null){
            applyMutualView.showLoadingView();
        }

        BaseApi.request(BaseApi.createApi(IApplyMutualApi.class).getGroupMutualStatus(userId, userGrade, userMajor, userClazz),
                new BaseApi.IResponseListener<GroupStatusBean>() {
                    @Override
                    public void onSuccess(GroupStatusBean data) {
                        Log.e("zkp", "getGroupMutualStatus==" + data.getStatus());
                        if (applyMutualView != null) {
                            if (data.getStatus() == 200) {
                                applyMutualView.getGroupMutualStatusSuccess(data);
                            } else {
                                applyMutualView.getGroupMutualStatusError(data.getStatus());
                            }
                            applyMutualView.hideLoadingView();
                        }
                    }

                    @Override
                    public void onFail() {
                        if (applyMutualView != null) {
                            applyMutualView.getGroupMutualStatusError(100);
                            applyMutualView.hideLoadingView();
                        }
                    }
                });

    }

    public void detachView() {
        if (applyMutualView != null) {
            this.applyMutualView = null;
        }
    }
}
