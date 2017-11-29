package com.bohui.art.start.splash;

import com.framework.core.util.StatusBarCompatUtil;
import com.bohui.art.R;
import com.bohui.art.common.activity.AbsBaseActivity;

/**
 * @author : gaojigong
 * @date : 2017/11/17
 * @description:
 *
 * 防止从后台进入每次都进入启屏页
 * 全屏模式
 * 展示图片
 *  3秒后
 *      如果是第一次安装启动，进入欢迎页
 *      如果已经启动过，请求启动页接口，返回后显示图片
 *          图片加载成功后进行5秒倒计时，倒计时过程中可以点击跳转到响应广告页
 *          加载失败进入主页面
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
