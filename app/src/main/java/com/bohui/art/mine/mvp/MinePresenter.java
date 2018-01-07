package com.bohui.art.mine.mvp;

import com.bohui.art.bean.mine.MineInfoResult;
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
                .subscribe(new BaseSubscriber<MineInfoResult>(mView,MineContact.TAG_MINE_GET_USERINFO) {
                    @Override
                    protected void onResultSuccess(MineInfoResult mineInfoResult) {
                        mView.getUserInfoSuccess(mineInfoResult);
                    }
                });
    }
}
