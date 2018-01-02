package com.bohui.art.start.reg;

import com.bohui.art.bean.start.RegResult;
import com.bohui.art.bean.start.VerCodeResult;
import com.framework.core.http.exception.ApiException;
import com.framework.core.http.subsciber.BaseSubscriber;

/**
 * @author : gaojigong
 * @date : 2018/1/2
 * @description:
 */


public class RegPresenter extends RegContact.Presenter {
    @Override
    public void getCode(String phone) {
        mRxManage.add(mModel.getCode(phone)
        .subscribeWith(new BaseSubscriber<VerCodeResult>(){
            @Override
            public void onNext(VerCodeResult result) {
                super.onNext(result);
                mView.getCodeSuccess(result);
            }

            @Override
            public void onError(ApiException e) {
                mView.handleException(RegContact.TAG_REG_GET_CODE,e);
            }
        }));
    }

    @Override
    public void reg(String phone, String code) {
        mRxManage.add(mModel.reg(phone,code)
        .subscribeWith(new BaseSubscriber<RegResult>(){
            @Override
            public void onNext(RegResult regResult) {
                super.onNext(regResult);
                mView.regSuccess(regResult);
            }

            @Override
            public void onError(ApiException e) {
                mView.handleException(RegContact.TAG_REG,e);
            }
        }));
    }
}
