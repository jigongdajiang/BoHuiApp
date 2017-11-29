package com.jimeijf.core.toast;

import android.content.Context;
import android.widget.Toast;

/**
 * @author : gaojigong
 * @date : 2017/11/14
 * @description:
 */


public class ToastShow {
    private static boolean useToast = true;
    private static IToastStrategy toastStrategy;
    public static void init(IToastStrategy strategy){
        if(strategy == null){
            strategy = new SysToastStrategy();
        }
        toastStrategy = strategy;
    }
    public static void init(){
        init(null);
    }
    private ToastShow(){}
    public static void show(Context context,String str,int duration){
        if(useToast){
            checkInit();
            toastStrategy.show(context,str,duration);
        }
    }
    private static void checkInit() {
        if(toastStrategy == null){
            throw new IllegalStateException("please init Toast strategy");
        }
    }
    public static void showShort(Context context,String str){
        show(context,str, Toast.LENGTH_SHORT);
    }
    public static void showLong(Context context,String str){
        show(context,str, Toast.LENGTH_LONG);
    }
    public static void showShort(Context context,int strResId){
        show(context,context.getResources().getString(strResId), Toast.LENGTH_SHORT);
    }
    public static void showLong(Context context,int strResId){
        show(context,context.getResources().getString(strResId), Toast.LENGTH_LONG);
    }

    public static void cancel(){
        checkInit();
        toastStrategy.cancel();
    }
}
