package com.bohui.art.common.mvp;

import com.bohui.art.bean.common.ArtListParam;
import com.bohui.art.bean.common.ArtListResult;
import com.bohui.art.common.net.mvp.BaseLoadingView;
import com.framework.core.base.BaseModel;
import com.framework.core.base.BasePresenter;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/21
 * @description:
 */


public interface ArtListContact {
    String URL_ART_LIST = "pintingClass/searchPainting";
    String TAG_ART_LIST = "tag_art_list";
    interface Model extends BaseModel{
        Observable<ArtListResult> getArtList(ArtListParam param);
    }
    interface View extends BaseLoadingView{
        void getArtListSuccess(ArtListResult result);
    }
    abstract class Presenter extends BasePresenter<Model,View>{
        public abstract void getArtList(ArtListParam param);
    }
}
