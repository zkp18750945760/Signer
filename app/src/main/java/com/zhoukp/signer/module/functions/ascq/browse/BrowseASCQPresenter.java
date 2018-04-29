package com.zhoukp.signer.module.functions.ascq.browse;

import android.util.Log;

import com.zhoukp.signer.module.managedevice.DeviceBean;
import com.zhoukp.signer.utils.BaseApi;

/**
 * @author zhoukp
 * @time 2018/4/14 22:16
 * @email 275557625@qq.com
 * @function
 */
public class BrowseASCQPresenter {

    private BrowseASCQView browseASCQView;

    public void attachView(BrowseASCQView browseASCQView) {
        if (this.browseASCQView == null) {
            this.browseASCQView = browseASCQView;
        }
    }

    /**
     * 获取综测成绩
     *
     * @param userId     用户ID
     * @param schoolYear 学年
     * @param userGrade  年级
     * @param userMajor  专业
     * @param userClazz  班级
     */
    public void getASCQScore(String userId, String schoolYear, String userGrade, String userMajor, String userClazz) {

        if (browseASCQView != null) {
            browseASCQView.showLoadingView();
        }

        BaseApi.request(BaseApi.createApi(IBrowseASCQApi.class).getASCQScore(userId, schoolYear, userGrade, userMajor, userClazz),
                new BaseApi.IResponseListener<ASCQScoreBean>() {
                    @Override
                    public void onSuccess(ASCQScoreBean data) {
                        Log.e("zkp", "getASCQScore==" + data.getStatus());
                        if (browseASCQView != null) {
                            if (data.getStatus() == 200) {
                                browseASCQView.getASCQSuccess(data);
                            } else if (data.getStatus() == 101) {
                                browseASCQView.getASCQUnChecked(data);
                            } else {
                                browseASCQView.getASCQError(data.getStatus());
                            }
                            browseASCQView.hideLoadingView();
                        }
                    }

                    @Override
                    public void onFail() {
                        if (browseASCQView != null) {
                            browseASCQView.getASCQError(100);
                            browseASCQView.hideLoadingView();
                        }
                    }
                });
    }

    /**
     * 确认综测成绩
     *
     * @param userId     用户ID
     * @param schoolYear 学年
     */
    public void confirmASCQ(String userId, String schoolYear) {

        if (browseASCQView != null) {
            browseASCQView.showLoadingView();
        }

        BaseApi.request(BaseApi.createApi(IBrowseASCQApi.class).confirmASCQ(userId, schoolYear),
                new BaseApi.IResponseListener<DeviceBean>() {
                    @Override
                    public void onSuccess(DeviceBean data) {
                        Log.e("zkp", "confirmASCQ==" + data.getStatus());
                        if (browseASCQView != null) {
                            if (data.getStatus() == 200) {
                                browseASCQView.confirmSuccess();
                            } else {
                                browseASCQView.confirmError(data.getStatus());
                            }
                            browseASCQView.hideLoadingView();
                        }
                    }

                    @Override
                    public void onFail() {
                        if (browseASCQView != null) {
                            browseASCQView.confirmError(100);
                            browseASCQView.hideLoadingView();
                        }
                    }
                });

    }

    /**
     * 请求修改综测成绩
     *
     * @param userId     用户ID
     * @param schoolYear 学年
     */
    public void modifyASCQ(String userId, String schoolYear) {

        if (browseASCQView != null) {
            browseASCQView.showLoadingView();
        }

        BaseApi.request(BaseApi.createApi(IBrowseASCQApi.class).modifyASCQ(userId, schoolYear),
                new BaseApi.IResponseListener<DeviceBean>() {
                    @Override
                    public void onSuccess(DeviceBean data) {
                        Log.e("zkp", "modifyASCQ==" + data.getStatus());
                        if (browseASCQView != null) {
                            if (data.getStatus() == 200) {
                                browseASCQView.modifySuccess();
                            } else {
                                browseASCQView.modifyError(data.getStatus());
                            }
                            browseASCQView.hideLoadingView();
                        }
                    }

                    @Override
                    public void onFail() {
                        if (browseASCQView != null) {
                            browseASCQView.modifyError(100);
                            browseASCQView.hideLoadingView();
                        }
                    }
                });

    }

    public void detachView() {
        if (this.browseASCQView != null) {
            this.browseASCQView = null;
        }
    }
}
