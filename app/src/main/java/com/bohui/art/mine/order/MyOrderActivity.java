package com.bohui.art.mine.order;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bohui.art.R;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.widget.title.DefaultTitleBar;
import com.bohui.art.found.order.OrderActivity;
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


public class MyOrderActivity extends AbsNetBaseActivity {
    @BindView(R.id.rv)
    RecyclerView rv;
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

        MyOrderAdapter myOrderAdapter = new MyOrderAdapter(mContext);
        List<MyOrderBean> myOrderBeans = new ArrayList<>();
        for(int i=0;i<20;i++){
            MyOrderBean myOrderBean = new MyOrderBean();
            myOrderBeans.add(myOrderBean);
        }
        myOrderAdapter.setDatas(myOrderBeans);
        delegateAdapter.addAdapter(myOrderAdapter);
        rv.setLayoutManager(virtualLayoutManager);
        rv.setAdapter(delegateAdapter);
        rv.addOnItemTouchListener(new RvClickListenerIml(){
            @Override
            public void onItemClick(BaseAdapter adapter, View view, int position) {
                ((BaseHelperUtil)mHelperUtil).startAty(OrderActivity.class);
            }
        });

    }
}
