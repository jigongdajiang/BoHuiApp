package com.bohui.art.home.mvp;

import com.bohui.art.bean.home.TypeResult;
import com.bohui.art.common.net.AppProgressSubScriber;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 */


public class TypePresenter extends HomeContact.AbsTypedPresenter {
    @Override
    public void getTypeInfo() {
        mRxManage.add(mModel.getTypeInfo().subscribeWith(new AppProgressSubScriber<TypeResult>(mView, HomeContact.TAG_GET_TYPE) {
            @Override
            protected void onResultSuccess(TypeResult result) {
                mView.getTypeInfoSuccess(result);
            }
        }));
    }
}
