package com.bohui.art.mine.collect.mvp;

import com.bohui.art.bean.mine.MyCollectResult;
import com.bohui.art.common.net.AppProgressSubScriber;

/**
 * @author : gaojigong
 * @date : 2018/1/8
 * @description:
 */


public class MyCollectPresenter extends MyCollectContact.Presenter {
    @Override
    public void myCollectList() {
        mRxManage.add(mModel.myCollectList().subscribeWith(new AppProgressSubScriber<MyCollectResult>(mView,MyCollectContact.TAG_MY_COLLECT,mView) {
            @Override
            protected void onResultSuccess(MyCollectResult result) {
                mView.myCollectListSuccess(result);
            }
        }));
    }
}
