package com.bohui.art.mine.setting.changepassword;

import android.view.View;
import android.widget.EditText;

import com.bohui.art.R;
import com.bohui.art.bean.mine.ChangePasswordResult;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.app.AppFuntion;
import com.bohui.art.common.widget.title.DefaultTitleBar;
import com.bohui.art.mine.setting.changepassword.mvp.ChangePwdContanct;
import com.bohui.art.mine.setting.changepassword.mvp.ChangePwdModel;
import com.bohui.art.mine.setting.changepassword.mvp.ChangePwdPresenter;
import com.framework.core.util.StrOperationUtil;

import butterknife.BindView;

/**
 * @author : gaojigong
 * @date : 2017/12/16
 * @description:
 */


public class ChangePasswordActivity extends AbsNetBaseActivity<ChangePwdPresenter,ChangePwdModel> implements ChangePwdContanct.View {
    @BindView(R.id.et_current_password)
    EditText et_current_password;
    @BindView(R.id.et_new_password)
    EditText et_new_password;
    @Override
    public int getLayoutId() {
        return R.layout.activity_change_password;
    }

    @Override
    public void initView() {
        new DefaultTitleBar.DefaultBuilder(mContext)
                .setTitle("修改密码")
                .setRightText("保存")
                .setRightTextClickListner(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String oldPwd = et_current_password.getText().toString();
                        if(StrOperationUtil.isEmpty(oldPwd)){
                            showMsgDialg("原密码不能为空");
                            return;
                        }
                        String newPwd = et_new_password.getText().toString();
                        if(StrOperationUtil.isEmpty(newPwd)){
                            showMsgDialg("新密码不能为空");
                            return;
                        }
                        if(oldPwd.equals(newPwd)){
                            showMsgDialg("新旧密码不能相同");
                            return;
                        }
                        if(newPwd.length()<6 || oldPwd.length() <6){
                            showMsgDialg("新旧密码都不能小于6位");
                            return;
                        }
                        String op = StrOperationUtil.pwdMd5(oldPwd);
                        String np = StrOperationUtil.pwdMd5(newPwd);
                        mPresenter.changePwd(AppFuntion.getUid(),op,np);
                    }
                })
                .builder();
    }

    @Override
    public void initPresenter() {
        mPresenter.setMV(mModel,this);
    }

    @Override
    public void changePwdSuccess(ChangePasswordResult result) {

    }
}
