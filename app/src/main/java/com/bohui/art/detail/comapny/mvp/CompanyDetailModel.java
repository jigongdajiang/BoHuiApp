package com.bohui.art.detail.comapny.mvp;

import com.bohui.art.bean.detail.CAResult;
import com.bohui.art.bean.detail.CompanyAttentionResult;
import com.bohui.art.bean.detail.CompanyDetailResult;
import com.framework.core.http.EasyHttp;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/3/12
 * @description:
 */


public class CompanyDetailModel implements CompanyDetailContact.ICompanyDetailModel {
    @Override
    public Observable<CompanyDetailResult> getCompanyDetail(long uid, int mid) {
        return EasyHttp.post(CompanyDetailContact.URL_COMPANY_DETAIL)
                .params("uid",String.valueOf(uid))
                .params("mid",String.valueOf(mid))
                .execute(CompanyDetailResult.class);
    }

    @Override
    public Observable<CompanyAttentionResult> attentionCompany(long uid, int mid, int type) {
        return EasyHttp.post(CompanyDetailContact.URL_ATTENTION_COMAPNY)
                .params("uid",String.valueOf(uid))
                .params("mid",String.valueOf(mid))
                .params("type",String.valueOf(type))
                .execute(CompanyAttentionResult.class);
    }
}
