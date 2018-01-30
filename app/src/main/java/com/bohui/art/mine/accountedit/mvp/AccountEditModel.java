package com.bohui.art.mine.accountedit.mvp;

import com.bohui.art.bean.mine.AccountEditResult;
import com.framework.core.http.EasyHttp;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/8
 * @description:
 */


public class AccountEditModel implements AccountEditContact.Model {
    @Override
    public Observable<AccountEditResult> accountEdit(UserInfoEditParam param) {
        return EasyHttp.post(AccountEditContact.URL_ACCOUNT_DEIT)
                .params("uid",String.valueOf(param.getUid()))
                .params("sex",String.valueOf(param.getSex()))
                .params("name",param.getName())
                .params("industry",param.getIndustry())
                .execute(AccountEditResult.class);
    }
}
