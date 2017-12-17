package com.bohui.art.mine.setting.changepassword;

import com.bohui.art.R;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.widget.title.DefaultTitleBar;

/**
 * @author : gaojigong
 * @date : 2017/12/16
 * @description:
 */


public class ChangePasswordActivity extends AbsNetBaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_change_password;
    }

    @Override
    public void initView() {
        new DefaultTitleBar.DefaultBuilder(mContext)
                .setTitle("修改密码")
                .setRightText("保存")
                .builder();
    }
}
