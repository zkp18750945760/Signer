package com.zhoukp.signer.module.login;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.zhoukp.signer.application.SignApplication;

public class UserUtil {
    private static UserUtil instance;
    private SharedPreferences sharedPreferences = SignApplication.getContext().getSharedPreferences("user", Context.MODE_PRIVATE);

    public UserUtil(){
    }

    public static UserUtil getInstance(){
        if (instance == null) {
            synchronized (UserUtil.class){
                instance = new UserUtil();
            }
        }
        return instance;
    }

    public void setUser(LoginBean.UserBean userBean){
        Gson gson = new Gson();
        String userJson = gson.toJson(userBean);
        sharedPreferences.edit().putString("user", userJson).apply();
    }

    public LoginBean.UserBean getUser(){
        Gson gson = new Gson();
        String userJson = sharedPreferences.getString("user", "");
        if (userJson.equals("")){
            return null;
        }
        LoginBean.UserBean userBean = gson.fromJson(userJson, LoginBean.UserBean.class);
        return userBean;
    }

    public void removeUser(){
        sharedPreferences.edit().remove("user").apply();
    }
}
