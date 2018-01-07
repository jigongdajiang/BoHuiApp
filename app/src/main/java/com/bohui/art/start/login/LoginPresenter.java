package com.bohui.art.start.login;

import com.bohui.art.bean.start.LoginResult;
import com.bohui.art.common.net.AppProgressSubScriber;
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
        .subscribeWith(new AppProgressSubScriber<LoginResult>(mView,LoginContact.TAG_LOGIN,mView){
            @Override
            protected void onResultSuccess(LoginResult result) {
                mView.loginSuccess(result);
            }
        }));
    }
}
