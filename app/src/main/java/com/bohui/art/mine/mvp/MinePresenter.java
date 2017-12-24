package com.bohui.art.mine.mvp;

import com.bohui.art.mine.mvp.bean.MineInfoResult;
import com.framework.core.http.exception.ApiException;
import com.framework.core.http.subsciber.BaseSubscriber;

/**
 * @author : gaojigong
 * @date : 2017/12/23
 * @description:
 */


public class MinePresenter extends MineContact.Presenter {
    @Override
    public void getUserInfo(String uid) {
        mModel.getUserInfo(uid)
                .subscribe(new BaseSubscriber<MineInfoResult>() {
                    @Override
                    public void onNext(MineInfoResult mineInfoResult) {
                        mView.getUserInfoSuccess(mineInfoResult);
                    }

                    @Override
                    public void onError(ApiException e) {
                        mView.handleException(MineContact.TAG_MINE_GET_USERINFO,e);
                    }
                });
    }
}
