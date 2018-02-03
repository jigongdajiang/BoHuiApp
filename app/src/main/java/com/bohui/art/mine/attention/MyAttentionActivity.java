package com.bohui.art.mine.attention;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bohui.art.R;
import com.bohui.art.bean.common.ArtListResult;
import com.bohui.art.bean.found.ArtManItemBean;
import com.bohui.art.bean.home.ArtItemBean;
import com.bohui.art.bean.mine.MyAttentionResult;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.app.AppFuntion;
import com.bohui.art.common.widget.rv.adapter.NormalWrapAdapter;
import com.bohui.art.common.widget.title.DefaultTitleBar;
import com.bohui.art.detail.artman.ArtManDetailActivity;
import com.bohui.art.found.artman.ArtManListAdapter;
import com.bohui.art.bean.found.ArtManListResult;
import com.bohui.art.home.RecommendFragment;
import com.bohui.art.bean.home.ArtCoverItemBean;
import com.bohui.art.mine.attention.mvp.MyAttentionContact;
import com.bohui.art.mine.attention.mvp.MyAttentionModel;
import com.bohui.art.mine.attention.mvp.MyAttentionPresenter;
import com.bohui.art.mine.collect.mvp.MyCollectParam;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.framework.core.http.exception.ApiException;
import com.framework.core.util.CollectionUtil;
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
    @BindView(R.id.ptr)
    PtrClassicFrameLayout ptrClassicFrameLayout;
    @BindView(R.id.rv)
    RecyclerView rv;
    private ArtManListAdapter artManListAdapter;
    MyCollectParam param = new MyCollectParam();
    private boolean isRefresh;
    private boolean isRequesting;
    @Override
    public int getLayoutId() {
        return R.layout.layout_refresh_rv;
    }

    @Override
    public void initView() {
        new DefaultTitleBar.DefaultBuilder(mContext)
                .setTitle("我的关注")
                .builder();
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(mContext);
        final DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        //猜你喜欢数据适配器
        artManListAdapter = new ArtManListAdapter(mContext);
        artManListAdapter.setDelegateAdapter(delegateAdapter);
        NormalWrapAdapter wrapper = new NormalWrapAdapter(mContext,artManListAdapter);
        artManListAdapter.setWrapper(wrapper);
        wrapper.setDelegateAdapter(delegateAdapter);
        delegateAdapter.addAdapter(wrapper);
        rv.setLayoutManager(virtualLayoutManager);
        rv.setAdapter(delegateAdapter);
        rv.addOnItemTouchListener(new RvClickListenerIml(){
            @Override
            public void onItemClick(BaseAdapter adapter, View view, int position) {
                ArtManItemBean itemBean = (ArtManItemBean) adapter.getData(position);
                if(itemBean != null){
                    ArtManDetailActivity.comeIn(MyAttentionActivity.this,itemBean.getAid());
                }
            }
        });
        ptrClassicFrameLayout.setAutoLoadMoreEnable(false);
        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                requestFirstPage();
            }
        });
        ptrClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                requestNextPage();
            }
        });
    }

    @Override
    public void initPresenter() {
        mPresenter.setMV(mModel,this);
    }

    @Override
    protected void extraInit() {
        param.setUid(AppFuntion.getUid());
        requestFirstPage();
    }
    private void requestFirstPage(){
        isRefresh = true;
        param.setStart(0);
        request();
    }
    private void requestNextPage(){
        isRefresh = false;
        param.setStart(param.getStart()+param.getLength());
        request();
    }
    private void request() {
        if(!isRequesting){
            mPresenter.myAttention(param);
            isRequesting = true;
        }
    }

    @Override
    protected boolean childInterceptException(String apiName, ApiException e) {
        isRequesting = false;
        if(isRefresh){
            ptrClassicFrameLayout.refreshComplete();
        }else{
            ptrClassicFrameLayout.setLoadMoreEnable(false);
            ptrClassicFrameLayout.loadMoreComplete(false);
        }
        return super.childInterceptException(apiName, e);
    }

    @Override
    public void myAttentionSuccess(MyAttentionResult result) {
        isRequesting = false;
        List<ArtManItemBean> list = result.getList();
        if(isRefresh){
            ptrClassicFrameLayout.refreshComplete();
            if(!CollectionUtil.isEmpty(list)){
                artManListAdapter.replaceAllItem(list);
                if(list.size() >= param.getLength()){
                    ptrClassicFrameLayout.setLoadMoreEnable(true);
                }else{
                    ptrClassicFrameLayout.setLoadMoreEnable(false);
                }
            }else{
                ptrClassicFrameLayout.setLoadMoreEnable(false);
            }
        }else{
            if(CollectionUtil.isEmpty(list)){
                ptrClassicFrameLayout.loadMoreComplete(false);
            }else{
                artManListAdapter.addItems(list);
                if(list.size() >= param.getLength()){
                    ptrClassicFrameLayout.loadMoreComplete(true);
                }else{
                    ptrClassicFrameLayout.loadMoreComplete(false);
                }
            }
        }
    }
}
