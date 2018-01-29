package com.bohui.art.start.login;

import com.bohui.art.bean.start.LoginResult;
import com.bohui.art.common.net.mvp.BaseLoadingView;
import com.framework.core.base.BaseModel;
import com.framework.core.base.BasePresenter;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/2
 * @description:
 */


public interface LoginContact {
    String URL_LOGIN = "login/loigin";
    String TAG_LOGIN = "tag_login";
    interface Model extends BaseModel{
        Observable<LoginResult> login(String mobile,String password);
    }
    interface View extends BaseLoadingView {
        void loginSuccess(LoginResult result);
    }
    abstract class Presenter extends BasePresenter<Model,View>{
        public abstract void login(String mobile,String password);
    }
}
