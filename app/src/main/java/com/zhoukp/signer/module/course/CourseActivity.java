package com.zhoukp.signer.module.course;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhoukp.signer.R;
import com.zhoukp.signer.module.course.view.OnSubjectBindViewListener;
import com.zhoukp.signer.module.course.view.OnSubjectItemClickListener;
import com.zhoukp.signer.module.course.view.OnSubjectItemLongClickListener;
import com.zhoukp.signer.module.course.view.SubjectBean;
import com.zhoukp.signer.module.course.view.TimetableView;
import com.zhoukp.signer.view.dialog.ProgressDialog;
import com.zhoukp.signer.module.login.LoginBean;
import com.zhoukp.signer.module.login.UserUtil;
import com.zhoukp.signer.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author zhoukp
 * @time 2018/4/5 15:33
 * @email 275557625@qq.com
 * @function
 */
public class CourseActivity extends Activity implements OnSubjectItemClickListener,
        View.OnClickListener, OnSubjectBindViewListener, OnSubjectItemLongClickListener, CourseView {

    private TimetableView mTimetableView;
    private TextView mTitleTextView;
    private LinearLayout backLayout;

    private int curWeek = 6;
    private List<SubjectBean> subjectBeans;

    private ProgressDialog dialog;
    private CoursePresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        ActivityTools.setTransparent(this);
        inits();
    }

    private void inits() {
        mTitleTextView = findViewById(R.id.id_title);
        backLayout = findViewById(R.id.id_back);
        backLayout.setOnClickListener(this);

        presenter = new CoursePresenter();
        presenter.attachView(this);
        LoginBean.UserBean user = UserUtil.getInstance().getUser();
        presenter.getCourse(user.getUserId(), user.getUserGrade(), user.getUserMajor(), user.getUserClass());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    /**
     * Item点击处理
     *
     * @param subjectList 该Item处的课程集合
     */
    @Override
    public void onItemClick(View v, List<SubjectBean> subjectList) {
        int size = subjectList.size();
        String subjectStr = "";

        for (int i = 0; i < size; i++) {
            SubjectBean bean = subjectList.get(i);
            subjectStr += bean.getName() + "\n";
            subjectStr += "上课周次:" + bean.getWeekList().toString() + "\n";
            subjectStr += "时间:周" + bean.getDay() + "," + bean.getStart() + "至" + (bean.getStart() + bean.getStep() - 1) + "节上";
            if (i != (size - 1)) {
                subjectStr += "\n########\n";
            }
        }
        subjectStr += "\n";

        Toast.makeText(this, "该时段有" + size + "门课\n\n" + subjectStr, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_back:
                setResult(RESULT_OK);
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 绑定TitleView，你可以自定义一个文本的填充规则，
     * 当文本需要变化时将由系统回调
     * <p>
     * 当有以下事件时将会触发该函数
     * 1.setCurWeek(...)被调用
     * 2.setCurTerm(...)被调用
     * 3.notifyDataSourceChanged()被调用
     * 4.showTimetableView()被调用
     * 5.changeWeek(...)被调用
     *
     * @param titleTextView 绑定的TextView
     */
    @Override
    public void onBindTitleView(TextView titleTextView, int curWeek, String curTerm, List<SubjectBean> subjectBeans) {
        String text = "第" + curWeek + "周" + ",共" + subjectBeans.size() + "门课";
        titleTextView.setText(text);
        this.curWeek = curWeek;
    }

    @Override
    public void onItemLongClick(View view, int day, int start) {
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
    public void getCourseSuccess(CourseBean bean) {
        subjectBeans = transform(MySubjectParser.parse(bean));

        mTimetableView = findViewById(R.id.timetableView);
        mTimetableView.setDataSource(subjectBeans)
                .setCurTerm("大三上学期")
                .setCurWeek(curWeek)
                .bindTitleView(mTitleTextView)
                .setShowDashLayer(true)
                .setOnSubjectBindViewListener(this)
                .setOnSubjectItemClickListener(this)
                .setOnSubjectItemLongClickListener(this)
                .showTimetableView();

        //调用过showSubjectView后需要调用changWeek()
        //第二个参数为true时在改变课表布局的同时也会将第一个参数设置为当前周
        //第二个参数为false时只改变课表布局
        mTimetableView.changeWeek(curWeek, true);
    }

    /**
     * 自定义转换规则,将自己的课程对象转换为所需要的对象集合
     *
     * @param mySubjects
     * @return
     */
    public List<SubjectBean> transform(List<Subject> mySubjects) {
        //待返回的集合
        List<SubjectBean> subjectBeans = new ArrayList<>();

        //保存课程名、颜色的对应关系
        Map<String, Integer> colorMap = new HashMap<>();
        int colorCount = 1;

        //开始转换
        for (int i = 0; i < mySubjects.size(); i++) {
            Subject mySubject = mySubjects.get(i);
            //计算课程颜色
            int color;
            if (colorMap.containsKey(mySubject.getName())) {
                color = colorMap.get(mySubject.getName());
            } else {
                colorMap.put(mySubject.getName(), colorCount);
                color = colorCount;
                colorCount++;
            }
            //转换
            subjectBeans.add(new SubjectBean(mySubject.getName(), mySubject.getRoom(), mySubject.getTeacher(), mySubject.getWeekList(),
                    mySubject.getStart(), mySubject.getStep(), mySubject.getDay(), color, mySubject.getTime()));
        }
        return subjectBeans;
    }

    @Override
    public void getCourseError(int status) {
        switch (status) {
            case 100:
                ToastUtil.showToast(this, "获取课程表失败");
                break;
            case 101:
                ToastUtil.showToast(this, "管理员还没有上传课表");
                break;
            case 102:
                ToastUtil.showToast(this, "数据库IO错误");
                break;
        }
    }
}
