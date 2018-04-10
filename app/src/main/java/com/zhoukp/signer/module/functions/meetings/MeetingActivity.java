package com.zhoukp.signer.module.functions.meetings;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;
import com.zhoukp.signer.R;
import com.zhoukp.signer.module.chose.SelectSchoolYearActivity;
import com.zhoukp.signer.module.chose.SelectTermActivity;
import com.zhoukp.signer.module.chose.SelectWeekActivity;
import com.zhoukp.signer.module.functions.ledgers.scanxls.ProgressDialog;
import com.zhoukp.signer.module.functions.meetings.pdf.ReadPdfActivity;
import com.zhoukp.signer.module.login.UserUtil;
import com.zhoukp.signer.utils.ToastUtil;
import com.zhoukp.signer.utils.WindowUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author zhoukp
 * @time 2018/2/3 19:54
 * @email 275557625@qq.com
 * @function 支书会议页面
 */

public class MeetingActivity extends AppCompatActivity implements View.OnClickListener, MeetView {

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
    PullToRefreshListView listView;

    private MeetPresenter presenter;
    private MeetAdapter adapter;
    private ProgressDialog dialog;

    private ArrayList<MeetBean.DataBean> dataBeans;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtils.getStatusBarHeight(this);
        WindowUtils.setTransluteWindow(this);

        setContentView(R.layout.activity_convention);

        ButterKnife.bind(this);

        SoundPullEventListener<ListView> soundPullEventListener = new SoundPullEventListener<>(getApplication());
        soundPullEventListener.addSoundEvent(PullToRefreshBase.State.MANUAL_REFRESHING, R.raw.pull_event);
        soundPullEventListener.addSoundEvent(PullToRefreshBase.State.RESET, R.raw.reset_sound);
        soundPullEventListener.addSoundEvent(PullToRefreshBase.State.REFRESHING, R.raw.refreshing_sound);
        listView.setOnPullEventListener(soundPullEventListener);

        initVariates();

        initEvents();
    }

    private void initVariates() {

        dataBeans = new ArrayList<>();

        presenter = new MeetPresenter();
        presenter.attachView(this);
        presenter.getDiscussion(UserUtil.getInstance().getUser().getUserId());
    }

    private void initEvents() {
        ivBack.setOnClickListener(this);
        tvYear.setOnClickListener(this);
        tvTerm.setOnClickListener(this);
        tvWeek.setOnClickListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("zkp", position + "被点击");

                presenter.updateDiscussionRead(UserUtil.getInstance().getUser().getUserId(),
                        dataBeans.get(position - 1).getTheme(),
                        dataBeans.get(position - 1).getDate(),
                        position - 1);

                Intent intent = new Intent(MeetingActivity.this, ReadPdfActivity.class);
                intent.putExtra("pdfUrl", dataBeans.get(position - 1).getPdfUrl());
                intent.putExtra("content", adapter.getItem(position - 1).getTheme());
                startActivity(intent);
            }
        });
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

    @Override
    public void showLoadingView() {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.showMessage("加载中...");
        }
        dialog.show();
    }

    @Override
    public void hideLoadingView() {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.showMessage("加载中...");
        }
        dialog.dismiss();
    }

    @Override
    public void getDisscussionSuccess(final MeetBean bean) {
        if (bean.getData().size() == 0) {
            ToastUtil.showToast(this, "还没有会议记录哦");
            return;
        }

        listSort(bean.getData());
        dataBeans.clear();

        if (bean.getData().size() <= 10) {
            for (MeetBean.DataBean dataBean : bean.getData()) {
                dataBeans.add(dataBean);
            }
        } else {
            for (int i = 0; i < 10; i++) {
                dataBeans.add(bean.getData().get(i));
            }
        }

        adapter = new MeetAdapter(this, dataBeans, presenter);
        listView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                Log.e("zkp", "onPullDownToRefresh");
                //下拉刷新
                presenter.getDiscussion(UserUtil.getInstance().getUser().getUserId());
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                Log.e("zkp", "onPullUpToRefresh");
                //加载更多
                int start = listView.getChildCount();
                int end;
                if (bean.getData().size() >= start + 10) {
                    end = start + 10;
                } else {
                    end = bean.getData().size();
                }
                for (int i = start; i < end; i++) {
                    dataBeans.add(bean.getData().get(i));
                }
                listView.onRefreshComplete();
                adapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 对时间做排序，越接近现在的越靠前
     *
     * @param list list
     */
    private static void listSort(List<MeetBean.DataBean> list) {
        Collections.sort(list, new Comparator<MeetBean.DataBean>() {
            @Override
            public int compare(MeetBean.DataBean o1, MeetBean.DataBean o2) {
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date dt1 = format.parse(o1.getDate());
                    Date dt2 = format.parse(o2.getDate());
                    if (dt1.getTime() < dt2.getTime()) {
                        return 1;
                    } else if (dt1.getTime() > dt2.getTime()) {
                        return -1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }

    @Override
    public void getDiscyssionError(int status) {
        switch (status) {
            case 100:
                ToastUtil.showToast(this, "获取支书会议记录失败");
                break;
            case 101:
                ToastUtil.showToast(this, "还没有支书会议记录");
                break;
            case 103:
                ToastUtil.showToast(this, "数据库IO错误");
                break;
        }
    }

    @Override
    public void refreshComplete() {
        listView.onRefreshComplete();
    }

    @Override
    public void updateReadSuccess(UpdateReadBean bean, int position) {
        adapter.getItem(position).setRead(true);
        adapter.getItem(position).setBrowse(bean.getBrowse());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void updateReadError(int status) {
        Log.e("updateReadError", status + "");
    }
}
