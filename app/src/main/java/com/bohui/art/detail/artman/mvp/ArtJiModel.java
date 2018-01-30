package com.bohui.art.detail.artman.mvp;

import com.bohui.art.bean.common.ArtListResult;
import com.framework.core.http.EasyHttp;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/30
 * @description:
 */


public class ArtJiModel implements ArtJiContanct.Model {
    @Override
    public Observable<ArtListResult> getArtJi(long id) {
        return EasyHttp.post(ArtJiContanct.URL_ART_JI)
                .params("id",String.valueOf(id))
                .execute(ArtListResult.class);
    }
}
