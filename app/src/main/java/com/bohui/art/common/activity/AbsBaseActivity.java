package com.bohui.art.common.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.bohui.art.R;
import com.bohui.art.common.helperutil.AbsBaseHelperUtil;
import com.framework.core.base.AbsHelperUtil;
import com.framework.core.base.BaseActivity;
import com.framework.core.base.BaseModel;
import com.framework.core.base.BasePresenter;
import com.framework.core.util.StatusBarCompatUtil;


/**
 * @author : gaojigong
 * @date : 2017/11/29
 * @description:
 *  基础抽象类，
 *      主要承载系统所有Activity的配置层逻辑
 *
 */

public abstract class AbsBaseActivity<P extends BasePresenter, M extends BaseModel> extends BaseActivity<P,M> {
    protected static final boolean OPEN_IMMERSIVE = true;

    @Override
    protected void doBeforeSetContentView() {
        //去掉标题栏
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        //强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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

    /**
     * 默认方案
     * 沉浸式
     * 顶部自适应padding
     * 图标颜色颜色根据指定决定 1 深色  2浅色
     * 如果有键盘监控时需要另行配置，否则将和键盘事件冲突
     * 由于要指定topView所以要在initView中使用
     */
    protected void defaultStatusBar(View topView, int changeIcon){
        if(OPEN_IMMERSIVE){
            new StatusBarCompatUtil.Builder(this)
                    .setSupportType(0)
                    .setPaddingChangedView(topView)
                    .setChangeIconType(changeIcon)
                    .setImmerseForIconColor(R.color.sys_status_bar_color)
                    .builder()
                    .apply();
        }
    }
    protected void defaultStatusBar(View topView){
        defaultStatusBar(topView,1);
    }




    public void showMsgDialg(String title, String message) {
        if(mHelperUtil != null && mHelperUtil instanceof AbsBaseHelperUtil){
            ((AbsBaseHelperUtil)mHelperUtil).showMsgDialg(title,message);
        }
    }

    public void showMsgDialg(String message) {
        showMsgDialg("提示", message);
    }

    public void showLoadingDialog() {
        if(mHelperUtil != null && mHelperUtil instanceof AbsBaseHelperUtil){
            ((AbsBaseHelperUtil)mHelperUtil).showLoadingDialog();
        }
    }

    public void missLoadingDialog() {
        if(mHelperUtil != null && mHelperUtil instanceof AbsBaseHelperUtil){
            ((AbsBaseHelperUtil)mHelperUtil).missLoadingDialog();
        }
    }

}