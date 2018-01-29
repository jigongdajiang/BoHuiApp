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
    public Observable<ChangePasswordResult> changePwd(long uid,String oldpassword,String password) {
        return EasyHttp.post(ChangePwdContanct.URL_CHANGE_PWD)
                .params("uid",String.valueOf(uid))
                .params("oldpassword",oldpassword)
                .params("password",password)
                .execute(ChangePasswordResult.class);
    }
}
