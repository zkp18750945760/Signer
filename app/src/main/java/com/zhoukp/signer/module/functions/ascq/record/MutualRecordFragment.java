package com.zhoukp.signer.module.functions.ascq.record;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhoukp.signer.R;
import com.zhoukp.signer.fragment.BaseFragment;
import com.zhoukp.signer.module.functions.ascq.mutual.IsMutualMemberBean;
import com.zhoukp.signer.module.functions.ascq.mutual.MutualTaskBean;
import com.zhoukp.signer.view.dialog.ProgressDialog;
import com.zhoukp.signer.module.login.LoginBean;
import com.zhoukp.signer.module.login.UserUtil;
import com.zhoukp.signer.utils.ToastUtil;
import com.zhoukp.signer.view.dialog.CommonDialog;
import com.zhoukp.signer.view.dialog.SelectSchoolYearDialog;
import com.zhoukp.signer.view.linearLayout.ExpandableLinearLayout;
import com.zhoukp.signer.viewpager.CommonViewPager2;
import com.zhoukp.signer.viewpager.ViewPagerHolder;
import com.zhoukp.signer.viewpager.ViewPagerHolderCreator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhoukp
 * @time 2018/4/15 13:56
 * @email 275557625@qq.com
 * @function
 */
public class MutualRecordFragment extends BaseFragment implements MutualRecordView, View.OnClickListener {

    @BindView(R.id.rlNotMember)
    RelativeLayout rlNotMember;
    @BindView(R.id.tvMutualFinish)
    TextView tvMutualFinish;
    @BindView(R.id.rlMutualFinish)
    RelativeLayout rlMutualFinish;
    @BindView(R.id.tvDivide)
    TextView tvDivide;
    @BindView(R.id.tvCurrentPage)
    TextView tvCurrentPage;
    @BindView(R.id.tvTotalPage)
    TextView tvTotalPage;
    @BindView(R.id.btnPageUp)
    Button btnPageUp;
    @BindView(R.id.btnPageDown)
    Button btnPageDown;
    @BindView(R.id.rlTitle)
    RelativeLayout rlTitle;
    @BindView(R.id.commonViewPager)
    CommonViewPager2 commonViewPager;
    @BindView(R.id.rlMutual)
    LinearLayout rlMutual;
    @BindView(R.id.rlMember)
    RelativeLayout rlMember;

    private ProgressDialog dialog;
    private MutualRecordPresenter presenter;
    private String schoolYear;
    private LoginBean.UserBean userBean;

