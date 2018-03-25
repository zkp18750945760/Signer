package com.zhoukp.signer.module.activity.activities;

import com.zhoukp.signer.module.activity.ActivityBean;

/**
 * @author zhoukp
 * @time 2018/3/19 15:24
 * @email 275557625@qq.com
 * @function
 */

public interface ActivityFragmentView {
    //显示LoadingView
    void showLoadingView();

    //隐藏LoadingView
    void hideLoadingView();

    //获取所有文体活动成功
    void getActivitiesSuccess();

    void getData(ActivityBean bean);

    //获取所有文体活动失败
    void getActivitiesError();

    //报名文体活动成功
    void applyActivitiesSuccess();

    //报名文体活动失败
    void applyActivitiesError();

    //取消报名成功
    void cancelApplySuccess();

    //取消报名失败
    void cancelApplyError();

    //报名文体活动成功后刷新数据
    void setData(ActivityBean data);
}
