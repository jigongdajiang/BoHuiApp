package com.bohui.art.mine.attentioncompany.mvp;

import com.bohui.art.bean.mine.MyAttentionCompanyResult;
import com.bohui.art.common.net.mvp.BaseLoadingView;
import com.bohui.art.mine.collect.mvp.MyCollectParam;
import com.framework.core.base.BaseModel;
import com.framework.core.base.BasePresenter;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/3/12
 * @description:
 */


public interface MyAttentionCompanyContact {
    String URL_MY_ATTENTION_COMPANY = "";
    String TAG_MY_ATTENTION_COMPANY = "tag_my_attention_company";
    interface Model extends BaseModel{
        Observable<MyAttentionCompanyResult> getMyAttentionCompany(MyCollectParam param);
    }
    interface View extends BaseLoadingView{
        void getMyAttentionCompanySuccess(MyAttentionCompanyResult result);
    }
    abstract class Presenter extends BasePresenter<Model,View>{
        public abstract void getMyAttentionCompany(MyCollectParam param);
    }
}
