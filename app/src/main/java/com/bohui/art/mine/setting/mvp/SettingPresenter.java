package com.bohui.art.mine.setting.mvp;

import com.bohui.art.bean.mine.CheckVersionResult;
import com.bohui.art.bean.mine.LogoutResult;
import com.bohui.art.common.net.AppProgressSubScriber;

/**
 * @author : gaojigong
 * @date : 2018/1/8
 * @description:
 */


public class SettingPresenter extends SettingContact.Presenter {
    @Override
    public void checkVersion() {
        mRxManage.add(mModel.checkVersion().subscribeWith(new AppProgressSubScriber<CheckVersionResult>(mView,SettingContact.TAG_CHECK_VERSION,mView) {
            @Override
            protected void onResultSuccess(CheckVersionResult checkVersionResult) {
                mView.checkVersionSuccess(checkVersionResult);
            }
        }));
    }

    @Override
    public void logout() {
        mRxManage.add(mModel.logout().subscribeWith(new AppProgressSubScriber<LogoutResult>(mView,SettingContact.TAG_LOGOUT,mView) {
            @Override
            protected void onResultSuccess(LogoutResult result) {
                mView.logoutSuccess(result);
            }
        }));
    }
}
