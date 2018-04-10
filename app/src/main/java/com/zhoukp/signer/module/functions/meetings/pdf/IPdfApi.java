package com.zhoukp.signer.module.functions.meetings.pdf;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * @author zhoukp
 * @time 2018/4/10 15:27
 * @email 275557625@qq.com
 * @function
 */
public interface IPdfApi {

    @GET
    Observable<ResponseBody> downloadPicFromNet(@Url String fileUrl);
}
