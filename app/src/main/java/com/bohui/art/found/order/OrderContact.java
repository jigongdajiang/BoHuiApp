package com.bohui.art.found.order;

import com.bohui.art.bean.found.OrderBean;
import com.bohui.art.bean.found.OrderResult;
import com.bohui.art.common.net.mvp.BaseLoadingView;
import com.framework.core.base.BaseModel;
import com.framework.core.base.BasePresenter;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 */


public interface OrderContact {
    String URL_ORDER = "user/addCustomized";
    String TAG_ORDER = "tag_order";
    String URL_ORDER_DETAIL = "user/customDetail";
    String TAG_ORDER_DETAIL = "tag_order_detail";
    interface Model extends BaseModel{
        Observable<OrderResult> order(OrderBean param);
        Observable<OrderBean> orderDetail(long uid,long id);
    }
    interface View extends BaseLoadingView{
        void orderSuccess(OrderResult result);
        void orderDetailSuccess(OrderBean result);
    }
    abstract class Presenter extends BasePresenter<Model,View>{
        public abstract void order(OrderBean param);
        public abstract void orderDetail(long uid,long id);
    }
}
