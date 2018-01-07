package com.bohui.art.detail.art.mvp;

import com.bohui.art.bean.detail.ArtDetailResult;
import com.framework.core.http.EasyHttp;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 */


public class ArtDetailModel implements ArtDetailContact.Model {
    @Override
    public Observable<ArtDetailResult> getArtDetail(String id) {
        return EasyHttp.post(ArtDetailContact.URL_GET_ART_DETAIL)
                .params("id",id)
                .execute(ArtDetailResult.class);
    }
}
