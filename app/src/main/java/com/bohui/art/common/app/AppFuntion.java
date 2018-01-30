package com.bohui.art.common.app;

import android.app.ActivityManager;
import android.content.Context;

import com.bohui.art.bean.mine.LogoutResult;
import com.bohui.art.bean.start.LoginResult;
import com.framework.core.app.AtyManager;
import com.framework.core.cache.core.CacheCoreFactory;
import com.framework.core.http.EasyHttp;
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
     * @return true 已登录  未登录
     */
    public static boolean isLogin() {
        String token = getToken();
        return !StrOperationUtil.isEmpty(token);
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

    public static long getUid(){
        try {
            return getUserInfo().getUid();
        }catch (Exception e){
            return -1;
        }
    }
    public static String getToken(){
        try {
            return getUserInfo().getToken();
        }catch (Exception e){
            return "";
        }
    }
    public static void saveUserInfo(LoginResult user){
         CacheCoreFactory.getPreferenceCache(PApplicationLike.getApp()).save(SharePreferenceKey.ACCOUNT_USER,user);
    }
    public static LoginResult getUserInfo(){
        return CacheCoreFactory.getPreferenceCache(PApplicationLike.getApp()).load(LoginResult.class,SharePreferenceKey.ACCOUNT_USER);
    }

    /**
     * 静态退出登录
     */
    public static void staticLogout(Context context) {
        CacheCoreFactory.getPreferenceCache(context).remove(SharePreferenceKey.ACCOUNT_USER);
        EasyHttp.clearCache();
    }

}
