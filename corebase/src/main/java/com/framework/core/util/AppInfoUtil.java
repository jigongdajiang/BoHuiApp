package com.framework.core.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * @author : gaojigong
 * @date : 2017/11/14
 * @description:
 */


public class AppInfoUtil {
    /**
     * 获取手机版本号
     */
    public static int getAppVersionCode(Context context) {
        int appVersion;
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            appVersion = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            appVersion = 1;
        }
        return appVersion;
    }

    /**
     * 获取当前应用程序的版本号 versionName
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "版本号未知";
        }
    }
    /**
     * 获取程序的包名
     */
    public static String getPackname(Context context) {
        String packageNames = "";
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            // 当前版本的包名
            packageNames = info.packageName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageNames;
    }
    /*
    * 获取程序的签名
    */
    public static String getAppSignature(Context context, String packname) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo packinfo = pm.getPackageInfo(packname, PackageManager.GET_SIGNATURES);
            //获取到所有的权限
            return packinfo.signatures[0].toCharsString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
}
