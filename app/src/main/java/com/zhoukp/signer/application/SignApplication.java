package com.zhoukp.signer.application;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;
import com.zhoukp.signer.R;
import com.zhoukp.signer.module.crash.CrashHandler;
import com.zhoukp.signer.module.network.NetworkConnectChangedReceiver;

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


    private static final int TIMEOUT_READ = 15;
    private static final int TIMEOUT_CONNECTION = 15;
    public static List<?> images;
    public static ArrayList<String> banners;
    private static OkHttpClient mOkHttpClient;

    private static Context context;


    private static SignApplication instance;

    private NetworkConnectChangedReceiver receiver;
    private boolean wifi;
    private boolean mobile;
    private boolean connected;
    private boolean enableWifi;

    public SignApplication() {
    }

    public static SignApplication getInstance() {
        return instance;
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

    public static Context getContext() {
        return context;
    }

    public boolean isWifi() {
        return wifi;
    }

    public void setWifi(boolean wifi) {
        this.wifi = wifi;
    }

    public boolean isMobile() {
        return mobile;
    }

    public void setMobile(boolean mobile) {
        this.mobile = mobile;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public boolean isEnableWifi() {
        return enableWifi;
    }

    public void setEnableWifi(boolean enableWifi) {
        this.enableWifi = enableWifi;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        //增加这句话
        //初始化X5内核
        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                //x5内核初始化完成回调接口，此接口回调并表示已经加载起来了x5，有可能特殊情况下x5内核加载失败，切换到系统内核。

            }

            @Override
            public void onViewInitFinished(boolean b) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.e("zkp", "加载内核是否成功:" + b);
            }
        });

        CrashHandler.getInstance().init(this);

        receiver = new NetworkConnectChangedReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        filter.addAction("android.net.wifi.STATE_CHANGE");
        //注册广播
        registerReceiver(receiver, filter);

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

    @Override
    public void onTerminate() {
        super.onTerminate();
        //取消注册广播
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }
}
