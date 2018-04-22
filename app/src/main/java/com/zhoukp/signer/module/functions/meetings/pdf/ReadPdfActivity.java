package com.zhoukp.signer.module.functions.meetings.pdf;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.TrafficStats;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.zhoukp.signer.R;
import com.zhoukp.signer.utils.Constant;
import com.zhoukp.signer.utils.ToastUtil;
import com.zhoukp.signer.utils.WindowUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @author zhoukp
 * @time 2018/4/9 22:59
 * @email 275557625@qq.com
 * @function
 */
public class ReadPdfActivity extends AppCompatActivity implements View.OnClickListener, ReadPdfView {

    /**
     * 显示网络速度
     */
    private static final int SHOW_SPEED = 1;

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvContent)
    TextView tvContent;
    @BindView(R.id.pdfView)
    PDFView pdfView;
    @BindView(R.id.ll_loading)
    RelativeLayout loading;
    @BindView(R.id.rl_speed)
    LinearLayout rlSpeed;
    @BindView(R.id.tvNetspeed)
    TextView tvNetspeed;


    private String pdfUrl;
    private String pdfPath;
    private ReadPdfPresenter presenter;
    private long lastTotalRxBytes = 0;
    private long lastTimeStamp = 0;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SHOW_SPEED://显示网速
                    //显示网络速
                    tvNetspeed.setText(getNetSpeed(getApplicationContext()));
                    //2.每两秒更新一次
                    handler.removeMessages(SHOW_SPEED);
                    handler.sendEmptyMessageDelayed(SHOW_SPEED, 2000);
                    break;
            }
        }
    };

    /**
     * 得到网络速度
     * 每隔两秒调用一次
     *
     * @param context 上下文
     * @return 100kb/s
     */
    public String getNetSpeed(Context context) {
        String netSpeed;
        long nowTotalRxBytes = TrafficStats.getUidRxBytes(context.getApplicationInfo().uid) == TrafficStats.UNSUPPORTED ?
                0 : (TrafficStats.getTotalRxBytes() / 1024);//转为KB;
        long nowTimeStamp = System.currentTimeMillis();
        long speed = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 / (nowTimeStamp - lastTimeStamp));//毫秒转换

        lastTimeStamp = nowTimeStamp;
        lastTotalRxBytes = nowTotalRxBytes;
        netSpeed = String.valueOf(speed) + " kb/s";
        return netSpeed;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtils.setTransluteWindow(this);

        setContentView(R.layout.activity_read_pdf);
        ButterKnife.bind(this);

        tvContent.setText(getIntent().getStringExtra("content"));

        initVariates();

        initEvents();

    }

    private void initVariates() {
        pdfUrl = getIntent().getStringExtra("pdfUrl");
        pdfPath = pdfUrl.substring(5);

        presenter = new ReadPdfPresenter();
        presenter.attachView(this);


        File file = new File(Constant.appFilePath, pdfPath);
        if (!file.exists()) {
            //开始更新网络速度
            handler.sendEmptyMessage(SHOW_SPEED);
            presenter.downloadPdf(Constant.BaseUrl + pdfUrl, file);
            Log.e("zkp", "1");
        } else {
            Log.e("zkp", "2");
            loading.setVisibility(View.GONE);
            openPdf(file);
        }
    }

    private void initEvents() {
        ivBack.setOnClickListener(this);
    }

    private void openPdf(File file) {
        pdfView.fromFile(file)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                .enableAnnotationRendering(true)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                //关闭当前页面
                finish();
                break;
        }
    }

    @Override
    public void showLoadingView() {
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        loading.setVisibility(View.GONE);
    }

    @Override
    public void downloadSuccess(File file) {
        openPdf(file);
    }

    @Override
    public void downloadError() {
        ToastUtil.showToast(this, "下载文件失败");
    }
}
