package com.zhoukp.signer.module.splash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;

import com.zhoukp.signer.R;
import com.zhoukp.signer.module.main.MainActivity;
import com.zhoukp.signer.module.welcome.BasicPage;
import com.zhoukp.signer.module.welcome.TitlePage;
import com.zhoukp.signer.module.welcome.WelcomeActivity;
import com.zhoukp.signer.module.welcome.WelcomeConfiguration;
import com.zhoukp.signer.utils.WindowUtils;

/**
 * @author zhoukp
 * @time 2018/4/1 15:05
 * @email 275557625@qq.com
 * @function
 */
public class SplashActivity extends WelcomeActivity {

    private String isSplash;

    @Override
    protected WelcomeConfiguration configuration() {
        return new WelcomeConfiguration.Builder(this)
                .defaultBackgroundColor(R.color.colorBtnPressed)
                .page(new TitlePage(R.drawable.logo,
                        "华侨大学计算机学院学工系统")
                )
                .page(new BasicPage(R.drawable.logo,
                        "综测上报",
                        "手机上报、审核综测")
                        .background(R.color.page2)
                )
                .page(new BasicPage(R.drawable.logo,
                        "科创统计",
                        "科创立项、获奖情况一览无余")
                        .background(R.color.page3)
                )
                .page(new BasicPage(R.drawable.logo,
                        "出勤签到",
                        "会议、课堂、活动一键签到")
                        .background(R.color.color_orange)
                )
                .page(new BasicPage(R.drawable.logo,
                        "台账查询",
                        "第一台账、第二台账随时可查")
                        .background(R.color.colorDarkOrange)
                )
                .swipeToDismiss(true)
                .build();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtils.getStatusBarHeight(this);
        WindowUtils.setTransluteWindow(this);
    }

    @Override
    protected void showOrToMain() {
        SharedPreferences sp = getSharedPreferences("HQUZkP", Context.MODE_PRIVATE);
        isSplash = sp.getString("isSplash", "");
        if (!TextUtils.isEmpty(isSplash)) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}
