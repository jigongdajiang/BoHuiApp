package com.bohui.art.start.stop;

import android.view.KeyEvent;
import android.widget.TextView;

import com.bohui.art.R;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.util.RxViewUtil;
import com.bohui.art.start.login.LoginContact;
import com.framework.core.app.AtyManager;
import com.framework.core.http.EasyHttp;
import com.framework.core.http.exception.ApiException;
import com.framework.core.toast.ToastShow;
import com.framework.core.util.strformat.TimeFromatUtil;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * @author : gaojigong
 * @date : 2017/12/25
 * @description:
 */


public class StopServiceActivity extends AbsNetBaseActivity {
    @BindView(R.id.tv_copyby)
    TextView tv_copyby;//版权
    @BindView(R.id.tv_leave)
    TextView tv_leave;

    @Override
    public int getLayoutId() {
        return R.layout.activity_stop_service;
    }

    @Override
    public void initView() {
        tv_copyby.setText("© " + TimeFromatUtil.getCurrentDay() + " bohui.com 保留所有权利");
        RxViewUtil.addOnClick(mRxManager, tv_leave, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                AtyManager.getInstance().AppExit(mContext);
                System.exit(0);
            }
        });
    }

    /**
     * 按返回键的操作
     */
    long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastShow.showLong(mContext,"再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                AtyManager.getInstance().AppExit(mContext);
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
