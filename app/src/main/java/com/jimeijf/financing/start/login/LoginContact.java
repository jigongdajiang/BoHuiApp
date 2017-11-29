package com.jimeijf.financing.start.login;

import com.jimeijf.core.base.BaseModel;
import com.jimeijf.core.base.BasePresenter;
import com.jimeijf.core.base.BaseView;
import com.jimeijf.core.http.exception.ApiException;
import com.jimeijf.financing.start.login.result.LoginResult;
import com.jimeijf.financing.start.login.result.VerCodeResult;

import io.reactivex.Observable;

/**
 * @author : gongdaocai
 * @date : 2017/11/17
 * FileName:
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
