package com.bohui.art.start.reg;

import com.bohui.art.bean.start.LoginResult;
import com.bohui.art.bean.start.RegResult;
import com.bohui.art.bean.start.VerCodeResult;
import com.bohui.art.common.net.mvp.BaseLoadingView;
import com.framework.core.base.BaseModel;
import com.framework.core.base.BasePresenter;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/2
 * @description:
 */


public interface RegContact {
    String URL_REG = "login/register";
    String TAG_REG = "tag_reg";
    interface Model extends BaseModel{
        Observable<LoginResult> reg(String mobile,String password);
    }
    interface View extends BaseLoadingView{
        void regSuccess(LoginResult result);
    }
    abstract class Presenter extends BasePresenter<Model,View>{
        public abstract void reg(String mobile,String password);
    }
}
