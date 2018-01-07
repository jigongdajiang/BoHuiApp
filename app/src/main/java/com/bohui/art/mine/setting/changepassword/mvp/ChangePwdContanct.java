package com.bohui.art.mine.setting.changepassword.mvp;

import com.bohui.art.bean.mine.ChangePasswordResult;
import com.bohui.art.common.net.mvp.BaseLoadingView;
import com.framework.core.base.BaseModel;
import com.framework.core.base.BasePresenter;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/8
 * @description:
 */


public interface ChangePwdContanct {
    String URL_CHANGE_PWD = "";
    String TAG_CHANGE_PWD = "tag_change_pwd";
    interface Model extends BaseModel{
        Observable<ChangePasswordResult> changePwd();
    }
    interface View extends BaseLoadingView{
        void changePwdSuccess(ChangePasswordResult result);
    }
    abstract class Presenter extends BasePresenter<Model,View>{
        public abstract void changePwd();
    }
}
