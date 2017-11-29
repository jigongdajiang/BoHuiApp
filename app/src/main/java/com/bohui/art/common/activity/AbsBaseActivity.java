package com.bohui.art.common.activity;

import android.content.pm.ActivityInfo;

import com.framework.core.base.BaseActivity;
import com.framework.core.base.BaseModel;
import com.framework.core.base.BasePresenter;
import com.framework.core.http.exception.ApiException;
import com.framework.core.http.exception.IApiExceptionHandleFun;
import com.framework.core.util.StatusBarCompatUtil;
import com.bohui.art.common.net.CommonApiExceptionHandler;

/**
 * @author : gaojigong
 * @date : 2017/11/29
 * @description:
 */

public abstract class AbsBaseActivity<P extends BasePresenter, M extends BaseModel> extends BaseActivity<P,M> implements IApiExceptionHandleFun{
    @Override
    protected void doBeforeSetContentView() {
        //去掉标题栏
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        //强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //沉浸式
        new StatusBarCompatUtil.Builder(this)
                .setSupportType(1)
                .setColor(com.jimeijf.operation.R.color.sys_status_bar_color)
                .builder()
                .apply();
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