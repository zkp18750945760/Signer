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
import com.zhoukp.signer.module.chose.SelectSchoolYearActivity;
import com.zhoukp.signer.module.chose.SelectTermActivity;
import com.zhoukp.signer.module.chose.SelectWeekActivity;
import com.zhoukp.signer.module.login.UserUtil;
import com.zhoukp.signer.utils.AssetsHelper;
import com.zhoukp.signer.utils.WindowUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author zhoukp
 * @time 2018/2/3 19:54
 * @email 275557625@qq.com
 * @function 支书会议页面
 */

public class MeetingActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int YEAR = 1;
    private static final int TERM = 2;
    private static final int WEEK = 3;

    @Bind(R.id.ivSearch)
    ImageView ivSearch;
    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.tvYear)
    TextView tvYear;
    @Bind(R.id.tvTerm)
    TextView tvTerm;
    @Bind(R.id.tvWeek)
    TextView tvWeek;
    @Bind(R.id.listView)
    ListView listView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtils.getStatusBarHeight(this);
        WindowUtils.setTransluteWindow(this);

        setContentView(R.layout.activity_convention);

        ButterKnife.bind(this);

        initVariates();

        initEvents();
    }

    private void initVariates() {
        ConvertionBean bean = new Gson().fromJson(AssetsHelper.readAssetsTxt(MeetingActivity.this, "convertion_json"), ConvertionBean.class);
        ConvertionListViewAdapter adapter = new ConvertionListViewAdapter(MeetingActivity.this, bean);
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
                intent = new Intent(MeetingActivity.this, SelectSchoolYearActivity.class);
                intent.putExtra("type", "schoolYear");
                intent.putExtra("userId", UserUtil.getInstance().getUser().getUserId());
                startActivityForResult(intent, YEAR);
                break;
            case R.id.tvTerm:
                intent = new Intent(MeetingActivity.this, SelectTermActivity.class);
                intent.putExtra("type", "term");
                startActivityForResult(intent, TERM);
                break;
            case R.id.tvWeek:
                intent = new Intent(MeetingActivity.this, SelectWeekActivity.class);
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
