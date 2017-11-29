package com.framework.core.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.UUID;

/**
 * @author : gaojigong
 * @date : 2017/11/14
 * @description:
 */


public class DeviceInfoUtil {
    /**
     * 获取IMEI
     */
    public static String getImei(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return telephonyManager.getDeviceId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取手机的IMSI
     * 国际移动用户识别码
     */
    public static String getImsi(Context context) {
        try {
            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return manager.getSubscriberId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 手机唯一性
     */
    public static String getUnique(Context context) {
        String tmDevice, androidId;
        tmDevice = getImei(context);
        androidId = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32));
        return deviceUuid.toString().replaceAll("-", "");
    }

    /**
     * 获取设置的渠道号
     */
    public static Integer getChannel(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            return bundle.getInt("UMENG_CHANNEL");//键
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 获得Android_id
     */
    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 唯一识别码;
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }
}
