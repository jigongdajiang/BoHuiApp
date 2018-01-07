package com.bohui.art.home.mvp;

import com.bohui.art.bean.home.RecommendResult;
import com.framework.core.http.EasyHttp;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 */


public class RecommendModel implements HomeContact.IRecommendModel {
    @Override
    public Observable<RecommendResult> getRecommend() {
        return EasyHttp.post(HomeContact.URL_GET_RECOMMEND)
                .execute(RecommendResult.class);
    }
}
