package com.bohui.art.mine.attentioncompany.mvp;

import com.bohui.art.bean.mine.MyAttentionCompanyResult;
import com.bohui.art.mine.collect.mvp.MyCollectParam;
import com.framework.core.http.EasyHttp;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/3/12
 * @description:
 */


public class MyAttentionCompanyModel implements MyAttentionCompanyContact.Model {
    @Override
    public Observable<MyAttentionCompanyResult> getMyAttentionCompany(MyCollectParam param) {
        return EasyHttp.post(MyAttentionCompanyContact.URL_MY_ATTENTION_COMPANY)
                .params("uid",String.valueOf(param.getUid()))
                .params("start",String.valueOf(param.getStart()))
                .params("length",String.valueOf(param.getLength()))
                .execute(MyAttentionCompanyResult.class);
    }
}
