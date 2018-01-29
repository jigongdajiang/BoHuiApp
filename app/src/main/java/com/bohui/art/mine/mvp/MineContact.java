package com.bohui.art.mine.mvp;

import com.bohui.art.bean.mine.MineInfoResult;
import com.framework.core.base.BaseModel;
import com.framework.core.base.BasePresenter;
import com.framework.core.base.BaseView;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2017/12/23
 * @description:
 */


public interface MineContact {
    String URL_MINE_GET_USERINFO = "user/personCenter";
    String TAG_MINE_GET_USERINFO = "url_mine_get_userinfo";
    interface Model extends BaseModel{
        Observable<MineInfoResult> getUserInfo(long uid);
    }
    interface View extends BaseView{
        void getUserInfoSuccess(MineInfoResult result);
    }
    abstract class Presenter extends BasePresenter<Model,View>{
        public abstract void getUserInfo(long uid);
    }
}
