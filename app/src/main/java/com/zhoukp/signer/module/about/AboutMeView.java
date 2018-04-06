package com.zhoukp.signer.module.about;

import com.zhoukp.signer.module.main.UpdateBean;

/**
 * @author zhoukp
 * @time 2018/4/6 23:27
 * @email 275557625@qq.com
 * @function
 */
public interface AboutMeView {

    void showLoadingView();

    void hideLoadingView();

    void getUpdateInfoSuccess(UpdateBean bean);

    void getUpdateInfoError(int status);
}
