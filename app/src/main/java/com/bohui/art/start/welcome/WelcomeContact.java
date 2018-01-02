package com.bohui.art.start.welcome;

import com.bohui.art.bean.start.WelcomeResult;
import com.framework.core.base.BaseModel;
import com.framework.core.base.BasePresenter;
import com.framework.core.base.BaseView;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/2
 * @description:
 */


public interface WelcomeContact {
    String URL_WELCOME = "";
    String TAG_WELCOME = "tag_welcome";
    interface Model extends BaseModel{
        Observable<WelcomeResult> welCome();
    }
    interface View extends BaseView{
        void welComeSuccess(WelcomeResult result);
    }
    abstract class Presenter extends BasePresenter<Model,View>{
        public abstract void welCome();
    }
}
