package com.bohui.art.home.art2;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bohui.art.R;
import com.bohui.art.bean.common.ArtListParam;
import com.bohui.art.bean.common.ArtListResult;
import com.bohui.art.bean.home.ArtItemBean;
import com.bohui.art.bean.home.ClassifyLevelBean;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.mvp.ArtListContact;
import com.bohui.art.common.mvp.ArtListModel;
import com.bohui.art.common.mvp.ArtListPresenter;
import com.bohui.art.common.widget.rv.adapter.NormalWrapAdapter;
import com.bohui.art.common.widget.title.DefaultTitleBar;
import com.bohui.art.detail.art.ArtDetailActivity;
import com.bohui.art.home.art1.Art2Adapter;
import com.bohui.art.search.SearchActivity;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.framework.core.base.BaseHelperUtil;
import com.framework.core.http.exception.ApiException;
import com.framework.core.util.CollectionUtil;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.listener.RvClickListenerIml;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author : gaojigong
 * @date : 2017/12/15
 * @description:
 * 作品集列表
 */


public class Art2Activity extends AbsNetBaseActivity<ArtListPresenter,ArtListModel> implements ArtListContact.View {
    @BindView(R.id.ptr)
    PtrClassicFrameLayout ptrClassicFrameLayout;
    @BindView(R.id.rv)
    RecyclerView rv;
    @Override
    public int getLayoutId() {
        return R.layout.activity_art2;
    }
    public static final String TYPE = "TYPE";
    private ClassifyLevelBean mType;
    private Art2Adapter art2Adapter;
    private ArtListParam param = new ArtListParam();
    private boolean isRefresh;
    private boolean isRequesting;

    @Override
    protected void doBeforeSetContentView() {
        super.doBeforeSetContentView();
        mType = (ClassifyLevelBean) getIntent().getSerializableExtra(TYPE);
    }

    @Override
    public void initView() {
        new DefaultTitleBar.DefaultBuilder(mContext)
                .setTitle(mType.getName())
                .setRightImage1(R.mipmap.ic_search)
                .setRightImage1ClickListner(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(SearchActivity.SEARCH_TYPE,0);
                        ((BaseHelperUtil)mHelperUtil).startAty(SearchActivity.class,bundle);
                    }
                })
                .builder();
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(mContext);
        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        art2Adapter = new Art2Adapter(mContext);
        List<ArtItemBean> artBeans = new ArrayList<>();
        art2Adapter.setDatas(artBeans);
        art2Adapter.setDelegateAdapter(delegateAdapter);
        NormalWrapAdapter wrapper = new NormalWrapAdapter(mContext,art2Adapter);
        art2Adapter.setWrapper(wrapper);
        wrapper.setDelegateAdapter(delegateAdapter);
        delegateAdapter.addAdapter(wrapper);
        rv.setLayoutManager(virtualLayoutManager);
        rv.setAdapter(delegateAdapter);
        rv.addOnItemTouchListener(new RvClickListenerIml(){
            @Override
            public void onItemClick(BaseAdapter adapter, View view, int position) {
                ArtItemBean itemBean = (ArtItemBean) adapter.getData(position);
                if(itemBean != null){
                    ArtDetailActivity.comeIn(Art2Activity.this,itemBean.getAid());
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
        List<Long> oneClass = new ArrayList<>();
        oneClass.add(mType.getPid());
        param.setOneclass(oneClass);
        List<Long> towClass = new ArrayList<>();
        towClass.add(mType.getId());
        param.setTowclass(towClass);
        requestFirstPage();
    }

    @Override
    public void initPresenter() {
        mPresenter.setMV(mModel,this);
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
            mPresenter.getArtList(param);
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
    public void getArtListSuccess(ArtListResult result) {
        isRequesting = false;
        List<ArtItemBean> list = result.getPaintingList();
        if(isRefresh){
            ptrClassicFrameLayout.refreshComplete();
            if(!CollectionUtil.isEmpty(list)){
                art2Adapter.replaceAllItem(list);
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
                art2Adapter.addItems(list);
                if(list.size() >= param.getLength()){
                    ptrClassicFrameLayout.loadMoreComplete(true);
                }else{
                    ptrClassicFrameLayout.loadMoreComplete(false);
                }
            }
        }
    }
}
