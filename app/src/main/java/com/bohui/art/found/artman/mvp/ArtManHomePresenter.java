package com.bohui.art.found.artman.mvp;

import com.bohui.art.bean.found.ArtManHomeResult;
import com.bohui.art.common.net.AppProgressSubScriber;

/**
 * @author : gaojigong
 * @date : 2018/3/12
 * @description:
 */


public class ArtManHomePresenter extends ArtManHomeContact.AbsArtManHomePresenter {
    @Override
    public void getArtManHome() {
        mRxManage.add(mModel.getArtManHome().subscribeWith(new AppProgressSubScriber<ArtManHomeResult>(mView, ArtManHomeContact.TAG_ARTMAN_HOME, mView) {
            @Override
            protected void onResultSuccess(ArtManHomeResult artManHomeResult) {
                mView.getArtManHomeSuccess(artManHomeResult);
            }
        }));
    }
}
