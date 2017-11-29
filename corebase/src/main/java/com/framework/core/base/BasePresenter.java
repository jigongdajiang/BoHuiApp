package com.framework.core.base;

import android.content.Context;

import com.framework.core.http.exception.AbsApiExceptionHandler;
import com.framework.core.rxcore.RxManager;

/**
 * @author : gaojigong
 * @date : 2017/11/13
 * @description:
 * M V 的中间交互组件
 */


public abstract class BasePresenter<M extends BaseModel, V extends BaseView> {
    public Context mContext;
    public M mModel;
    public V mView;
    public AbsApiExceptionHandler mApiExceptionHandler;
    public RxManager mRxManage = new RxManager();

    public void setMV(M m, V v) {
        this.mModel = m;
        this.mView = v;
    }

    public void setApiExceptionHandler(AbsApiExceptionHandler apiExceptionHandler) {
        this.mApiExceptionHandler = apiExceptionHandler;
    }

    public void onDestroy() {
        mRxManage.clear();
    }
}
