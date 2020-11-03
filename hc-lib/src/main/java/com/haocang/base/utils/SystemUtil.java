package com.haocang.base.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import androidx.annotation.RequiresApi;

import com.haocang.base.config.AppApplication;

import java.util.Locale;

public class SystemUtil {


    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return 语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }
//
//    /**
//     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
//     *
//     * @return  手机IMEI
//     */
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    public static String getIMEI(Context ctx) {
//        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
//        if (tm != null) {
//            return tm.getDeviceId();
//        }
//        return null;
//    }

    public static String getDevice() {
        return getDeviceBrand() + "--" + getSystemModel() + "--" + getSystemVersion() + "--" + getVersionName();
    }

    //版本名
    private static String getVersionName() {
        return getPackageInfo().versionName;
    }

    //获取版本info
    private static PackageInfo getPackageInfo() {
        PackageInfo pi = null;

        try {
            PackageManager pm = AppApplication.getContext().getPackageManager();
            pi = pm.getPackageInfo(AppApplication.getContext().getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

}
