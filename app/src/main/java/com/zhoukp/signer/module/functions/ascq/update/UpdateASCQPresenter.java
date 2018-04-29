package com.zhoukp.signer.module.functions.ascq.update;

import android.util.Log;

import com.zhoukp.signer.module.functions.ascq.mutual.IsMutualMemberBean;
import com.zhoukp.signer.module.managedevice.DeviceBean;
import com.zhoukp.signer.utils.BaseApi;

/**
 * @author zhoukp
 * @time 2018/4/15 15:21
 * @email 275557625@qq.com
 * @function
 */
public class UpdateASCQPresenter {

    private UpdateASCQView updateASCQView;

    public void attachView(UpdateASCQView updateASCQView) {
        if (this.updateASCQView == null) {
            this.updateASCQView = updateASCQView;
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

        if (updateASCQView != null) {
            updateASCQView.showLoadingView();
        }

        BaseApi.request(BaseApi.createApi(IUpdateASCQApi.class).isMutualMembers(userId, userGrade, userMajor, userClazz),
                new BaseApi.IResponseListener<IsMutualMemberBean>() {
                    @Override
                    public void onSuccess(IsMutualMemberBean data) {
                        Log.e("zkp", "isMutualMembers==" + data.getStatus());
                        if (updateASCQView != null) {
                            if (data.getStatus() == 200) {
                                updateASCQView.isMemberSuccess(data);
                            } else {
                                updateASCQView.isMemberError(data.getStatus());
                            }
                            updateASCQView.hideLoadingView();
                        }
                    }

                    @Override
                    public void onFail() {
                        if (updateASCQView != null) {
                            updateASCQView.isMemberError(100);
                            updateASCQView.hideLoadingView();
                        }
                    }
                });

    }

    /**
     * 获取某个学年审核的所有学生学号
     *
     * @param userId     用户ID
     * @param schoolYear 学年
     */
    public void getMutualIDs(String userId, String schoolYear) {

        if (updateASCQView != null) {
            updateASCQView.showLoadingView();
        }

        BaseApi.request(BaseApi.createApi(IUpdateASCQApi.class).getMutualIDs(userId, schoolYear),
                new BaseApi.IResponseListener<MutualIDsBean>() {
                    @Override
                    public void onSuccess(MutualIDsBean data) {
                        Log.e("zkp", "getMutualIDs==" + data.getStatus());
                        if (updateASCQView != null) {
                            if (data.getStatus() == 200) {
                                updateASCQView.getIdsSuccess(data);
                            } else {
                                updateASCQView.getIdsError(data.getStatus());
                            }
                            updateASCQView.hideLoadingView();
                        }
                    }

                    @Override
                    public void onFail() {
                        if (updateASCQView != null) {
                            updateASCQView.getIdsError(100);
                            updateASCQView.hideLoadingView();
                        }
                    }
                });

    }

    /**
     * 获取申请修改综测成绩学生的互评成绩
     *
     * @param userId     用户ID
     * @param schoolYear 学年
     */
    public void getModifyASCQ(String userId, String schoolYear) {

        if (updateASCQView != null) {
            updateASCQView.showLoadingView();
        }

        BaseApi.request(BaseApi.createApi(IUpdateASCQApi.class).getModifyASCQ(userId, schoolYear),
                new BaseApi.IResponseListener<UpdateASCQBean>() {
                    @Override
                    public void onSuccess(UpdateASCQBean data) {
                        Log.e("zkp", "getModifyASCQ==" + data.getStatus());
                        if (updateASCQView != null) {
                            if (data.getStatus() == 200) {
                                updateASCQView.getASCQSuccess(data);
                            } else {
                                updateASCQView.getASCQError(data.getStatus());
                            }
                            updateASCQView.hideLoadingView();
                        }
                    }

                    @Override
                    public void onFail() {
                        if (updateASCQView != null) {
                            updateASCQView.getASCQError(100);
                            updateASCQView.hideLoadingView();
                        }
                    }
                });

    }

    /**
     * 更新某个学年某个学生的综测成绩
     *
     * @param ASCQ ASCQ
     */
    public void updateASCQ(String ASCQ) {

        if (updateASCQView != null) {
            updateASCQView.showLoadingView();
        }

        BaseApi.request(BaseApi.createApi(IUpdateASCQApi.class).updateASCQ(ASCQ),
                new BaseApi.IResponseListener<DeviceBean>() {
                    @Override
                    public void onSuccess(DeviceBean data) {
                        Log.e("zkp", "updateASCQ==" + data.getStatus());
                        if (updateASCQView != null) {
                            if (data.getStatus() == 200) {
                                updateASCQView.updateSuccess();
                            } else {
                                updateASCQView.updateError(data.getStatus());
                            }
                            updateASCQView.hideLoadingView();
                        }
                    }

                    @Override
                    public void onFail() {
                        if (updateASCQView != null) {
                            updateASCQView.updateError(100);
                            updateASCQView.hideLoadingView();
                        }
                    }
                });

    }

    public void detachView() {
        if (this.updateASCQView != null) {
            this.updateASCQView = null;
        }
    }
}
