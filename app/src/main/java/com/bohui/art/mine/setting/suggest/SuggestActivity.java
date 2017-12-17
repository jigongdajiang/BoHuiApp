package com.bohui.art.mine.setting.suggest;

import com.bohui.art.R;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.widget.title.DefaultTitleBar;

/**
 * @author : gaojigong
 * @date : 2017/12/16
 * @description:
 */


public class SuggestActivity extends AbsNetBaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_suggest;
    }

    @Override
    public void initView() {
        new DefaultTitleBar.DefaultBuilder(mContext)
                .setTitle("咨询建议")
                .setRightText("提交")
                .builder();
    }
}
