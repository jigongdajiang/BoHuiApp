package com.bohui.art.start.login;

import com.bohui.art.bean.start.LoginResult;
import com.framework.core.http.exception.ApiException;
import com.framework.core.http.subsciber.BaseSubscriber;

/**
 * @author : gaojigong
 * @date : 2018/1/2
 * @description:
 */


public class LoginPresenter extends LoginContact.Presenter {
    @Override
    public void login(String phone, String pwd) {
        mRxManage.add(mModel.login(phone,pwd)
        .subscribeWith(new BaseSubscriber<LoginResult>(){
            @Override
            public void onNext(LoginResult result) {
                super.onNext(result);
                mView.loginSuccess(result);
            }

            @Override
            public void onError(ApiException e) {
                mView.handleException(LoginContact.TAG_LOGIN,e);
            }
        }));
    }
}
