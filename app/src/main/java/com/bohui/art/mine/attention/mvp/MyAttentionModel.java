package com.bohui.art.mine.attention.mvp;

import com.bohui.art.bean.mine.MyAttentionResult;
import com.framework.core.http.EasyHttp;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/8
 * @description:
 */


public class MyAttentionModel implements MyAttentionContact.Model {
    @Override
    public Observable<MyAttentionResult> myAttention() {
        return EasyHttp.post(MyAttentionContact.URL_MY_ATTENTION)
                .execute(MyAttentionResult.class);
    }
}
