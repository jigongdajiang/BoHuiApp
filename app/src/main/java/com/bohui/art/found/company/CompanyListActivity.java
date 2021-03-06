package com.bohui.art.found.company;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bohui.art.R;
import com.bohui.art.bean.common.PageParam;
import com.bohui.art.bean.found.CompanyListItemBean;
import com.bohui.art.bean.found.CompanyListResult;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.widget.rv.adapter.NormalWrapAdapter;
import com.bohui.art.common.widget.title.DefaultTitleBar;
import com.bohui.art.detail.comapny.CompanyDetailActivity;
import com.bohui.art.found.company.mvp.CompanyContact;
import com.bohui.art.found.company.mvp.CompanyModel;
import com.bohui.art.found.company.mvp.CompanyPresenter;
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
 * @date : 2018/3/10
 * @description:
 * 机构列表
 */


public class CompanyListActivity extends AbsNetBaseActivity<CompanyPresenter,CompanyModel> implements CompanyContact.ICompanyView {
    @BindView(R.id.ptr)
    PtrClassicFrameLayout ptrClassicFrameLayout;
    @BindView(R.id.rv)
    RecyclerView rv;
    private boolean isRefresh;
    private boolean isRequesting;
    private CompanyListAdapter companyListAdapter;
    private PageParam param = new PageParam();
    @Override
    public int getLayoutId() {
        return R.layout.activity_company_list;
    }

    @Override
    public void initView() {
        new DefaultTitleBar.DefaultBuilder(mContext)
                .setTitle("机构列表")
                .builder();
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(mContext);
        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        companyListAdapter = new CompanyListAdapter(mContext);
        List<CompanyListItemBean> companyListItemBeans = new ArrayList<>();
        companyListAdapter.setDatas(companyListItemBeans);
        companyListAdapter.setDelegateAdapter(delegateAdapter);
        NormalWrapAdapter wrapper = new NormalWrapAdapter(mContext,companyListAdapter);
        companyListAdapter.setWrapper(wrapper);
        wrapper.setDelegateAdapter(delegateAdapter);
        delegateAdapter.addAdapter(wrapper);
        rv.setLayoutManager(virtualLayoutManager);
        rv.setAdapter(delegateAdapter);
        rv.addOnItemTouchListener(new RvClickListenerIml(){
            @Override
            public void onItemClick(BaseAdapter adapter, View view, int position) {
                CompanyListItemBean companyListItemBean = (CompanyListItemBean) adapter.getData(position);
                CompanyDetailActivity.comeIn(CompanyListActivity.this,companyListItemBean.getMid());
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
            mPresenter.getCompanyList(param);
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
    public void getCompanyListSuccess(CompanyListResult result) {
        isRequesting = false;
        if(!CollectionUtil.isEmpty(result.getMechanismList())){
            List<CompanyListItemBean> list = result.getMechanismList();
            if(isRefresh){
                ptrClassicFrameLayout.refreshComplete();
                if(!CollectionUtil.isEmpty(list)){
                    companyListAdapter.replaceAllItem(list);
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
                    companyListAdapter.addItems(list);
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
