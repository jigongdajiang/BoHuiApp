package com.bohui.art.mine.setting.mvp;

import com.bohui.art.bean.mine.CheckVersionResult;
import com.bohui.art.bean.mine.LogoutResult;
import com.bohui.art.common.net.mvp.BaseLoadingView;
import com.framework.core.base.BaseModel;
import com.framework.core.base.BasePresenter;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/8
 * @description:
 */


public interface SettingContact {
    String URL_CHECK_VERSION = "login/isVersion";
    String TAG_CHECK_VERSION = "tag_check_version";

    String URL_LOGOUT = "login/logout";
    String TAG_LOGOUT = "TAG_LOGOUT";

    interface Model extends BaseModel{
        Observable<CheckVersionResult> checkVersion();
        Observable<LogoutResult> logout(long uid);
    }
    interface View extends BaseLoadingView{
        void checkVersionSuccess(CheckVersionResult result);
        void logoutSuccess(LogoutResult result);
    }
    abstract class Presenter extends BasePresenter<Model,View>{
        public abstract void checkVersion();
        public abstract void logout(long uid);
    }

}
