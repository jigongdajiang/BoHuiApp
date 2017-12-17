package com.bohui.art.found.order;

import com.bohui.art.R;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.widget.title.DefaultTitleBar;

/**
 * @author : gaojigong
 * @date : 2017/12/16
 * @description: 定制页
 */


public class OrderActivity extends AbsNetBaseActivity{
    @Override
    public int getLayoutId() {
        return R.layout.activity_order;
    }

    @Override
    public void initView() {
        new DefaultTitleBar.DefaultBuilder(mContext)
                .setTitle("我要定制")
                .builder();
    }
}
