package com.bohui.art.found.order;

import com.bohui.art.bean.found.OrderBean;
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
    public Observable<OrderResult> order(OrderBean param) {
        return EasyHttp.post(OrderContact.URL_ORDER)
                .params("type",param.getType())
                .params("price",param.getPrice())
                .params("endprice",param.getEndprice())
                .params("size",param.getSize())
                .params("num",param.getNum())
                .params("remarks",param.getRemarks())
                .params("mobile",param.getMobile())
                .params("name",param.getName())
                .params("uid",String.valueOf(param.getUid()))
                .execute(OrderResult.class);
    }

    @Override
    public Observable<OrderBean> orderDetail(long uid, long id) {
        return EasyHttp.post(OrderContact.URL_ORDER_DETAIL)
                .params("uid",String.valueOf(uid))
                .params("id",String.valueOf(id))
                .execute(OrderBean.class);
    }
}
