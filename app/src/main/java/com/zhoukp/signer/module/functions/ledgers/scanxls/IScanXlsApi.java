package com.zhoukp.signer.module.functions.ledgers.scanxls;

import com.zhoukp.signer.module.functions.sign.SponsorSignBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author zhoukp
 * @time 2018/3/28 12:19
 * @email 275557625@qq.com
 * @function
 */

public interface IScanXlsApi {

    /**
     * 上传某个月份的第二台账文件
     *
     * @param month 月份
     * @param body  body
     * @return
     */
    @POST("UploadLedger?")
    Observable<SponsorSignBean> uploadLedger(
            @Query("month") int month,
            @Body RequestBody body
    );
}
