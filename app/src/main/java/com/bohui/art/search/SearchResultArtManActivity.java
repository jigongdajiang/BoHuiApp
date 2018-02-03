package com.bohui.art.search;

import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bohui.art.R;
import com.bohui.art.bean.common.ArtListParam;
import com.bohui.art.bean.common.ArtListResult;
import com.bohui.art.bean.common.ArtManListParam;
import com.bohui.art.bean.found.ArtManItemBean;
import com.bohui.art.bean.found.ArtManListResult;
import com.bohui.art.bean.home.ArtCoverItemBean;
import com.bohui.art.bean.home.ArtItemBean;
import com.bohui.art.bean.home.RecommendDesignerBean;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.mvp.ArtListContact;
import com.bohui.art.common.mvp.ArtListModel;
import com.bohui.art.common.mvp.ArtListPresenter;
import com.bohui.art.common.util.RxViewUtil;
import com.bohui.art.common.widget.rv.adapter.NormalWrapAdapter;
import com.bohui.art.common.widget.title.DefaultTitleBar;
import com.bohui.art.detail.art.ArtDetailActivity;
import com.bohui.art.detail.artman.ArtManDetailActivity;
import com.bohui.art.found.artman.ArtManListAdapter;
import com.bohui.art.found.artman.mvp.ArtManListContact;
import com.bohui.art.found.artman.mvp.ArtManListModel;
import com.bohui.art.found.artman.mvp.ArtManListPresenter;
import com.bohui.art.home.adapter.DesignerAdapter;
import com.bohui.art.home.art1.Art2Adapter;
import com.bohui.art.home.art2.Art2Activity;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.framework.core.http.exception.ApiException;
import com.framework.core.util.CollectionUtil;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.listener.RvClickListenerIml;
import com.widget.smallelement.dialog.BasePowfullDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * @author : gaojigong
 * @date : 2017/12/17
 * @description:
 * 艺术家搜索结果页
 */


public class SearchResultArtManActivity extends AbsNetBaseActivity<ArtManListPresenter,ArtManListModel> implements ArtManListContact.IArtManListView {
    @BindView(R.id.ptr)
    PtrClassicFrameLayout ptrClassicFrameLayout;
    @BindView(R.id.rv)
    RecyclerView rv;

    public static final String SEARCH_KEY = "search_key";
    private String mSearchKey;

    private ArtManListAdapter adapter;
    private ArtManListParam param = new ArtManListParam();
    private boolean isRefresh;
    private boolean isRequesting;
    @Override
    protected void doBeforeSetContentView() {
        super.doBeforeSetContentView();
        mSearchKey = getIntent().getStringExtra(SEARCH_KEY);
        param.setName(mSearchKey);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_result_art_man;
    }

    @Override
    public void initView() {
        new DefaultTitleBar.DefaultBuilder(mContext)
                .setLeftText(mSearchKey)
                .builder();
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(mContext);
        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        adapter = new ArtManListAdapter(mContext);
        adapter.setDelegateAdapter(delegateAdapter);
        NormalWrapAdapter wrapper = new NormalWrapAdapter(mContext,adapter);
        adapter.setWrapper(wrapper);
        wrapper.setDelegateAdapter(delegateAdapter);
        delegateAdapter.addAdapter(wrapper);
        rv.setLayoutManager(virtualLayoutManager);
        rv.setAdapter(delegateAdapter);
        rv.addOnItemTouchListener(new RvClickListenerIml(){
            @Override
            public void onItemClick(BaseAdapter adapter, View view, int position) {
                ArtManItemBean itemBean = (ArtManItemBean) adapter.getData(position);
                if(null != itemBean){
                    ArtManDetailActivity.comeIn(SearchResultArtManActivity.this,itemBean.getAid());
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
            mPresenter.getArtManList(param);
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
    public void getArtManListSuccess(ArtManListResult result) {
        isRequesting = false;
        List<ArtManItemBean> list = result.getArtistList();
        if(isRefresh){
            ptrClassicFrameLayout.refreshComplete();
            if(!CollectionUtil.isEmpty(list)){
                adapter.replaceAllItem(list);
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
                adapter.addItems(list);
                if(list.size() >= param.getLength()){
                    ptrClassicFrameLayout.loadMoreComplete(true);
                }else{
                    ptrClassicFrameLayout.loadMoreComplete(false);
                }
            }
        }
    }
}
