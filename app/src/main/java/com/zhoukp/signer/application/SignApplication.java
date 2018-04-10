package com.zhoukp.signer.application;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;

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


    private static final int TIMEOUT_READ = 5;
    private static final int TIMEOUT_CONNECTION = 5;
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
