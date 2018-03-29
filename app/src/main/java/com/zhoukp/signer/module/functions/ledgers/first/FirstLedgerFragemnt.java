package com.zhoukp.signer.module.functions.ledgers.first;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhoukp.signer.R;
import com.zhoukp.signer.adapter.FirstLedgerListViewAdapter;
import com.zhoukp.signer.bean.FirstLedgerBean;
import com.zhoukp.signer.fragment.BaseFragment;
import com.zhoukp.signer.module.chose.SelectSchoolYearActivity;
import com.zhoukp.signer.module.chose.SelectTermActivity;
import com.zhoukp.signer.module.chose.SelectWeekActivity;
import com.zhoukp.signer.module.login.UserUtil;
import com.zhoukp.signer.utils.AssetsHelper;

/**
 * @author zhoukp
 * @time 2018/2/4 17:56
 * @email 275557625@qq.com
 * @function 第一台账页面
 */

public class FirstLedgerFragemnt extends BaseFragment implements View.OnClickListener {

    private static final int YEAR = 1;
    private static final int TERM = 2;
    private static final int WEEK = 3;

    private TextView tvYear, tvTerm, tvWeek;
    private ListView listView;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.pager_firstledger, null);
        tvYear = view.findViewById(R.id.tvYear);
        tvTerm = view.findViewById(R.id.tvTerm);
        tvWeek = view.findViewById(R.id.tvWeek);
        listView = view.findViewById(R.id.listView);
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        FirstLedgerBean bean = new Gson().fromJson(AssetsHelper.readAssetsTxt(context, "firstledger_json"), FirstLedgerBean.class);
        FirstLedgerListViewAdapter adapter = new FirstLedgerListViewAdapter(context, bean);
        listView.setAdapter(adapter);

        initEvents();
    }

    private void initEvents() {
        tvYear.setOnClickListener(this);
        tvTerm.setOnClickListener(this);
        tvWeek.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tvYear:
                intent = new Intent(context, SelectSchoolYearActivity.class);
                intent.putExtra("type", "schoolYear");
                intent.putExtra("userId", UserUtil.getInstance().getUser().getUserId());
                startActivityForResult(intent, YEAR);
                break;
            case R.id.tvTerm:
                intent = new Intent(context, SelectTermActivity.class);
                intent.putExtra("type", "term");
                startActivityForResult(intent, TERM);
                break;
            case R.id.tvWeek:
                intent = new Intent(context, SelectWeekActivity.class);
                intent.putExtra("type", "week");
                startActivityForResult(intent, WEEK);
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
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
