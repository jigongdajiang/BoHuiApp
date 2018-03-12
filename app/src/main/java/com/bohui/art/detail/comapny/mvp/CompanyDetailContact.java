package com.bohui.art.detail.comapny.mvp;

import com.bohui.art.bean.detail.CAResult;
import com.bohui.art.bean.detail.CompanyAttentionResult;
import com.bohui.art.bean.detail.CompanyDetailResult;
import com.bohui.art.common.net.mvp.BaseLoadingView;
import com.framework.core.base.BaseModel;
import com.framework.core.base.BasePresenter;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/3/12
 * @description:
 */


public interface CompanyDetailContact {
    String URL_COMPANY_DETAIL = "mechanism/getMechanismDetail";
    String TAG_COMPANY_DETAIL = "tag_company_detail";
    String URL_ATTENTION_COMAPNY = "mechanism/followMechanism";
    String TAG_ATTENTION_COMPANY = "tag_attention_company";
    interface ICompanyDetailModel extends BaseModel{
        Observable<CompanyDetailResult> getCompanyDetail(long uid,int mid);
        Observable<CompanyAttentionResult> attentionCompany(long uid, int mid, int type);

    }
    interface ICompanyDetailView extends BaseLoadingView{
        void getCompanyDetailSuccess(CompanyDetailResult result);
        void attentionCompanySuccess(CompanyAttentionResult result);
    }
    abstract class AbsCompanyDetailPresenter extends BasePresenter<ICompanyDetailModel,ICompanyDetailView>{
        public abstract void getCompanyDetail(long uid,int mid);
        public abstract void attentionCompany(long uid, int mid,int type);
    }
}
