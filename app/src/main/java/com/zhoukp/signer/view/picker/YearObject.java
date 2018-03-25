package com.zhoukp.signer.view.picker;

import java.util.ArrayList;

/**
 * @author zhoukp
 * @time 2018/2/1 19:05
 * @email 275557625@qq.com
 * @function
 */

public class YearObject {

    private String studentID;
    private String year;

    public YearObject() {
    }

    public YearObject(String studentID, String year) {
        this.studentID = studentID.substring(0, 2);
        this.year = year;
    }

    public ArrayList<String> getYears() {
        ArrayList<String> years = new ArrayList<>();
        int id = Integer.parseInt(studentID);
        while (id <= Integer.parseInt(year.substring(2))) {
            years.add("20" + id);
            id++;
        }
        return years;
    }

    public ArrayList<String> getSchoolYears() {
        ArrayList<String> schoolYears = new ArrayList<>();
        int id = Integer.parseInt(studentID);
        while (id <= Integer.parseInt(year.substring(2))) {
            schoolYears.add("20" + id + "-" + "20" + (id + 1) + "学年");
            id++;
        }
        return schoolYears;
    }

    public ArrayList<String> getMonths() {
        ArrayList<String> months = new ArrayList<>();
        for (int i = 1; i < 13; i++) {
            months.add(i + "");
        }
        return months;
    }
}
