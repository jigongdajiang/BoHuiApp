package com.bohui.art.start.splash;

import com.framework.core.base.BaseModel;
import com.framework.core.base.BasePresenter;
import com.framework.core.base.BaseView;
/**
 * @author : gaojigong
 * @date : 2017/11/17
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
