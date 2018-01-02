package com.bohui.art.start.splash;

import com.bohui.art.bean.start.SplashResult;
import com.framework.core.http.EasyHttp;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/2
 * @description:
 */


public class SplashModel implements SplashContact.Model {
    @Override
    public Observable<SplashResult> splash() {
        return EasyHttp.post(SplashContact.URL_SPLASH)
                .execute(SplashResult.class);
    }
}
