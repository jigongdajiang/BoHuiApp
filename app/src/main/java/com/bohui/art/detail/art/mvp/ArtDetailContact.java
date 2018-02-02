package com.bohui.art.detail.art.mvp;

import com.bohui.art.bean.detail.ArtDetailResult;
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


public interface ArtDetailContact {
    String URL_GET_ART_DETAIL = "pintingClass/paintDetail";
    String TAG_GET_ART_DETAIL = "tag_get_art_detail";

    String URL_COLLECT_ART = "user/isCollection";
    String TAG_COLLECT_ART = "tag_collect_art";
    interface Model extends BaseModel{
        Observable<ArtDetailResult> getArtDetail(long uid,long id);

        /**
         *
         * @param uid
         * @param paintId
         * @param type 1添加收藏2取消取消
         */
        Observable<CAResult> collectArt(long uid,long paintId,int type);
    }
    interface View extends BaseLoadingView{
        void getArtDetailSuccess(ArtDetailResult result);
        void collectSuccess(CAResult result);
    }
    abstract class Presenter extends BasePresenter<Model,View>{
        public abstract void getArtDetail(long uid,long id);
        public abstract void collectArt(long uid,long paintId,int type);
    }
}
