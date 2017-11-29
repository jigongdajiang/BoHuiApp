package com.jimeijf.financing.start.login;

import android.widget.Button;
import android.widget.EditText;

import com.jimeijf.core.http.exception.ApiException;
import com.jimeijf.financing.R;
import com.jimeijf.financing.common.activity.AbsBaseActivity;
import com.jimeijf.financing.common.widget.dialog.DialogFactory;
import com.jimeijf.financing.common.widget.title.DefaultTitleBar;
import com.jimeijf.financing.start.login.result.LoginResult;
import com.jimeijf.financing.start.login.result.VerCodeResult;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : gaojigong
 * @date : 2017/11/17
 * @description:
 */


public class LoginActivity extends AbsBaseActivity<LoginPresenter,LoginModel> implements LoginContact.View {
    @BindView(R.id.edt_phone)
    EditText edt_phone;
    @BindView(R.id.edt_verify_code)
    EditText edt_verify_code;
    @BindView(R.id.btn_get_verify_code)
    Button btn_get_verify_code;
    @BindView(R.id.btn_login)
    Button btn_login;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected LoginModel createModel() {
        return new LoginModel();
    }

    @Override
    public void initView() {
        new DefaultTitleBar.DefaultBuilder(this)
                .setLeftImageShow(false)
                .setTitle("登录")
                .builder();
    }

    @Override
    protected void initModel() {

    }

    @Override
    public void initPresenter() {
        mPresenter.setMV(mModel,this);
    }

    @OnClick(R.id.btn_get_verify_code)
    public void getVerCode(){
        String mobile = edt_phone.getText().toString();
        mPresenter.getVerCode(mobile);
    }
    @OnClick(R.id.btn_login)
    public void doLogin(){
        String mobile = edt_phone.getText().toString();
        String verCode = edt_verify_code.getText().toString();
        mPresenter.login(mobile,verCode);
    }
    @Override
    public void getVerCodeSuccess(VerCodeResult verCodeResult) {
        DialogFactory.createDefalutMessageDialog(this,getSupportFragmentManager(),verCodeResult.toString());
    }

    @Override
    public void loginSuccess(LoginResult loginResult) {
        DialogFactory.createDefalutMessageDialog(this,getSupportFragmentManager(),loginResult.toString());
    }

    @Override
    public void requestFailed(ApiException e) {
        DialogFactory.createDefalutMessageDialog(this,getSupportFragmentManager(),e.getMessage());
    }

}
