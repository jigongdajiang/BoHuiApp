package com.bohui.art.found.designer;

import com.bohui.art.bean.found.DesignerAttrResult;
import com.bohui.art.bean.found.DesignerListParam;
import com.bohui.art.bean.found.DesignerListResult;
import com.bohui.art.common.net.mvp.BaseLoadingView;
import com.framework.core.base.BaseModel;
import com.framework.core.base.BasePresenter;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 */


public interface DesignerListContact {
    String URL_GET_DESIGNER_ATTR = "designer/getDesignerStyle";
    String TAG_GET_DESIGNER_ATTR = "tag_get_designer_attr";
    String URL_GET_DESIGNER_LIST = "designer/getDesignerList";
    String TAG_GET_DESIGNER_LIST = "tag_get_designer_list";
    interface Model extends BaseModel{
        Observable<DesignerAttrResult> getDesignerAttr();
        Observable<DesignerListResult> getDesignerList(DesignerListParam param);
    }
    interface View extends BaseLoadingView{
        void getDesignerAttrSuccess(DesignerAttrResult result);
        void getDesignerListSuccess(DesignerListResult result);
    }
    abstract class Presenter extends BasePresenter<Model,View>{
        public abstract void getDesignerAttr();
        public abstract void getDesignerList(DesignerListParam param);
    }
}
