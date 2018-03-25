package com.zhoukp.signer.module.home;

/**
 * @author zhoukp
 * @time 2018/3/22 20:48
 * @email 275557625@qq.com
 * @function
 */

public interface HomePagerView {
    //显示LoadingView
    void showLoadingView();

    //隐藏LoadingView
    void hideLoadingView();

    //获取日程成功
    void getScheduleSuccess(ScheduleBean bean);

    //获取日程失败
    void getScheduleError(int status);

    //获取banner成功
    void getBannersSuccess(BannerBean bean);

    //获取banner失败
    void getBannersError(int status);
}
