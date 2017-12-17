package com.bohui.art.mine.accountedit;

import com.bohui.art.R;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.widget.title.DefaultTitleBar;

/**
 * @author : gaojigong
 * @date : 2017/12/16
 * @description:
 */


public class AccountEditActivity extends AbsNetBaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_account_edit;
    }

    @Override
    public void initView() {
        new DefaultTitleBar.DefaultBuilder(mContext)
                .setTitle("个人资料")
                .setRightText("保存")
                .builder();
    }
}
