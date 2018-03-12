package com.bohui.art.found.company.mvp;

import com.bohui.art.bean.common.PageParam;
import com.bohui.art.bean.found.CompanyListResult;
import com.bohui.art.common.net.AppProgressSubScriber;

/**
 * @author : gaojigong
 * @date : 2018/3/12
 * @description:
 */


public class CompanyPresenter extends CompanyContact.AbsCompanyPresenter {
    @Override
    public void getCompanyList(PageParam pageParam) {
        mRxManage.add(mModel.getCompanyList(pageParam).subscribeWith(new AppProgressSubScriber<CompanyListResult>(mView, CompanyContact.TAG_COMPANY, mView) {
            @Override
            protected void onResultSuccess(CompanyListResult companyListResult) {
                mView.getCompanyListSuccess(companyListResult);
            }
        }));
    }
}
