package com.bohui.art.detail.art.mvp;

import com.bohui.art.bean.detail.ArtDetailResult;
import com.bohui.art.common.net.mvp.BaseLoadingView;
import com.framework.core.base.BaseModel;
import com.framework.core.base.BasePresenter;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 */


public interface ArtDetailContact {
    String URL_GET_ART_DETAIL = "";
    String TAG_GET_ART_DETAIL = "tag_get_art_detail";
    interface Model extends BaseModel{
        Observable<ArtDetailResult> getArtDetail(String id);
    }
    interface View extends BaseLoadingView{
        void getArtDetailSuccess(ArtDetailResult result);
    }
    abstract class Presenter extends BasePresenter<Model,View>{
        public abstract void getArtDetail(String id);
    }
}
