package com.bohui.art.search.mvp;

import com.bohui.art.bean.search.AllClassifyResult;
import com.bohui.art.common.net.AppProgressSubScriber;

/**
 * @author : gaojigong
 * @date : 2018/2/3
 * @description:
 */


public class GetAllClassifyPresenter extends GetAllClassifyContact.Presenter {
    @Override
    public void getAllClassify() {
        mRxManage.add(mModel.getAllClassify().subscribeWith(new AppProgressSubScriber<AllClassifyResult>(mView,GetAllClassifyContact.TAG_GET_ALL_CLASSIFY) {
            @Override
            protected void onResultSuccess(AllClassifyResult result) {
                mView.getAllClassifySuccess(result);
            }
        }));
    }
}
