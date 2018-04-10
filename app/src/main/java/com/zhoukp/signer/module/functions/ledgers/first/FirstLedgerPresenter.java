package com.zhoukp.signer.module.functions.ledgers.first;

import android.util.Log;

import com.zhoukp.signer.utils.BaseApi;

/**
 * @author zhoukp
 * @time 2018/4/9 13:21
 * @email 275557625@qq.com
 * @function
 */
public class FirstLedgerPresenter {

    private FirstLedgerView firstLedgerView;

    public void attachView(FirstLedgerView firstLedgerView) {
        this.firstLedgerView = firstLedgerView;
    }


    /**
     * 获取第一台账
     *
     * @param userId 用户ID
     * @param grade  年级
     * @param major  专业
     * @param clazz  班级
     */
    public void getFirstLedger(String userId, String grade, String major, String clazz) {

        firstLedgerView.showLoadingView();

        BaseApi.request(BaseApi.createApi(IFirstLedgerApi.class).getFirstLedger(userId, grade, major, clazz),
                new BaseApi.IResponseListener<FirstLedgerBean>() {
                    @Override
                    public void onSuccess(FirstLedgerBean data) {
                        Log.e("zkp", "getFirstLedger==" + data.getStatus());
                        if (data.getStatus() == 200) {
                            firstLedgerView.getFirstLedgerSuccess(data);
                        } else {
                            firstLedgerView.getFirstLedgerError(data.getStatus());
                        }
                        firstLedgerView.hideLoadingView();
                    }

                    @Override
                    public void onFail() {
                        firstLedgerView.getFirstLedgerError(100);
                        firstLedgerView.hideLoadingView();
                    }
                });

    }

    public void detachView() {
        if (firstLedgerView != null) {
            this.firstLedgerView = null;
        }
    }
}
