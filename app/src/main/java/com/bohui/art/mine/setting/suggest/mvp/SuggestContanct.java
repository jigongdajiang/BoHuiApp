package com.bohui.art.mine.setting.suggest.mvp;

import com.bohui.art.bean.mine.SuggestSubmitResult;
import com.bohui.art.common.net.mvp.BaseLoadingView;
import com.framework.core.base.BaseModel;
import com.framework.core.base.BasePresenter;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/8
 * @description:
 */


public interface SuggestContanct {
    String URL_SUGGEST = "";
    String TAG_SUGGEST = "tag_suggest";
    interface Model extends BaseModel{
        Observable<SuggestSubmitResult> suggestSubmit();
    }
    interface View extends BaseLoadingView{
        void suggestSubmitSuccess(SuggestSubmitResult result);
    }
    abstract class Presenter extends BasePresenter<Model,View>{
        public abstract void suggestSubmit();
    }
}
