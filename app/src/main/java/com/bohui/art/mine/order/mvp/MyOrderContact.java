package com.bohui.art.mine.order.mvp;

import com.bohui.art.bean.mine.MyOrderListResult;
import com.bohui.art.common.net.mvp.BaseLoadingView;
import com.bohui.art.mine.collect.mvp.MyCollectParam;
import com.framework.core.base.BaseModel;
import com.framework.core.base.BasePresenter;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/8
 * @description:
 */


public interface MyOrderContact {
    String URL_MY_ORDER = "user/customList";
    String TAG_MY_ORDER = "tag_my_order";
    interface Model extends BaseModel{
        Observable<MyOrderListResult> myOrder(MyCollectParam param);
    }
    interface View extends BaseLoadingView{
        void myOrderSuccess(MyOrderListResult result);
    }
    abstract class Presenter extends BasePresenter<Model,View>{
        public abstract void myOrder(MyCollectParam param);
    }
}
