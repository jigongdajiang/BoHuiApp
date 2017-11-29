package com.jimeijf.financing.common.activity;

import android.content.pm.ActivityInfo;

import com.jimeijf.core.base.BaseActivity;
import com.jimeijf.core.base.BaseModel;
import com.jimeijf.core.base.BasePresenter;
import com.jimeijf.core.util.StatusBarCompatUtil;

/**
 * @author : gongdaocai
 * @date : 2017/11/29
 * FileName:
 * @description:
 */

public abstract class AbsBaseActivity<P extends BasePresenter, M extends BaseModel> extends BaseActivity<P,M>{
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
}