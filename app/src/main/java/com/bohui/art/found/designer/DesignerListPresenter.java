package com.bohui.art.found.designer;

import com.bohui.art.bean.found.DesignerAttrResult;
import com.bohui.art.bean.found.DesignerListParam;
import com.bohui.art.bean.found.DesignerListResult;
import com.bohui.art.common.net.AppProgressSubScriber;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 */


public class DesignerListPresenter extends DesignerListContact.Presenter {
    @Override
    public void getDesignerAttr() {
        mRxManage.add(mModel.getDesignerAttr().subscribeWith(new AppProgressSubScriber<DesignerAttrResult>(mView,DesignerListContact.TAG_GET_DESIGNER_ATTR,mView) {
            @Override
            protected void onResultSuccess(DesignerAttrResult result) {
                mView.getDesignerAttrSuccess(result);
            }
        }));
    }

    @Override
    public void getDesignerList(DesignerListParam param) {
        mRxManage.add(mModel.getDesignerList(param).subscribeWith(new AppProgressSubScriber<DesignerListResult>(mView,DesignerListContact.TAG_GET_DESIGNER_LIST,mView) {
            @Override
            protected void onResultSuccess(DesignerListResult designerListResult) {
                mView.getDesignerListSuccess(designerListResult);
            }
        }));
    }
}
