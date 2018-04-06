package com.zhoukp.signer.module.welcome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
/**
 * @author zhoukp
 * @time 2018/4/1 15:21
 * @email 275557625@qq.com
 * @function
 */
public class WelcomeHelper {
    public static final int DEFAULT_WELCOME_SCREEN_REQUEST = 1;

    private static final String KEY_WELCOME_SCREEN_STARTED = "com.zhoukp.signer.welcome.welcome_screen_started";

    private Activity mActivity;
    private Class<? extends WelcomeActivity> mActivityClass;
    private boolean welcomeScreenStarted = false;


    public WelcomeHelper(Activity activity, Class<? extends WelcomeActivity> activityClass) {
        mActivity = activity;
        mActivityClass = activityClass;
    }

    private boolean getWelcomeScreenStarted(Bundle savedInstanceState) {
        if (!welcomeScreenStarted) {
            welcomeScreenStarted = savedInstanceState != null && savedInstanceState.getBoolean(KEY_WELCOME_SCREEN_STARTED, false);
        }
        return welcomeScreenStarted;
    }

    private boolean shouldShow(Bundle savedInstanceState) {
        return !getWelcomeScreenStarted(savedInstanceState) &&
                !WelcomeSharedPreferencesHelper.welcomeScreenCompleted(mActivity, WelcomeUtils.getKey(mActivityClass));
    }


    public boolean show(Bundle savedInstanceState) {
        return show(savedInstanceState, DEFAULT_WELCOME_SCREEN_REQUEST);
    }


    public boolean show(Bundle savedInstanceState, int requestCode) {
        boolean shouldShow = shouldShow(savedInstanceState);
        if (shouldShow) {
            welcomeScreenStarted = true;
            startActivity(requestCode);
        }
        return shouldShow;
    }

    public void forceShow() {
        forceShow(DEFAULT_WELCOME_SCREEN_REQUEST);
    }

    public void forceShow(int requestCode) {
        startActivity(requestCode);
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(KEY_WELCOME_SCREEN_STARTED, welcomeScreenStarted);
    }

    private void startActivity(int requestCode) {
        Intent intent = new Intent(mActivity, mActivityClass);
        mActivity.startActivityForResult(intent, requestCode);
    }
}
