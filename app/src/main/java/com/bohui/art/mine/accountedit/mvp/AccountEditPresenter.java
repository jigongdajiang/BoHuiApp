package com.bohui.art.mine.accountedit.mvp;

import com.bohui.art.bean.mine.AccountEditResult;
import com.bohui.art.common.net.AppProgressSubScriber;

/**
 * @author : gaojigong
 * @date : 2018/1/8
 * @description:
 */


public class AccountEditPresenter extends AccountEditContact.Presenter {
    @Override
    public void accountEdit() {
        mRxManage.add(mModel.accountEdit().subscribeWith(new AppProgressSubScriber<AccountEditResult>(mView,AccountEditContact.TAG_ACCOUNT_EDIT,mView) {
            @Override
            protected void onResultSuccess(AccountEditResult result) {
                mView.accountEditSuccess(result);
            }
        }));
    }
}
