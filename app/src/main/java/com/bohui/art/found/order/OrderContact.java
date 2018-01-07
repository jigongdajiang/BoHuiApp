package com.bohui.art.found.order;

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
    String URL_ORDER = "";
    String TAG_ORDER = "tag_order";
    interface Model extends BaseModel{
        Observable<OrderResult> order();
    }
    interface View extends BaseLoadingView{
        void orderSuccess(OrderResult result);
    }
    abstract class Presenter extends BasePresenter<Model,View>{
        public abstract void order();
    }
}
