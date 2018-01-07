package com.bohui.art.home.art1.mvp;

import com.bohui.art.bean.home.ArtListResult;
import com.bohui.art.common.net.AppProgressSubScriber;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 */


public class ArtListPresenter extends ArtListContact.Presenter {
    @Override
    public void getArtList(String level2, int pageSize, int pageNumber) {
        mRxManage.add(mModel.getArtList(level2,pageSize,pageNumber)
        .subscribeWith(new AppProgressSubScriber<ArtListResult>() {
            @Override
            protected void onResultSuccess(ArtListResult result) {
                mView.getArtListSuccess(result);
            }
        }));
    }
}
