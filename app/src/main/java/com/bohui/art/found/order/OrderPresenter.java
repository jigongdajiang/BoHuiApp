package com.bohui.art.found.order;

import com.bohui.art.bean.found.OrderBean;
import com.bohui.art.bean.found.OrderResult;
import com.bohui.art.common.net.AppProgressSubScriber;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 */


public class OrderPresenter extends OrderContact.Presenter {
    @Override
    public void order(OrderBean param) {
        mRxManage.add(mModel.order(param).subscribeWith(new AppProgressSubScriber<OrderResult>(mView,OrderContact.URL_ORDER,mView) {
            @Override
            protected void onResultSuccess(OrderResult result) {
                mView.orderSuccess(result);
            }
        }));
    }

    @Override
    public void orderDetail(long uid, long id) {
        mRxManage.add(mModel.orderDetail(uid,id).subscribeWith(new AppProgressSubScriber<OrderBean>(mView,OrderContact.URL_ORDER,mView) {
            @Override
            protected void onResultSuccess(OrderBean result) {
                mView.orderDetailSuccess(result);
            }
        }));
    }
}
