package com.bohui.art.detail.artman.mvp;

import com.bohui.art.bean.common.ArtListResult;
import com.bohui.art.common.net.mvp.BaseLoadingView;
import com.framework.core.base.BaseModel;
import com.framework.core.base.BasePresenter;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/30
 * @description:
 */


public interface ArtJiContanct {
    String URL_ART_JI = "pintingClass/getSetPainting";
    String TAG_ART_JI = "tag_art_ji";
    interface Model extends BaseModel{
        Observable<ArtListResult> getArtJi(long id);
    }
    interface View extends BaseLoadingView{
        void getArtJiSuccess(ArtListResult result);
    }
    abstract class Preseneter extends BasePresenter<Model,View>{
        public abstract void getArtJi(long id);
    }
}
