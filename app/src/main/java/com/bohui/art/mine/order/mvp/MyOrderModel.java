package com.bohui.art.mine.order.mvp;

import com.bohui.art.bean.mine.MyOrderListResult;
import com.framework.core.http.EasyHttp;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/8
 * @description:
 */


public class MyOrderModel implements MyOrderContact.Model {
    @Override
    public Observable<MyOrderListResult> myOrder() {
        return EasyHttp.post(MyOrderContact.URL_MY_ORDER)
                .execute(MyOrderListResult.class);
    }
}
