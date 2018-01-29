package com.bohui.art.mine.collect;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bohui.art.R;
import com.bohui.art.bean.home.ArtItemBean;
import com.bohui.art.bean.mine.MyCollectResult;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.app.AppFuntion;
import com.bohui.art.common.widget.title.DefaultTitleBar;
import com.bohui.art.detail.art.ArtDetailActivity;
import com.bohui.art.home.art1.Art2Adapter;
import com.bohui.art.bean.home.ArtCoverItemBean;
import com.bohui.art.mine.collect.mvp.MyCollectContact;
import com.bohui.art.mine.collect.mvp.MyCollectModel;
import com.bohui.art.mine.collect.mvp.MyCollectParam;
import com.bohui.art.mine.collect.mvp.MyCollectPresenter;
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


public class MyCollectActivity extends AbsNetBaseActivity<MyCollectPresenter,MyCollectModel> implements MyCollectContact.View {
    @BindView(R.id.rv)
    RecyclerView rv;

    private Art2Adapter art2Adapter;
    @Override
    public int getLayoutId() {
        return R.layout.layout_common_rv;
    }

    @Override
    public void initView() {
        new DefaultTitleBar.DefaultBuilder(mContext)
                .setTitle("我的收藏")
                .builder();
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(mContext);
        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        art2Adapter = new Art2Adapter(mContext);
        art2Adapter.setDelegateAdapter(delegateAdapter);
        delegateAdapter.addAdapter(art2Adapter);
        rv.setLayoutManager(virtualLayoutManager);
        rv.setAdapter(delegateAdapter);
        rv.addOnItemTouchListener(new RvClickListenerIml(){
            @Override
            public void onItemClick(BaseAdapter adapter, View view, int position) {
                ArtDetailActivity.comeIn(MyCollectActivity.this,new Bundle());
            }
        });
    }

    @Override
    public void initPresenter() {
        mPresenter.setMV(mModel,this);
    }

    @Override
    protected void extraInit() {
        MyCollectParam myCollectParam = new MyCollectParam();
//        myCollectParam.setUid(AppFuntion.getUid());
        myCollectParam.setUid(1);
        mPresenter.myCollectList(myCollectParam);
    }

    @Override
    public void myCollectListSuccess(MyCollectResult result) {
        art2Adapter.replaceAllItem(result.getList());
    }
}
