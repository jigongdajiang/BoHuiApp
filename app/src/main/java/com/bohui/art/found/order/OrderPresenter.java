package com.bohui.art.found.order;

import com.bohui.art.bean.found.OrderResult;
import com.bohui.art.common.net.AppProgressSubScriber;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 */


public class OrderPresenter extends OrderContact.Presenter {
    @Override
    public void order() {
        mRxManage.add(mModel.order().subscribeWith(new AppProgressSubScriber<OrderResult>(mView,OrderContact.URL_ORDER,mView) {
            @Override
            protected void onResultSuccess(OrderResult result) {
                mView.orderSuccess(result);
            }
        }));
    }
}
