package com.bohui.art.mine.accountedit;

import android.view.View;

import com.bohui.art.R;
import com.bohui.art.bean.mine.AccountEditResult;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.widget.title.DefaultTitleBar;
import com.bohui.art.mine.accountedit.mvp.AccountEditContact;
import com.bohui.art.mine.accountedit.mvp.AccountEditModel;
import com.bohui.art.mine.accountedit.mvp.AccountEditPresenter;

/**
 * @author : gaojigong
 * @date : 2017/12/16
 * @description:
 */


public class AccountEditActivity extends AbsNetBaseActivity<AccountEditPresenter,AccountEditModel> implements AccountEditContact.View {
    @Override
    public int getLayoutId() {
        return R.layout.activity_account_edit;
    }

    @Override
    public void initView() {
        new DefaultTitleBar.DefaultBuilder(mContext)
                .setTitle("个人资料")
                .setRightText("保存")
                .setRightTextClickListner(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.accountEdit();
                    }
                })
                .builder();
    }

    @Override
    public void initPresenter() {
        mPresenter.setMV(mModel,this);
    }

    @Override
    public void accountEditSuccess(AccountEditResult result) {

    }
}
