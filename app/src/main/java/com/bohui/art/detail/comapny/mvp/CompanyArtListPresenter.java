package com.bohui.art.detail.comapny.mvp;

import com.bohui.art.bean.common.CompanyArtListParam;
import com.bohui.art.bean.detail.CompanyArtListResult;
import com.bohui.art.common.net.AppProgressSubScriber;

/**
 * @author : gaojigong
 * @date : 2018/3/12
 * @description:
 */


public class CompanyArtListPresenter extends CompanyArtListContact.Presenter {
    @Override
    public void getCompanyArtList(CompanyArtListParam param) {
        mRxManage.add(mModel.getCompanyArtList(param).subscribeWith(new AppProgressSubScriber<CompanyArtListResult>(mView, CompanyArtListContact.TAG_COMPANY_ART_LIST, mView) {
            @Override
            protected void onResultSuccess(CompanyArtListResult result) {
                mView.getCompanyArtListSuccess(result);
            }
        }));
    }
}
