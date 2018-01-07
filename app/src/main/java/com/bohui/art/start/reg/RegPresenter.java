package com.bohui.art.start.reg;

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
    public void getCode(String phone) {
        mRxManage.add(mModel.getCode(phone)
        .subscribeWith(new AppProgressSubScriber<VerCodeResult>(mView,RegContact.TAG_REG_GET_CODE,mView){
            @Override
            protected void onResultSuccess(VerCodeResult result) {
                mView.getCodeSuccess(result);
            }
        }));
    }

    @Override
    public void reg(String phone, String code) {
        mRxManage.add(mModel.reg(phone,code)
        .subscribeWith(new AppProgressSubScriber<RegResult>(mView,RegContact.TAG_REG,mView){
            @Override
            protected void onResultSuccess(RegResult regResult) {
                mView.regSuccess(regResult);
            }
        }));
    }
}
