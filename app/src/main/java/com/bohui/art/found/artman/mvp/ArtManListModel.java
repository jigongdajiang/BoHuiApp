package com.bohui.art.found.artman.mvp;

import com.bohui.art.bean.found.ArtManListResult;
import com.framework.core.http.EasyHttp;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 */


public class ArtManListModel implements ArtManListContact.IArtManListModel {
    @Override
    public Observable<ArtManListResult> getArtManList() {
        return EasyHttp.post(ArtManListContact.URL_GET_ART_MAN_LIST)
                .execute(ArtManListResult.class);
    }
}
