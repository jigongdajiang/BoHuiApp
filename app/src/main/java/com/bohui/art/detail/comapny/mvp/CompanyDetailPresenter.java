package com.bohui.art.detail.comapny.mvp;

import com.bohui.art.bean.detail.CAResult;
import com.bohui.art.bean.detail.CompanyAttentionResult;
import com.bohui.art.bean.detail.CompanyDetailResult;
import com.bohui.art.common.net.AppProgressSubScriber;
import com.bohui.art.found.company.mvp.CompanyContact;

/**
 * @author : gaojigong
 * @date : 2018/3/12
 * @description:
 */


public class CompanyDetailPresenter extends CompanyDetailContact.AbsCompanyDetailPresenter {
    @Override
    public void getCompanyDetail(long uid, int mid) {
        mRxManage.add(mModel.getCompanyDetail(uid,mid).subscribeWith(new AppProgressSubScriber<CompanyDetailResult>(mView, CompanyDetailContact.TAG_COMPANY_DETAIL, mView) {
            @Override
            protected void onResultSuccess(CompanyDetailResult result) {
                mView.getCompanyDetailSuccess(result);
            }
        }));
    }

    @Override
    public void attentionCompany(long uid, int mid,int type) {
        mRxManage.add(mModel.attentionCompany(uid,mid,type).subscribeWith(new AppProgressSubScriber<CompanyAttentionResult>(mView, CompanyDetailContact.TAG_ATTENTION_COMPANY, mView) {
            @Override
            protected void onResultSuccess(CompanyAttentionResult result) {
                mView.attentionCompanySuccess(result);
            }
        }));
    }
}
