package com.zhoukp.signer.module.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhoukp.signer.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhoukp
 * @time 2018/4/2 13:44
 * @email 275557625@qq.com
 * @function
 */
public class LauncherActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.ivLogo)
    ImageView ivLogo;
    @BindView(R.id.tvSkip)
    TextView tvSkip;

    private AdCountDownTimer timer;

    private Handler handler = new Handler();

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            startActivity(new Intent(LauncherActivity.this, SplashActivity.class));
            if (timer != null){
                timer.cancel();
            }
            finish();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置没有标题栏(状态栏)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置页面全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_lanucher);

        ButterKnife.bind(this);

        tvSkip.setText("5s 跳过");
        timer = new AdCountDownTimer(5000, 1000);
        timer.start();
        handler.postDelayed(runnable, 5000);

        tvSkip.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSkip:
                if (timer != null) {
                    timer.cancel();
                }
                handler.removeCallbacks(runnable);
                handler.post(runnable);
                break;
        }
    }

    class AdCountDownTimer extends CountDownTimer {
        /**
         * @param millisInFuture    表示以「 毫秒 」为单位倒计时的总数
         *                          例如 millisInFuture = 1000 表示1秒
         * @param countDownInterval 表示 间隔 多少微秒 调用一次 onTick()
         *                          例如: countDownInterval = 1000 ;
         *                          表示每 1000 毫秒调用一次 onTick()
         */
        public AdCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        public void onFinish() {
            tvSkip.setText("0s 跳过");
        }

        public void onTick(long millisUntilFinished) {
            tvSkip.setText(millisUntilFinished / 1000 + "s 跳过");
        }
    }

}
