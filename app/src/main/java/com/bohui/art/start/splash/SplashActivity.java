package com.bohui.art.start.splash;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bohui.art.R;
import com.bohui.art.bean.start.SplashResult;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.activity.BaseWebActivity;
import com.bohui.art.common.activity.CommonStaticActivity;
import com.bohui.art.common.app.AppParams;
import com.bohui.art.common.app.SharePreferenceKey;
import com.bohui.art.common.util.RxViewUtil;
import com.bohui.art.start.MainActivity;
import com.bohui.art.start.welcome.WelcomeActivity;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.framework.core.base.BaseHelperUtil;
import com.framework.core.cache.core.CacheCoreFactory;
import com.framework.core.glideext.GlideApp;
import com.framework.core.glideext.GlideUtil;
import com.framework.core.http.exception.ApiException;
import com.framework.core.util.StatusBarCompatUtil;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
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
    @BindView(R.id.iv_adv)
    ImageView iv_adv;
    @BindView(R.id.tv_leave_splash)
    TextView tv_leave_splash;
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
        new StatusBarCompatUtil.Builder(this).setSupportType(2).builder().apply();
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
        afterRequest();
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
            jump();
        }
        return true;
    }

    @Override
    public void splashSuccess(SplashResult result) {
        loadImg(result);
    }
    private void afterRequest() {
        mRxManager.add(Observable.timer(3000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                mPresenter.splash();
            }
        }));
    }
    private void jump(){
        boolean hasInstallAndStart = CacheCoreFactory.getPreferenceCache(mContext).load(Boolean.class, SharePreferenceKey.HAS_INSTALL_AND_START);
        if(hasInstallAndStart){
            ((BaseHelperUtil)mHelperUtil).startAty(MainActivity.class,true);
        }else{
            CacheCoreFactory.getPreferenceCache(mContext).save(SharePreferenceKey.HAS_INSTALL_AND_START,true);
            ((BaseHelperUtil)mHelperUtil).startAty(WelcomeActivity.class,true);
        }
    }
    private void loadImg(final SplashResult result) {
        if(null != result){
            if (!TextUtils.isEmpty(result.getCover())) {
                SimpleTarget target = new SimpleTarget<BitmapDrawable>() {
                    @Override
                    public void onResourceReady(BitmapDrawable resource, Transition transition) {
                        //图片真正加载成功
                        iv_adv.setImageDrawable(resource);
                        //添加点击事件
                        addClick(result);
                        openTimeCountdown();
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        jump();
                    }
                };
                GlideApp.with(this).load(result.getCover()).into(target);
            } else {
                jump();
            }
        }else{
            jump();
        }
    }

    private void openTimeCountdown() {
        final int count = 5;
        tv_leave_splash.setVisibility(View.VISIBLE);
        mRxManager.add(RxView.clicks(tv_leave_splash).throttleFirst(1,TimeUnit.SECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                jump();
            }
        }));
        tv_leave_splash.setText("跳过" + count + "s");
        mRxManager.add(Observable.interval(0,1,TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .take(count)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        long step = count - aLong;
                        tv_leave_splash.setText("跳过" + step + "s");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        jump();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        jump();
                    }
                }));
    }

    private void addClick(final SplashResult result) {
        RxViewUtil.addOnClick(mRxManager, iv_adv, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                if(null != result && !TextUtils.isEmpty(result.getLinkurl())){
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "splash");
                    bundle.putString(CommonStaticActivity.WEB_TITLE, "博辉艺术");
                    bundle.putString(CommonStaticActivity.WEB_URL_CONTENT, result.getLinkurl());
                    startAty(CommonStaticActivity.class,bundle,true);
                }
            }
        });
    }
}
