package com.bohui.art.detail.art.mvp;

import com.bohui.art.bean.detail.ArtDetailResult;
import com.bohui.art.common.net.AppProgressSubScriber;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 */


public class ArtDetailPesenter extends ArtDetailContact.Presenter {
    @Override
    public void getArtDetail(String id) {
        mRxManage.add(mModel.getArtDetail(id).subscribeWith(new AppProgressSubScriber<ArtDetailResult>(mView, ArtDetailContact.TAG_GET_ART_DETAIL, mView) {
            @Override
            protected void onResultSuccess(ArtDetailResult result) {
                mView.getArtDetailSuccess(result);
            }
        }));
    }
}
