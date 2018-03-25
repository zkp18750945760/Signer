package com.zhoukp.signer.module.activity.activities;

import android.util.Log;

import com.zhoukp.signer.module.activity.IActivityApi;
import com.zhoukp.signer.module.activity.VolunteerBean;
import com.zhoukp.signer.utils.BaseApi;

/**
 * @author zhoukp
 * @time 2018/3/19 16:09
 * @email 275557625@qq.com
 * @function
 */

public class VolunteerFragmentPresenter {

    private VolunteerFragmentView volunteerFragmentView;

    public void attachView(VolunteerFragmentView volunteerFragmentView) {
        this.volunteerFragmentView = volunteerFragmentView;
    }

    /**
     * 获取所有志愿活动
     *
     * @param userId 用户ID
     */
    public void getVolunteers(String userId) {
        volunteerFragmentView.showLoadingView();
        BaseApi.request(BaseApi.createApi(IActivityApi.class).getVolunteers(userId),
                new BaseApi.IResponseListener<VolunteerBean>() {

                    @Override
                    public void onSuccess(VolunteerBean data) {
                        Log.e("zkp", data.getStatus() + "");
                        if (data.getStatus() == 200) {
                            //成功
                            volunteerFragmentView.getVolunteersSuccess();
                        } else {
                            //失败
                            volunteerFragmentView.getVolunteersError();
                        }
                        volunteerFragmentView.getData(data);
                        volunteerFragmentView.hideLoadingView();
                    }

                    @Override
                    public void onFail() {
                        volunteerFragmentView.getVolunteersError();
                        volunteerFragmentView.hideLoadingView();
                    }
                });
    }

    /**
     * 报名志愿活动
     *
     * @param userId  用户ID
     * @param volName 活动名称
     * @param volTime 活动时间
     */
    public void applyVolunteers(String userId, String volName, String volTime) {
        volunteerFragmentView.showLoadingView();
        BaseApi.request(BaseApi.createApi(IActivityApi.class).applyVolunteers(userId, volName, volTime),
                new BaseApi.IResponseListener<VolunteerBean>() {
                    @Override
                    public void onSuccess(VolunteerBean data) {
                        Log.e("zkp", data.getStatus() + "");
                        if (data.getStatus() == 200) {
                            //成功
                            volunteerFragmentView.applyVolunteersSuccess();
                        } else {
                            //失败
                            volunteerFragmentView.applyVolunteersError();
                        }
                        volunteerFragmentView.setData(data);
                        volunteerFragmentView.hideLoadingView();
                    }

                    @Override
                    public void onFail() {
                        volunteerFragmentView.applyVolunteersError();
                        volunteerFragmentView.hideLoadingView();
                    }
                });
    }

    /**
     * 取消志愿活动的报名
     *
     * @param userId  用户ID
     * @param volName 活动名称
     * @param volTime 活动时间
     */
    public void cancelApplyVolunteers(String userId, String volName, String volTime) {
        volunteerFragmentView.showLoadingView();
        BaseApi.request(BaseApi.createApi(IActivityApi.class).cancelApplyVolunteers(userId, volName, volTime),
                new BaseApi.IResponseListener<VolunteerBean>() {
                    @Override
                    public void onSuccess(VolunteerBean data) {
                        Log.e("zkp", data.getStatus() + "");
                        if (data.getStatus() == 200) {
                            //成功
                            volunteerFragmentView.cancelApplySuccess();
                        } else {
                            //失败
                            volunteerFragmentView.cancelApplyError();
                        }
                        volunteerFragmentView.setData(data);
                        volunteerFragmentView.hideLoadingView();
                    }

                    @Override
                    public void onFail() {
                        volunteerFragmentView.cancelApplyError();
                        volunteerFragmentView.hideLoadingView();
                    }
                });
    }

    public void detachView() {
        this.volunteerFragmentView = null;
    }
}
