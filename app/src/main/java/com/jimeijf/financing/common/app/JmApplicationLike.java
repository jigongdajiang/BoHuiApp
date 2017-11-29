package com.jimeijf.financing.common.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.multidex.MultiDex;

import com.jimeijf.core.app.AtyManager;
import com.jimeijf.core.http.EasyHttp;
import com.jimeijf.core.http.interceptor.HttpLoggingInterceptor;
import com.jimeijf.core.log.LoggerStrategy;
import com.jimeijf.core.log.PrintLog;
import com.jimeijf.core.thinker.Log.MyLogImp;
import com.jimeijf.core.thinker.util.TinkerManager;
import com.jimeijf.core.toast.EToastStrategy;
import com.jimeijf.core.toast.ToastShow;
import com.jimeijf.core.util.ExceptionHandler;
import com.jimeijf.core.util.StrictUtil;
import com.jimeijf.financing.common.net.ParamConvert;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * @author : gaojigong
 * @date : 2017/11/27
 * @description:
 */

@DefaultLifeCycle(
        application = "com.jimeijf.financing.common.app.JmApplication", //application类名
        flags = ShareConstants.TINKER_ENABLE_ALL,                 //tinkerFlags
        loaderClass = "com.tencent.tinker.loader.TinkerLoader",   //loaderClassName, 我们这里使用默认即可!
        loadVerifyFlag = false)                                   //tinkerLoadVerifyFlag
public class JmApplicationLike extends DefaultApplicationLike {
    private static JmApplicationLike mAppliactionLike;
    private RefWatcher refWatcher;
    public JmApplicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        //you must install multiDex whatever tinker is installed!
        MultiDex.install(base);

        TinkerManager.setTinkerApplicationLike(this);

        TinkerManager.initFastCrashProtect();
        //should set before tinker is installed
        TinkerManager.setUpgradeRetryEnable(true);

        //optional set logIml, or you can use default debug log
        TinkerInstaller.setLogIml(new MyLogImp());

        //installTinker after load multiDex
        //or you can put com.tencent.tinker.** to main dex
        TinkerManager.installTinker(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAppliactionLike = this;
        StrictUtil.openStrictMode(JmAppConfig.isOpenStrictMode());
        ExceptionHandler.getInstance().init(getApplication(), JmAppConfig.isOpenExceptionHandler());
        if(JmAppConfig.isOpenLeakCanary()){
            if (LeakCanary.isInAnalyzerProcess(getApplication())) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                return;
            }
            refWatcher = LeakCanary.install(getApplication());
        }

        registerActivityCallBack();
        PrintLog.init(new LoggerStrategy());
        ToastShow.init(new EToastStrategy());
        EasyHttp.init(getApp());
        EasyHttp.getInstance()
                .setBaseUrl(JmAppConfig.getBaseUrl())
                .setParamConvert(new ParamConvert(getApp(), JmAppConfig.isEntrypt()))
                .addInterceptor(new HttpLoggingInterceptor("RequestLog").setLevel(HttpLoggingInterceptor.Level.BODY));

    }

    private void registerActivityCallBack() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
            getApplication().registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
                private static final String TAG = "ActivityLifecycleCallbacks";
                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                    PrintLog.d(TAG,activity.getLocalClassName()+"onActivityCreated");
                    AtyManager.getInstance().attach(activity);
                }

                @Override
                public void onActivityStarted(Activity activity) {
                    PrintLog.d(TAG,activity.getLocalClassName()+"onActivityStarted");
                }

                @Override
                public void onActivityResumed(Activity activity) {
                    PrintLog.d(TAG,activity.getLocalClassName()+"onActivityResumed");
                }

                @Override
                public void onActivityPaused(Activity activity) {
                    PrintLog.d(TAG,activity.getLocalClassName()+"onActivityPaused");
                }

                @Override
                public void onActivityStopped(Activity activity) {
                    PrintLog.d(TAG,activity.getLocalClassName()+"onActivityStopped");
                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                    PrintLog.d(TAG,activity.getLocalClassName()+"onActivitySaveInstanceState");
                }

                @Override
                public void onActivityDestroyed(Activity activity) {
                    PrintLog.d(TAG,activity.getLocalClassName()+"onActivityDestroyed");
                    AtyManager.getInstance().detach(activity);
                }
            });
        }
    }

    public static Application getApp(){
        return mAppliactionLike.getApplication();
    }

    public static RefWatcher getRefWatcher(){
        return mAppliactionLike.refWatcher;
    }
}
