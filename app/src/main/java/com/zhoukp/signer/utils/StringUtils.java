package com.zhoukp.signer.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    /**
     * 返回单个字符串，若匹配到多个的话就返回第一个
     *
     * @param str   字符串
     * @param regex 正则表达式
     * @return 符合条件的子串
     */
    public static String getSubString(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);// 匹配的模式
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }
}
