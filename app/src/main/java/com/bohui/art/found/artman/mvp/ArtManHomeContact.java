package com.bohui.art.found.artman.mvp;

import com.bohui.art.bean.found.ArtManHomeResult;
import com.bohui.art.common.net.mvp.BaseLoadingView;
import com.framework.core.base.BaseModel;
import com.framework.core.base.BasePresenter;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/3/12
 * @description:
 */


public interface ArtManHomeContact {
    String URL_ARTMAN_HOME = "artist/getArtistHomePage";
    String TAG_ARTMAN_HOME = "tag_artman_home";
    interface IArtManHomeModel extends BaseModel{
        Observable<ArtManHomeResult> getArtManHome();
    }
    interface IArtManHomeView extends BaseLoadingView{
        void getArtManHomeSuccess(ArtManHomeResult artManHomeResult);
    }
    abstract class AbsArtManHomePresenter extends BasePresenter<IArtManHomeModel,IArtManHomeView>{
        public abstract void getArtManHome();
    }
}
