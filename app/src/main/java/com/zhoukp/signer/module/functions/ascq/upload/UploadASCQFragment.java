package com.zhoukp.signer.module.functions.ascq.upload;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.zhoukp.signer.R;
import com.zhoukp.signer.fragment.BaseFragment;
import com.zhoukp.signer.module.chose.SelectLevelActivity;
import com.zhoukp.signer.module.chose.SelectSchoolYearActivity;
import com.zhoukp.signer.view.dialog.ProgressDialog;
import com.zhoukp.signer.module.login.LoginBean;
import com.zhoukp.signer.module.login.UserUtil;
import com.zhoukp.signer.module.managedevice.DeviceBean;
import com.zhoukp.signer.utils.SchoolYearUtils;
import com.zhoukp.signer.utils.StringUtils;
import com.zhoukp.signer.utils.TimeUtils;
import com.zhoukp.signer.utils.ToastUtil;
import com.zhoukp.signer.view.linearLayout.ExpandableLinearLayout;

import java.util.Iterator;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhoukp
 * @time 2018/4/11 21:04
 * @email 275557625@qq.com
 * @function
 */
public class UploadASCQFragment extends BaseFragment implements View.OnClickListener, UploadASCQView {

    private static final int YEAR = 1;
    private static final int LEVEL = 2;

    @BindView(R.id.tvYear)
    TextView tvYear;
    @BindView(R.id.etMoral1)
    TextInputLayout etMoral1;
    @BindView(R.id.etMoral2)
    TextInputLayout etMoral2;
    @BindView(R.id.etMoral3)
    TextInputLayout etMoral3;
    @BindView(R.id.etMoral4)
    TextInputLayout etMoral4;
    @BindView(R.id.etMoral5)
    TextInputLayout etMoral5;
    @BindView(R.id.etMoral6)
    TextInputLayout etMoral6;
    @BindView(R.id.llMoral)
    ExpandableLinearLayout llMoral;
    @BindView(R.id.etWit)
    TextInputLayout etWit;
    @BindView(R.id.llWit)
    ExpandableLinearLayout llWit;
    @BindView(R.id.tvSports)
    TextView tvSports;
    @BindView(R.id.llSports)
    ExpandableLinearLayout llSports;
    @BindView(R.id.practiceBasic1)
    CheckBox practiceBasic1;
    @BindView(R.id.practiceBasic2)
    CheckBox practiceBasic2;
    @BindView(R.id.practiceBasic3)
    CheckBox practiceBasic3;
    @BindView(R.id.etPractice1)
    TextInputLayout etPractice1;
    @BindView(R.id.etPractice2)
    TextInputLayout etPractice2;
    @BindView(R.id.etPractice3)
    TextInputLayout etPractice3;
    @BindView(R.id.etPractice4)
    TextInputLayout etPractice4;
    @BindView(R.id.etPractice5)
    TextInputLayout etPractice5;
    @BindView(R.id.etPractice6)
    TextInputLayout etPractice6;
    @BindView(R.id.etPractice7)
    TextInputLayout etPractice7;
    @BindView(R.id.llPractice)
    ExpandableLinearLayout llPractice;
    @BindView(R.id.genresBasic1)
    CheckBox genresBasic1;
    @BindView(R.id.genresBasic2)
    CheckBox genresBasic2;
    @BindView(R.id.genresBasic3)
    CheckBox genresBasic3;
    @BindView(R.id.etGenres1)
    TextInputLayout etGenres1;
    @BindView(R.id.etGenres2)
    TextInputLayout etGenres2;
    @BindView(R.id.etGenres3)
    TextInputLayout etGenres3;
    @BindView(R.id.etGenres4)
    TextInputLayout etGenres4;
    @BindView(R.id.etGenres5)
    TextInputLayout etGenres5;
    @BindView(R.id.llGenres)
    ExpandableLinearLayout llGenres;
    @BindView(R.id.teamBasic1)
    CheckBox teamBasic1;
    @BindView(R.id.teamBasic2)
    CheckBox teamBasic2;
    @BindView(R.id.teamBasic3)
    CheckBox teamBasic3;
    @BindView(R.id.etTeam1)
    TextInputLayout etTeam1;
    @BindView(R.id.etTeam2)
    TextInputLayout etTeam2;
    @BindView(R.id.etTeam3)
    TextInputLayout etTeam3;
    @BindView(R.id.etTeam4)
    TextInputLayout etTeam4;
    @BindView(R.id.etTeam5)
    TextInputLayout etTeam5;
    @BindView(R.id.etTeam6)
    TextInputLayout etTeam6;
    @BindView(R.id.etTeam7)
    TextInputLayout etTeam7;
    @BindView(R.id.llTeam)
    ExpandableLinearLayout llTeam;
    @BindView(R.id.etExtra)
    TextInputLayout etExtra;
    @BindView(R.id.llExtra)
    ExpandableLinearLayout llExtra;
    @BindView(R.id.btnHideAll)
    Button btnHideAll;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    private UploadASCQPresenter presenter;
    private ProgressDialog dialog;

