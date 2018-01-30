package com.bohui.art.detail.artman;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bohui.art.R;
import com.bohui.art.bean.common.ArtListParam;
import com.bohui.art.bean.common.ArtListResult;
import com.bohui.art.bean.detail.ShowreelBean;
import com.bohui.art.bean.home.ArtItemBean;
import com.bohui.art.bean.home.ClassifyLevelBean;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.mvp.ArtListContact;
import com.bohui.art.common.mvp.ArtListModel;
import com.bohui.art.common.mvp.ArtListPresenter;
import com.bohui.art.common.widget.rv.adapter.NormalWrapAdapter;
import com.bohui.art.common.widget.title.DefaultTitleBar;
import com.bohui.art.detail.art.ArtDetailActivity;
import com.bohui.art.detail.artman.mvp.ArtJiContanct;
import com.bohui.art.detail.artman.mvp.ArtJiModel;
import com.bohui.art.detail.artman.mvp.ArtJiPresenter;
import com.bohui.art.home.art1.Art2Adapter;
import com.bohui.art.search.SearchActivity;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.framework.core.base.BaseHelperUtil;
import com.framework.core.http.exception.ApiException;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.listener.RvClickListenerIml;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author : gaojigong
 * @date : 2017/12/15
 * @description:
 */


public class ArtJiActivity extends AbsNetBaseActivity<ArtJiPresenter,ArtJiModel> implements ArtJiContanct.View {
    @BindView(R.id.rv)
    RecyclerView rv;
    private Art2Adapter art2Adapter;
    public static final String ART_JI_ITEM = "art_ji_item";
    private ShowreelBean showreelBean;
    @Override
    public int getLayoutId() {
        return R.layout.activity_art_ji;
    }

    @Override
    protected void doBeforeSetContentView() {
        super.doBeforeSetContentView();
        showreelBean = (ShowreelBean) getIntent().getSerializableExtra(ART_JI_ITEM);
    }

    @Override
    public void initView() {
        new DefaultTitleBar.DefaultBuilder(mContext)
                .setTitle(showreelBean.getName())
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
                ArtDetailActivity.comeIn(ArtJiActivity.this,itemBean.getId());
            }
        });
    }

    @Override
    public void initPresenter() {
        mPresenter.setMV(mModel,this);
    }

    @Override
    protected void extraInit() {
        mPresenter.getArtJi(showreelBean.getId());
    }

    @Override
    public void getArtJiSuccess(ArtListResult result) {
        art2Adapter.replaceAllItem(result.getPaintingList());
    }
}
