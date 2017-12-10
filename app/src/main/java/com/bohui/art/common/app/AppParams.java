package com.bohui.art.common.app;

/**
 * @author : gaojigong
 * @date : 2017/11/30
 * @description:
 * 全局参数存储
 */


public class AppParams {
    /**
     * 是否启动过  对应存储的是boolean
     */
    public boolean splashStarted;
    /**
     * 第一次进入主页面
     */
    public boolean isfirst2mian;


    private static AppParams mInstance;

    private AppParams(){

    }
    public static AppParams getInstance(){
        if(mInstance == null){
            synchronized (AppParams.class){
                if(mInstance == null){
                    mInstance = new AppParams();
                }
            }
        }
        return mInstance;
    }
}
