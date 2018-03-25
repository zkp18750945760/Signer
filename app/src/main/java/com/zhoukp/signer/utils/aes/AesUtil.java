package com.zhoukp.signer.utils.aes;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

/**
 * 作者：zhoukp
 * 时间：2017/12/26 16:26
 * 邮箱：zhoukaiping@szy.cn
 * 作用：AES/ECB/PKCS5Padding  加密解密工具类
 */
public class AesUtil {
    /**
     * 密钥算法
     */
    private static final String KEY_ALGORITHM = "AES";

    /**
     * 加密/解密算法 / 工作模式 / 填充方式
     * Java 6支持PKCS5Padding填充方式
     * Bouncy Castle支持PKCS7Padding填充方式
     */
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * 生成密钥
     *
     * @return 密钥
     * @throws Exception e
     */
    public static String generateKey() throws Exception {
        //实例化密钥生成器
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        /**
         * 设置AES密钥长度
         * AES要求密钥长度为128位或192位或256位，java默认限制AES密钥长度最多128位
         * 如需192位或256位，则需要到oracle官网找到对应版本的jdk下载页，在"Additional Resources"中找到
         * "Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files",点击[DOWNLOAD]下载
         * 将下载后的local_policy.jar和US_export_policy.jar放到jdk安装目录下的jre/lib/security/目录下，替换该目录下的同名文件
         */
        kg.init(128);
        //生成密钥
        SecretKey secretKey = kg.generateKey();
        //获得密钥的字符串形式
        return Base64.encodeBase64String(secretKey.getEncoded());
    }

    /**
     * AES加密
     *
     * @param source 源字符串
     * @param key    密钥
     * @return 加密后的密文
     * @throws Exception e
     */
    public static String encrypt(String source, String key) throws Exception {
        byte[] sourceBytes = source.getBytes("UTF-8");
        BASE64Decoder decoder = new BASE64Decoder();
        BASE64Encoder encoder = new BASE64Encoder();
        byte[] keyBytes = decoder.decodeBuffer(key);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyBytes, KEY_ALGORITHM));
        byte[] decrypted = cipher.doFinal(sourceBytes);
        return encoder.encode(decrypted);
    }

    /**
     * AES解密
     *
     * @param encryptStr 加密后的密文
     * @param key        密钥
     * @return 源字符串
     * @throws Exception e
     */
    public static String decrypt(String encryptStr, String key) throws Exception {
        byte[] sourceBytes = Base64.decodeBase64(encryptStr);
        byte[] keyBytes = Base64.decodeBase64(key);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyBytes, KEY_ALGORITHM));
        byte[] decoded = cipher.doFinal(sourceBytes);
        return new String(decoded, "UTF-8");
    }
}
