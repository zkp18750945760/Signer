package com.zhoukp.coursetable;

import android.content.Context;

import java.util.List;

/**
 * @author zhoukp
 * @time 2018/4/3 21:20
 * @email 275557625@qq.com
 * @function
 */
public class MySubjectModel {

    public static List<Subject> loadDefaultSubjects(Context context) {
        //json转义
        String json = AssetsHelper.readAssetsTxt(context, "courseJson");
        return loadSubjects(json);
    }

    public static List<Subject> loadSubjects(String json) {
        MySubjectParser mySubjectParser = new MySubjectParser();
        return mySubjectParser.parse(json);
    }
}