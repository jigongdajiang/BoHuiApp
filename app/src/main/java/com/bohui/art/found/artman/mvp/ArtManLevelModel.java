package com.bohui.art.found.artman.mvp;

import com.bohui.art.bean.found.ArtManLevelResult;
import com.framework.core.http.EasyHttp;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 */


public class ArtManLevelModel implements ArtManListContact.IArtManLevelModel {
    @Override
    public Observable<ArtManLevelResult> getArtManLevel(int tid) {
        return EasyHttp.post(ArtManListContact.URL_GET_ART_MAN_LEVEL)
                .params("tid",String.valueOf(tid))
                .execute(ArtManLevelResult.class);
    }
}
