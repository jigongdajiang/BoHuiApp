package com.bohui.art.start.welcome;

import com.bohui.art.bean.start.WelcomeResult;
import com.framework.core.http.EasyHttp;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/2
 * @description:
 */


public class WelcomeModel implements WelcomeContact.Model {
    @Override
    public Observable<WelcomeResult> welCome() {
        return EasyHttp.post(WelcomeContact.URL_WELCOME)
                .execute(WelcomeResult.class);
    }
}
