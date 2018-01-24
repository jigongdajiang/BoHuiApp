package com.bohui.art.mine.attention;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bohui.art.R;
import com.bohui.art.bean.home.ArtItemBean;
import com.bohui.art.bean.mine.MyAttentionResult;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.widget.title.DefaultTitleBar;
import com.bohui.art.detail.artman.ArtManDetailActivity;
import com.bohui.art.found.artman.ArtManListAdapter;
import com.bohui.art.bean.found.ArtManListResult;
import com.bohui.art.home.RecommendFragment;
import com.bohui.art.bean.home.ArtCoverItemBean;
import com.bohui.art.mine.attention.mvp.MyAttentionContact;
import com.bohui.art.mine.attention.mvp.MyAttentionModel;
import com.bohui.art.mine.attention.mvp.MyAttentionPresenter;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.listener.RvClickListenerIml;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author : gaojigong
 * @date : 2017/12/16
 * @description: 我的关注
 */


public class MyAttentionActivity extends AbsNetBaseActivity<MyAttentionPresenter,MyAttentionModel> implements MyAttentionContact.View {
    @BindView(R.id.rv)
    RecyclerView rv;
    @Override
    public int getLayoutId() {
        return R.layout.layout_common_rv;
    }

    @Override
    public void initView() {
        new DefaultTitleBar.DefaultBuilder(mContext)
                .setTitle("我的关注")
                .builder();
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(mContext);
        final DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        //猜你喜欢数据适配器
        ArtManListAdapter artManListAdapter = new ArtManListAdapter(mContext);
        List<ArtManListResult> artManListItemBeans = new ArrayList<>();
        for(int j=0;j<20;j++){
            ArtManListResult artManListItemBean = new ArtManListResult();
            artManListItemBeans.add(artManListItemBean);
        }
        delegateAdapter.addAdapter(artManListAdapter);
        rv.setLayoutManager(virtualLayoutManager);
        rv.setAdapter(delegateAdapter);
        rv.addOnItemTouchListener(new RvClickListenerIml(){
            @Override
            public void onItemClick(BaseAdapter adapter, View view, int position) {
                ArtManDetailActivity.comeIn(MyAttentionActivity.this,new Bundle());
            }
        });
    }

    @Override
    public void initPresenter() {
        mPresenter.setMV(mModel,this);
    }

    @Override
    protected void extraInit() {
        mPresenter.myAttention();
    }

    @Override
    public void myAttentionSuccess(MyAttentionResult result) {

    }
}
