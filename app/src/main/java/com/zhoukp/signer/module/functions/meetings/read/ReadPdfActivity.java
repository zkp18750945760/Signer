package com.zhoukp.signer.module.functions.meetings.read;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.tencent.smtt.sdk.TbsReaderView;
import com.zhoukp.signer.R;
import com.zhoukp.signer.utils.Constant;
import com.zhoukp.signer.utils.WindowUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhoukp
 * @time 2018/4/20 21:52
 * @email 275557625@qq.com
 * @function
 */
public class ReadPdfActivity extends AppCompatActivity implements TbsReaderView.ReaderCallback, View.OnClickListener {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.webView)
    FrameLayout webView;

    private TbsReaderView tbsReaderView;

    private String path, fileName;
    private boolean result;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtils.setTransluteWindow(this);

        setContentView(R.layout.activity_read_pdf_word);
        ButterKnife.bind(this);

        path = getIntent().getStringExtra("path");
        fileName = getIntent().getStringExtra("fileName");
        System.out.println("path==" + path + ", fileName==" + fileName);

        initData();

        initEvents();
    }

    private void initData() {
        tbsReaderView = new TbsReaderView(this, this);
        webView.addView(tbsReaderView);

        Bundle bundle = new Bundle();
        bundle.putString("filePath", path);
        bundle.putString("tempPath", Constant.appFilePath);
        result = tbsReaderView.preOpen(parseFormat(fileName), false);
        if (result) {
            tbsReaderView.openFile(bundle);
        }
    }

    private void initEvents() {
        ivBack.setOnClickListener(this);
    }

    private String parseFormat(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tbsReaderView != null) {
            tbsReaderView.onStop();
        }
    }

    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                //关闭当前页面
                finish();
                break;
        }
    }
}
