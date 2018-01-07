package com.bohui.art.detail.designer.mvp;

import com.bohui.art.bean.detail.DesignerDetailResult;
import com.bohui.art.common.net.AppProgressSubScriber;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 */


public class DesignerDetailPresenter extends DesignerDetailContact.Presenter {
    @Override
    public void getDesignerDetail(String id) {
        mRxManage.add(mModel.getDesingerDetail(id).subscribeWith(new AppProgressSubScriber<DesignerDetailResult>(mView,DesignerDetailContact.TAG_GET_DESIGNER_DETAIL,mView) {
            @Override
            protected void onResultSuccess(DesignerDetailResult result) {
                mView.getDesignerDetailSuccess(result);
            }
        }));
    }
}
