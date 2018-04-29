package com.zhoukp.signer.module.home;

import android.util.Log;

import com.zhoukp.signer.utils.BaseApi;

/**
 * @author zhoukp
 * @time 2018/3/22 20:49
 * @email 275557625@qq.com
 * @function HomePagerPresenter
 */

public class HomePagerPresenter {
    private HomePagerView homePagerView;

    /**
     * 绑定视图
     *
     * @param homePagerView homePagerView
     */
    public void attachView(HomePagerView homePagerView) {
        if (this.homePagerView == null) {
            this.homePagerView = homePagerView;
        }
    }


    public void getAllSchedule(String userId) {

        if (homePagerView != null) {
            homePagerView.showLoadingView();
        }

        BaseApi.request(BaseApi.createApi(IHomePagerApi.class).getAllSchedule(userId),
                new BaseApi.IResponseListener<ScheduleBean>() {
                    @Override
                    public void onSuccess(ScheduleBean data) {
                        Log.e("zkp", data.getStatus() + "");
                        if (homePagerView != null){
                            if (data.getStatus() == 200) {
                                homePagerView.getScheduleSuccess(data);
                            } else {
                                homePagerView.getScheduleError(data.getStatus());
                            }
                            homePagerView.hideLoadingView();
                        }
                    }

                    @Override
                    public void onFail() {
                        if (homePagerView != null){
                            homePagerView.getScheduleError(100);
                            homePagerView.hideLoadingView();
                        }
                    }
                });
    }

    public void getBanners() {

        if (homePagerView != null){
            homePagerView.showLoadingView();
        }

        BaseApi.request(BaseApi.createApi(IHomePagerApi.class).getBanners(),
                new BaseApi.IResponseListener<BannerBean>() {
                    @Override
                    public void onSuccess(BannerBean data) {
                        Log.e("zkp", data.getStatus() + "");
                        if (homePagerView != null){
                            if (data.getStatus() == 200) {
                                homePagerView.getBannersSuccess(data);
                            } else {
                                homePagerView.getBannersError(data.getStatus());
                            }
                            homePagerView.hideLoadingView();
                        }
                    }

                    @Override
                    public void onFail() {
                        if (homePagerView != null){
                            homePagerView.getBannersError(100);
                            homePagerView.hideLoadingView();
                        }
                    }
                });
    }

    /**
     * 解绑视图
     */
    public void detach() {
        if (this.homePagerView != null) {
            this.homePagerView = null;
        }
    }
}
