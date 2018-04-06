package com.zhoukp.signer.module.course;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoukp
 * @time 2018/4/3 21:21
 * @email 275557625@qq.com
 * @function
 */
public class MySubjectParser {

    public List<Subject> parse(String parseString) {
        List<Subject> courses = new ArrayList<>();

        CourseBean courseBean = new Gson().fromJson(parseString, CourseBean.class);
        for (CourseBean.DataBean dataBean : courseBean.getData()) {
            String name = dataBean.getName();
            String location = dataBean.getLocation();
            int day = dataBean.getDay();
            int start = dataBean.getStart();
            int end = dataBean.getEnd();
            String weeks = dataBean.getWeeks();
            courses.add(new Subject("", name, location, "", getWeekList(weeks), start, (end - start + 1), day, -1, null));

        }

        return courses;
    }

    public static List<Subject> parse(CourseBean bean) {
        List<Subject> courses = new ArrayList<>();
        for (CourseBean.DataBean dataBean : bean.getData()) {
            String name = dataBean.getName();
            String location = dataBean.getLocation();
            int day = dataBean.getDay();
            int start = dataBean.getStart();
            int end = dataBean.getEnd();
            String weeks = dataBean.getWeeks();
            courses.add(new Subject("", name, location, "", getWeekList(weeks), start, (end - start + 1), day, -1, null));

        }

        return courses;
    }

    public static List<Integer> getWeekList(String weeksString) {
        List<Integer> weekList = new ArrayList<>();
        if (weeksString == null || weeksString.length() == 0) return weekList;

        weeksString = weeksString.replaceAll("[^\\d\\-\\,]", "");
        if (weeksString.indexOf(",") != -1) {
            String[] arr = weeksString.split(",");
            for (int i = 0; i < arr.length; i++) {
                weekList.addAll(getWeekList2(arr[i]));
            }
        } else {
            weekList.addAll(getWeekList2(weeksString));
        }
        return weekList;
    }

    public static List<Integer> getWeekList2(String weeksString) {
        List<Integer> weekList = new ArrayList<>();
        int first = -1, end = -1, index = -1;
        if ((index = weeksString.indexOf("-")) != -1) {
            first = Integer.parseInt(weeksString.substring(0, index));
            end = Integer.parseInt(weeksString.substring(index + 1));
        } else {
            first = Integer.parseInt(weeksString);
            end = first;
        }

        for (int i = first; i <= end; i++)
            weekList.add(i);
        return weekList;
    }

}
