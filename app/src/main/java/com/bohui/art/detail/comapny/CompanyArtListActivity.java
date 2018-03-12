package com.bohui.art.detail.comapny;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bohui.art.R;
import com.bohui.art.bean.common.CompanyArtListParam;
import com.bohui.art.bean.detail.CompanyArtListResult;
import com.bohui.art.bean.detail.CompanyDetailResult;
import com.bohui.art.bean.home.ArtItemBean;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.app.AppFuntion;
import com.bohui.art.common.widget.rv.adapter.NormalWrapAdapter;
import com.bohui.art.common.widget.title.DefaultTitleBar;
import com.bohui.art.detail.art.ArtDetailActivity;
import com.bohui.art.detail.comapny.mvp.CompanyArtListContact;
import com.bohui.art.detail.comapny.mvp.CompanyArtListModel;
import com.bohui.art.detail.comapny.mvp.CompanyArtListPresenter;
import com.bohui.art.home.art1.Art2Adapter;
import com.bohui.art.home.art2.Art2Activity;
import com.bohui.art.search.SearchActivity;
import com.bohui.art.start.login.LoginActivity;
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
 * @date : 2018/3/11
 * @description:
 */


public class CompanyArtListActivity extends AbsNetBaseActivity<CompanyArtListPresenter,CompanyArtListModel> implements CompanyArtListContact.View {
    @BindView(R.id.ptr)
    PtrClassicFrameLayout ptrClassicFrameLayout;
    @BindView(R.id.rv)
    RecyclerView rv;
    public static final String TYPE = "TYPE";
    private boolean isRefresh;
    private boolean isRequesting;
    private Art2Adapter art2Adapter;
    private CompanyDetailResult companyDetail;

    private CompanyArtListParam param = new CompanyArtListParam();

    @Override
    protected void doBeforeSetContentView() {
        super.doBeforeSetContentView();
        companyDetail = (CompanyDetailResult) getIntent().getSerializableExtra(TYPE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_art2;
    }

    @Override
    public void initView() {
        new DefaultTitleBar.DefaultBuilder(mContext)
                .setTitle(companyDetail.getName()+"作品列表")
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
                    if(AppFuntion.isLogin()){
                        ArtDetailActivity.comeIn(CompanyArtListActivity.this,itemBean.getAid());
                    }else{
                        startAty(LoginActivity.class);
                    }
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
        super.initPresenter();
        mPresenter.setMV(mModel,this);
    }

    @Override
    protected void extraInit() {
        super.extraInit();
        param.setMid(companyDetail.getMid());
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
            mPresenter.getCompanyArtList(param);
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
    public void getCompanyArtListSuccess(CompanyArtListResult result) {
        isRequesting = false;
        List<ArtItemBean> list = result.getMechanismList();
        if(!CollectionUtil.isEmpty(list)){
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
}
