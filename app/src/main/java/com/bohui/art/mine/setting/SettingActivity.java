package com.bohui.art.mine.setting;

import android.os.Build;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.bohui.art.R;
import com.bohui.art.bean.mine.CheckVersionResult;
import com.bohui.art.bean.mine.LogoutResult;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.app.AppFuntion;
import com.bohui.art.common.util.helperutil.NetBaseHelperUtil;
import com.bohui.art.common.util.RxViewUtil;
import com.bohui.art.common.widget.title.DefaultTitleBar;
import com.bohui.art.mine.setting.changepassword.ChangePasswordActivity;
import com.bohui.art.mine.setting.mvp.SettingContact;
import com.bohui.art.mine.setting.mvp.SettingModel;
import com.bohui.art.mine.setting.mvp.SettingPresenter;
import com.bohui.art.mine.setting.suggest.SuggestActivity;
import com.bohui.art.start.MainActivity;
import com.bohui.art.start.login.LoginActivity;
import com.framework.core.base.BaseHelperUtil;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * @author : gaojigong
 * @date : 2017/12/16
 * @description:
 */


public class SettingActivity extends AbsNetBaseActivity<SettingPresenter,SettingModel> implements SettingContact.View {
    @BindView(R.id.rl_password_change)
    RelativeLayout rl_password_change;
    @BindView(R.id.rl_suggest)
    RelativeLayout rl_suggest;
    @BindView(R.id.rl_update)
    RelativeLayout rl_update;
    @BindView(R.id.rl_exit)
    RelativeLayout rl_exit;

    private UpdateHelper updateHelper;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {
        new DefaultTitleBar.DefaultBuilder(mContext)
                .setTitle("设置")
                .builder();
        RxViewUtil.addOnClick(mRxManager, rl_password_change, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                ((BaseHelperUtil)mHelperUtil).startAty(ChangePasswordActivity.class);
            }
        });
        RxViewUtil.addOnClick(mRxManager, rl_suggest, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                ((BaseHelperUtil)mHelperUtil).startAty(SuggestActivity.class);
            }
        });
        RxViewUtil.addOnClick(mRxManager, rl_update, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                mPresenter.checkVersion();
            }
        });
        RxViewUtil.addOnClick(mRxManager, rl_exit, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                mPresenter.logout(AppFuntion.getUid());
            }
        });
    }

    @Override
    public void initPresenter() {
        mPresenter.setMV(mModel,this);
    }

    @Override
    public void checkVersionSuccess(CheckVersionResult result) {
        if(result.getStatus() == 1){
            //有更新
            updateHelper = new UpdateHelper(SettingActivity.this,result);
            updateHelper.handleUpdate();
        }else{
            showMsgDialg("当前是最新版本");
        }
    }

    @Override
    public void logoutSuccess(LogoutResult result) {
        if(result.getIsSuccess() == 1){
            //成功
            AppFuntion.staticLogout(mContext);
            Bundle bundle = new Bundle();
            bundle.putBoolean("logout",true);
            startAty(MainActivity.class,bundle);
        }else{
            showMsgDialg("退出登录失败，请稍后重试");
        }
    }
}
