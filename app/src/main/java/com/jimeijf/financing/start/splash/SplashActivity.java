package com.jimeijf.financing.start.splash;

import com.jimeijf.financing.R;
import com.jimeijf.financing.common.activity.AbsBaseActivity;

/**
 * @author : gongdaocai
 * @date : 2017/11/17
 * FileName:
 * @description:
 */


public class SplashActivity extends AbsBaseActivity<SplashPresenter,SplashModel> implements SplashContact.View {
    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {

    }
}
