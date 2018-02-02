package com.bohui.art.detail.artman.mvp;

import com.bohui.art.bean.detail.ArtMainDetailResult;
import com.bohui.art.bean.detail.CAResult;
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
    String URL_GET_ART_MAN_DETAIL = "artist/getArtistDetail";
    String TAG_GET_ART_MAN_DETAIL = "tag_get_art_man_detail";
    String URL_ATTENTION_ART_MAN = "user/isFollow";
    String TAG_ATTENTION_ART_MAN = "tag_attention_art_man";
    interface Model extends BaseModel{
        Observable<ArtMainDetailResult> getArtManDetail(long uid,long aid);
        Observable<CAResult> attentionArtMan(long uid,long artId,int type);
    }
    interface View extends BaseLoadingView{
        void getArtManDetailSuccess(ArtMainDetailResult result);
        void attentionArtManSuccess(CAResult result);
    }
    abstract class Presenter extends BasePresenter<Model,View>{
        public abstract void getArtManDetail(long uid,long aid);
        public abstract void attentionArtMan(long uid,long artId,int type);
    }
}
