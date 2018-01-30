package com.bohui.art.detail.designer.mvp;

import com.bohui.art.bean.detail.DesignerDetailResult;
import com.bohui.art.common.net.mvp.BaseLoadingView;
import com.framework.core.base.BaseModel;
import com.framework.core.base.BasePresenter;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 */


public interface DesignerDetailContact {
    String URL_GET_DESIGNER_DETAIL = "designer/getDesignerDetail";
    String TAG_GET_DESIGNER_DETAIL = "tag_get_designer_detail";
    interface Model extends BaseModel{
        Observable<DesignerDetailResult> getDesingerDetail(long uid,long did);
    }
    interface View extends BaseLoadingView{
        void getDesignerDetailSuccess(DesignerDetailResult result);
    }
    abstract class Presenter extends BasePresenter<Model,View>{
        public abstract void getDesignerDetail(long uid,long did);
    }
}
