package com.bohui.art.detail.artman.mvp;

import com.bohui.art.bean.common.ArtListResult;
import com.bohui.art.common.net.AppProgressSubScriber;

/**
 * @author : gaojigong
 * @date : 2018/1/30
 * @description:
 */


public class ArtJiPresenter extends ArtJiContanct.Preseneter {
    @Override
    public void getArtJi(long id) {
        mRxManage.add(mModel.getArtJi(id).subscribeWith(new AppProgressSubScriber<ArtListResult>(mView,ArtManDetailContact.TAG_GET_ART_MAN_DETAIL,mView) {
            @Override
            protected void onResultSuccess(ArtListResult result) {
                mView.getArtJiSuccess(result);
            }
        }));
    }
}
