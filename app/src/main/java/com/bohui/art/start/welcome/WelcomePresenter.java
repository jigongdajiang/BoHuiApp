package com.bohui.art.start.welcome;

import com.bohui.art.bean.start.WelcomeResult;
import com.framework.core.http.exception.ApiException;
import com.framework.core.http.subsciber.BaseSubscriber;

/**
 * @author : gaojigong
 * @date : 2018/1/2
 * @description:
 */


public class WelcomePresenter extends WelcomeContact.Presenter {
    @Override
    public void welCome() {
        mRxManage.add(mModel.welCome()
        .subscribeWith(new BaseSubscriber<WelcomeResult>(){
            @Override
            public void onNext(WelcomeResult welcomeResult) {
                super.onNext(welcomeResult);
                mView.welComeSuccess(welcomeResult);
            }

            @Override
            public void onError(ApiException e) {
                mView.handleException(WelcomeContact.TAG_WELCOME,e);
            }
        }));
    }
}
