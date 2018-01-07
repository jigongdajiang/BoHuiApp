package com.bohui.art.mine.attention.mvp;

import com.bohui.art.bean.mine.MyAttentionResult;
import com.bohui.art.common.net.mvp.BaseLoadingView;
import com.framework.core.base.BaseModel;
import com.framework.core.base.BasePresenter;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/8
 * @description:
 */


public interface MyAttentionContact {
    String URL_MY_ATTENTION = "";
    String TAG_MY_ATTENTION = "tag_my_attention";
    interface Model extends BaseModel{
        Observable<MyAttentionResult> myAttention();
    }
    interface View extends BaseLoadingView{
        void myAttentionSuccess(MyAttentionResult result);
    }
    abstract class Presenter extends BasePresenter<Model,View>{
        public abstract void myAttention();
    }
}
