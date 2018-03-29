package com.zhoukp.signer.module.functions.ledgers.second;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.zhoukp.signer.R;
import com.zhoukp.signer.activity.SelectTimeActivity;
import com.zhoukp.signer.fragment.BaseFragment;
import com.zhoukp.signer.module.chose.SelectYearActivity;
import com.zhoukp.signer.module.functions.ledgers.scanxls.ProgressDialog;
import com.zhoukp.signer.module.login.UserUtil;
import com.zhoukp.signer.utils.TimeUtils;
import com.zhoukp.signer.utils.ToastUtil;

/**
 * @author zhoukp
 * @time 2018/2/4 17:56
 * @email 275557625@qq.com
 * @function 第一台账页面
 */

public class SecondLedgerFragemnt extends BaseFragment implements View.OnClickListener, SecondLedgerView {

    private static final int YEAR = 1;

    private TextView tvYear;
    private ListView listView;

    private ProgressDialog dialog;
    private ListViewAdapter adapter;
    private SecondLedgerPresenter presenter;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.pager_secondledger, null);
        tvYear = view.findViewById(R.id.tvYear);
        tvYear.setText(TimeUtils.getCurrentYear() + "年");
        listView = view.findViewById(R.id.listView);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        presenter = new SecondLedgerPresenter();
        presenter.attachView(this);

        initEvents();
    }

    private void initEvents() {
        tvYear.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tvYear:
                intent = new Intent(context, SelectYearActivity.class);
                intent.putExtra("type", "year");
                intent.putExtra("userId", UserUtil.getInstance().getUser().getUserId());
                startActivityForResult(intent, YEAR);
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getSecondLedger(UserUtil.getInstance().getUser().getUserId(),
                Integer.parseInt(tvYear.getText().toString().replace("年", "")));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            switch (requestCode) {
                case YEAR:
                    String year = data.getStringExtra("year");
                    if (!TextUtils.isEmpty(year)) {
                        tvYear.setText(year + "年");
                        presenter.getSecondLedger(UserUtil.getInstance().getUser().getUserId(),
                                Integer.parseInt(year));
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void showLoadingView() {
        if (dialog == null) {
            dialog = new ProgressDialog(context);
        }
        dialog.showMessage("正在加载");
    }

    @Override
    public void hideLoadingView() {
        if (dialog == null) {
            dialog = new ProgressDialog(context);
        }
        dialog.dismiss();
    }

    @Override
    public void getLedgerSuccess(SecondLedgerBean bean) {
        adapter = new ListViewAdapter(context, bean);
        listView.setAdapter(adapter);
    }

    @Override
    public void getLedgerError(int status) {
        switch (status) {
            case 100:
                ToastUtil.showToast(context, "请求失败");
                break;
            case 101:
                ToastUtil.showToast(context, "班长还未上传数据哦~");
                break;
            case 102:
                ToastUtil.showToast(context, "数据库IO错误");
                break;
        }
    }
}
