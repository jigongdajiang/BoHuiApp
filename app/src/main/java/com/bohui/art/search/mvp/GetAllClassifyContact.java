package com.bohui.art.search.mvp;

import com.bohui.art.bean.search.AllClassifyResult;
import com.bohui.art.common.net.mvp.BaseLoadingView;
import com.framework.core.base.BaseModel;
import com.framework.core.base.BasePresenter;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/2/3
 * @description:
 */


public interface GetAllClassifyContact {
    String URL_GET_ALL_CLASSIFY = "common/getPaintingClass";
    String TAG_GET_ALL_CLASSIFY = "tag_get_all_classify";
    interface Model extends BaseModel{
        Observable<AllClassifyResult> getAllClassify();
    }
    interface View extends BaseLoadingView{
        void getAllClassifySuccess(AllClassifyResult result);
    }
    abstract class Presenter extends BasePresenter<Model,View>{
        public abstract void getAllClassify();
    }
}
