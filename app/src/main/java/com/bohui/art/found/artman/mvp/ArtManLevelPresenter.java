package com.bohui.art.found.artman.mvp;

import com.bohui.art.bean.found.ArtManLevelResult;
import com.bohui.art.common.net.AppProgressSubScriber;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 */


public class ArtManLevelPresenter extends ArtManListContact.IArtManLevelPresenter {
    @Override
    public void getArtManLevel() {
        mRxManage.add(mModel.getArtManLevel().subscribeWith(new AppProgressSubScriber<ArtManLevelResult>(mView,ArtManListContact.TAG_GET_ART_MAN_LEVEL,mView) {
            @Override
            protected void onResultSuccess(ArtManLevelResult result) {
                mView.getArtManLevelSuccess(result);
            }
        }));
    }
}
