package com.bohui.art.start.splash;

import com.bohui.art.bean.start.SplashResult;
import com.bohui.art.common.net.AppProgressSubScriber;
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
                .subscribeWith(new AppProgressSubScriber<SplashResult>(mView,SplashContact.TAG_SPLASH){
                    @Override
                    protected void onResultSuccess(SplashResult result) {
                        mView.splashSuccess(result);
                    }
                }));
    }
}
