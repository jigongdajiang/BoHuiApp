package com.bohui.art.detail.artman.mvp;

import com.bohui.art.bean.detail.ArtMainDetailResult;
import com.bohui.art.common.net.AppProgressSubScriber;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 */


public class ArtManDetailPresenter extends ArtManDetailContact.Presenter {
    @Override
    public void getArtManDetail(String id) {
        mRxManage.add(mModel.getArtManDetail(id).subscribeWith(new AppProgressSubScriber<ArtMainDetailResult>(mView,ArtManDetailContact.TAG_GET_ART_MAN_DETAIL,mView) {
            @Override
            protected void onResultSuccess(ArtMainDetailResult result) {
                mView.getArtManDetailSuccess(result);
            }
        }));
    }
}
