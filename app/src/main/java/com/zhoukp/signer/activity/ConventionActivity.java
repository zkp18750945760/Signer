package com.zhoukp.signer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhoukp.signer.R;
import com.zhoukp.signer.adapter.ConvertionListViewAdapter;
import com.zhoukp.signer.bean.ConvertionBean;
import com.zhoukp.signer.utils.AssetsHelper;
import com.zhoukp.signer.utils.WindowUtils;

/**
 * @author zhoukp
 * @time 2018/2/3 19:54
 * @email 275557625@qq.com
 * @function
 */

public class ConventionActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int YEAR = 1;
    private static final int TERM = 2;
    private static final int WEEK = 3;

    private ImageView ivSearch, ivBack;
    private TextView tvYear, tvTerm, tvWeek;
    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtils.getStatusBarHeight(this);
        WindowUtils.setTransluteWindow(this);
        setContentView(R.layout.activity_convention);

        initViews();

        initVariates();

        initEvents();
    }

    private void initViews() {
        ivSearch = (ImageView) findViewById(R.id.ivSearch);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        tvYear = (TextView) findViewById(R.id.tvYear);
        tvTerm = (TextView) findViewById(R.id.tvTerm);
        tvWeek = (TextView) findViewById(R.id.tvWeek);
        listView = (ListView) findViewById(R.id.listView);
    }

    private void initVariates() {
        ConvertionBean bean = new Gson().fromJson(AssetsHelper.readAssetsTxt(ConventionActivity.this, "convertion_json"), ConvertionBean.class);
        ConvertionListViewAdapter adapter = new ConvertionListViewAdapter(ConventionActivity.this, bean);
        listView.setAdapter(adapter);
    }

    private void initEvents() {
        ivBack.setOnClickListener(this);
        tvYear.setOnClickListener(this);
        tvTerm.setOnClickListener(this);
        tvWeek.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.tvYear:
                intent = new Intent(ConventionActivity.this, SelectTimeActivity.class);
                intent.putExtra("type", "schoolYear");
                startActivityForResult(intent, YEAR);
                break;
            case R.id.tvTerm:
                intent = new Intent(ConventionActivity.this, SelectTimeActivity.class);
                intent.putExtra("type", "term");
                startActivityForResult(intent, TERM);
                break;
            case R.id.tvWeek:
                intent = new Intent(ConventionActivity.this, SelectTimeActivity.class);
                intent.putExtra("type", "week");
                startActivityForResult(intent, WEEK);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case YEAR:
                    String schoolYear = data.getStringExtra("schoolYear");
                    if (!TextUtils.isEmpty(schoolYear)) {
                        tvYear.setText(schoolYear);
                    }
                    break;
                case TERM:
                    String term = data.getStringExtra("term");
                    if (!TextUtils.isEmpty(term)) {
                        tvTerm.setText(term);
                    }
                    break;
                case WEEK:
                    String week = data.getStringExtra("week");
                    if (!TextUtils.isEmpty(week)) {
                        tvWeek.setText(week);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
