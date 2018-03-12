package com.bohui.art.found.artman;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bohui.art.R;
import com.bohui.art.bean.detail.ArtManLevelBean;
import com.bohui.art.bean.found.ArtMan1LevelBean;
import com.bohui.art.bean.found.ArtMan2LevelBean;
import com.bohui.art.bean.found.ArtManHomeItemBean;
import com.bohui.art.bean.found.ArtManHomeResult;
import com.bohui.art.bean.home.ArtItemBean;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.widget.title.DefaultTitleBar;
import com.bohui.art.detail.art.ArtDetailActivity;
import com.bohui.art.detail.artman.ArtManDetailActivity;
import com.bohui.art.found.artman.mvp.ArtManHomeContact;
import com.bohui.art.found.artman.mvp.ArtManHomeModel;
import com.bohui.art.found.artman.mvp.ArtManHomePresenter;
import com.bohui.art.home.art2.Art2Activity;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
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
 */


public class ArtManHomeActivity extends AbsNetBaseActivity<ArtManHomePresenter,ArtManHomeModel> implements ArtMan2LevelAdapter.OnGirdItemClickListener,ArtManHomeContact.IArtManHomeView {
    @BindView(R.id.ptr)
    PtrClassicFrameLayout mPtrClassicFrameLayout;
    @BindView(R.id.rv)
    RecyclerView rv;
    private DelegateAdapter delegateAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_art_man_home;
    }

    @Override
    public void initView() {
        new DefaultTitleBar.DefaultBuilder(mContext)
                .setTitle("艺术家")
                .builder();
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(mContext);
        delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        rv.setLayoutManager(virtualLayoutManager);
        rv.setAdapter(delegateAdapter);
        rv.addOnItemTouchListener(new RvClickListenerIml(){
            @Override
            public void onItemClick(BaseAdapter adapter, View view, int position) {
                if(adapter instanceof ArtMan1LevelAdapter){
                    ArtMan1LevelBean artMan1LevelBean = ((ArtMan1LevelAdapter)adapter).getData(position);
                    if(artMan1LevelBean != null){
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(ArtManActivity.ARTMAN_LEVEL1,artMan1LevelBean);
                        startAty(ArtManActivity.class,bundle);
                    }
                }
            }

            @Override
            public void onItemChildClick(BaseAdapter adapter, View view, int position) {
                if(adapter instanceof ArtMan2LevelAdapter){
                    switch (view.getId()){
                        case R.id.rl_art_man_2:
                        case R.id.iv_more:
                            //进入二级列表
                            ArtMan2LevelAdapter artMan2LevelAdapter = (ArtMan2LevelAdapter) adapter;
                            ArtMan2LevelBean artMan2LevelBean = artMan2LevelAdapter.getData(position);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(ArtMan2LevelActivity.TYPE,artMan2LevelBean);
                            startAty(ArtMan2LevelActivity.class,bundle);
                            break;
                    }
                }
            }
        });
        mPtrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                doLoad();
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
        doLoad();
    }

    private void doLoad() {
        mPresenter.getArtManHome();
    }

    private void refreshView(List<ArtMan1LevelBean> artMan1LevelBeans) {
        List<DelegateAdapter.Adapter> adapters = new ArrayList<>();
        for (ArtMan1LevelBean artMan1LevelBean : artMan1LevelBeans) {
            if(hasData(artMan1LevelBean)){
                //有二级列表，且二级列表中有数据
                ArtMan1LevelAdapter artMan1LevelAdapter = new ArtMan1LevelAdapter(mContext,artMan1LevelBean);
                adapters.add(artMan1LevelAdapter);
                List<ArtMan2LevelBean> artMan2LevelBeans = filterArtMan2Level(artMan1LevelBean);
                if(!CollectionUtil.isEmpty(artMan2LevelBeans)){
                    ArtMan2LevelAdapter artMan2LevelAdapter = new ArtMan2LevelAdapter(mContext,artMan2LevelBeans);
                    artMan2LevelAdapter.setOnGirdItemClickListener(this);
                    adapters.add(artMan2LevelAdapter);
                }
            }
        }
        delegateAdapter.setAdapters(adapters);
    }

    private List<ArtMan2LevelBean> filterArtMan2Level(ArtMan1LevelBean artMan1LevelBean) {
        List<ArtMan2LevelBean> artMan2LevelBeans = new ArrayList<>();
        for (ArtMan2LevelBean artMan2LevelBean : artMan1LevelBean.getList()) {
            if(artMan2LevelBean != null && !CollectionUtil.isEmpty(artMan2LevelBean.getList())){
                artMan2LevelBeans.add(artMan2LevelBean);
            }
        }
        return artMan2LevelBeans;
    }

    private boolean hasData(ArtMan1LevelBean artMan1LevelBean) {
        if(CollectionUtil.isEmpty(artMan1LevelBean.getList())){
            return false;
        }
        for (ArtMan2LevelBean artMan2LevelBean : artMan1LevelBean.getList()) {
            if(!CollectionUtil.isEmpty(artMan2LevelBean.getList())){
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean childInterceptException(String apiName, ApiException e) {
        mPtrClassicFrameLayout.refreshComplete();
        return super.childInterceptException(apiName, e);
    }

    @Override
    public void girdItemClick(ArtManHomeItemBean itemBean) {
        ArtManDetailActivity.comeIn(ArtManHomeActivity.this,itemBean.getAid());
    }

    @Override
    public void getArtManHomeSuccess(ArtManHomeResult artManHomeResult) {
        mPtrClassicFrameLayout.refreshComplete();
        if(!CollectionUtil.isEmpty(artManHomeResult.getArtistList())){
            refreshView(artManHomeResult.getArtistList());
        }
    }
}
