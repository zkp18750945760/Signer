package com.zhoukp.signer.module.functions.sign;

/**
 * @author zhoukp
 * @time 2018/3/15 22:32
 * @email 275557625@qq.com
 * @function SignView
 */

public interface SignView {
    //隐藏loading view
    void hideLoadingView();

    //显示loading view
    void showLoadingView();

    //获取课程成功
    void getSignEventsSuccess(SignEventsBean bean);

    //获取课程失败
    void getSignEventsError(int status);

    //发起签到成功
    void sponsorSignSuccess();

    //发起签到失败
    void sponsorSignError(int status);

    //获取事项的发起签到状态成功
    void getEventsSponsorSignStatusSuccess(int status);

    //获取事项的发起签到状态失败
    void getEventsSponsorSignStatusError(int status);

    //签到成功
    void signSuccess();

    //签到失败
    void signError(int status);

    //获取某个事项已签到人的头像成功
    void getSignedHeadIconsSuccess(SignedHeadIconsBean bean);

    //获取某个事项已签到人的头像失败
    void getSignedHeadIconsError(int status);
}
