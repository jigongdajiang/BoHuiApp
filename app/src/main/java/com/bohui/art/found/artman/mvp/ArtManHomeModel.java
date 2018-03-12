package com.bohui.art.found.artman.mvp;

import com.bohui.art.bean.found.ArtManHomeResult;
import com.framework.core.http.EasyHttp;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/3/12
 * @description:
 */


public class ArtManHomeModel implements ArtManHomeContact.IArtManHomeModel {
    @Override
    public Observable<ArtManHomeResult> getArtManHome() {
        return EasyHttp.post(ArtManHomeContact.URL_ARTMAN_HOME)
                .execute(ArtManHomeResult.class);
    }
}
