package com.jimeijf.financing.start.splash;

import com.jimeijf.core.util.StatusBarCompatUtil;
import com.jimeijf.financing.R;
import com.jimeijf.financing.common.activity.AbsBaseActivity;

/**
 * @author : gongdaocai
 * @date : 2017/11/17
 * FileName:
 * @description:
 *
 * 防止从后台进入每次都进入启屏页
 * 全屏模式
 *
 */


public class SplashActivity extends AbsBaseActivity<SplashPresenter,SplashModel> implements SplashContact.View {
    @Override
    protected void doBeforeSetContentView() {
        super.doBeforeSetContentView();
        new StatusBarCompatUtil.Builder(this).setSupportType(2).builder().apply();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {

    }
}
