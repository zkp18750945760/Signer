package com.zhoukp.signer.utils.aes;

import java.security.MessageDigest;

import Decoder.BASE64Encoder;

/**
 * 作者：zhoukp
 * 时间：2017/12/26 9:55
 * 邮箱：zhoukaiping@szy.cn
 * 作用：md5加密后转base64
 */
public class MD5 {
    public static String encode(String string) throws Exception {
        //确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        String newstr = base64en.encode(md5.digest(string.getBytes("utf-8")));
        return newstr;
    }
}
