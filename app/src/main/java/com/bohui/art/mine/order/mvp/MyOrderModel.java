package com.bohui.art.mine.order.mvp;

import com.bohui.art.bean.mine.MyOrderListResult;
import com.bohui.art.mine.collect.mvp.MyCollectParam;
import com.framework.core.http.EasyHttp;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/8
 * @description:
 */


public class MyOrderModel implements MyOrderContact.Model {
    @Override
    public Observable<MyOrderListResult> myOrder(MyCollectParam param) {
        return EasyHttp.post(MyOrderContact.URL_MY_ORDER)
                .params("uid", String.valueOf(param.getUid()))
                .params("start", String.valueOf(param.getStart()))
                .params("length", String.valueOf(param.getLength()))
                .execute(MyOrderListResult.class);
    }
}
