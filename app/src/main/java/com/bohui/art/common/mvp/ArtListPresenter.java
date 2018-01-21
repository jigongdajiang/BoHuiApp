package com.bohui.art.common.mvp;

import com.bohui.art.bean.common.ArtListParam;
import com.bohui.art.bean.common.ArtListResult;
import com.bohui.art.common.net.AppProgressSubScriber;

/**
 * @author : gaojigong
 * @date : 2018/1/21
 * @description:
 */


public class ArtListPresenter extends ArtListContact.Presenter {
    @Override
    public void getArtList(ArtListParam param) {
        mRxManage.add(mModel.getArtList(param).subscribeWith(new AppProgressSubScriber<ArtListResult>() {
            @Override
            protected void onResultSuccess(ArtListResult result) {
                mView.getArtListSuccess(result);
            }
        }));
    }
}
