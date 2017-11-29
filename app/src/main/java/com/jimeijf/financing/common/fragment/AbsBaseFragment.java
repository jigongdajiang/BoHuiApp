package com.jimeijf.financing.common.fragment;

import com.jimeijf.core.base.BaseFragment;
import com.jimeijf.core.base.BaseModel;
import com.jimeijf.core.base.BasePresenter;

/**
 * @author : gongdaocai
 * @date : 2017/11/29
 * FileName:
 * @description:
 */


public abstract class AbsBaseFragment<P extends BasePresenter, M extends BaseModel> extends BaseFragment<P,M> {

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
}
