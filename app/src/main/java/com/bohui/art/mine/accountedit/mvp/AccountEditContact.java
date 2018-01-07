package com.bohui.art.mine.accountedit.mvp;

import com.bohui.art.bean.mine.AccountEditResult;
import com.bohui.art.common.net.mvp.BaseLoadingView;
import com.framework.core.base.BaseModel;
import com.framework.core.base.BasePresenter;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/8
 * @description:
 */


public interface AccountEditContact {
    String URL_ACCOUNT_DEIT = "";
    String TAG_ACCOUNT_EDIT = "tag_account_edit";
    interface Model extends BaseModel{
        Observable<AccountEditResult> accountEdit();
    }
    interface View extends BaseLoadingView{
        void accountEditSuccess(AccountEditResult result);
    }
    abstract class Presenter extends BasePresenter<Model,View>{
        public abstract void accountEdit();
    }
}
