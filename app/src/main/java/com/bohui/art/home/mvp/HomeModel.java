package com.bohui.art.home.mvp;

import com.bohui.art.bean.home.ClassifyLevelResult;
import com.framework.core.http.EasyHttp;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 */


public class HomeModel implements HomeContact.IHomeModel {
    @Override
    public Observable<ClassifyLevelResult> getClassifyLevel1() {
        return EasyHttp.post(HomeContact.URL_GET_CLASSIFY_LEVET1)
                .execute(ClassifyLevelResult.class);
    }
}
