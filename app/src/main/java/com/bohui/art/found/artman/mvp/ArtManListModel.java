package com.bohui.art.found.artman.mvp;

import com.bohui.art.bean.common.ArtManListParam;
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
    public Observable<ArtManListResult> getArtManList(ArtManListParam param) {
        return EasyHttp.post(ArtManListContact.URL_GET_ART_MAN_LIST)
                .params("start",String.valueOf(param.getStart()))
                .params("length",String.valueOf(param.getLength()))
                .params("level",String.valueOf(param.getLevel()))
                .params("oneid",String.valueOf(param.getOneid()))
                .params("towid",String.valueOf(param.getTowid()))
                .params("name",param.getName())
                .execute(ArtManListResult.class);
    }
}
