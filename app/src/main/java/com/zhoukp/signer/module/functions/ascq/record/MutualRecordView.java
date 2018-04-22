package com.zhoukp.signer.module.functions.ascq.record;

import com.zhoukp.signer.module.functions.ascq.mutual.IsMutualMemberBean;

/**
 * @author zhoukp
 * @time 2018/4/15 13:59
 * @email 275557625@qq.com
 * @function
 */
public interface MutualRecordView {

    void showLoadingView();

    void hideLoadingView();

    void isMemberSuccess(IsMutualMemberBean bean);

    void isMemberError(int status);

    void getRecordSuccess(MutualRecordBean bean);

    void getRecordError(int status);

}
