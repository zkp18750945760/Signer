package com.zhoukp.signer.module.functions.ascq.mutual;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.zhoukp.signer.R;
import com.zhoukp.signer.fragment.BaseFragment;
import com.zhoukp.signer.module.functions.ledgers.scanxls.ProgressDialog;
import com.zhoukp.signer.module.login.LoginBean;
import com.zhoukp.signer.module.login.UserUtil;
import com.zhoukp.signer.utils.ToastUtil;
import com.zhoukp.signer.view.CommonDialog;
import com.zhoukp.signer.view.SelectLevelDialog;
import com.zhoukp.signer.view.linearLayout.ExpandableLinearLayout;
import com.zhoukp.signer.viewpager.CommonViewPager2;
import com.zhoukp.signer.viewpager.ViewPagerHolder;
import com.zhoukp.signer.viewpager.ViewPagerHolderCreator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author zhoukp
 * @time 2018/4/12 18:44
 * @email 275557625@qq.com
 * @function
 */
public class MutualASCQFragment extends BaseFragment implements MutualASCQView, View.OnClickListener {

    private CommonViewPager2 commonViewPager;
    private RelativeLayout rlNotMember, rlMember;
    private RelativeLayout rlMutualFinish;
    private LinearLayout rlMutual;
    private TextView tvMutualFinish;
    private TextView tvCurrentPage, tvTotalPage;
    private Button btnPageUp, btnPageDown;

    private ProgressDialog dialog;

