package com.bohui.art.mine.setting.changepassword.mvp;

import com.bohui.art.bean.mine.ChangePasswordResult;
import com.framework.core.http.EasyHttp;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/8
 * @description:
 */


public class ChangePwdModel implements ChangePwdContanct.Model {
    @Override
    public Observable<ChangePasswordResult> changePwd() {
        return EasyHttp.post(ChangePwdContanct.URL_CHANGE_PWD)
                .execute(ChangePasswordResult.class);
    }
}
