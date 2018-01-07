package com.bohui.art.home.mvp;

import com.bohui.art.bean.home.ClassifyLevelResult;
import com.bohui.art.common.net.AppProgressSubScriber;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 */


public class HomePresenter extends HomeContact.AbsHomePresenter {
    @Override
    public void getClassifyLevel1() {
        mRxManage.add(mModel.getClassifyLevel1().subscribeWith(new AppProgressSubScriber<ClassifyLevelResult>(mView,HomeContact.TAG_GET_CLASSIFY_LEVET1) {
            @Override
            protected void onResultSuccess(ClassifyLevelResult result) {
                mView.getClassifyLevel1Success(result);
            }
        }));
    }
}
