package com.bohui.art.detail.artman.mvp;

import com.bohui.art.bean.detail.ArtMainDetailResult;
import com.bohui.art.bean.detail.CAResult;
import com.framework.core.http.EasyHttp;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 */


public class ArtManDetailModel implements ArtManDetailContact.Model {
    @Override
    public Observable<ArtMainDetailResult> getArtManDetail(long aid) {
        return EasyHttp.post(ArtManDetailContact.URL_GET_ART_MAN_DETAIL)
                .params("aid",String.valueOf(aid))
                .execute(ArtMainDetailResult.class);
    }

    @Override
    public Observable<CAResult> attentionArtMan(long uid, long artId, int type) {
        return EasyHttp.post(ArtManDetailContact.URL_ATTENTION_ART_MAN)
                .params("uid",String.valueOf(uid))
                .params("artId",String.valueOf(artId))
                .params("type",String.valueOf(type))
                .execute(CAResult.class);
    }
}
