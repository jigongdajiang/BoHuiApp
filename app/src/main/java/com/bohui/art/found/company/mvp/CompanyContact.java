package com.bohui.art.found.company.mvp;

import com.bohui.art.bean.common.PageParam;
import com.bohui.art.bean.found.CompanyListResult;
import com.bohui.art.common.net.mvp.BaseLoadingView;
import com.framework.core.base.BaseModel;
import com.framework.core.base.BasePresenter;
import com.framework.core.base.BaseView;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/3/12
 * @description:
 */


public interface CompanyContact {
    String URL_COMPANY = "mechanism/getMechanismList";
    String TAG_COMPANY = "tag_company";
    interface ICompanyModel extends BaseModel{
        Observable<CompanyListResult> getCompanyList(PageParam pageParam);
    }
    interface ICompanyView extends BaseLoadingView{
        void getCompanyListSuccess(CompanyListResult companyListResult);
    }
    abstract class AbsCompanyPresenter extends BasePresenter<ICompanyModel,ICompanyView>{
        public abstract void getCompanyList(PageParam pageParam);
    }
}
