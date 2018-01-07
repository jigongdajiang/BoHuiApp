package com.bohui.art.found.mvp;

import com.bohui.art.bean.common.BannerResult;
import com.framework.core.http.EasyHttp;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 */


public class FoundModel implements FoundContact.Model {
    @Override
    public Observable<BannerResult> getFoundBanner() {
        return EasyHttp.post(FoundContact.URL_GET_FOUND_BANNER)
                .execute(BannerResult.class);
    }
}
