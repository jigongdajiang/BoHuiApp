package com.bohui.art.common.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.multidex.MultiDex;

import com.framework.core.app.AtyManager;
import com.framework.core.cache.converter.GsonDiskConverter;
import com.framework.core.http.EasyHttp;
import com.framework.core.http.interceptor.HttpLoggingInterceptor;
import com.framework.core.log.LoggerStrategy;
import com.framework.core.log.PrintLog;
import com.framework.core.thinker.Log.MyLogImp;
import com.framework.core.thinker.util.TinkerManager;
import com.framework.core.toast.EToastStrategy;
import com.framework.core.toast.ToastShow;
import com.framework.core.util.ExceptionHandler;
import com.framework.core.util.StrictUtil;
import com.bohui.art.common.net.ParamConvert;
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
        application = "com.bohui.art.common.app.PApplication", //application类名
        flags = ShareConstants.TINKER_ENABLE_ALL,                 //tinkerFlags
        loaderClass = "com.tencent.tinker.loader.TinkerLoader",   //loaderClassName, 我们这里使用默认即可!
        loadVerifyFlag = false)                                   //tinkerLoadVerifyFlag
public class PApplicationLike extends DefaultApplicationLike {
    private static PApplicationLike mAppliactionLike;
    private RefWatcher refWatcher;
    public PApplicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
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
        StrictUtil.openStrictMode(AppConfig.isOpenStrictMode());
        ExceptionHandler.getInstance().init(getApplication(), AppConfig.isOpenExceptionHandler());
        if(AppConfig.isOpenLeakCanary()){
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
                .setBaseUrl(AppConfig.getBaseUrl())
                .setCacheDiskConverter(new GsonDiskConverter())
                .setParamConvert(new ParamConvert(getApp()))
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
