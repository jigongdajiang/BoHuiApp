package com.bohui.art.classify.mvp;

import com.bohui.art.bean.classify.ClassifyLevel2Result;
import com.bohui.art.home.mvp.HomeContact;
import com.framework.core.cache.stategy.CacheMode;
import com.framework.core.http.EasyHttp;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 */


public class ClassifyModel implements ClassifyContact.Model {
    @Override
    public Observable<ClassifyLevel2Result> getClassifyLevel2(String level1) {
        return EasyHttp.post(ClassifyContact.URL_GET_CLASSIFY_LEVEL2)
                .cacheKey(ClassifyContact.TAG_GET_CLASSIFY_LEVEL2)
                .cacheMode(CacheMode.CACHEANDREMOTEDISTINCT)
                .params("classType",level1)
                .execute(ClassifyLevel2Result.class);
    }
}
