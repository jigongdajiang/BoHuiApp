package com.bohui.art.found.company;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bohui.art.R;
import com.bohui.art.bean.found.CompanyListItemBean;
import com.bohui.art.bean.home.ArtItemBean;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.widget.title.DefaultTitleBar;
import com.bohui.art.detail.art.ArtDetailActivity;
import com.bohui.art.detail.comapny.CompanyDetailActivity;
import com.bohui.art.home.art2.Art2Activity;
import com.bohui.art.search.SearchActivity;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.framework.core.base.BaseHelperUtil;
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


public class CompanyListActivity extends AbsNetBaseActivity {
    @BindView(R.id.ptr)
    PtrClassicFrameLayout ptrClassicFrameLayout;
    @BindView(R.id.rv)
    RecyclerView rv;
    private boolean isRefresh;
    private boolean isRequesting;
    private CompanyListAdapter companyListAdapter;
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
        companyListAdapter.setDelegateAdapter(delegateAdapter);
        delegateAdapter.addAdapter(companyListAdapter);
        rv.setLayoutManager(virtualLayoutManager);
        rv.setAdapter(delegateAdapter);
        rv.addOnItemTouchListener(new RvClickListenerIml(){
            @Override
            public void onItemClick(BaseAdapter adapter, View view, int position) {
                CompanyDetailActivity.comeIn(CompanyListActivity.this,1);
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

    private void requestNextPage() {
        isRefresh = true;
        request();
    }

    private void requestFirstPage() {
        isRefresh = false;
        request();
    }

    private void request() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ptrClassicFrameLayout.refreshComplete();
        List<CompanyListItemBean> companyListItemBeans = new ArrayList<>();
        for(int i=0;i<10;i++){
            CompanyListItemBean companyListItemBean = new CompanyListItemBean();
            companyListItemBean.setName("机构"+i);
            companyListItemBean.setLogo("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1520704210497&di=5d4bdbcc149d50a8714f6c4b65acbc62&imgtype=0&src=http%3A%2F%2Fdealer0.autoimg.cn%2Fdl%2F125117%2Fnewsimg%2F130492619601208820.jpg");
            companyListItemBean.setNums(100);
            companyListItemBeans.add(companyListItemBean);
        }
        companyListAdapter.replaceAllItem(companyListItemBeans);
    }
}
