package com.bohui.art.start.reg;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.bohui.art.R;
import com.bohui.art.bean.start.LoginResult;
import com.bohui.art.bean.start.RegResult;
import com.bohui.art.bean.start.VerCodeResult;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.app.AppFuntion;
import com.bohui.art.common.util.helperutil.NetBaseHelperUtil;
import com.bohui.art.common.util.RxViewUtil;
import com.bohui.art.common.widget.title.DefaultTitleBar;
import com.bohui.art.start.MainActivity;
import com.bohui.art.start.login.LoginActivity;
import com.framework.core.util.StrOperationUtil;
import com.framework.core.util.StrVerifyUtil;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * @author : gaojigong
 * @date : 2017/12/24
 * @description:
 */


public class RegActivity extends AbsNetBaseActivity<RegPresenter,RegModel> implements RegContact.View{
    @BindView(R.id.tv_btn_reg)
    TextView tv_btn_reg;
    @BindView(R.id.tv_re_login)
    TextView tv_re_login;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.cb_xieyi)
    CheckBox cb_xieyi;
    @Override
    public int getLayoutId() {
        return R.layout.activity_reg;
    }

    @Override
    public void initView() {
        new DefaultTitleBar.DefaultBuilder(mContext)
                .setBackgroundColor(R.color.sys_color_base)
                .setLeftImageResourceId(R.mipmap.ic_back_white)
                .builder();
        RxViewUtil.addOnClick(mRxManager, tv_btn_reg, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                boolean isCheck = cb_xieyi.isChecked();
                if(!isCheck){
                    showMsgDialg("请先同意用户协议");
                    return;
                }
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
                mPresenter.reg(phone,password);
            }
        });
        RxViewUtil.addOnClick(mRxManager, tv_re_login, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                ((NetBaseHelperUtil)mHelperUtil).startAty(LoginActivity.class);
            }
        });
    }

    @Override
    public void initPresenter() {
        mPresenter.setMV(mModel,this);
    }

    @Override
    public void regSuccess(LoginResult result) {
        //保存用户登录信息
        AppFuntion.saveUserInfo(result);
        //进入首页
        startAty(MainActivity.class);
    }
}
