package com.bohui.art.start.splash;

import com.bohui.art.bean.start.SplashResult;
import com.bohui.art.common.net.mvp.BaseLoadingView;
import com.framework.core.base.BaseModel;
import com.framework.core.base.BasePresenter;

import io.reactivex.Observable;


/**
 * @author : gaojigong
 * @date : 2018/1/2
 * @description:
 */


public interface SplashContact {
    String URL_SPLASH = "user/startUp";
    String TAG_SPLASH = "tag_splash";
    interface Model extends BaseModel{
        Observable<SplashResult> splash();
    }
    interface View extends BaseLoadingView{
        void splashSuccess(SplashResult result);
    }
    abstract class Presenter extends BasePresenter<Model,View>{
        public abstract void splash();
    }
}
