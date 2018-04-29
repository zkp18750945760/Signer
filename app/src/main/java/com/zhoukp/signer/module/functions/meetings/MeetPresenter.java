package com.zhoukp.signer.module.functions.meetings;

import android.util.Log;

import com.zhoukp.signer.module.managedevice.DeviceBean;
import com.zhoukp.signer.utils.BaseApi;

/**
 * @author zhoukp
 * @time 2018/4/9 22:18
 * @email 275557625@qq.com
 * @function
 */
public class MeetPresenter {

    private MeetView meetView;

    public void attachView(MeetView meetView) {
        if (this.meetView == null) {
            this.meetView = meetView;
        }
    }

    /**
     * 获取支书会议列表
     *
     * @param userId     用户ID
     * @param userGrade  年级
     * @param userMajor  专业
     * @param userClazz  班级
     * @param schoolYear 学年
     * @param term       学期
     */
    public void getDiscussion(String userId, String userGrade, String userMajor,
                              String userClazz, String schoolYear, String term) {

        if (meetView != null) {
            meetView.showLoadingView();
        }

        BaseApi.request(BaseApi.createApi(IMeetApi.class).getDiscussion(userId, userGrade, userMajor,
                userClazz, schoolYear, term), new BaseApi.IResponseListener<MeetBean>() {
            @Override
            public void onSuccess(MeetBean data) {
                Log.e("zkp", "getDiscussion==" + data.getStatus());
                if (meetView != null) {
                    if (data.getStatus() == 200) {
                        meetView.getDisscussionSuccess(data);
                    } else {
                        meetView.getDiscyssionError(data.getStatus());
                    }
                    meetView.refreshComplete();
                    meetView.hideLoadingView();
                }
            }

            @Override
            public void onFail() {
                if (meetView != null) {
                    meetView.getDiscyssionError(100);
                    meetView.refreshComplete();
                    meetView.hideLoadingView();
                }
            }
        });

    }

    /**
     * 标记支书会议记录已读
     *
     * @param userId 用户ID
     * @param theme  theme
     * @param date   date
     */
    public void updateDiscussionRead(String userId, String theme, String date, final int position) {

        if (meetView != null) {
            meetView.showLoadingView();
        }

        BaseApi.request(BaseApi.createApi(IMeetApi.class).updateDiscussionRead(userId, theme, date),
                new BaseApi.IResponseListener<UpdateReadBean>() {
                    @Override
                    public void onSuccess(UpdateReadBean data) {
                        Log.e("zkp", "updateDiscussionRead==" + data.getClass());
                        if (meetView != null) {
                            if (data.getStatus() == 200) {
                                meetView.updateReadSuccess(data, position);
                            } else {
                                meetView.updateReadError(data.getStatus());
                            }
                            meetView.hideLoadingView();
                        }
                    }

                    @Override
                    public void onFail() {
                        if (meetView != null) {
                            meetView.updateReadError(100);
                            meetView.hideLoadingView();
                        }
                    }
                });

    }

    public void detachView() {
        if (this.meetView != null) {
            meetView = null;
        }
    }
}
