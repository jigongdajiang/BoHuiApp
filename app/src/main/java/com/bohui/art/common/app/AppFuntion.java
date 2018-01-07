package com.bohui.art.common.app;

import android.app.ActivityManager;
import android.content.Context;

import com.framework.core.app.AtyManager;
import com.framework.core.cache.core.CacheCoreFactory;
import com.framework.core.util.StrOperationUtil;

/**
 * @author : gaojigong
 * @date : 2017/12/1
 * @description:
 * 全局功能类
 * 1.是否登录
 * 2.退出App
 * 3.清空各种缓存功能
 */


public class AppFuntion {
    /**
     * 是否已登录
     *
     * @param context
     * @return true 已登录  未登录
     */
    public static boolean isLogin(Context context) {
        String ticket = CacheCoreFactory.getPreferenceCache(context).load(String.class, SharePreferenceKey.TICKET);
        return !StrOperationUtil.isEmpty(ticket);
    }

    public static void exitApp(Context context){
        //关闭所有Activity
        AtyManager.getInstance().AppExit(context);
        try {
            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
