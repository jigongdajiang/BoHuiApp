package com.bohui.art.mine.setting.changepassword.mvp;

import com.bohui.art.bean.mine.ChangePasswordResult;
import com.bohui.art.common.net.AppProgressSubScriber;

/**
 * @author : gaojigong
 * @date : 2018/1/8
 * @description:
 */


public class ChangePwdPresenter extends ChangePwdContanct.Presenter {
    @Override
    public void changePwd(long uid,String oldpassword,String password) {
        mRxManage.add(mModel.changePwd(uid,oldpassword,password).subscribeWith(new AppProgressSubScriber<ChangePasswordResult>(mView,ChangePwdContanct.TAG_CHANGE_PWD,mView) {
            @Override
            protected void onResultSuccess(ChangePasswordResult result) {
                mView.changePwdSuccess(result);
            }
        }));
    }
}
