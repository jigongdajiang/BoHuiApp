package com.bohui.art.mine.collect.mvp;

import com.bohui.art.bean.mine.MyCollectResult;
import com.framework.core.http.EasyHttp;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/8
 * @description:
 */


public class MyCollectModel implements MyCollectContact.Model {
    @Override
    public Observable<MyCollectResult> myCollectList() {
        return EasyHttp.post(MyCollectContact.URL_MY_COLLECT)
                .execute(MyCollectResult.class);
    }
}
