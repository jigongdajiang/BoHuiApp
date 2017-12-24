package com.bohui.art.common.app;

import com.framework.core.app.BaseAppConfig;

/**
 * @author : gaojigong
 * @date : 2017/11/17
 * @description:
 */


public class AppConfig extends BaseAppConfig {
    /**
     * 基础Url获取
     * @return
     */
    public static String getBaseUrl(){
        if(IS_PUBLISH_VERSION){
            return "https://restv2.jimeijinfu.com/rest/";
        }else{
            return "http://192.168.31.191:8080/";//武强电脑
        }
    }

    /**
     * 是否加密
     */
    public static boolean isEntrypt(){
        if(IS_PUBLISH_VERSION){
            //正式版开启
            return true;
        }else{
            return false;
        }
    }

    public static boolean isOpenStrictMode(){
        if(IS_PUBLISH_VERSION){
            //正式版关闭
            return false;
        }else{
            return true;
        }
    }

    public static boolean isOpenExceptionHandler(){
        if(IS_PUBLISH_VERSION){
            //正式版开启
            return true;
        }else{
            return false;
        }
    }

    public static boolean isOpenLeakCanary(){
        if(IS_PUBLISH_VERSION){
            //正式版关闭
            return false;
        }else{
            return true;
        }
    }
}
