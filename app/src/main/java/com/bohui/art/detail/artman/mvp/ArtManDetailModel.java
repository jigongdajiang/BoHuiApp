package com.bohui.art.detail.artman.mvp;

import com.bohui.art.bean.detail.ArtMainDetailResult;
import com.framework.core.http.EasyHttp;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 */


public class ArtManDetailModel implements ArtManDetailContact.Model {
    @Override
    public Observable<ArtMainDetailResult> getArtManDetail(String id) {
        return EasyHttp.post(ArtManDetailContact.URL_GET_ART_MAN_DETAIL)
                .params("id",id)
                .execute(ArtMainDetailResult.class);
    }
}
