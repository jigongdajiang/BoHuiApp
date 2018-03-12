package com.bohui.art.found.company.mvp;

import com.bohui.art.bean.common.PageParam;
import com.bohui.art.bean.found.CompanyListResult;
import com.framework.core.http.EasyHttp;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/3/12
 * @description:
 */


public class CompanyModel implements CompanyContact.ICompanyModel {
    @Override
    public Observable<CompanyListResult> getCompanyList(PageParam pageParam) {
        return EasyHttp.post(CompanyContact.URL_COMPANY)
                .params("start",String.valueOf(pageParam.getStart()))
                .params("length",String.valueOf(pageParam.getLength()))
                .execute(CompanyListResult.class);
    }
}
