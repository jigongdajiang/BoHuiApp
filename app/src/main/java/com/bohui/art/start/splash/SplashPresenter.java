package com.bohui.art.start.splash;

import com.bohui.art.bean.start.SplashResult;
import com.framework.core.http.exception.ApiException;
import com.framework.core.http.subsciber.BaseSubscriber;

/**
 * @author : gaojigong
 * @date : 2018/1/2
 * @description:
 */


public class SplashPresenter extends SplashContact.Presenter {
    @Override
    public void splash() {
        mRxManage.add(mModel.splash()
                .subscribeWith(new BaseSubscriber<SplashResult>(){
                    @Override
                    public void onNext(SplashResult result) {
                        super.onNext(result);
                        mView.splashSuccess(result);
                    }

                    @Override
                    public void onError(ApiException e) {
                        mView.handleException(SplashContact.TAG_SPLASH,e);
                    }
                }));
    }
}
