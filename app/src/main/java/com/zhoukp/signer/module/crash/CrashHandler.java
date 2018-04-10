package com.zhoukp.signer.module.crash;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;

import com.zhoukp.signer.utils.Constant;
import com.zhoukp.signer.utils.TimeUtils;
import com.zhoukp.signer.utils.ToastUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.ACTIVITY_SERVICE;


/**
 * @author zhoukp
 * @time 2018/3/30 21:21
 * @email 275557625@qq.com
 * @function 错误日志收集类 当程序发生Uncaught异常的时候,有该类来接管程序,并记录错误日志
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    public static String TAG = "Crash";
    private static CrashHandler instance = new CrashHandler();
    /**
     * 系统默认的UncaughtException处理类
     */
    private Thread.UncaughtExceptionHandler defaultHandler;
    private Context context;

    /**
     * 用来存储设备信息和异常信息
     */
    private Map<String, String> infos = new HashMap<>();

    /**
     * 用于格式化日期,作为日志文件名的一部分
     */
    @SuppressLint("SimpleDateFormat")
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 保证只有一个CrashHandler实例
     */
    private CrashHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        return instance;
    }

    public static void setTag(String tag) {
        TAG = tag;
    }

    /**
     * 初始化
     *
     * @param context context
     */
    public void init(Context context) {
        this.context = context;
        // 获取系统默认的UncaughtException处理器
        defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
        autoClear(5);
    }

    /**
     * 文件删除
     *
     * @param autoClearDay 文件保存天数
     */
    public void autoClear(final int autoClearDay) {
        FileUtil.delete(getGlobalpath(), new FilenameFilter() {

            @Override
            public boolean accept(File file, String filename) {
                String s = FileUtil.getFileNameWithoutExtension(filename);
                int day = autoClearDay < 0 ? autoClearDay : -1 * autoClearDay;
                String date = "crash-" + DateUtil.getOtherDay(day);
                return date.compareTo(s) >= 0;
            }
        });
    }

    public static String getGlobalpath() {
        return Constant.appCrashPath;
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
//        if (!handleException(ex) && defaultHandler != null) {
//            // 如果用户没有处理则让系统默认的异常处理器来处理
//            defaultHandler.uncaughtException(thread, ex);
//        } else {
//            SystemClock.sleep(3000);
//            // 退出程序
//            android.os.Process.killProcess(android.os.Process.myPid());
//            System.exit(1);
//        }
        ActivityManager manager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        manager.killBackgroundProcesses(context.getPackageName());
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成
     *
     * @param ex ex
     * @return true:如果处理了该异常信息; 否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null)
            return false;
        try {
            // 使用Toast来显示异常信息
            new Thread() {
                @Override
                public void run() {
                    Looper.prepare();
                    ToastUtil.showToast(context, "很抱歉,程序出现异常,即将重启.");
                    Looper.loop();
                }
            }.start();
            // 收集设备参数信息
            collectDeviceInfo(context);
            // 保存日志文件
            saveCrashInfoFile(ex);
            SystemClock.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * 收集设备参数信息
     *
     * @param ctx context
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName + "";
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "收集包信息时出现错误", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                Log.e(TAG, "收集包信息时出现错误", e);
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex ex
     * @return 返回文件名称, 便于将文件传送到服务器
     * @throws Exception
     */
    private String saveCrashInfoFile(Throwable ex) throws Exception {
        StringBuffer sb = new StringBuffer();
        try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = sDateFormat.format(new java.util.Date());
            sb.append("\r\n").append(date).append("\n");
            for (Map.Entry<String, String> entry : infos.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                sb.append(key).append("=").append(value).append("\n");
            }

            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            ex.printStackTrace(printWriter);
            Throwable cause = ex.getCause();
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            printWriter.flush();
            printWriter.close();
            String result = writer.toString();
            sb.append(result);

            return writeFile(sb.toString());
        } catch (Exception e) {
            Log.e(TAG, "写入文件时出现错误", e);
            sb.append("写入文件时出现错误\r\n");
            writeFile(sb.toString());
        }
        return null;
    }

    private String writeFile(String sb) throws Exception {
        String fileName = "crash-" + TimeUtils.getCurrentTime() + ".log";
        if (FileUtil.hasSdcard()) {
            String path = getGlobalpath();
            File dir = new File(path);
            if (!dir.exists())
                dir.mkdirs();

            FileOutputStream fos = new FileOutputStream(path + fileName, true);
            fos.write(sb.getBytes());
            fos.flush();
            fos.close();
        }
        return fileName;
    }
}
