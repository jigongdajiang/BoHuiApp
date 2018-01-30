package com.bohui.art.detail.art.mvp;

import com.bohui.art.bean.detail.ArtDetailResult;
import com.bohui.art.bean.detail.CAResult;
import com.framework.core.http.EasyHttp;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 */


public class ArtDetailModel implements ArtDetailContact.Model {
    @Override
    public Observable<ArtDetailResult> getArtDetail(long id) {
        return EasyHttp.post(ArtDetailContact.URL_GET_ART_DETAIL)
                .params("id",String.valueOf(id))
                .execute(ArtDetailResult.class);
    }

    @Override
    public Observable<CAResult> collectArt(long uid, long paintId, int type) {
        return EasyHttp.post(ArtDetailContact.URL_COLLECT_ART)
                .params("uid",String.valueOf(uid))
                .params("paintId",String.valueOf(paintId))
                .params("type",String.valueOf(type))
                .execute(CAResult.class);
    }
}
