package com.bohui.art.start.login;

import com.bohui.art.bean.start.LoginResult;
import com.framework.core.http.EasyHttp;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/2
 * @description:
 */


public class LoginModel implements LoginContact.Model {
    @Override
    public Observable<LoginResult> login(String mobile,String password) {
        return EasyHttp.post(LoginContact.URL_LOGIN)
                .params("mobile",mobile)
                .params("password",password)
                .execute(LoginResult.class);
    }
}
