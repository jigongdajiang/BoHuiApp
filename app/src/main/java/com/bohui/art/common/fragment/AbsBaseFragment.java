package com.bohui.art.common.fragment;

import com.framework.core.base.BaseFragment;
import com.framework.core.base.BaseModel;
import com.framework.core.base.BasePresenter;
import com.framework.core.http.exception.ApiException;
import com.framework.core.http.exception.IApiExceptionHandleFun;
import com.bohui.art.common.net.CommonApiExceptionHandler;

/**
 * @author : gaojigong
 * @date : 2017/11/29
 * @description:
 */


public abstract class AbsBaseFragment<P extends BasePresenter, M extends BaseModel> extends BaseFragment<P,M> implements IApiExceptionHandleFun {

    @Override
    protected void doBeforeOnCreateView() {

    }

    @Override
    protected P createPresenter() {
        return null;
    }

    @Override
    protected M createModel() {
        return null;
    }

    @Override
    protected void initModel() {

    }

    @Override
    public void initPresenter() {

    }

    @Override
    public boolean exceptionHandle(ApiException e) {
        CommonApiExceptionHandler handler = new CommonApiExceptionHandler(this);
        return handler.exceptionHandle(e);
    }
}
