package com.bohui.art.start.splash;

import android.content.Intent;
import android.view.KeyEvent;

import com.bohui.art.R;
import com.bohui.art.bean.start.SplashResult;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.app.AppParams;
import com.bohui.art.common.app.SharePreferenceKey;
import com.bohui.art.start.MainActivity;
import com.bohui.art.start.welcome.WelcomeActivity;
import com.bumptech.glide.Glide;
import com.framework.core.base.AbsHelperUtil;
import com.framework.core.base.BaseHelperUtil;
import com.framework.core.cache.core.CacheCoreFactory;
import com.framework.core.glideext.GlideUtil;
import com.framework.core.http.exception.ApiException;
import com.framework.core.util.StatusBarCompatUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * @author : gaojigong
 * @date : 2017/12/9
 * @description:
 * 启动页
 * 停留三秒
 * 如果是第一次 进入欢迎页
 * 不是第一次 进入主页
 */


public class SplashActivity extends AbsNetBaseActivity<SplashPresenter,SplashModel> implements SplashContact.View {
    @Override
    protected void doBeforeSetContentView() {
        ///确保程序是已启动过的，且没有完全退出
        boolean splashStarted = AppParams.getInstance().splashStarted;
        //防止启屏页每次都进入主界面
        if (!this.isTaskRoot()) {
            Intent mainIntent = getIntent();
            String action = mainIntent.getAction();
            if (mainIntent.hasCategory(Intent.CATEGORY_LAUNCHER) && action.equals(Intent.ACTION_MAIN)) {
                finish();
                return;
            }
        }
        AppParams.getInstance().splashStarted = true;
        super.doBeforeSetContentView();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
        //全屏
        new StatusBarCompatUtil.Builder(this).setSupportType(2).builder().apply();
    }

    @Override
    protected void extraInit() {
//        mPresenter.splash();
        jumpAfter();
    }

    @Override
    public void initPresenter() {
        mPresenter.setMV(mModel,this);
    }

    /**
     * 按返回键无反应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return false;
    }

    @Override
    protected boolean childInterceptException(String apiName, ApiException e) {
        if(SplashContact.TAG_SPLASH.equals(apiName)){
            //启动结果返回错误，直接进入主页
            jumpAfter();
        }
        return true;
    }

    @Override
    public void splashSuccess(SplashResult result) {
        //获取广告页图片，更新广告页图片
        //倒计时后进入主页
        jumpAfter();
    }
    private void jumpAfter() {
        mRxManager.add(Observable.timer(3000, TimeUnit.MILLISECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                boolean hasInstallAndStart = CacheCoreFactory.getPreferenceCache(mContext).load(Boolean.class, SharePreferenceKey.HAS_INSTALL_AND_START);
                if(hasInstallAndStart){
                    ((BaseHelperUtil)mHelperUtil).startAty(MainActivity.class,true);
                }else{
                    CacheCoreFactory.getPreferenceCache(mContext).save(SharePreferenceKey.HAS_INSTALL_AND_START,true);
                    ((BaseHelperUtil)mHelperUtil).startAty(WelcomeActivity.class,true);
                }
            }
        }));
    }
}