    private LoginBean.UserBean userBean;
    /**
     * 用于生成json的哈希表
     */
    private ArrayMap<String, Object> hashMap;
    private ArrayMap<String, Object> map;
    private ArrayMap<String, Object> base;
    private Iterator<Map.Entry<String, Object>> iter;

    //学年
    private String schoolYear;
    //体测等级
    private String level;

    //德育素质
    private float moralMax = 10.0f;
    private float moralSelf;

    //智育素质
    private float witMax = 60.0f;
    private float witSelf;

    //体育素质
    private float sportsMax = 5.0f;
    private float sportsSelf;

    //科技创新与社会实践
    private float practiceMax = 12.0f;
    private float practiceSelf;

    //文体艺术与身心发展
    private float GenresMax = 7.5f;
    private float GenresSelf;

    //团体活动与社会工作
    private float teamMax = 6.0f;
    private float teamSelf;

    //附加分
    private float extraMax = 5.0f;
    private float extraSelf;

    private int i = 0;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_upload_ascq, null);
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

        presenter = new UploadASCQPresenter();
        presenter.attachView(this);

        userBean = UserUtil.getInstance().getUser();

        //获取当前学年，设置到选择器上
        schoolYear = SchoolYearUtils.getSchoolYear(SchoolYearUtils.getGradeCode(userBean.getUserGrade()),
                SchoolYearUtils.getTermCodeByMonth(userBean.getUserId(), Integer.parseInt(TimeUtils.getCurrentYear()), TimeUtils.getMonthOfYear()))
                .get("schoolYear").toString();

        tvYear.setText(schoolYear);
    }

    private void initEvents() {
        btnHideAll.setOnClickListener(this);
        tvYear.setOnClickListener(this);
        tvSports.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnHideAll:
                hideAll();
                break;
            case R.id.btnSubmit:
                submit();
                break;
            case R.id.tvYear:
                intent = new Intent(context, SelectSchoolYearActivity.class);
                intent.putExtra("type", "schoolYear");
                intent.putExtra("userId", userBean.getUserId());
                startActivityForResult(intent, YEAR);
                break;
            case R.id.tvSports:
                intent = new Intent(context, SelectLevelActivity.class);
                intent.putExtra("type", "level");
                startActivityForResult(intent, LEVEL);
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

    /**
     * 提交填写的数据到服务器
     */
    private void submit() {
        moralSelf = 0f;
        practiceSelf = 0f;
        GenresSelf = 0f;
        teamSelf = 0f;
        extraSelf = 0f;

        hashMap = new ArrayMap<>();
        //德育素质
        map = new ArrayMap<>();
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
            i++;
            ArrayMap.Entry entry = iter.next();
            moralSelf += (float) entry.getValue();
        }

        if (moralSelf > moralMax) {
            //扣的分数比最大分数还要多，非法
            ToastUtil.showToast(context, "德育素质扣分太多了哦");
            return;
        } else {
            map.put("moralMax", moralMax);
            map.put("moralSelf", moralMax - moralSelf);
            map.put("moralMutual", 0.0f);
            hashMap.put("moral", map);
        }

        //智育素质
        map = new ArrayMap<>();
        map.put("GPA", TextUtils.isEmpty(etWit.getEditText().getText().toString()) ?
                0.0f : Float.parseFloat(etWit.getEditText().getText().toString()));

        if ((float) map.get("GPA") > 5.0f) {
            ToastUtil.showToast(context, "同学，你的绩点超过5.0了哦");
            return;
        } else {
            map.put("witMax", witMax);
            map.put("witSelf", ((float) map.get("GPA") + 2.5f) * 8);
            map.put("witMutual", 0.0f);
            hashMap.put("wit", map);
        }

        //体育素质
        map = new ArrayMap<>();
        if (TextUtils.isEmpty(level)) {
            //没有选择体测等级
            ToastUtil.showToast(context, "请选择体测等级");
            return;
        } else {
            map.put("level", getSportsLevel(level));
            map.put("sportsMax", sportsMax);
            map.put("sportsSelf", getSportsSore(level));
            map.put("sportsMutual", 0.0f);
            hashMap.put("sports", map);
        }

        //科技创新与社会实践
        map = new ArrayMap<>();
        base = new ArrayMap<>();

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
            i++;
            ArrayMap.Entry entry = iter.next();
            practiceSelf += (float) entry.getValue();
        }

        base.put("practiceBasic1", practiceBasic1.isChecked());
        base.put("practiceBasic2", practiceBasic2.isChecked());
        base.put("practiceBasic3", practiceBasic3.isChecked());
        map.put("practiceBasic", base);

        iter = base.entrySet().iterator();
        i = 0;
        while (iter.hasNext()) {
            i++;
            if ((boolean) (iter.next()).getValue()) {
                practiceSelf += 6.0f;
                break;
            }
        }

        if (practiceSelf > practiceMax) {
            ToastUtil.showToast(context, "科技创新与社会实践加分太多了哦");
            return;
        } else {
            map.put("practiceMax", practiceMax);
            map.put("practiceSelf", practiceSelf);
            map.put("practiceMutual", 0.0f);
            hashMap.put("practice", map);
        }

        //文体艺术与身心发展
        map = new ArrayMap<>();
        base = new ArrayMap<>();

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
            i++;
            ArrayMap.Entry entry = iter.next();
            GenresSelf += (float) entry.getValue();
        }

        base.put("GenresBasic1", genresBasic1.isChecked());
        base.put("GenresBasic2", genresBasic2.isChecked());
        base.put("GenresBasic3", genresBasic3.isChecked());
        map.put("GenresBasic", base);

        iter = base.entrySet().iterator();
        i = 0;
        while (iter.hasNext()) {
            i++;
            if ((boolean) (iter.next()).getValue()) {
                GenresSelf += 3.0f;
                break;
            }
        }

        if (GenresSelf > GenresMax - 0.5f) {
            ToastUtil.showToast(context, "文体艺术与身心发展加分太多了哦");
            return;
        } else {
            map.put("GenresMax", GenresMax);
            map.put("GenresSelf", GenresSelf);
            map.put("GenresMutual", 0.0f);
            hashMap.put("Genres", map);
        }

        //团体活动与社会工作
        map = new ArrayMap<>();
        base = new ArrayMap<>();

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
            i++;
            ArrayMap.Entry entry = iter.next();
            teamSelf += (float) entry.getValue();
        }

        base.put("teamBasic1", teamBasic1.isChecked());
        base.put("teamBasic2", teamBasic2.isChecked());
        base.put("teamBasic3", teamBasic3.isChecked());
        map.put("teamBasic", base);

        iter = base.entrySet().iterator();
        i = 0;
        while (iter.hasNext()) {
            i++;
            if ((boolean) (iter.next()).getValue()) {
                teamSelf += 3.0f;
                break;
            }
        }

        if (teamSelf > teamMax) {
            ToastUtil.showToast(context, "团体活动与社会工作加分太多了哦");
            return;
        } else {
            map.put("teamMax", teamMax);
            map.put("teamSelf", teamSelf);
            map.put("teamMutual", 0.0f);
            hashMap.put("team", map);
        }

        //附加分
        map = new ArrayMap<>();
        map.put("extraSelf", TextUtils.isEmpty(etExtra.getEditText().getText().toString()) ?
                0.0f : Float.parseFloat(etExtra.getEditText().getText().toString()));

        if ((float) map.get("extraSelf") > 5.0f) {
            ToastUtil.showToast(context, "附加分不能超过5.0分");
            return;
        } else {
            map.put("extraMax", extraMax);
            map.put("extraMutual", 0.0f);
            hashMap.put("extra", map);
        }

        hashMap.put("status", 200);
        hashMap.put("time", TimeUtils.getCurrentTime());
        hashMap.put("userId", userBean.getUserId());
        hashMap.put("schoolYear", StringUtils.getSubString(schoolYear, "(.*?)学年"));

        System.out.println(JSON.toJSONString(hashMap));

        LoginBean.UserBean user = UserUtil.getInstance().getUser();
        presenter.uploadASCQ(JSON.toJSONString(hashMap), user.getUserId(),
                user.getUserGrade(), user.getUserMajor(), user.getUserClass());

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            switch (requestCode) {
                case YEAR:
                    schoolYear = data.getStringExtra("schoolYear");
                    if (!TextUtils.isEmpty(schoolYear)) {
                        tvYear.setText(schoolYear);
                    }
                    break;
                case LEVEL:
                    level = data.getStringExtra("level");
                    if (!TextUtils.isEmpty(level)) {
                        tvSports.setText(level);
                    }
                    break;
            }
        }
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
    public void uploadASCQSuccess(DeviceBean bean) {
        ToastUtil.showToast(context, "上传综测成绩成功");
        clearUI();
    }

    /**
     * 恢复UI到初始状态
     */
    private void clearUI() {
        //获取当前学年，设置到选择器上
        schoolYear = SchoolYearUtils.getSchoolYear(SchoolYearUtils.getGradeCode(userBean.getUserGrade()),
                SchoolYearUtils.getTermCodeByMonth(userBean.getUserId(), Integer.parseInt(TimeUtils.getCurrentYear()), TimeUtils.getMonthOfYear()))
                .get("schoolYear").toString();

        tvYear.setText(schoolYear);

        etMoral1.getEditText().setText("");
        etMoral2.getEditText().setText("");
        etMoral3.getEditText().setText("");
        etMoral4.getEditText().setText("");
        etMoral5.getEditText().setText("");
        etMoral6.getEditText().setText("");
        etWit.getEditText().setText("");
        tvSports.setText("");
        etPractice1.getEditText().setText("");
        etPractice2.getEditText().setText("");
        etPractice3.getEditText().setText("");
        etPractice4.getEditText().setText("");
        etPractice5.getEditText().setText("");
        etPractice6.getEditText().setText("");
        etPractice7.getEditText().setText("");
        practiceBasic1.setChecked(false);
        practiceBasic2.setChecked(false);
        practiceBasic3.setChecked(false);
        etGenres1.getEditText().setText("");
        etGenres2.getEditText().setText("");
        etGenres3.getEditText().setText("");
        etGenres4.getEditText().setText("");
        etGenres5.getEditText().setText("");
        genresBasic1.setChecked(false);
        genresBasic2.setChecked(false);
        genresBasic3.setChecked(false);
        etTeam1.getEditText().setText("");
        etTeam2.getEditText().setText("");
        etTeam3.getEditText().setText("");
        etTeam4.getEditText().setText("");
        etTeam5.getEditText().setText("");
        etTeam6.getEditText().setText("");
        etTeam7.getEditText().setText("");
        teamBasic1.setChecked(false);
        teamBasic2.setChecked(false);
        teamBasic3.setChecked(false);
        etExtra.getEditText().setText("");
    }

    @Override
    public void uploadASCQError(int status) {
        switch (status) {
            case 100:
                ToastUtil.showToast(context, "上传综测成绩失败");
                break;
            case 101:
                ToastUtil.showToast(context, "数据库IO错误");
                break;
        }
    }
}
