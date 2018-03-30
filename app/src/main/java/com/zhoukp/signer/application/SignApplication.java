package com.zhoukp.signer.application;

import android.app.Application;
import android.content.Context;

import com.zhoukp.signer.R;
import com.zhoukp.signer.module.crash.CrashHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * @author zhoukp
 * @time 2018/1/29 19:12
 * @email 275557625@qq.com
 * @function
 */

public class SignApplication extends Application {

    public static List<?> images;
    public static ArrayList<String> banners;

    private static final int TIMEOUT_READ = 15;
    private static final int TIMEOUT_CONNECTION = 15;
    private static OkHttpClient mOkHttpClient;

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        CrashHandler.getInstance().init(this);

        String[] urls = getResources().getStringArray(R.array.banner);
        List<String> list = Arrays.asList(urls);
        images = new ArrayList<>(list);

        banners = new ArrayList<>();
        banners.add("file:///android_asset/banner.png");
        banners.add("file:///android_asset/banner.png");
        banners.add("file:///android_asset/banner.png");
        banners.add("file:///android_asset/banner.png");

        context = getApplicationContext();
    }

    public static OkHttpClient genericClient() {

        if (mOkHttpClient != null)
            return mOkHttpClient;

        return mOkHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                .build();
    }

    public static Context getContext(){
        return context;
    }
}
