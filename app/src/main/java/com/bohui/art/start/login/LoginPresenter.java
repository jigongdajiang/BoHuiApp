package com.bohui.art.start.login;

import com.framework.core.http.exception.ApiException;
import com.framework.core.http.subsciber.BaseSubscriber;
import com.bohui.art.start.login.result.LoginResult;
import com.bohui.art.start.login.result.VerCodeResult;

/**
 * @author : gaojigong
 * @date : 2017/11/17
 * @description:
 */


public class LoginPresenter extends LoginContact.Presenter {
    @Override
    void getVerCode(String mobile) {
        mModel.getVerCode(mobile).subscribe(new BaseSubscriber<VerCodeResult>() {
            @Override
            public void onError(ApiException e) {
                mView.requestFailed(e);
            }

            @Override
            public void onNext(VerCodeResult r) {
                mView.getVerCodeSuccess(r);
            }
        });
    }

    @Override
    void login(String mobile, String verCode) {
        mModel.login(mobile,verCode).subscribe(new BaseSubscriber<LoginResult>() {
            @Override
            public void onError(ApiException e) {
                mApiExceptionHandler.exceptionHandle(e);
            }

            @Override
            public void onNext(LoginResult loginResult) {
                mView.loginSuccess(loginResult);
            }
        });
    }
}
