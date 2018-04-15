package com.zhoukp.signer.module.functions.ascq.browse;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhoukp.signer.R;
import com.zhoukp.signer.fragment.BaseFragment;
import com.zhoukp.signer.module.functions.ledgers.scanxls.ProgressDialog;
import com.zhoukp.signer.module.login.LoginBean;
import com.zhoukp.signer.module.login.UserUtil;
import com.zhoukp.signer.utils.ToastUtil;
import com.zhoukp.signer.view.SelectSchoolYearDialog;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author zhoukp
 * @time 2018/4/11 21:15
 * @email 275557625@qq.com
 * @function
 */
public class BrowseASCQFragment extends BaseFragment implements BrowseASCQView, View.OnClickListener {

    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.tvScore)
    TextView tvScore;
    @Bind(R.id.tvRank)
    TextView tvRank;
    @Bind(R.id.tvTotal)
    TextView tvTotal;
    @Bind(R.id.llScore)
    LinearLayout llScore;
    @Bind(R.id.tvTime)
    TextView tvTime;
    @Bind(R.id.llTime)
    LinearLayout llTime;
    @Bind(R.id.tvMutualID)
    TextView tvMutualID;
    @Bind(R.id.tvMutualTime)
    TextView tvMutualTime;
    @Bind(R.id.llMutualID)
    LinearLayout llMutualID;
    @Bind(R.id.tvUploadTime)
    TextView tvUploadTime;
    @Bind(R.id.tvMoral)
    TextView tvMoral;
    @Bind(R.id.tvMoralFull)
    TextView tvMoralFull;
    @Bind(R.id.tvMoralMutual)
    TextView tvMoralMutual;
    @Bind(R.id.tvMoralSelfStr)
    TextView tvMoralSelfStr;
    @Bind(R.id.tvMoralSelf)
    TextView tvMoralSelf;
    @Bind(R.id.rlMoral)
    RelativeLayout rlMoral;
    @Bind(R.id.tvWit)
    TextView tvWit;
    @Bind(R.id.tvWitFull)
    TextView tvWitFull;
    @Bind(R.id.tvWitMutual)
    TextView tvWitMutual;
    @Bind(R.id.tvWitSelfStr)
    TextView tvWitSelfStr;
    @Bind(R.id.tvWitSelf)
    TextView tvWitSelf;
    @Bind(R.id.rlWit)
    RelativeLayout rlWit;
    @Bind(R.id.tvSports)
    TextView tvSports;
    @Bind(R.id.tvSportsFull)
    TextView tvSportsFull;
    @Bind(R.id.tvSportsMutual)
    TextView tvSportsMutual;
    @Bind(R.id.tvSportsSelfStr)
    TextView tvSportsSelfStr;
    @Bind(R.id.tvSportsSelf)
    TextView tvSportsSelf;
    @Bind(R.id.rlSports)
    RelativeLayout rlSports;
    @Bind(R.id.tvPractice)
    TextView tvPractice;
    @Bind(R.id.tvPracticeFull)
    TextView tvPracticeFull;
    @Bind(R.id.tvPracticeMutual)
    TextView tvPracticeMutual;
    @Bind(R.id.tvPracticeSelfStr)
    TextView tvPracticeSelfStr;
    @Bind(R.id.tvPracticeSelf)
    TextView tvPracticeSelf;
    @Bind(R.id.rlPractice)
    RelativeLayout rlPractice;
    @Bind(R.id.tvGenres)
    TextView tvGenres;
    @Bind(R.id.tvGenresFull)
    TextView tvGenresFull;
    @Bind(R.id.tvGenresMutual)
    TextView tvGenresMutual;
    @Bind(R.id.tvGenresSelfStr)
    TextView tvGenresSelfStr;
    @Bind(R.id.tvGenresSelf)
    TextView tvGenresSelf;
    @Bind(R.id.rlGenres)
    RelativeLayout rlGenres;
    @Bind(R.id.tvTeam)
    TextView tvTeam;
    @Bind(R.id.tvTeamFull)
    TextView tvTeamFull;
    @Bind(R.id.tvTeamMutual)
    TextView tvTeamMutual;
    @Bind(R.id.tvTeamSelfStr)
    TextView tvTeamSelfStr;
    @Bind(R.id.tvTeamSelf)
    TextView tvTeamSelf;
    @Bind(R.id.rlTeam)
    RelativeLayout rlTeam;
    @Bind(R.id.tvExtra)
    TextView tvExtra;
    @Bind(R.id.tvExtraFull)
    TextView tvExtraFull;
    @Bind(R.id.tvExtraMutual)
    TextView tvExtraMutual;
    @Bind(R.id.tvExtraSelfStr)
    TextView tvExtraSelfStr;
    @Bind(R.id.tvExtraSelf)
    TextView tvExtraSelf;
    @Bind(R.id.rlExtra)
    RelativeLayout rlExtra;
    @Bind(R.id.btnSubmit)
    Button btnSubmit;
    @Bind(R.id.btnModify)
    Button btnModify;
    @Bind(R.id.llContent)
    LinearLayout llContent;
    @Bind(R.id.llUploadTime)
    LinearLayout llUploadTime;

    private ProgressDialog dialog;

    private String schoolYear;
    private LoginBean.UserBean userBean;
    private BrowseASCQPresenter presenter;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_browse_ascq, null);
        ButterKnife.bind(this, view);
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
        ButterKnife.unbind(this);
        presenter.detachView();
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
}
