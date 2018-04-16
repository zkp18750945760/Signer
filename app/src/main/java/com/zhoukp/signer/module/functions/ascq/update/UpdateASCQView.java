package com.zhoukp.signer.module.functions.ascq.update;

import com.zhoukp.signer.module.functions.ascq.mutual.IsMutualMemberBean;

/**
 * @author zhoukp
 * @time 2018/4/15 15:18
 * @email 275557625@qq.com
 * @function
 */
public interface UpdateASCQView {

    void showLoadingView();

    void hideLoadingView();

    void isMemberSuccess(IsMutualMemberBean bean);

    void isMemberError(int status);

    void getIdsSuccess(MutualIDsBean bean);

    void getIdsError(int status);

    void getASCQSuccess(UpdateASCQBean bean);

    void getASCQError(int status);

    void updateSuccess();

    void updateError(int status);
}
