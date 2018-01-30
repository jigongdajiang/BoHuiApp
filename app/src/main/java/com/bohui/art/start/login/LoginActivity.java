package com.bohui.art.start.login;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bohui.art.R;
import com.bohui.art.bean.start.LoginResult;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.app.AppFuntion;
import com.bohui.art.common.util.helperutil.NetBaseHelperUtil;
import com.bohui.art.common.util.RxViewUtil;
import com.bohui.art.common.widget.title.DefaultTitleBar;
import com.bohui.art.start.MainActivity;
import com.bohui.art.start.reg.RegActivity;
import com.framework.core.util.StrOperationUtil;
import com.framework.core.util.StrVerifyUtil;

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
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_password)
    EditText et_password;
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
                String phone  = et_phone.getText().toString();
                if(!StrVerifyUtil.isMobileNO(phone)){
                    showMsgDialg("请输入正确的手机号");
                    return;
                }
                String pwd = et_password.getText().toString();
                if(StrOperationUtil.isEmpty(pwd)){
                    showMsgDialg("密码不能为空");
                    return;
                }
                if(pwd.length() < 6){
                    showMsgDialg("密码不能小于6位");
                    return;
                }
                String password = StrOperationUtil.pwdMd5(pwd);

                mPresenter.login(phone,password);
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
        AppFuntion.saveUserInfo(result);
        //进入首页
        Bundle bundle = new Bundle();
        bundle.putBoolean("login_success",true);
        startAty(MainActivity.class,bundle);
    }
}
