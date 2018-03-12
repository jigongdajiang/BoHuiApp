package com.bohui.art.detail.comapny.mvp;

import com.bohui.art.bean.common.CompanyArtListParam;
import com.bohui.art.bean.detail.CompanyArtListResult;
import com.framework.core.http.EasyHttp;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/3/12
 * @description:
 */


public class CompanyArtListModel implements CompanyArtListContact.Model {
    @Override
    public Observable<CompanyArtListResult> getCompanyArtList(CompanyArtListParam param) {
        return EasyHttp.post(CompanyArtListContact.URL_COMPANY_ART_LIST)
                .params("mid",String.valueOf(param.getMid()))
                .params("start",String.valueOf(param.getStart()))
                .params("length",String.valueOf(param.getLength()))
                .params("name",param.getName())
                .execute(CompanyArtListResult.class);
    }
}
