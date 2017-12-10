package com.bohui.art.common.fragment;


import com.bohui.art.common.helperutil.AbsBaseHelperUtil;
import com.framework.core.base.AbsHelperUtil;
import com.framework.core.base.BaseFragment;
import com.framework.core.base.BaseModel;
import com.framework.core.base.BasePresenter;

/**
 * @author : gaojigong
 * @date : 2017/11/29
 * @description:
 */


public abstract class AbsBaseFragment<P extends BasePresenter, M extends BaseModel> extends BaseFragment<P,M> {

    @Override
    protected void doBeforeOnCreateView() {

    }
    @Override
    protected AbsHelperUtil createHelperUtil() {
        return new AbsBaseHelperUtil(this);
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
