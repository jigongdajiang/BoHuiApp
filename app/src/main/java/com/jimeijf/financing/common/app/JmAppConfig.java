package com.jimeijf.financing.common.app;

import com.jimeijf.core.app.AppConfig;

/**
 * @author : gongdaocai
 * @date : 2017/11/17
 * FileName:
 * @description:
 */


public class JmAppConfig extends AppConfig{
    /**
     * 基础Url获取
     * @return
     */
    public static String getBaseUrl(){
        if(IS_PUBLISH_VERSION){
            return "https://restv2.jimeijinfu.com/rest/";
        }else{
//            return "https://testrest.jimeijinfu.com/rest/";//外网测试
            return "http://192.168.1.224:8085/rest/";//内网测试
//        return "http://192.168.1.63:8085/rest/";//内网测试
//        return "http://192.168.1.157:10002/jm-rest/rest/";//张海
//        return "http://192.168.1.156:10002/jm-rest/rest/";//付兵
//        return "http://192.168.1.252:10002/jm-rest/rest/";//小龙
//        return "http://192.168.1.236:10002/jm-rest/rest/";//晋静
//        return "http://192.168.1.233:8080/jm-rest/rest/";//朱志光
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
