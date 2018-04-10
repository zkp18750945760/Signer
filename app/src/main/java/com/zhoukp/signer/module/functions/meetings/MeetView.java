package com.zhoukp.signer.module.functions.meetings;


/**
 * @author zhoukp
 * @time 2018/4/9 22:14
 * @email 275557625@qq.com
 * @function
 */
public interface MeetView {

    //显示LoadingView
    void showLoadingView();

    //隐藏LoadingView
    void hideLoadingView();

    //获取支书会议记录成功
    void getDisscussionSuccess(MeetBean bean);

    //获取支书会议记录失败
    void getDiscyssionError(int status);

    //刷新完毕
    void refreshComplete();

    void updateReadSuccess(UpdateReadBean bean, int position);

    void updateReadError(int status);
}
