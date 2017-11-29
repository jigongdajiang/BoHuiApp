package com.bohui.art.start.login;

import com.framework.core.base.BaseModel;
import com.framework.core.base.BasePresenter;
import com.framework.core.base.BaseView;
import com.framework.core.http.exception.ApiException;
import com.bohui.art.start.login.result.LoginResult;
import com.bohui.art.start.login.result.VerCodeResult;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2017/11/17
 * @description:
 */


public interface LoginContact {
    String STARTRUN_PATH = "common/startup/v2.0";
    String LOGINCODE_PATH = "login/code/v2.0";
    String LOGIN_PATH = "login/submit/v2.0";
    interface Model extends BaseModel {
        Observable<VerCodeResult> getVerCode(String mobile);
        Observable<LoginResult> login(String mobile,String verCode);
    }

    interface View extends BaseView {
        void getVerCodeSuccess(VerCodeResult verCodeResult);
        void loginSuccess(LoginResult loginResult);
        void requestFailed(ApiException e);
    }

    abstract class Presenter extends BasePresenter<Model,View> {
        abstract void getVerCode(String mobile);
        abstract void login(String mobile,String verCode);
    }
}
