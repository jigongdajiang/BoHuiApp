package com.bohui.art.detail.art.mvp;

import com.bohui.art.bean.detail.ArtDetailResult;
import com.bohui.art.bean.detail.CAResult;
import com.bohui.art.common.net.AppProgressSubScriber;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 */


public class ArtDetailPesenter extends ArtDetailContact.Presenter {
    @Override
    public void getArtDetail(long uid,long id) {
        mRxManage.add(mModel.getArtDetail(uid,id).subscribeWith(new AppProgressSubScriber<ArtDetailResult>(mView, ArtDetailContact.TAG_GET_ART_DETAIL, mView) {
            @Override
            protected void onResultSuccess(ArtDetailResult result) {
                mView.getArtDetailSuccess(result);
            }
        }));
    }

    @Override
    public void collectArt(long uid, long paintId, int type) {
        mRxManage.add(mModel.collectArt(uid,paintId,type).subscribeWith(new AppProgressSubScriber<CAResult>(mView, ArtDetailContact.TAG_COLLECT_ART, mView) {
            @Override
            protected void onResultSuccess(CAResult result) {
                mView.collectSuccess(result);
            }
        }));
    }
}
