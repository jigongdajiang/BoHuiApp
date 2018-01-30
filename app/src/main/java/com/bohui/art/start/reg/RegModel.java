package com.bohui.art.start.reg;

import com.bohui.art.bean.start.LoginResult;
import com.bohui.art.bean.start.RegResult;
import com.bohui.art.bean.start.VerCodeResult;
import com.framework.core.http.EasyHttp;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/2
 * @description:
 */


public class RegModel implements RegContact.Model {

    @Override
    public Observable<LoginResult> reg(String mobile, String password) {
        return EasyHttp.post(RegContact.URL_REG)
                .params("mobile",mobile)
                .params("password",password)
                .params("code","123456")
                .execute(LoginResult.class);
    }
}
