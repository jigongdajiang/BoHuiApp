package com.bohui.art.mine.order.mvp;

import com.bohui.art.bean.mine.MyOrderListResult;
import com.bohui.art.common.net.AppProgressSubScriber;

/**
 * @author : gaojigong
 * @date : 2018/1/8
 * @description:
 */


public class MyOrderPresenter extends MyOrderContact.Presenter {
    @Override
    public void myOrder() {
        mRxManage.add(mModel.myOrder().subscribeWith(new AppProgressSubScriber<MyOrderListResult>(mView,MyOrderContact.TAG_MY_ORDER,mView) {
            @Override
            protected void onResultSuccess(MyOrderListResult result) {
                mView.myOrderSuccess(result);
            }
        }));
    }
}