    private List<MutualRecordBean.DataBean> data;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_mutual_ascq, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        initVariates();
    }

    private void initVariates() {
        presenter = new MutualRecordPresenter();
        presenter.attachView(this);

        userBean = UserUtil.getInstance().getUser();

        presenter.isMutualMembers(userBean.getUserId(), userBean.getUserGrade(),
                userBean.getUserMajor(), userBean.getUserClass());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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

    @Override
    public void isMemberSuccess(IsMutualMemberBean bean) {
        if (bean.isIsMember()) {
            //是互评小组成员
            rlNotMember.setVisibility(View.GONE);
            rlMember.setVisibility(View.VISIBLE);

            new SelectSchoolYearDialog(context, "请选择学年", R.style.dialog, new SelectSchoolYearDialog.OnCloseListener() {
                @Override
                public void onClick(Dialog dialog, boolean confirm, String data) {
                    if (confirm) {
                        schoolYear = data;
                        //加载对应学年的综测成绩
                        presenter.getMutualRecord(userBean.getUserId(), schoolYear);
                    }
                    dialog.dismiss();
                }
            }).show();

        } else {
            //不是互评小组成员
            rlNotMember.setVisibility(View.VISIBLE);
            rlMember.setVisibility(View.GONE);
        }
    }

    @Override
    public void isMemberError(int status) {
        switch (status) {
            case 100:
                ToastUtil.showToast(context, "判断是否为互评小组成员失败");
                break;
            case 101:
                ToastUtil.showToast(context, "数据库IO错误");
                break;
        }
    }

    @Override
    public void getRecordSuccess(MutualRecordBean bean) {
        data = bean.getData();
        tvCurrentPage.setText(1 + "");
        tvTotalPage.setText(data.size() + "");
        commonViewPager.setPages(data, new ViewPagerHolderCreator<ViewHolder>() {
            @Override
            public ViewHolder createViewHolder() {
                // 返回ViewPagerHolder
                return new ViewHolder();
            }
        });

        commonViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvCurrentPage.setText(position + 1 + "");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        initEvents();
    }

    private void initEvents() {
        btnPageUp.setOnClickListener(this);
        btnPageDown.setOnClickListener(this);
    }

    @Override
    public void getRecordError(int status) {
        switch (status) {
            case 100:
                ToastUtil.showToast(context, "获取互评记录失败");
                break;
            case 101:
                ToastUtil.showToast(context, "还没有审核记录");
                new SelectSchoolYearDialog(context, "请选择学年", R.style.dialog, new SelectSchoolYearDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm, String data) {
                        if (confirm) {
                            schoolYear = data;
                            //加载对应学年的综测成绩
                            presenter.getMutualRecord(userBean.getUserId(), schoolYear);
                        }
                        dialog.dismiss();
                    }
                }).show();
                break;
            case 102:
                ToastUtil.showToast(context, "数据库IO错误");
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPageUp:
                //上一页
                if (commonViewPager.getCurrentItem() == 0) {
                    new CommonDialog(context, "确认退出吗？", R.style.dialog, new CommonDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            if (confirm) {
                                context.finish();
                            }
                            dialog.dismiss();
                        }
                    }).show();
                } else {
                    //上翻一页
                    commonViewPager.setCurrentItem(commonViewPager.getCurrentItem() - 1);
                    tvCurrentPage.setText(commonViewPager.getCurrentItem() + 1 + "");
                }
                break;
            case R.id.btnPageDown:
                //下一页
                if ((commonViewPager.getCurrentItem() + 1) < data.size()) {
                    commonViewPager.setCurrentItem(commonViewPager.getCurrentItem() + 1);
                    tvCurrentPage.setText(commonViewPager.getCurrentItem() + 1 + "");
                } else {
                    ToastUtil.showToast(context, "已经是最后一页了");
                }
                break;
        }
    }

    class ViewHolder implements ViewPagerHolder<MutualRecordBean.DataBean>, View.OnClickListener {

        @BindView(R.id.tvYearString)
        TextView tvYearString;
        @BindView(R.id.tvYear)
        TextView tvYear;
        @BindView(R.id.tvStudentIDString)
        TextView tvStudentIDString;
        @BindView(R.id.tvStudentID)
        TextView tvStudentID;
        @BindView(R.id.tvMoral1)
        TextView tvMoral1;
        @BindView(R.id.tvMoral2)
        TextView tvMoral2;
        @BindView(R.id.tvMoral3)
        TextView tvMoral3;
        @BindView(R.id.tvMoral4)
        TextView tvMoral4;
        @BindView(R.id.tvMoral5)
        TextView tvMoral5;
        @BindView(R.id.tvMoral6)
        TextView tvMoral6;
        @BindView(R.id.llMoral)
        ExpandableLinearLayout llMoral;
        @BindView(R.id.tvWit)
        TextView tvWit;
        @BindView(R.id.llWit)
        ExpandableLinearLayout llWit;
        @BindView(R.id.tvSport)
        TextView tvSport;
        @BindView(R.id.llSports)
        ExpandableLinearLayout llSports;
        @BindView(R.id.practiceBasic1)
        CheckBox practiceBasic1;
        @BindView(R.id.practiceBasic2)
        CheckBox practiceBasic2;
        @BindView(R.id.practiceBasic3)
        CheckBox practiceBasic3;
        @BindView(R.id.tvPractice1)
        TextView tvPractice1;
        @BindView(R.id.tvPractice2)
        TextView tvPractice2;
        @BindView(R.id.tvPractice3)
        TextView tvPractice3;
        @BindView(R.id.tvPractice4)
        TextView tvPractice4;
        @BindView(R.id.tvPractice5)
        TextView tvPractice5;
        @BindView(R.id.tvPractice6)
        TextView tvPractice6;
        @BindView(R.id.tvPractice7)
        TextView tvPractice7;
        @BindView(R.id.llPractice)
        ExpandableLinearLayout llPractice;
        @BindView(R.id.genresBasic1)
        CheckBox genresBasic1;
        @BindView(R.id.genresBasic2)
        CheckBox genresBasic2;
        @BindView(R.id.genresBasic3)
        CheckBox genresBasic3;
        @BindView(R.id.tvGenres1)
        TextView tvGenres1;
        @BindView(R.id.tvGenres2)
        TextView tvGenres2;
        @BindView(R.id.tvGenres3)
        TextView tvGenres3;
        @BindView(R.id.tvGenres4)
        TextView tvGenres4;
        @BindView(R.id.tvGenres5)
        TextView tvGenres5;
        @BindView(R.id.llGenres)
        ExpandableLinearLayout llGenres;
        @BindView(R.id.teamBasic1)
        CheckBox teamBasic1;
        @BindView(R.id.teamBasic2)
        CheckBox teamBasic2;
        @BindView(R.id.teamBasic3)
        CheckBox teamBasic3;
        @BindView(R.id.tvTeam1)
        TextView tvTeam1;
        @BindView(R.id.tvTeam2)
        TextView tvTeam2;
        @BindView(R.id.tvTeam3)
        TextView tvTeam3;
        @BindView(R.id.tvTeam4)
        TextView tvTeam4;
        @BindView(R.id.tvTeam5)
        TextView tvTeam5;
        @BindView(R.id.tvTeam6)
        TextView tvTeam6;
        @BindView(R.id.tvTeam7)
        TextView tvTeam7;
        @BindView(R.id.llTeam)
        ExpandableLinearLayout llTeam;
        @BindView(R.id.tvExtra)
        TextView tvExtra;
        @BindView(R.id.llExtra)
        ExpandableLinearLayout llExtra;
        @BindView(R.id.btnHideAll)
        Button btnHideAll;

        private Context context;

        //学年
        private String schoolYear;
        //体测等级
        private String level;

        //德育素质
        private float moralMax = 10.0f;
        private float moralSelf;
        private float moralMutual;

        //智育素质
        private float witMax = 60.0f;
        private float witSelf;
        private float witMutual;

        //体育素质
        private float sportsMax = 5.0f;
        private float sportsSelf;
        private float sportsMutual;

        //科技创新与社会实践
        private float practiceMax = 12.0f;
        private float practiceSelf;
        private float practiceMutual;

        //文体艺术与身心发展
        private float GenresMax = 7.0f;
        private float GenresSelf;
        private float GenresMutual;

        //团体活动与社会工作
        private float teamMax = 6.0f;
        private float teamSelf;
        private float teamMutual;

        //附加分
        private float extraMax = 5.0f;
        private float extraSelf;
        private float extraMutual;

        private int i = 0;

        @Override
        public View createView(Context context) {
            //返回ViewPager 页面展示的布局
            View view = LayoutInflater.from(context).inflate(R.layout.asqc_view_pager_item2, null);
            this.context = context;
            ButterKnife.bind(this, view);
            practiceBasic1.setClickable(false);
            practiceBasic2.setClickable(false);
            practiceBasic3.setClickable(false);
            genresBasic1.setClickable(false);
            genresBasic2.setClickable(false);
            genresBasic3.setClickable(false);
            teamBasic1.setClickable(false);
            teamBasic2.setClickable(false);
            teamBasic3.setClickable(false);
            return view;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBind(Context context, int position, MutualRecordBean.DataBean data) {
            //数据绑定

            tvYear.setText(data.getSchoolYear() + "学年");
            tvStudentID.setText(data.getStuId());

            tvMoral1.setText(data.getMoral().getMoral1() + "");
            tvMoral2.setText(data.getMoral().getMoral2() + "");
            tvMoral3.setText(data.getMoral().getMoral3() + "");
            tvMoral4.setText(data.getMoral().getMoral4() + "");
            tvMoral5.setText(data.getMoral().getMoral5() + "");
            tvMoral6.setText(data.getMoral().getMoral6() + "");
            tvMoral1.setText(data.getMoral().getMoral1() + "");

            tvWit.setText(data.getWit().getGPA() + "");

            tvSport.setText(getSportsLevel(data.getSports().getLevel()));

            if (data.getPractice().getPracticeBasic().getPracticeBasic1().equals("true")) {
                practiceBasic1.setChecked(true);
            } else {
                practiceBasic1.setChecked(false);
            }

            if (data.getPractice().getPracticeBasic().getPracticeBasic2().equals("true")) {
                practiceBasic2.setChecked(true);
            } else {
                practiceBasic2.setChecked(false);
            }

            if (data.getPractice().getPracticeBasic().getPracticeBasic3().equals("true")) {
                practiceBasic3.setChecked(true);
            } else {
                practiceBasic3.setChecked(false);
            }
            tvPractice1.setText(data.getPractice().getPractice1() + "");
            tvPractice2.setText(data.getPractice().getPractice2() + "");
            tvPractice3.setText(data.getPractice().getPractice3() + "");
            tvPractice4.setText(data.getPractice().getPractice4() + "");
            tvPractice5.setText(data.getPractice().getPractice5() + "");
            tvPractice6.setText(data.getPractice().getPractice6() + "");
            tvPractice7.setText(data.getPractice().getPractice7() + "");


            if (data.getGenres().getGenresBasic().getGenresBasic1().equals("true")) {
                genresBasic1.setChecked(true);
            } else {
                genresBasic1.setChecked(false);
            }

            if (data.getGenres().getGenresBasic().getGenresBasic2().equals("true")) {
                genresBasic2.setChecked(true);
            } else {
                genresBasic2.setChecked(false);
            }

            if (data.getGenres().getGenresBasic().getGenresBasic3().equals("true")) {
                genresBasic3.setChecked(true);
            } else {
                genresBasic3.setChecked(false);
            }
            tvGenres1.setText(data.getGenres().getGenres1() + "");
            tvGenres2.setText(data.getGenres().getGenres2() + "");
            tvGenres3.setText(data.getGenres().getGenres3() + "");
            tvGenres4.setText(data.getGenres().getGenres4() + "");
            tvGenres5.setText(data.getGenres().getGenres5() + "");


            if (data.getTeam().getTeamBasic().getTeamBasic1().equals("true")) {
                teamBasic1.setChecked(true);
            } else {
                teamBasic1.setChecked(false);
            }

            if (data.getTeam().getTeamBasic().getTeamBasic2().equals("true")) {
                teamBasic2.setChecked(true);
            } else {
                teamBasic2.setChecked(false);
            }

            if (data.getTeam().getTeamBasic().getTeamBasic3().equals("true")) {
                teamBasic3.setChecked(true);
            } else {
                teamBasic3.setChecked(false);
            }
            tvTeam1.setText(data.getTeam().getTeam1() + "");
            tvTeam2.setText(data.getTeam().getTeam2() + "");
            tvTeam3.setText(data.getTeam().getTeam3() + "");
            tvTeam4.setText(data.getTeam().getTeam4() + "");
            tvTeam5.setText(data.getTeam().getTeam5() + "");
            tvTeam6.setText(data.getTeam().getTeam6() + "");
            tvTeam7.setText(data.getTeam().getTeam7() + "");

            tvExtra.setText(data.getExtra().getExtraSelf() + "");
        }

        private String getSportsLevel(int level) {
            String result = "不及格";
            switch (level) {
                case 1:
                    result = "优秀";
                    break;
                case 2:
                    result = "良好";
                    break;
                case 3:
                    result = "及格";
                    break;
                case 4:
                    result = "不及格";
                    break;
            }
            return result;
        }

        private void initEvents(final MutualTaskBean.DataBean data) {
            btnHideAll.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnHideAll:
                    //折叠本页面所有模块
                    hideAll();
                    break;
            }
        }

        /**
         * 收起所有的模块
         */
        private void hideAll() {
            if (llMoral.isExpand) {
                llMoral.toggle();
            }
            if (llWit.isExpand) {
                llWit.toggle();
            }
            if (llSports.isExpand) {
                llSports.toggle();
            }
            if (llPractice.isExpand) {
                llPractice.toggle();
            }
            if (llGenres.isExpand) {
                llGenres.toggle();
            }
            if (llTeam.isExpand) {
                llTeam.toggle();
            }
            if (llExtra.isExpand) {
                llExtra.toggle();
            }
        }
    }
}
