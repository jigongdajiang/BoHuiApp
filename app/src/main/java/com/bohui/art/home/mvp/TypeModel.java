package com.bohui.art.home.mvp;

import com.bohui.art.bean.home.TypeResult;
import com.framework.core.cache.stategy.CacheMode;
import com.framework.core.http.EasyHttp;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 */


public class TypeModel implements HomeContact.ITypedModel {
    @Override
    public Observable<TypeResult> getTypeInfo(long classType) {
        return EasyHttp.post(HomeContact.URL_GET_TYPE)
                .cacheKey(HomeContact.URL_GET_TYPE)
                .cacheMode(CacheMode.CACHEANDREMOTEDISTINCT)
                .params("classType",String.valueOf(classType))
                .execute(TypeResult.class);
    }
}
