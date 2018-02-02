package com.bohui.art.mine.setting.mvp;

import com.bohui.art.bean.mine.CheckVersionResult;
import com.bohui.art.bean.mine.LogoutResult;
import com.framework.core.http.EasyHttp;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/8
 * @description:
 */


public class SettingModel implements SettingContact.Model {
    @Override
    public Observable<CheckVersionResult> checkVersion() {
        return EasyHttp.post(SettingContact.URL_CHECK_VERSION)
                .execute(CheckVersionResult.class);
    }

    @Override
    public Observable<LogoutResult> logout(long uid) {
        return EasyHttp.post(SettingContact.URL_LOGOUT)
                .params("id",String.valueOf(uid))
                .execute(LogoutResult.class);
    }
}
