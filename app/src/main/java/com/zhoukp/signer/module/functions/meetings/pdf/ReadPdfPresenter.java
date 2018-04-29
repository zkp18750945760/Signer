package com.zhoukp.signer.module.functions.meetings.pdf;

import android.util.Log;

import com.zhoukp.signer.utils.BaseApi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import okhttp3.ResponseBody;

/**
 * @author zhoukp
 * @time 2018/4/10 14:12
 * @email 275557625@qq.com
 * @function
 */
public class ReadPdfPresenter {

    private ReadPdfView readPdfView;

    public void attachView(ReadPdfView readPdfView) {
        if (this.readPdfView == null){
            this.readPdfView = readPdfView;
        }
    }


    public void downloadPdf(String url, final File file) {
        if (readPdfView != null){
            readPdfView.showLoadingView();
        }
        Log.e("zkp", url + "  \n" + file.getPath());

        BaseApi.request(BaseApi.createApi(IPdfApi.class).downloadPicFromNet(url),
                new BaseApi.IResponseListener<ResponseBody>() {
                    @Override
                    public void onSuccess(ResponseBody data) {
                        InputStream inputStream = data.byteStream();

                        if (!file.exists()){
                            file.getParentFile().mkdirs();
                        }

                        try {
                            byte[] buf = new byte[128];
                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                            int len;
                            while ((len = inputStream.read(buf)) != -1) {
                                fileOutputStream.write(buf, 0, len);
                            }
                            fileOutputStream.flush();
                            fileOutputStream.close();
                            inputStream.close();

                            if (readPdfView != null){
                                readPdfView.downloadSuccess(file);
                                readPdfView.hideLoadingView();
                            }

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail() {
                        if (readPdfView != null){
                            readPdfView.downloadError();
                            readPdfView.hideLoadingView();
                        }
                    }
                });
    }

    public void detachView() {
        if (this.readPdfView != null) {
            this.readPdfView = null;
        }
    }
}
