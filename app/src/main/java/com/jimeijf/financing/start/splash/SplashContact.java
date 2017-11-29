package com.jimeijf.financing.start.splash;

import com.jimeijf.core.base.BaseModel;
import com.jimeijf.core.base.BasePresenter;
import com.jimeijf.core.base.BaseView;
/**
 * @author : gongdaocai
 * @date : 2017/11/17
 * FileName:
 * @description:
 */


public interface SplashContact {
    String SPLASH_URL = "common/appupdate/v1.1.0";

    interface Model extends BaseModel {

    }

    interface View extends BaseView {

    }

    abstract class Presenter extends BasePresenter<SplashContact.Model,SplashContact.View> {

    }
}
