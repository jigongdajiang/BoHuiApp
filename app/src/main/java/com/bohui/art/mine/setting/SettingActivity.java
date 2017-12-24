package com.bohui.art.mine.setting;

import android.widget.RelativeLayout;

import com.bohui.art.R;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.helperutil.NetBaseHelperUtil;
import com.bohui.art.common.util.RxViewUtil;
import com.bohui.art.common.widget.title.DefaultTitleBar;
import com.bohui.art.mine.setting.changepassword.ChangePasswordActivity;
import com.bohui.art.mine.setting.suggest.SuggestActivity;
import com.bohui.art.start.login.LoginActivity;
import com.framework.core.base.BaseHelperUtil;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * @author : gaojigong
 * @date : 2017/12/16
 * @description:
 */


public class SettingActivity extends AbsNetBaseActivity {
    @BindView(R.id.rl_password_change)
    RelativeLayout rl_password_change;
    @BindView(R.id.rl_suggest)
    RelativeLayout rl_suggest;
    @BindView(R.id.rl_update)
    RelativeLayout rl_update;
    @BindView(R.id.rl_exit)
    RelativeLayout rl_exit;

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

            }
        });
        RxViewUtil.addOnClick(mRxManager, rl_exit, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                ((NetBaseHelperUtil)mHelperUtil).startAty(LoginActivity.class);
            }
        });
    }
}
