package com.bohui.art.detail.artman.mvp;

import com.bohui.art.bean.detail.ArtMainDetailResult;
import com.bohui.art.common.net.mvp.BaseLoadingView;
import com.framework.core.base.BaseModel;
import com.framework.core.base.BasePresenter;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 */


public interface ArtManDetailContact {
    String URL_GET_ART_MAN_DETAIL = "";
    String TAG_GET_ART_MAN_DETAIL = "tag_get_art_man_detail";
    interface Model extends BaseModel{
        Observable<ArtMainDetailResult> getArtManDetail(String id);
    }
    interface View extends BaseLoadingView{
        void getArtManDetailSuccess(ArtMainDetailResult result);
    }
    abstract class Presenter extends BasePresenter<Model,View>{
        public abstract void getArtManDetail(String id);
    }
}
