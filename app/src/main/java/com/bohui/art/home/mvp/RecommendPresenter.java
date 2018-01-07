package com.bohui.art.home.mvp;

import com.bohui.art.bean.home.RecommendResult;
import com.bohui.art.common.net.AppProgressSubScriber;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 */


public class RecommendPresenter extends HomeContact.AbsRecommendPresenter {
    @Override
    public void getRecommend() {
        mRxManage.add(mModel.getRecommend().subscribeWith(new AppProgressSubScriber<RecommendResult>(mView,HomeContact.TAG_GET_RECOMMEND) {
            @Override
            protected void onResultSuccess(RecommendResult result) {
                mView.getRecommendSuccess(result);
            }
        }));
    }
}
