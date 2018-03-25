package com.zhoukp.signer.module.activity.activities;

import android.util.Log;

import com.zhoukp.signer.module.activity.ActivityBean;
import com.zhoukp.signer.module.activity.IActivityApi;
import com.zhoukp.signer.utils.BaseApi;

/**
 * @author zhoukp
 * @time 2018/3/19 16:09
 * @email 275557625@qq.com
 * @function
 */

public class ActivityFragmentPresenter {

    private ActivityFragmentView activityFragmentView;

    public void attachView(ActivityFragmentView activityFragmentView) {
        this.activityFragmentView = activityFragmentView;
    }

    /**
     * 获取所有文体活动
     *
     * @param userId 用户ID
     */
    public void getActivities(String userId) {
        activityFragmentView.showLoadingView();
        BaseApi.request(BaseApi.createApi(IActivityApi.class).getActivities(userId),
                new BaseApi.IResponseListener<ActivityBean>() {

                    @Override
                    public void onSuccess(ActivityBean data) {
                        Log.e("zkp", data.getStatus() + "");
                        if (data.getStatus() == 200) {
                            //成功
                            activityFragmentView.getActivitiesSuccess();
                        } else {
                            //失败
                            activityFragmentView.getActivitiesError();
                        }
                        activityFragmentView.getData(data);
                        activityFragmentView.hideLoadingView();
                    }

                    @Override
                    public void onFail() {
                        activityFragmentView.getActivitiesError();
                        activityFragmentView.hideLoadingView();
                    }
                });
    }

    /**
     * 报名文体活动
     *
     * @param userId  用户ID
     * @param actName 活动名称
     * @param actTime 活动时间
     */
    public void applyActivities(String userId, String actName, String actTime) {
        activityFragmentView.showLoadingView();
        BaseApi.request(BaseApi.createApi(IActivityApi.class).applyActivities(userId, actName, actTime),
                new BaseApi.IResponseListener<ActivityBean>() {
                    @Override
                    public void onSuccess(ActivityBean data) {
                        Log.e("zkp", data.getStatus() + "");
                        if (data.getStatus() == 200) {
                            //成功
                            activityFragmentView.applyActivitiesSuccess();
                        } else {
                            //失败
                            activityFragmentView.applyActivitiesError();
                        }
                        activityFragmentView.setData(data);
                        activityFragmentView.hideLoadingView();
                    }

                    @Override
                    public void onFail() {
                        activityFragmentView.applyActivitiesError();
                        activityFragmentView.hideLoadingView();
                    }
                });
    }

    /**
     * 取消报名文体活动
     *
     * @param userId  用户ID
     * @param actName 活动名称
     * @param actTime 活动时间
     */
    public void cancelApplyActivities(String userId, String actName, String actTime) {
        activityFragmentView.showLoadingView();
        BaseApi.request(BaseApi.createApi(IActivityApi.class).cancelApplyActivities(userId, actName, actTime),
                new BaseApi.IResponseListener<ActivityBean>() {
            @Override
            public void onSuccess(ActivityBean data) {
                Log.e("zkp", data.getStatus() + "");
                if (data.getStatus() == 200) {
                    //成功
                    activityFragmentView.cancelApplySuccess();
                } else {
                    //失败
                    activityFragmentView.cancelApplyError();
                }
                activityFragmentView.setData(data);
                activityFragmentView.hideLoadingView();
            }

            @Override
            public void onFail() {
                activityFragmentView.cancelApplyError();
                activityFragmentView.hideLoadingView();
            }
        });
    }

    public void detachView() {
        this.activityFragmentView = null;
    }
}
