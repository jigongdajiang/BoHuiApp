package com.bohui.art.detail.comapny.mvp;

import com.bohui.art.bean.common.CompanyArtListParam;
import com.bohui.art.bean.detail.CompanyArtListResult;
import com.bohui.art.common.net.mvp.BaseLoadingView;
import com.framework.core.base.BaseModel;
import com.framework.core.base.BasePresenter;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/3/12
 * @description:
 */


public interface CompanyArtListContact {
    String URL_COMPANY_ART_LIST = "mechanism/getMechanismByNameList";
    String TAG_COMPANY_ART_LIST = "tag_company_art_list";
    interface Model extends BaseModel{
        Observable<CompanyArtListResult> getCompanyArtList(CompanyArtListParam param);
    }
    interface View extends BaseLoadingView{
        void getCompanyArtListSuccess(CompanyArtListResult result);
    }
    abstract class Presenter extends BasePresenter<Model,View>{
        public abstract void getCompanyArtList(CompanyArtListParam param);
    }
}
