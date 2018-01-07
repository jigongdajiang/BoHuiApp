package com.bohui.art.found.artman.mvp;

import com.bohui.art.bean.found.ArtManListResult;
import com.bohui.art.common.net.AppProgressSubScriber;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 */


public class ArtManListPresenter extends ArtManListContact.IArtManListPresenter {
    @Override
    public void getArtManList() {
        mRxManage.add(mModel.getArtManList().subscribeWith(new AppProgressSubScriber<ArtManListResult>(mView,ArtManListContact.TAG_GET_ART_MAN_LIST,mView) {
            @Override
            protected void onResultSuccess(ArtManListResult artManListResult) {
                mView.getArtManListSuccess(artManListResult);
            }
        }));
    }
}
