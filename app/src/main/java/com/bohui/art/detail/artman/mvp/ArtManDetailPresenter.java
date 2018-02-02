package com.bohui.art.detail.artman.mvp;

import com.bohui.art.bean.detail.ArtMainDetailResult;
import com.bohui.art.bean.detail.CAResult;
import com.bohui.art.common.net.AppProgressSubScriber;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 */


public class ArtManDetailPresenter extends ArtManDetailContact.Presenter {
    @Override
    public void getArtManDetail(long uid,long aid) {
        mRxManage.add(mModel.getArtManDetail(uid,aid).subscribeWith(new AppProgressSubScriber<ArtMainDetailResult>(mView,ArtManDetailContact.TAG_GET_ART_MAN_DETAIL,mView) {
            @Override
            protected void onResultSuccess(ArtMainDetailResult result) {
                mView.getArtManDetailSuccess(result);
            }
        }));
    }

    @Override
    public void attentionArtMan(long uid, long artId, int type) {
        mRxManage.add(mModel.attentionArtMan(uid,artId,type).subscribeWith(new AppProgressSubScriber<CAResult>(mView,ArtManDetailContact.TAG_ATTENTION_ART_MAN,mView) {
            @Override
            protected void onResultSuccess(CAResult result) {
                mView.attentionArtManSuccess(result);
            }
        }));
    }
}
