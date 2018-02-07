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
            return "http://118.190.149.169:8080/";
        }else{
            return "http://118.190.149.169:8080/";//测试域名
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
