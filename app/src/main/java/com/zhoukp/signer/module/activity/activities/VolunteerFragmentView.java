package com.zhoukp.signer.module.activity.activities;

import com.zhoukp.signer.module.activity.VolunteerBean;

/**
 * @author zhoukp
 * @time 2018/3/19 15:24
 * @email 275557625@qq.com
 * @function
 */

public interface VolunteerFragmentView {
    //显示LoadingView
    void showLoadingView();

    //隐藏LoadingView
    void hideLoadingView();

    //获取所有志愿活动成功
    void getVolunteersSuccess();

    void getData(VolunteerBean bean);

    //获取所有志愿活动失败
    void getVolunteersError();

    //报名志愿活动成功
    void applyVolunteersSuccess();

    //报名志愿活动失败
    void applyVolunteersError();

    //取消志愿活动报名成功
    void cancelApplySuccess();

    //取消志愿活动报名失败
    void cancelApplyError();

    //报名/取消报名成功后刷新数据适配器
    void setData(VolunteerBean data);
}
