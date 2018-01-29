package com.bohui.art.mine.attention.mvp;

import com.bohui.art.bean.mine.MyAttentionResult;
import com.bohui.art.mine.collect.mvp.MyCollectParam;
import com.framework.core.http.EasyHttp;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/8
 * @description:
 */


public class MyAttentionModel implements MyAttentionContact.Model {
    @Override
    public Observable<MyAttentionResult> myAttention(MyCollectParam param) {
        return EasyHttp.post(MyAttentionContact.URL_MY_ATTENTION)
                .params("uid", String.valueOf(param.getUid()))
                .params("start", String.valueOf(param.getStart()))
                .params("length", String.valueOf(param.getLength()))
                .execute(MyAttentionResult.class);
    }
}
