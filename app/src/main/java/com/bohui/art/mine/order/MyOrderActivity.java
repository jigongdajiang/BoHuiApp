package com.bohui.art.mine.order;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bohui.art.R;
import com.bohui.art.bean.mine.MyOrderBean;
import com.bohui.art.bean.mine.MyOrderListResult;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.app.AppFuntion;
import com.bohui.art.common.widget.title.DefaultTitleBar;
import com.bohui.art.found.order.OrderActivity;
import com.bohui.art.mine.collect.mvp.MyCollectParam;
import com.bohui.art.mine.order.mvp.MyOrderContact;
import com.bohui.art.mine.order.mvp.MyOrderModel;
import com.bohui.art.mine.order.mvp.MyOrderPresenter;
import com.framework.core.base.BaseHelperUtil;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.listener.RvClickListenerIml;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author : gaojigong
 * @date : 2017/12/16
 * @description:
 */


public class MyOrderActivity extends AbsNetBaseActivity<MyOrderPresenter,MyOrderModel> implements MyOrderContact.View {
    @BindView(R.id.rv)
    RecyclerView rv;
    private MyOrderAdapter myOrderAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.layout_common_rv;
    }

    @Override
    public void initView() {
        new DefaultTitleBar.DefaultBuilder(mContext)
                .setTitle("我的定制")
                .builder();
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(mContext);
        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);

        myOrderAdapter = new MyOrderAdapter(mContext);
        myOrderAdapter.setDelegateAdapter(delegateAdapter);
        delegateAdapter.addAdapter(myOrderAdapter);
        rv.setLayoutManager(virtualLayoutManager);
        rv.setAdapter(delegateAdapter);
        rv.addOnItemTouchListener(new RvClickListenerIml(){
            @Override
            public void onItemClick(BaseAdapter adapter, View view, int position) {

            }
        });

    }

    @Override
    public void initPresenter() {
        mPresenter.setMV(mModel,this);
    }

    @Override
    protected void extraInit() {
        MyCollectParam param = new MyCollectParam();
        param.setUid(AppFuntion.getUid());
        mPresenter.myOrder(param);
    }

    @Override
    public void myOrderSuccess(MyOrderListResult result) {
        myOrderAdapter.replaceAllItem(result.getList());
    }
}
