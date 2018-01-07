package com.bohui.art.start.welcome;

import com.bohui.art.bean.start.WelcomeResult;
import com.bohui.art.common.net.AppProgressSubScriber;
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
        .subscribeWith(new AppProgressSubScriber<WelcomeResult>(mView,WelcomeContact.TAG_WELCOME,mView){
            @Override
            protected void onResultSuccess(WelcomeResult result) {
                mView.welComeSuccess(result);
            }
        }));
    }
}
