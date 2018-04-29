package com.zhoukp.signer.module.functions.ascq.browse;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhoukp.signer.R;
import com.zhoukp.signer.fragment.BaseFragment;
import com.zhoukp.signer.module.login.LoginBean;
import com.zhoukp.signer.module.login.UserUtil;
import com.zhoukp.signer.utils.ToastUtil;
import com.zhoukp.signer.view.dialog.ProgressDialog;
import com.zhoukp.signer.view.dialog.SelectSchoolYearDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author zhoukp
 * @time 2018/4/11 21:15
 * @email 275557625@qq.com
 * @function
 */
public class BrowseASCQFragment extends BaseFragment implements BrowseASCQView, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvScore)
    TextView tvScore;
    @BindView(R.id.tvRank)
    TextView tvRank;
    @BindView(R.id.tvTotal)
    TextView tvTotal;
    @BindView(R.id.llScore)
    LinearLayout llScore;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.llTime)
    LinearLayout llTime;
    @BindView(R.id.tvMutualID)
    TextView tvMutualID;
    @BindView(R.id.tvMutualTime)
    TextView tvMutualTime;
    @BindView(R.id.llMutualID)
    LinearLayout llMutualID;
    @BindView(R.id.tvUploadTime)
    TextView tvUploadTime;
    @BindView(R.id.tvMoral)
    TextView tvMoral;
    @BindView(R.id.tvMoralFull)
    TextView tvMoralFull;
    @BindView(R.id.tvMoralMutual)
    TextView tvMoralMutual;
    @BindView(R.id.tvMoralSelfStr)
    TextView tvMoralSelfStr;
    @BindView(R.id.tvMoralSelf)
    TextView tvMoralSelf;
    @BindView(R.id.rlMoral)
    RelativeLayout rlMoral;
    @BindView(R.id.tvWit)
    TextView tvWit;
    @BindView(R.id.tvWitFull)
    TextView tvWitFull;
    @BindView(R.id.tvWitMutual)
    TextView tvWitMutual;
    @BindView(R.id.tvWitSelfStr)
    TextView tvWitSelfStr;
    @BindView(R.id.tvWitSelf)
    TextView tvWitSelf;
    @BindView(R.id.rlWit)
    RelativeLayout rlWit;
    @BindView(R.id.tvSports)
    TextView tvSports;
    @BindView(R.id.tvSportsFull)
    TextView tvSportsFull;
    @BindView(R.id.tvSportsMutual)
    TextView tvSportsMutual;
    @BindView(R.id.tvSportsSelfStr)
    TextView tvSportsSelfStr;
    @BindView(R.id.tvSportsSelf)
    TextView tvSportsSelf;
    @BindView(R.id.rlSports)
    RelativeLayout rlSports;
    @BindView(R.id.tvPractice)
    TextView tvPractice;
    @BindView(R.id.tvPracticeFull)
    TextView tvPracticeFull;
    @BindView(R.id.tvPracticeMutual)
    TextView tvPracticeMutual;
    @BindView(R.id.tvPracticeSelfStr)
    TextView tvPracticeSelfStr;
    @BindView(R.id.tvPracticeSelf)
    TextView tvPracticeSelf;
    @BindView(R.id.rlPractice)
    RelativeLayout rlPractice;
    @BindView(R.id.tvGenres)
    TextView tvGenres;
    @BindView(R.id.tvGenresFull)
    TextView tvGenresFull;
    @BindView(R.id.tvGenresMutual)
    TextView tvGenresMutual;
    @BindView(R.id.tvGenresSelfStr)
    TextView tvGenresSelfStr;
    @BindView(R.id.tvGenresSelf)
    TextView tvGenresSelf;
    @BindView(R.id.rlGenres)
    RelativeLayout rlGenres;
    @BindView(R.id.tvTeam)
    TextView tvTeam;
    @BindView(R.id.tvTeamFull)
    TextView tvTeamFull;
    @BindView(R.id.tvTeamMutual)
    TextView tvTeamMutual;
    @BindView(R.id.tvTeamSelfStr)
    TextView tvTeamSelfStr;
    @BindView(R.id.tvTeamSelf)
    TextView tvTeamSelf;
    @BindView(R.id.rlTeam)
    RelativeLayout rlTeam;
    @BindView(R.id.tvExtra)
    TextView tvExtra;
    @BindView(R.id.tvExtraFull)
    TextView tvExtraFull;
    @BindView(R.id.tvExtraMutual)
    TextView tvExtraMutual;
    @BindView(R.id.tvExtraSelfStr)
    TextView tvExtraSelfStr;
    @BindView(R.id.tvExtraSelf)
    TextView tvExtraSelf;
    @BindView(R.id.rlExtra)
    RelativeLayout rlExtra;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.btnModify)
    Button btnModify;
    @BindView(R.id.llContent)
    LinearLayout llContent;
    @BindView(R.id.llUploadTime)
    LinearLayout llUploadTime;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    Unbinder unbinder;

    private ProgressDialog dialog;

    private String schoolYear;
    private LoginBean.UserBean userBean;
    private BrowseASCQPresenter presenter;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_browse_ascq, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        initVariates();

        initEvents();
    }

    private void initVariates() {
        presenter = new BrowseASCQPresenter();
        presenter.attachView(this);
        userBean = UserUtil.getInstance().getUser();

        new SelectSchoolYearDialog(context, "请选择学年", R.style.dialog, new SelectSchoolYearDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm, String data) {
                if (confirm) {
                    schoolYear = data;
                    swipeRefreshLayout.setRefreshing(true);
                    swipeRefreshLayout.setOnRefreshListener(BrowseASCQFragment.this);
                    //加载对应学年的综测成绩
                    presenter.getASCQScore(userBean.getUserId(), schoolYear,
                            userBean.getUserGrade(), userBean.getUserMajor(), userBean.getUserClass());
                }
                dialog.dismiss();
            }
        }).show();

    }

    private void initEvents() {
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
        unbinder.unbind();
    }

    @Override
    public void showLoadingView() {
        if (dialog == null) {
            dialog = new ProgressDialog(context);
            dialog.showMessage("加载中...");
        }
        dialog.show();
    }

    @Override
    public void hideLoadingView() {
        if (dialog == null) {
            dialog = new ProgressDialog(context);
            dialog.showMessage("加载中...");
        }
        dialog.dismiss();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getASCQSuccess(ASCQScoreBean bean) {
        swipeRefreshLayout.setRefreshing(false);
        llContent.setVisibility(View.VISIBLE);
        if (bean.getData().isConfirm()) {
            btnSubmit.setVisibility(View.GONE);
        } else {
            btnSubmit.setVisibility(View.VISIBLE);
            btnSubmit.setOnClickListener(this);
            btnModify.setOnClickListener(this);
        }
        tvScore.setText(bean.getData().getTotalScore() + "");
        tvRank.setText(bean.getData().getRank() + "");
        tvTotal.setText(bean.getData().getTotal() + "");
        tvTime.setText(bean.getData().getSchoolYear() + "学年");
        tvMutualID.setText(bean.getData().getCheckedStuId() + "");
        tvMutualTime.setText(bean.getData().getCheckedTime().substring(0, 10));
        tvUploadTime.setText(bean.getData().getTime().substring(0, 10));

        tvMoralMutual.setText(bean.getData().getMoral().getMoralMutual() + "");
        tvMoralSelf.setText(bean.getData().getMoral().getMoralSelf() + "");

        tvWitMutual.setText(bean.getData().getWit().getWitMutual() + "");
        tvWitSelf.setText(bean.getData().getWit().getWitSelf() + "");

        tvSportsMutual.setText(bean.getData().getSports().getSportsMutual() + "");
        tvSportsSelf.setText(bean.getData().getSports().getSportsSelf() + "");

        tvPracticeMutual.setText(bean.getData().getPractice().getPracticeMutual() + "");
        tvPracticeSelf.setText(bean.getData().getPractice().getPracticeSelf() + "");

        tvGenresMutual.setText(bean.getData().getGenres().getGenresMutual() + "");
        tvGenresSelf.setText(bean.getData().getGenres().getGenresSelf() + "");

        tvTeamMutual.setText(bean.getData().getTeam().getTeamMutual() + "");
        tvTeamSelf.setText(bean.getData().getTeam().getTeamSelf() + "");

        tvExtraMutual.setText(bean.getData().getExtra().getExtraMutual() + "");
        tvExtraSelf.setText(bean.getData().getExtra().getExtraSelf() + "");
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getASCQUnChecked(ASCQScoreBean bean) {
        swipeRefreshLayout.setRefreshing(false);
        llContent.setVisibility(View.VISIBLE);
        //该分数是用户自己上报的分数
        btnSubmit.setVisibility(View.GONE);

        tvScore.setText(bean.getData().getTotalScore() + "(等待互评中)");
        tvRank.setText(bean.getData().getRank() + "");
        tvTotal.setText(bean.getData().getTotal() + "");
        tvTime.setText(bean.getData().getSchoolYear() + "学年");
        llMutualID.setVisibility(View.GONE);
        llUploadTime.setVisibility(View.GONE);

        tvMoralMutual.setText(bean.getData().getMoral().getMoralMutual() + "");
        tvMoralSelf.setText(bean.getData().getMoral().getMoralSelf() + "");

        tvWitMutual.setText(bean.getData().getWit().getWitMutual() + "");
        tvWitSelf.setText(bean.getData().getWit().getWitSelf() + "");

        tvSportsMutual.setText(bean.getData().getSports().getSportsMutual() + "");
        tvSportsSelf.setText(bean.getData().getSports().getSportsSelf() + "");

        tvPracticeMutual.setText(bean.getData().getPractice().getPracticeMutual() + "");
        tvPracticeSelf.setText(bean.getData().getPractice().getPracticeSelf() + "");

        tvGenresMutual.setText(bean.getData().getGenres().getGenresMutual() + "");
        tvGenresSelf.setText(bean.getData().getGenres().getGenresSelf() + "");

        tvTeamMutual.setText(bean.getData().getTeam().getTeamMutual() + "");
        tvTeamSelf.setText(bean.getData().getTeam().getTeamSelf() + "");

        tvExtraMutual.setText(bean.getData().getExtra().getExtraMutual() + "");
        tvExtraSelf.setText(bean.getData().getExtra().getExtraSelf() + "");
    }

    @Override
    public void getASCQError(int status) {
        swipeRefreshLayout.setRefreshing(false);
        switch (status) {
            case 100:
                ToastUtil.showToast(context, "获取" + schoolYear + "学年综测成绩失败");
                break;
            case 102:
                ToastUtil.showToast(context, "暂无数据");
                break;
            case 103:
                ToastUtil.showToast(context, "你还没有上传" + schoolYear + "学年综测成绩");
                //显示对话框，让用户再次选择学年
                new SelectSchoolYearDialog(context, "请选择学年", R.style.dialog, new SelectSchoolYearDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm, String data) {
                        if (confirm) {
                            schoolYear = data;
                            //加载对应学年的综测成绩
                            swipeRefreshLayout.setRefreshing(true);
                            presenter.getASCQScore(userBean.getUserId(), schoolYear,
                                    userBean.getUserGrade(), userBean.getUserMajor(), userBean.getUserClass());
                        }
                        dialog.dismiss();
                    }
                }).show();
                break;
            case 104:
                ToastUtil.showToast(context, "数据库IO错误");
                break;
        }
    }

    @Override
    public void confirmSuccess() {
        ToastUtil.showToast(context, "确认成功");
    }

    @Override
    public void confirmError(int status) {
        switch (status) {
            case 100:
                ToastUtil.showToast(context, "确认失败");
                break;
            case 101:
                ToastUtil.showToast(context, "还没有审核记录");
                break;
            case 102:
                ToastUtil.showToast(context, "数据库IO错误");
                break;
            case 103:
                ToastUtil.showToast(context, "已经确认过了");
                break;
        }
    }

    @Override
    public void modifySuccess() {
        ToastUtil.showToast(context, "请求修改成功");
    }

    @Override
    public void modifyError(int status) {
        switch (status) {
            case 100:
                ToastUtil.showToast(context, "请求修改失败");
                break;
            case 101:
                ToastUtil.showToast(context, "还没有审核记录");
                break;
            case 102:
                ToastUtil.showToast(context, "数据库IO错误");
                break;
            case 103:
                ToastUtil.showToast(context, "已经请求修改过了");
                break;
            case 104:
                ToastUtil.showToast(context, "已经确认过得分了");
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:
                presenter.confirmASCQ(userBean.getUserId(),
                        tvTime.getText().toString().replace("学年", ""));
                break;
            case R.id.btnModify:
                presenter.modifyASCQ(userBean.getUserId(),
                        tvTime.getText().toString().replace("学年", ""));
                break;
            case R.id.ivBack:
                context.finish();
                break;
        }
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        //加载对应学年的综测成绩
        presenter.getASCQScore(userBean.getUserId(), schoolYear,
                userBean.getUserGrade(), userBean.getUserMajor(), userBean.getUserClass());
    }
}
