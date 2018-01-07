package com.bohui.art.found.order;

import com.bohui.art.bean.found.OrderResult;
import com.framework.core.http.EasyHttp;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 */


public class OrderModel implements OrderContact.Model {
    @Override
    public Observable<OrderResult> order() {
        return EasyHttp.post(OrderContact.URL_ORDER)
                .execute(OrderResult.class);
    }
}
