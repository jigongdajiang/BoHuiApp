package com.bohui.art.start.reg;

import com.bohui.art.bean.start.LoginResult;
import com.bohui.art.bean.start.RegResult;
import com.bohui.art.bean.start.VerCodeResult;
import com.bohui.art.common.net.AppProgressSubScriber;
import com.framework.core.http.exception.ApiException;
import com.framework.core.http.subsciber.BaseSubscriber;

/**
 * @author : gaojigong
 * @date : 2018/1/2
 * @description:
 */


public class RegPresenter extends RegContact.Presenter {
    @Override
    public void reg(String mobile,String password) {
        mRxManage.add(mModel.reg(mobile,password)
        .subscribeWith(new AppProgressSubScriber<LoginResult>(mView,RegContact.TAG_REG,mView){
            @Override
            protected void onResultSuccess(LoginResult regResult) {
                mView.regSuccess(regResult);
            }
        }));
    }
}
