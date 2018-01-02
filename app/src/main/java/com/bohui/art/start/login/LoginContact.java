package com.bohui.art.start.login;

import com.bohui.art.bean.start.LoginResult;
import com.framework.core.base.BaseModel;
import com.framework.core.base.BasePresenter;
import com.framework.core.base.BaseView;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/2
 * @description:
 */


public interface LoginContact {
    String URL_LOGIN = "";
    String TAG_LOGIN = "tag_login";
    interface Model extends BaseModel{
        Observable<LoginResult> login(String phone,String pwd);
    }
    interface View extends BaseView{
        void loginSuccess(LoginResult result);
    }
    abstract class Presenter extends BasePresenter<Model,View>{
        public abstract void login(String phone,String pwd);
    }
}
