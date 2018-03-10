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
import com.bohui.art.bean.home.ArtItemBean;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.widget.title.DefaultTitleBar;
import com.bohui.art.detail.art.ArtDetailActivity;
import com.bohui.art.detail.artman.ArtManDetailActivity;
import com.bohui.art.home.art2.Art2Activity;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.framework.core.http.exception.ApiException;
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


public class ArtManHomeActivity extends AbsNetBaseActivity implements ArtMan2LevelAdapter.OnGirdItemClickListener {
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
                    startAty(ArtManActivity.class);
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
                            ArtManLevelBean artManLevelBean = new ArtManLevelBean(1,artMan2LevelBean.getArt2LevelName());
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(ArtMan2LevelActivity.TYPE,artManLevelBean);
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

    private void doLoad() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mPtrClassicFrameLayout.refreshComplete();
        List<ArtMan1LevelBean> artMan1LevelBeans = new ArrayList<>();
        for(int i=0;i<5;i++){
            ArtMan1LevelBean artMan1LevelBean = new ArtMan1LevelBean();
            artMan1LevelBean.setArtManLevel1Name("国画"+i);
            List<ArtMan2LevelBean> artMan2LevelBeans = new ArrayList<>();
            for(int j=0;j<6;j++){
                ArtMan2LevelBean artMan2LevelBean = new ArtMan2LevelBean();
                artMan2LevelBean.setArt2LevelName("国画二级"+j);
                List<ArtManHomeItemBean> artManHomeItemBeans = new ArrayList<>();
                for(int k=0;k<6;k++){
                    ArtManHomeItemBean artManHomeItemBean = new ArtManHomeItemBean();
                    artManHomeItemBean.setArtManName("艺术家"+k);
                    artManHomeItemBean.setArtManLogo("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1520692122633&di=cc5b7f965a2866675b19542b90736e70&imgtype=0&src=http%3A%2F%2Fpic11.nipic.com%2F20101107%2F1951702_172652019701_2.jpg");
                    artManHomeItemBeans.add(artManHomeItemBean);
                }
                artMan2LevelBean.setArtManHomeItemBeans(artManHomeItemBeans);
                artMan2LevelBeans.add(artMan2LevelBean);
            }
            artMan1LevelBean.setArtMan2LevelBeans(artMan2LevelBeans);
            artMan1LevelBeans.add(artMan1LevelBean);
        }
        List<DelegateAdapter.Adapter> adapters = new ArrayList<>();
        for (ArtMan1LevelBean artMan1LevelBean : artMan1LevelBeans) {
            ArtMan1LevelAdapter artMan1LevelAdapter = new ArtMan1LevelAdapter(mContext,artMan1LevelBean);
            adapters.add(artMan1LevelAdapter);
            ArtMan2LevelAdapter artMan2LevelAdapter = new ArtMan2LevelAdapter(mContext,artMan1LevelBean.getArtMan2LevelBeans());
            artMan2LevelAdapter.setOnGirdItemClickListener(this);
            adapters.add(artMan2LevelAdapter);
        }
        delegateAdapter.setAdapters(adapters);
    }

    @Override
    protected boolean childInterceptException(String apiName, ApiException e) {
        mPtrClassicFrameLayout.refreshComplete();
        return super.childInterceptException(apiName, e);
    }

    @Override
    public void girdItemClick(ArtManHomeItemBean itemBean) {
        ArtManDetailActivity.comeIn(ArtManHomeActivity.this,12);
    }
}
