package com.bohui.art.found.artman.mvp;

import com.bohui.art.bean.common.ArtManListParam;
import com.bohui.art.bean.found.ArtManLevelResult;
import com.bohui.art.bean.found.ArtManListResult;
import com.bohui.art.common.net.mvp.BaseLoadingView;
import com.framework.core.base.BaseModel;
import com.framework.core.base.BasePresenter;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 */


public interface ArtManListContact {
    String URL_GET_ART_MAN_LEVEL = "artist/getTowListByOneId";
    String TAG_GET_ART_MAN_LEVEL = "tag_get_art_man_level";
    String URL_GET_ART_MAN_LIST = "artist/getArtistList";
    String TAG_GET_ART_MAN_LIST = "tag_get_art_man_list";
    interface IArtManLevelModel extends BaseModel{
        Observable<ArtManLevelResult> getArtManLevel(int tid);
    }
    interface IArtManLevelView extends BaseLoadingView{
        void getArtManLevelSuccess(ArtManLevelResult result);
    }
    abstract class IArtManLevelPresenter extends BasePresenter<IArtManLevelModel,IArtManLevelView>{
        public abstract void getArtManLevel(int tid);
    }
    interface IArtManListModel extends BaseModel{
        Observable<ArtManListResult> getArtManList(ArtManListParam param);
    }
    interface IArtManListView extends BaseLoadingView{
        void getArtManListSuccess(ArtManListResult result);
    }
    abstract class IArtManListPresenter extends BasePresenter<IArtManListModel,IArtManListView>{
        public abstract void getArtManList(ArtManListParam param);
    }
}
