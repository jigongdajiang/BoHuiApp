package com.bohui.art.mine.collect.mvp;

import com.bohui.art.bean.mine.MyCollectResult;
import com.bohui.art.common.net.mvp.BaseLoadingView;
import com.framework.core.base.BaseModel;
import com.framework.core.base.BasePresenter;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/8
 * @description:
 */


public interface MyCollectContact {
    String URL_MY_COLLECT = "user/myCollection";
    String TAG_MY_COLLECT = "tag_my_collect";
    interface Model extends BaseModel{
        Observable<MyCollectResult> myCollectList(MyCollectParam param);
    }
    interface View extends BaseLoadingView{
        void myCollectListSuccess(MyCollectResult result);
    }
    abstract class Presenter extends BasePresenter<Model,View>{
        public abstract void myCollectList(MyCollectParam param);
    }
}
