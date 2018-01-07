package com.bohui.art.found.mvp;

import com.bohui.art.bean.common.BannerResult;
import com.bohui.art.common.net.mvp.BaseLoadingView;
import com.framework.core.base.BaseModel;
import com.framework.core.base.BasePresenter;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 */


public interface FoundContact {
    String URL_GET_FOUND_BANNER = "";
    String TAG_GET_FOUND_BANNER = "tag_get_found_banner";
    interface Model extends BaseModel{
        Observable<BannerResult> getFoundBanner();
    }
    interface View extends BaseLoadingView{
        void getFoundBannerSuccess(BannerResult result);
    }
    abstract class Presenter extends BasePresenter<Model,View>{
        public abstract void getFoundBanner();
    }
}
