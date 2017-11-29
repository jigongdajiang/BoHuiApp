package com.jimeijf.financing.start.login;

import com.jimeijf.core.http.EasyHttp;
import com.jimeijf.core.http.callback.CallClazzProxy;
import com.jimeijf.financing.common.net.CustomApiResult;
import com.jimeijf.financing.start.login.result.LoginResult;
import com.jimeijf.financing.start.login.result.StartRunResult;
import com.jimeijf.financing.start.login.result.VerCodeResult;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * @author : gaojigong
 * @date : 2017/11/17
 * @FileName:
 * @description:
 */


public class LoginModel implements LoginContact.Model {

    @Override
    public Observable<VerCodeResult> getVerCode(String mobile) {
        Observable<StartRunResult> startRunObservable = EasyHttp
                .post(LoginContact.STARTRUN_PATH)
                .execute(new CallClazzProxy<CustomApiResult<StartRunResult>, StartRunResult>(StartRunResult.class) {
                });
        return startRunObservable.flatMap(new Function<StartRunResult, ObservableSource<VerCodeResult>>() {
            @Override
            public ObservableSource<VerCodeResult> apply(StartRunResult startRunResult) throws Exception {
                return EasyHttp
                        .post(LoginContact.LOGINCODE_PATH)
                        .params("mobile","18660398602")
                        .execute(VerCodeResult.class);
            }
        });
    }

    @Override
    public Observable<LoginResult> login(String mobile, String verCode) {
        return EasyHttp
                .post(LoginContact.LOGIN_PATH)
                .params("mobile", mobile)
                .params("code", verCode)
                .execute(new CallClazzProxy<CustomApiResult<LoginResult>, LoginResult>(LoginResult.class) {
                });
    }
}
