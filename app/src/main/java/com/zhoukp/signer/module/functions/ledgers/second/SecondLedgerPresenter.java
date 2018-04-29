package com.zhoukp.signer.module.functions.ledgers.second;

import android.util.Log;

import com.zhoukp.signer.utils.BaseApi;

/**
 * @author zhoukp
 * @time 2018/3/28 14:27
 * @email 275557625@qq.com
 * @function
 */

public class SecondLedgerPresenter {

    private SecondLedgerView secondLedgerView;

    /**
     * 绑定视图
     *
     * @param secondLedgerView secondLedgerView
     */
    public void attachView(SecondLedgerView secondLedgerView) {
        if (this.secondLedgerView == null){
            this.secondLedgerView = secondLedgerView;
        }
    }

    /**
     * 获取对应学生对应年份的第二台账数据
     *
     * @param userId 用户ID
     * @param year   年份
     */
    public void getSecondLedger(String userId, int year) {
        if (secondLedgerView != null){
            secondLedgerView.showLoadingView();
        }

        BaseApi.request(BaseApi.createApi(ISecondLedgerApi.class).getSecondLedger(userId, year),
                new BaseApi.IResponseListener<SecondLedgerBean>() {
                    @Override
                    public void onSuccess(SecondLedgerBean data) {
                        Log.e("zkp", "getSecondLedger==" + data.getStatus());
                        if (secondLedgerView != null){
                            if (data.getStatus() == 200) {
                                secondLedgerView.getLedgerSuccess(data);
                            } else {
                                secondLedgerView.getLedgerError(data.getStatus());
                            }
                            secondLedgerView.hideLoadingView();
                        }
                    }

                    @Override
                    public void onFail() {
                        if (secondLedgerView != null){
                            secondLedgerView.getLedgerError(100);
                            secondLedgerView.hideLoadingView();
                        }
                    }
                });
    }

    /**
     * 解绑视图
     */
    public void detachView() {
        if (this.secondLedgerView != null){
            this.secondLedgerView = null;
        }
    }
}