    private MutualASCQPresnter presnter;
    private LoginBean.UserBean userBean;
    private List<MutualTaskBean.DataBean> data;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_mutual_ascq, null);
        commonViewPager = view.findViewById(R.id.commonViewPager);
        commonViewPager.setIndicatorVisible(true);
        rlNotMember = view.findViewById(R.id.rlNotMember);
        rlMember = view.findViewById(R.id.rlMember);
        rlMutualFinish = view.findViewById(R.id.rlMutualFinish);
        tvMutualFinish = view.findViewById(R.id.tvMutualFinish);
        rlMutual = view.findViewById(R.id.rlMutual);
        tvCurrentPage = view.findViewById(R.id.tvCurrentPage);
        tvTotalPage = view.findViewById(R.id.tvTotalPage);
        btnPageUp = view.findViewById(R.id.btnPageUp);
        btnPageDown = view.findViewById(R.id.btnPageDown);
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        initVariates();
    }

    private void initVariates() {
        presnter = new MutualASCQPresnter();
        presnter.attachView(this);

        userBean = UserUtil.getInstance().getUser();

        presnter.isMutualMembers(userBean.getUserId(), userBean.getUserGrade(),
                userBean.getUserMajor(), userBean.getUserClass());
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

            //获取任务完成状态
            presnter.isMutualFinish(userBean.getUserId(), userBean.getUserGrade(), userBean.getUserMajor(), userBean.getUserClass());

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
    public void isFinishSuccess(IsMutualFinishBean bean) {
        if (bean.isHasTask()) {
            //如果有任务

            if (bean.isIsFinish()) {
                //所有任务都完成了
                rlMutualFinish.setVisibility(View.VISIBLE);
                rlMutual.setVisibility(View.GONE);
                tvMutualFinish.setText("你已经审核完了哦");
            } else {
                //有任务，并且还没有审核完
                rlMutualFinish.setVisibility(View.GONE);
                rlMutual.setVisibility(View.VISIBLE);

                //获取还没有完成的任务列表
                presnter.getMutualTask(userBean.getUserId(), userBean.getUserGrade(),
                        userBean.getUserMajor(), userBean.getUserClass());
            }

        } else {
            //没有任务
            rlMutualFinish.setVisibility(View.VISIBLE);
            rlMutual.setVisibility(View.GONE);
            tvMutualFinish.setText("等待班长分配综测表");
        }
    }

    @Override
    public void isFinishError(int status) {
        switch (status) {
            case 100:
                ToastUtil.showToast(context, "获取任务完成状态失败");
                break;
            case 101:
                ToastUtil.showToast(context, "数据库IO错误");
                break;
        }
    }

    @Override
    public void getTaskSuccess(MutualTaskBean bean) {
        //获取未完成的任务成功
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
    public void ASQCNotExist() {
        //待审核的综测表还不存在
        rlMutualFinish.setVisibility(View.VISIBLE);
        rlMutual.setVisibility(View.GONE);
        tvMutualFinish.setText("暂无综测数据");
    }

    @Override
    public void getTaskError(int status) {
        switch (status) {
            case 100:
                ToastUtil.showToast(context, "获取任务完成状态失败");
                break;
            case 101:
                ToastUtil.showToast(context, "数据库IO错误");
                break;
        }
    }

    @Override
    public void uploadSuccess() {
        ToastUtil.showToast(context, "上传成功");
        data.remove(commonViewPager.getCurrentItem());
        //刷新数据适配器
        tvCurrentPage.setText(1 + "");
        tvTotalPage.setText(data.size() + "");

        commonViewPager.setPages(data, new ViewPagerHolderCreator<ViewHolder>() {
            @Override
            public ViewHolder createViewHolder() {
                // 返回ViewPagerHolder
                return new ViewHolder();
            }
        });
    }

    @Override
    public void uploadError(int status) {
        switch (status) {
            case 100:
                ToastUtil.showToast(context, "获取任务完成状态失败");
                break;
            case 101:
                ToastUtil.showToast(context, "已经上传过这个同学的成绩啦");
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
                    new CommonDialog(context, "确认退出互评吗？\n没有提交的数据不会保存哦。", R.style.dialog, new CommonDialog.OnCloseListener() {
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

    class ViewHolder implements ViewPagerHolder<MutualTaskBean.DataBean>, View.OnClickListener {

        private static final int LEVEL = 2;

        @Bind(R.id.tvYear)
        TextView tvYear;
        @Bind(R.id.tvMoral1)
        TextView tvMoral1;
        @Bind(R.id.etMoral1)
        TextInputLayout etMoral1;
        @Bind(R.id.tvMoral2)
        TextView tvMoral2;
        @Bind(R.id.etMoral2)
        TextInputLayout etMoral2;
        @Bind(R.id.tvMoral3)
        TextView tvMoral3;
        @Bind(R.id.etMoral3)
        TextInputLayout etMoral3;
        @Bind(R.id.tvMoral4)
        TextView tvMoral4;
        @Bind(R.id.etMoral4)
        TextInputLayout etMoral4;
        @Bind(R.id.tvMoral5)
        TextView tvMoral5;
        @Bind(R.id.etMoral5)
        TextInputLayout etMoral5;
        @Bind(R.id.tvMoral6)
        TextView tvMoral6;
        @Bind(R.id.etMoral6)
        TextInputLayout etMoral6;
        @Bind(R.id.llMoral)
        ExpandableLinearLayout llMoral;
        @Bind(R.id.tvWit)
        TextView tvWit;
        @Bind(R.id.etWit)
        TextInputLayout etWit;
        @Bind(R.id.llWit)
        ExpandableLinearLayout llWit;
        @Bind(R.id.tvSport)
        TextView tvSport;
        @Bind(R.id.tvSports)
        TextView tvSports;
        @Bind(R.id.llSports)
        ExpandableLinearLayout llSports;
        @Bind(R.id.practiceBasic1)
        CheckBox practiceBasic1;
        @Bind(R.id.practiceBasic2)
        CheckBox practiceBasic2;
        @Bind(R.id.practiceBasic3)
        CheckBox practiceBasic3;
        @Bind(R.id.tvPractice1)
        TextView tvPractice1;
        @Bind(R.id.etPractice1)
        TextInputLayout etPractice1;
        @Bind(R.id.tvPractice2)
        TextView tvPractice2;
        @Bind(R.id.etPractice2)
        TextInputLayout etPractice2;
        @Bind(R.id.tvPractice3)
        TextView tvPractice3;
        @Bind(R.id.etPractice3)
        TextInputLayout etPractice3;
        @Bind(R.id.tvPractice4)
        TextView tvPractice4;
        @Bind(R.id.etPractice4)
        TextInputLayout etPractice4;
        @Bind(R.id.tvPractice5)
        TextView tvPractice5;
        @Bind(R.id.etPractice5)
        TextInputLayout etPractice5;
        @Bind(R.id.tvPractice6)
        TextView tvPractice6;
        @Bind(R.id.etPractice6)
        TextInputLayout etPractice6;
        @Bind(R.id.tvPractice7)
        TextView tvPractice7;
        @Bind(R.id.etPractice7)
        TextInputLayout etPractice7;
        @Bind(R.id.llPractice)
        ExpandableLinearLayout llPractice;
        @Bind(R.id.genresBasic1)
        CheckBox genresBasic1;
        @Bind(R.id.genresBasic2)
        CheckBox genresBasic2;
        @Bind(R.id.genresBasic3)
        CheckBox genresBasic3;
        @Bind(R.id.tvGenres1)
        TextView tvGenres1;
        @Bind(R.id.etGenres1)
        TextInputLayout etGenres1;
        @Bind(R.id.tvGenres2)
        TextView tvGenres2;
        @Bind(R.id.etGenres2)
        TextInputLayout etGenres2;
        @Bind(R.id.tvGenres3)
        TextView tvGenres3;
        @Bind(R.id.etGenres3)
        TextInputLayout etGenres3;
        @Bind(R.id.tvGenres4)
        TextView tvGenres4;
        @Bind(R.id.etGenres4)
        TextInputLayout etGenres4;
        @Bind(R.id.tvGenres5)
        TextView tvGenres5;
        @Bind(R.id.etGenres5)
        TextInputLayout etGenres5;
        @Bind(R.id.llGenres)
        ExpandableLinearLayout llGenres;
        @Bind(R.id.teamBasic1)
        CheckBox teamBasic1;
        @Bind(R.id.teamBasic2)
        CheckBox teamBasic2;
        @Bind(R.id.teamBasic3)
        CheckBox teamBasic3;
        @Bind(R.id.tvTeam1)
        TextView tvTeam1;
        @Bind(R.id.etTeam1)
        TextInputLayout etTeam1;
        @Bind(R.id.tvTeam2)
        TextView tvTeam2;
        @Bind(R.id.etTeam2)
        TextInputLayout etTeam2;
        @Bind(R.id.tvTeam3)
        TextView tvTeam3;
        @Bind(R.id.etTeam3)
        TextInputLayout etTeam3;
        @Bind(R.id.tvTeam4)
        TextView tvTeam4;
        @Bind(R.id.etTeam4)
        TextInputLayout etTeam4;
        @Bind(R.id.tvTeam5)
        TextView tvTeam5;
        @Bind(R.id.etTeam5)
        TextInputLayout etTeam5;
        @Bind(R.id.tvTeam6)
        TextView tvTeam6;
        @Bind(R.id.etTeam6)
        TextInputLayout etTeam6;
        @Bind(R.id.tvTeam7)
        TextView tvTeam7;
        @Bind(R.id.etTeam7)
        TextInputLayout etTeam7;
        @Bind(R.id.llTeam)
        ExpandableLinearLayout llTeam;
        @Bind(R.id.tvExtra)
        TextView tvExtra;
        @Bind(R.id.etExtra)
        TextInputLayout etExtra;
        @Bind(R.id.llExtra)
        ExpandableLinearLayout llExtra;
        @Bind(R.id.btnHideAll)
        Button btnHideAll;
        @Bind(R.id.btnSubmit)
        Button btnSubmit;
        @Bind(R.id.tvStudentID)
        TextView tvStudentID;

        private Context context;

        /**
         * 用于生成json的哈希表
         */
        private HashMap<String, Object> hashMap;
        private HashMap<String, Object> map;
        private HashMap<String, Object> base;
        private Iterator<Map.Entry<String, Object>> iter;

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
            View view = LayoutInflater.from(context).inflate(R.layout.asqc_view_pager_item, null);
            this.context = context;
            ButterKnife.bind(this, view);
            return view;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBind(Context context, int position, MutualTaskBean.DataBean data) {
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

            initEvents(data);
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

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new CommonDialog(context, "确认上传互评成绩吗？\n上传后只能更改一次哦!", R.style.dialog, new CommonDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            if (confirm) {
                                submit(data);
                            }
                            dialog.dismiss();
                        }
                    }).show();
                }
            });

            tvSports.setOnClickListener(this);
        }

        /**
         * 提交互评成绩到服务器
         *
         * @param data MutualTaskBean.DataBean
         */
        private void submit(MutualTaskBean.DataBean data) {
            hashMap = new HashMap<>();
            //德育素质
            map = new HashMap<>();
            map.put("moral1", TextUtils.isEmpty(etMoral1.getEditText().getText().toString()) ?
                    0.0f : Float.parseFloat(etMoral1.getEditText().getText().toString()));
            map.put("moral2", TextUtils.isEmpty(etMoral2.getEditText().getText().toString()) ?
                    0.0f : Float.parseFloat(etMoral2.getEditText().getText().toString()));
            map.put("moral3", TextUtils.isEmpty(etMoral3.getEditText().getText().toString()) ?
                    0.0f : Float.parseFloat(etMoral3.getEditText().getText().toString()));
            map.put("moral4", TextUtils.isEmpty(etMoral4.getEditText().getText().toString()) ?
                    0.0f : Float.parseFloat(etMoral4.getEditText().getText().toString()));
            map.put("moral5", TextUtils.isEmpty(etMoral5.getEditText().getText().toString()) ?
                    0.0f : Float.parseFloat(etMoral5.getEditText().getText().toString()));
            map.put("moral6", TextUtils.isEmpty(etMoral6.getEditText().getText().toString()) ?
                    0.0f : Float.parseFloat(etMoral6.getEditText().getText().toString()));

            iter = map.entrySet().iterator();

            while (iter.hasNext()) {
                System.out.println("i==" + i);
                i++;
                HashMap.Entry entry = iter.next();
                moralMutual += (float) entry.getValue();
            }

            if (moralMutual > moralMax) {
                //扣的分数比最大分数还要多，非法
                ToastUtil.showToast(context, "德育素质扣分太多了哦");
                return;
            } else {
                map.put("moralMax", moralMax);
                map.put("moralSelf", data.getMoral().getMoralSelf());
                map.put("moralMutual", moralMax - moralMutual);
                hashMap.put("moral", map);
            }

            //智育素质
            map = new HashMap<>();
            map.put("GPA", TextUtils.isEmpty(etWit.getEditText().getText().toString()) ?
                    0.0f : Float.parseFloat(etWit.getEditText().getText().toString()));

            if ((float) map.get("GPA") > 5.0f) {
                ToastUtil.showToast(context, "同学，你的绩点超过5.0了哦");
                return;
            } else {
                map.put("witMax", witMax);
                map.put("witSelf", data.getWit().getWitSelf());
                map.put("witMutual", ((float) map.get("GPA") + 2.5f) * 8);
                hashMap.put("wit", map);
            }

            //体育素质
            map = new HashMap<>();
            if (TextUtils.isEmpty(level)) {
                //没有选择体测等级
                ToastUtil.showToast(context, "请选择体测等级");
                return;
            } else {
                map.put("level", getSportsLevel(level));
                map.put("sportsMax", sportsMax);
                map.put("sportsSelf", data.getSports().getSportsSelf());
                map.put("sportsMutual", getSportsSore(level));
                hashMap.put("sports", map);
            }

            //科技创新与社会实践
            map = new HashMap<>();
            base = new HashMap<>();

            map.put("practice1", TextUtils.isEmpty(etPractice1.getEditText().getText().toString()) ?
                    0.0f : Float.parseFloat(etPractice1.getEditText().getText().toString()));
            map.put("practice2", TextUtils.isEmpty(etPractice2.getEditText().getText().toString()) ?
                    0.0f : Float.parseFloat(etPractice2.getEditText().getText().toString()));
            map.put("practice3", TextUtils.isEmpty(etPractice3.getEditText().getText().toString()) ?
                    0.0f : Float.parseFloat(etPractice3.getEditText().getText().toString()));
            map.put("practice4", TextUtils.isEmpty(etPractice4.getEditText().getText().toString()) ?
                    0.0f : Float.parseFloat(etPractice4.getEditText().getText().toString()));
            map.put("practice5", TextUtils.isEmpty(etPractice5.getEditText().getText().toString()) ?
                    0.0f : Float.parseFloat(etPractice5.getEditText().getText().toString()));
            map.put("practice6", TextUtils.isEmpty(etPractice6.getEditText().getText().toString()) ?
                    0.0f : Float.parseFloat(etPractice6.getEditText().getText().toString()));
            map.put("practice7", TextUtils.isEmpty(etPractice7.getEditText().getText().toString()) ?
                    0.0f : Float.parseFloat(etPractice7.getEditText().getText().toString()));

            iter = map.entrySet().iterator();
            i = 0;
            while (iter.hasNext()) {
                System.out.println("i==" + i);
                i++;
                HashMap.Entry entry = iter.next();
                practiceMutual += (float) entry.getValue();
            }

            base.put("practiceBasic1", practiceBasic1.isChecked());
            base.put("practiceBasic2", practiceBasic2.isChecked());
            base.put("practiceBasic3", practiceBasic3.isChecked());
            map.put("practiceBasic", base);

            iter = base.entrySet().iterator();
            i = 0;
            while (iter.hasNext()) {
                System.out.println("i==" + i);
                i++;
                if ((boolean) (iter.next()).getValue()) {
                    practiceMutual += 6.0f;
                    break;
                }
            }

            if (practiceMutual > practiceMax) {
                ToastUtil.showToast(context, "科技创新与社会实践加分太多了哦");
                return;
            } else {
                map.put("practiceMax", practiceMax);
                map.put("practiceSelf", data.getPractice().getPracticeSelf());
                map.put("practiceMutual", practiceMutual);
                hashMap.put("practice", map);
            }

            //文体艺术与身心发展
            map = new HashMap<>();
            base = new HashMap<>();

            map.put("Genres1", TextUtils.isEmpty(etGenres1.getEditText().getText().toString()) ?
                    0.0f : Float.parseFloat(etGenres1.getEditText().getText().toString()));
            map.put("Genres2", TextUtils.isEmpty(etGenres2.getEditText().getText().toString()) ?
                    0.0f : Float.parseFloat(etGenres2.getEditText().getText().toString()));
            map.put("Genres3", TextUtils.isEmpty(etGenres3.getEditText().getText().toString()) ?
                    0.0f : Float.parseFloat(etGenres3.getEditText().getText().toString()));
            map.put("Genres4", TextUtils.isEmpty(etGenres4.getEditText().getText().toString()) ?
                    0.0f : Float.parseFloat(etGenres4.getEditText().getText().toString()));
            map.put("Genres5", TextUtils.isEmpty(etGenres5.getEditText().getText().toString()) ?
                    0.0f : Float.parseFloat(etGenres5.getEditText().getText().toString()));

            iter = map.entrySet().iterator();
            i = 0;
            while (iter.hasNext()) {
                System.out.println("i==" + i);
                i++;
                HashMap.Entry entry = iter.next();
                GenresMutual += (float) entry.getValue();
            }

            base.put("GenresBasic1", genresBasic1.isChecked());
            base.put("GenresBasic2", genresBasic2.isChecked());
            base.put("GenresBasic3", genresBasic3.isChecked());
            map.put("GenresBasic", base);

            iter = base.entrySet().iterator();
            i = 0;
            while (iter.hasNext()) {
                System.out.println("i==" + i);
                i++;
                if ((boolean) (iter.next()).getValue()) {
                    GenresMutual += 3.0f;
                    break;
                }
            }

            if (GenresMutual > GenresMax) {
                ToastUtil.showToast(context, "文体艺术与身心发展加分太多了哦");
                return;
            } else {
                map.put("GenresMax", GenresMax);
                map.put("GenresSelf", data.getGenres().getGenresSelf());
                map.put("GenresMutual", GenresMutual);
                hashMap.put("Genres", map);
            }

            //团体活动与社会工作
            map = new HashMap<>();
            base = new HashMap<>();

            map.put("team1", TextUtils.isEmpty(etTeam1.getEditText().getText().toString()) ?
                    0.0f : Float.parseFloat(etTeam1.getEditText().getText().toString()));
            map.put("team2", TextUtils.isEmpty(etTeam2.getEditText().getText().toString()) ?
                    0.0f : Float.parseFloat(etTeam2.getEditText().getText().toString()));
            map.put("team3", TextUtils.isEmpty(etTeam3.getEditText().getText().toString()) ?
                    0.0f : Float.parseFloat(etTeam3.getEditText().getText().toString()));
            map.put("team4", TextUtils.isEmpty(etTeam4.getEditText().getText().toString()) ?
                    0.0f : Float.parseFloat(etTeam4.getEditText().getText().toString()));
            map.put("team5", TextUtils.isEmpty(etTeam5.getEditText().getText().toString()) ?
                    0.0f : Float.parseFloat(etTeam5.getEditText().getText().toString()));
            map.put("team6", TextUtils.isEmpty(etTeam6.getEditText().getText().toString()) ?
                    0.0f : Float.parseFloat(etTeam6.getEditText().getText().toString()));
            map.put("team7", TextUtils.isEmpty(etTeam7.getEditText().getText().toString()) ?
                    0.0f : Float.parseFloat(etTeam7.getEditText().getText().toString()));

            iter = map.entrySet().iterator();
            i = 0;
            while (iter.hasNext()) {
                System.out.println("i==" + i);
                i++;
                HashMap.Entry entry = iter.next();
                teamMutual += (float) entry.getValue();
            }

            base.put("teamBasic1", teamBasic1.isChecked());
            base.put("teamBasic2", teamBasic2.isChecked());
            base.put("teamBasic3", teamBasic3.isChecked());
            map.put("teamBasic", base);

            iter = base.entrySet().iterator();
            i = 0;
            while (iter.hasNext()) {
                System.out.println("i==" + i);
                i++;
                if ((boolean) (iter.next()).getValue()) {
                    teamMutual += 3.0f;
                    break;
                }
            }

            if (teamMutual > teamMax) {
                ToastUtil.showToast(context, "团体活动与社会工作加分太多了哦");
                return;
            } else {
                map.put("teamMax", teamMax);
                map.put("teamSelf", data.getTeam().getTeamSelf());
                map.put("teamMutual", teamMutual);
                hashMap.put("team", map);
            }

            //附加分
            map = new HashMap<>();
            map.put("extraMutual", TextUtils.isEmpty(etExtra.getEditText().getText().toString()) ?
                    0.0f : Float.parseFloat(etExtra.getEditText().getText().toString()));

            if ((float) map.get("extraMutual") > 5.0f) {
                ToastUtil.showToast(context, "附加分不能超过5.0分");
                return;
            } else {
                map.put("extraMax", extraMax);
                map.put("extraSelf", data.getExtra().getExtraSelf());
                hashMap.put("extra", map);
            }

            hashMap.put("status", 200);
            hashMap.put("time", data.getTime());
            hashMap.put("userId", data.getStuId());
            hashMap.put("schoolYear", data.getSchoolYear());

            System.out.println(JSON.toJSONString(hashMap));

            //上传综测成绩
            presnter.uploadMutual(JSON.toJSONString(hashMap), userBean.getUserId(),
                    userBean.getUserGrade(), userBean.getUserMajor(), userBean.getUserClass());
        }

        private int getSportsLevel(String level) {
            int result = 4;
            switch (level) {
                case "优秀":
                    result = 1;
                    break;
                case "良好":
                    result = 2;
                    break;
                case "及格":
                    result = 3;
                    break;
                case "不及格":
                    result = 4;
                    break;
            }
            return result;
        }

        private float getSportsSore(String level) {
            float result = 1.0f;
            switch (level) {
                case "优秀":
                    result = 5.0f;
                    break;
                case "良好":
                    result = 4.5f;
                    break;
                case "及格":
                    result = 4.0f;
                    break;
                case "不及格":
                    result = 1.0f;
                    break;
            }
            return result;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnHideAll:
                    //折叠本页面所有模块
                    hideAll();
                    break;
                case R.id.tvSports:
                    new SelectLevelDialog(context, "请选择等级", R.style.dialog, new SelectLevelDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm, String data) {
                            if (confirm) {
                                //点击了确认按钮
                                level = data;
                                tvSports.setText(data);
                            }
                            dialog.dismiss();
                        }
                    }).show();
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
