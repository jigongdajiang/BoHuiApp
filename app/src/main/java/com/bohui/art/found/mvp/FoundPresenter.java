package com.bohui.art.found.mvp;

import com.bohui.art.bean.common.BannerResult;
import com.bohui.art.common.net.AppProgressSubScriber;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 */


public class FoundPresenter extends FoundContact.Presenter {
    @Override
    public void getFoundBanner() {
        mRxManage.add(mModel.getFoundBanner().subscribeWith(new AppProgressSubScriber<BannerResult>() {
            @Override
            protected void onResultSuccess(BannerResult result) {
                mView.getFoundBannerSuccess(result);
            }
        }));
    }
}
