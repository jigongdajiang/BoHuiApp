package com.bohui.art.start.login;

import android.view.View;
import android.widget.TextView;

import com.bohui.art.R;
import com.bohui.art.bean.start.LoginResult;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.util.helperutil.NetBaseHelperUtil;
import com.bohui.art.common.util.RxViewUtil;
import com.bohui.art.common.widget.title.DefaultTitleBar;
import com.bohui.art.start.MainActivity;
import com.bohui.art.start.reg.RegActivity;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * @author : gaojigong
 * @date : 2017/12/24
 * @description:
 */


public class LoginActivity extends AbsNetBaseActivity<LoginPresenter,LoginModel> implements LoginContact.View{
    @BindView(R.id.tv_btn_login)
    TextView tv_btn_login;
    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        new DefaultTitleBar.DefaultBuilder(mContext)
                .setBackgroundColor(R.color.sys_color_base)
                .setLeftImageResourceId(R.mipmap.ic_back_white)
                .setRightText("免费注册")
                .setRightTextColor(R.color.sys_color_font_white)
                .setRightTextClickListner(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((NetBaseHelperUtil)mHelperUtil).startAty(RegActivity.class);
                    }
                })
                .builder();
        RxViewUtil.addOnClick(mRxManager, tv_btn_login, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                mPresenter.login("","");
//                ((NetBaseHelperUtil)mHelperUtil).startAty(MainActivity.class);
            }
        });
    }
    @Override
    public void initPresenter() {
        mPresenter.setMV(mModel,this);
    }

    @Override
    public void loginSuccess(LoginResult result) {
        //登录成功
        //保存用户登录标示信息

        //进入首页
        startAty(MainActivity.class);
    }
}
