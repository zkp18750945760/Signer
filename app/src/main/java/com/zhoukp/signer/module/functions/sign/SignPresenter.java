package com.zhoukp.signer.module.functions.sign;


import android.util.Log;

import com.zhoukp.signer.utils.BaseApi;

/**
 * @author zhoukp
 * @time 2018/3/15 22:33
 * @email 275557625@qq.com
 * @function SignPresenter
 */

public class SignPresenter {
    private SignView signView;

    /**
     * 绑定视图
     *
     * @param signView SignView
     */
    public void attachView(SignView signView) {
        this.signView = signView;
    }

    /**
     * 获取对应用户的所有签到事项
     * 课程(上课前10分钟-上课后5分钟)、会议(开会前15分钟-开会后10分钟)
     *
     * @param userId 用户ID
     */
    public void getSignEvents(String userId) {
        signView.showLoadingView();

        BaseApi.request(BaseApi.createApi(ISignApi.class).getSignEvents(userId),
                new BaseApi.IResponseListener<SignEventsBean>() {
                    @Override
                    public void onSuccess(SignEventsBean data) {
                        Log.e("zkp", data.getStatus() + "");
                        if (data.getStatus() == 200) {
                            signView.getSignEventsSuccess(data);
                        } else {
                            signView.getSignEventsError(data.getStatus());
                        }
                        signView.hideLoadingView();
                    }

                    @Override
                    public void onFail() {
                        signView.getSignEventsError(100);
                        signView.hideLoadingView();
                    }
                });
    }

    /**
     * 获取事项的发起签到状态
     *
     * @param userId  用户ID
     * @param content 事项
     */
    public void getEventsSponsorSignStatus(String userId, String content) {
        signView.showLoadingView();

        BaseApi.request(BaseApi.createApi(ISignApi.class).GetEventsSponsorSignStatus(userId, content),
                new BaseApi.IResponseListener<SponsorSignBean>() {
                    @Override
                    public void onSuccess(SponsorSignBean data) {
                        Log.e("zkp", data.getStatus() + "");
                        if (data.getStatus() == 101) {
                            signView.getEventsSponsorSignStatusError(data.getStatus());
                        } else {
                            signView.getEventsSponsorSignStatusSuccess(data.getStatus());
                        }
                        signView.hideLoadingView();
                    }

                    @Override
                    public void onFail() {
                        signView.getEventsSponsorSignStatusError(100);
                        signView.hideLoadingView();
                    }
                });
    }

    /**
     * 发起签到
     *
     * @param userId    用户ID
     * @param grade     用户年级
     * @param major     用户专业
     * @param clazz     用户班级
     * @param type      签到类型
     * @param time      事项开始的时间
     * @param content   事项主题
     * @param longitude 经度
     * @param latitude  纬度
     */
    public void sponsorSign(String userId, String grade, String major, String clazz, int type,
                            String time, String content, double longitude, double latitude, float radius) {

        signView.showLoadingView();

        BaseApi.request(BaseApi.createApi(ISignApi.class).sponsorSign(userId, grade, major, clazz, type,
                time, content, longitude, latitude, radius), new BaseApi.IResponseListener<SponsorSignBean>() {
            @Override
            public void onSuccess(SponsorSignBean data) {
                Log.e("zkp", data.getStatus() + "");
                if (data.getStatus() == 200) {
                    signView.sponsorSignSuccess();
                } else {
                    signView.sponsorSignError(data.getStatus());
                }
                signView.hideLoadingView();
            }

            @Override
            public void onFail() {
                signView.sponsorSignError(100);
                signView.hideLoadingView();
            }
        });
    }

    /**
     * 签到
     *
     * @param userId    用户ID
     * @param grade     用户年级
     * @param major     用户专业
     * @param clazz     用户班级
     * @param type      签到类型
     * @param time      事项开始的时间
     * @param content   事项主题
     * @param longitude 经度
     * @param latitude  纬度
     */
    public void sign(String userId, String grade, String major, String clazz, int type,
                     String time, String content, double longitude, double latitude, float radius) {

        signView.showLoadingView();

        BaseApi.request(BaseApi.createApi(ISignApi.class).sign(userId, grade, major, clazz, type, time,
                content, longitude, latitude, radius), new BaseApi.IResponseListener<SponsorSignBean>() {
            @Override
            public void onSuccess(SponsorSignBean data) {
                Log.e("zkp", data.getStatus() + "");
                if (data.getStatus() == 200) {
                    signView.signSuccess();
                } else {
                    signView.signError(data.getStatus());
                }
                signView.hideLoadingView();
            }

            @Override
            public void onFail() {
                signView.signError(100);
                signView.hideLoadingView();
            }
        });
    }

    /**
     * 获取某个事项已签到人的头像
     *
     * @param userId  用户ID
     * @param grade   用户年级
     * @param major   用户专业
     * @param clazz   用户班级
     * @param type    事项类型
     * @param time    事项开始时间
     * @param content 事项主题
     */
    public void getSignedHeadIcons(String userId, String grade, String major,
                                   String clazz, int type, String time, String content) {

        signView.showLoadingView();

        BaseApi.request(BaseApi.createApi(ISignApi.class).getSignedHeadIcons(userId, grade, major, clazz,
                type, time, content), new BaseApi.IResponseListener<SignedHeadIconsBean>() {
            @Override
            public void onSuccess(SignedHeadIconsBean data) {
                Log.e("zkp", data.getStatus() + "");
                if (data.getStatus() == 200) {
                    signView.getSignedHeadIconsSuccess(data);
                } else {
                    signView.getSignedHeadIconsError(data.getStatus());
                }
                signView.hideLoadingView();
            }

            @Override
            public void onFail() {
                signView.getSignedHeadIconsError(100);
                signView.hideLoadingView();
            }
        });

    }


    /**
     * 解绑视图
     */
    public void detachView() {
        this.signView = null;
    }
}
