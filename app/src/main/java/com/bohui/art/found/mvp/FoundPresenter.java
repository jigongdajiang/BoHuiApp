package com.bohui.art.found.mvp;

import com.bohui.art.bean.common.BannerResult;
import com.bohui.art.bean.found.FoundResult;
import com.bohui.art.common.net.AppProgressSubScriber;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 */


public class FoundPresenter extends FoundContact.Presenter {
    @Override
    public void getFoundBanner() {
        mRxManage.add(mModel.getFoundBanner().subscribeWith(new AppProgressSubScriber<FoundResult>() {
            @Override
            protected void onResultSuccess(FoundResult result) {
                mView.getFoundBannerSuccess(result);
            }
        }));
    }
}
