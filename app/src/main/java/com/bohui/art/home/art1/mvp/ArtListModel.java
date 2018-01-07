package com.bohui.art.home.art1.mvp;

import com.bohui.art.bean.home.ArtListResult;
import com.framework.core.http.EasyHttp;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 */


public class ArtListModel implements ArtListContact.Model {
    @Override
    public Observable<ArtListResult> getArtList(String level2, int pageSize, int pageNumber) {
        return EasyHttp.post(ArtListContact.URL_GET_ART_LIST)
                .params("level2","level2")
                .params("pageSize",String.valueOf(pageSize))
                .params("pageNumber",String.valueOf(pageNumber))
                .execute(ArtListResult.class);
    }
}
