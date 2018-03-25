package com.zhoukp.signer.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * 作者： KaiPingZhou
 * 时间：2017/10/24 10:42
 * 邮箱：275557625@qq.com
 * 作用：为了实现在设备上更通用的获取设备唯一标识
 * 我们可以实现这样的一个类，为每个设备产生唯一的UUID
 * 以ANDROID_ID为基础
 * 在获取失败时以TelephonyManager.getDeviceId()为备选方法
 * 如果再失败，使用UUID的生成策略
 */
public class DeviceUuidFactory {
    protected static final String PREFS_FILE = "gank_device_id.xml";
    protected static final String PREFS_DEVICE_ID = "gank_device_id";
    protected static String uuid;

    /**
     * 获取唯一标识码
     *
     * @param context context
     * @return uuid
     */
    public synchronized static String getUDID(Context context) {
        if (uuid == null) {
            if (uuid == null) {
                final SharedPreferences prefs = context.getApplicationContext().getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
                final String id = prefs.getString(PREFS_DEVICE_ID, null);
                if (id != null) {
                    uuid = id;
                } else {

                    final String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                    try {
                        if (!"9774d56d682e549c".equals(androidId)) {
                            uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8")).toString();
                        } else {
                            final String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
                            uuid = deviceId != null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")).toString() : UUID.randomUUID().toString();
                        }
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                    prefs.edit().putString(PREFS_DEVICE_ID, uuid).apply();
                }
            }
        }
        System.out.println(uuid);
        return uuid;
    }
}
