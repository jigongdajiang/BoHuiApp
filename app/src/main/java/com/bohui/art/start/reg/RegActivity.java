package com.bohui.art.start.reg;

import android.view.View;
import android.widget.TextView;

import com.bohui.art.R;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.helperutil.NetBaseHelperUtil;
import com.bohui.art.common.util.RxViewUtil;
import com.bohui.art.common.widget.title.DefaultTitleBar;
import com.bohui.art.start.MainActivity;
import com.bohui.art.start.login.LoginActivity;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * @author : gaojigong
 * @date : 2017/12/24
 * @description:
 */


public class RegActivity extends AbsNetBaseActivity {
    @BindView(R.id.tv_btn_reg)
    TextView tv_btn_reg;
    @BindView(R.id.tv_re_login)
    TextView tv_re_login;
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
                ((NetBaseHelperUtil)mHelperUtil).startAty(MainActivity.class);
            }
        });
        RxViewUtil.addOnClick(mRxManager, tv_re_login, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                ((NetBaseHelperUtil)mHelperUtil).startAty(LoginActivity.class);
            }
        });
    }
}
