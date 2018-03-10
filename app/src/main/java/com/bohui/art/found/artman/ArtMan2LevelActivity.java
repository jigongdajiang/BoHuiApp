package com.bohui.art.found.artman;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bohui.art.R;
import com.bohui.art.bean.common.ArtManListParam;
import com.bohui.art.bean.detail.ArtManLevelBean;
import com.bohui.art.bean.found.ArtManItemBean;
import com.bohui.art.bean.found.ArtManListResult;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.widget.rv.adapter.NormalWrapAdapter;
import com.bohui.art.common.widget.title.DefaultTitleBar;
import com.bohui.art.detail.artman.ArtManDetailActivity;
import com.bohui.art.found.artman.mvp.ArtManListContact;
import com.bohui.art.found.artman.mvp.ArtManListModel;
import com.bohui.art.found.artman.mvp.ArtManListPresenter;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.framework.core.http.exception.ApiException;
import com.framework.core.util.CollectionUtil;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.listener.RvClickListenerIml;

import java.util.List;

import butterknife.BindView;

/**
 * @author : gaojigong
 * @date : 2018/3/10
 * @description:
 */


public class ArtMan2LevelActivity extends AbsNetBaseActivity<ArtManListPresenter,ArtManListModel> implements ArtManListContact.IArtManListView {
    @BindView(R.id.ptr)
    PtrClassicFrameLayout ptrClassicFrameLayout;
    @BindView(R.id.rv)
    RecyclerView rv;

    public static final String TYPE = "type";
    private ArtManLevelBean mType;
    private ArtManListAdapter artManListAdapter;
    private ArtManListParam param = new ArtManListParam();
    private boolean isRefresh;
    private boolean isRequesting;

    @Override
    protected void doBeforeSetContentView() {
        super.doBeforeSetContentView();
        mType = (ArtManLevelBean) getIntent().getSerializableExtra(TYPE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_art_man2level;
    }

    @Override
    public void initView() {
        new DefaultTitleBar.DefaultBuilder(mContext)
                .setTitle("国画-国家级艺术家")
                .builder();
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(ArtMan2LevelActivity.this);
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
                if(null != itemBean){
                    ArtManDetailActivity.comeIn(ArtMan2LevelActivity.this,itemBean.getAid());
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
    protected void extraInit() {
        super.extraInit();
        doLoad();
    }

    @Override
    public void initPresenter() {
        mPresenter.setMV(mModel,this);
    }

    protected void doLoad() {
        param.setLevel(mType.getId());
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
