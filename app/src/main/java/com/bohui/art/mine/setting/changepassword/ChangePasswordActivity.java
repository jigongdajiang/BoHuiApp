package com.bohui.art.mine.setting.changepassword;

import android.view.View;

import com.bohui.art.R;
import com.bohui.art.bean.mine.ChangePasswordResult;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.widget.title.DefaultTitleBar;
import com.bohui.art.mine.setting.changepassword.mvp.ChangePwdContanct;
import com.bohui.art.mine.setting.changepassword.mvp.ChangePwdModel;
import com.bohui.art.mine.setting.changepassword.mvp.ChangePwdPresenter;

/**
 * @author : gaojigong
 * @date : 2017/12/16
 * @description:
 */


public class ChangePasswordActivity extends AbsNetBaseActivity<ChangePwdPresenter,ChangePwdModel> implements ChangePwdContanct.View {
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
                        mPresenter.changePwd();
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
