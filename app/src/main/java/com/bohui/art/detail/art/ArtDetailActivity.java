package com.bohui.art.detail.art;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bohui.art.R;
import com.bohui.art.bean.home.ArtItemBean;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.util.RxViewUtil;
import com.bohui.art.detail.art.adapter.DetailAdapter;
import com.bohui.art.detail.art.adapter.DetailGuideAdapter;
import com.bohui.art.detail.art.adapter.IntroAdapter;
import com.bohui.art.detail.art.adapter.ProductAdapter;
import com.bohui.art.bean.detail.ArtDetailResult;
import com.bohui.art.detail.art.mvp.ArtDetailContact;
import com.bohui.art.detail.art.mvp.ArtDetailModel;
import com.bohui.art.detail.art.mvp.ArtDetailPesenter;
import com.bohui.art.detail.artman.ArtManDetailActivity;
import com.bohui.art.home.RecommendFragment;
import com.bohui.art.home.adapter.ArtGridAdapter;
import com.bohui.art.bean.home.ArtCoverItemBean;
import com.bohui.art.start.MainActivity;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.framework.core.log.PrintLog;
import com.framework.core.util.ResUtil;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.listener.RvClickListenerIml;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * @author : gaojigong
 * @date : 2017/12/18
 * @description:
 */


public class ArtDetailActivity extends AbsNetBaseActivity<ArtDetailPesenter,ArtDetailModel> implements ArtDetailContact.View {
    @BindView(R.id.segment_tab)
    SegmentTabLayout segment_tab;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.iv_home)
    ImageView iv_home;

    @BindView(R.id.rv)
    RecyclerView rv;

    private String[] mTabTitles = {"产品", "简介", "详情", "推荐"};
    @Override
    public int getLayoutId() {
        return R.layout.activity_art_detail;
    }

    private int rvStatus = 0;
    @Override
    public void initView() {
        segment_tab.setTabData(mTabTitles);

        ArtDetailResult artDetailBean = new ArtDetailResult();
        List<String> imgs = new ArrayList<>();
        for(int i=0;i<5;i++){
            imgs.add(RecommendFragment.imgs[i%RecommendFragment.imgs.length]);
        }
        artDetailBean.setImgs(imgs);
        artDetailBean.setDetailUrl("http://m.okhqb.com/item/description/1000334264.html?fromApp=true");
        artDetailBean.setIntro(ResUtil.getResString(mContext,R.string.temp_jianjie));

        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(mContext);
        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        //产品  0
        ProductAdapter productAdapter = new ProductAdapter(mContext,artDetailBean);
        delegateAdapter.addAdapter(productAdapter);
        //简介 Guide  1
        DetailGuideAdapter detailGuideAdapterIntro = new DetailGuideAdapter(mContext,"简介");
        delegateAdapter.addAdapter(detailGuideAdapterIntro);
        //简介 2
        IntroAdapter introAdapter = new IntroAdapter(mContext,artDetailBean);
        delegateAdapter.addAdapter(introAdapter);
        //详情 Guide  3
        DetailGuideAdapter detailGuideAdapterDetail = new DetailGuideAdapter(mContext,"详情");
        delegateAdapter.addAdapter(detailGuideAdapterDetail);
        //详情 Web显示 4
        DetailAdapter detailAdapter = new DetailAdapter(mContext,artDetailBean);
        delegateAdapter.addAdapter(detailAdapter);
        //推荐 Guide  5
        DetailGuideAdapter detailGuideAdapterRecommend = new DetailGuideAdapter(mContext,"推荐");
        delegateAdapter.addAdapter(detailGuideAdapterRecommend);
        //推荐 6+
        ArtGridAdapter artGridAdapter = new ArtGridAdapter(mContext);
        List<ArtItemBean> artBeansLikes = new ArrayList<>();
        for(int i=0;i<6;i++){
            artBeansLikes.add(new ArtItemBean());
        }
        artGridAdapter.setDatas(artBeansLikes);
        delegateAdapter.addAdapter(artGridAdapter);

        rv.setLayoutManager(virtualLayoutManager);
        rv.setAdapter(delegateAdapter);

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                rvStatus = newState;
                PrintLog.e("onScrollStateChanged","newState="+newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                PrintLog.e("onScrolled","dx="+dx+"---dy="+dy);

                VirtualLayoutManager layoutManager = (VirtualLayoutManager) recyclerView.getLayoutManager();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                int firstCompletelyVisibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition();
                int lastCompletelyVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition();

                PrintLog.e("onScrolled_position","f="+firstVisibleItemPosition+"---" +
                        "l="+lastVisibleItemPosition+"---" +
                        "fc="+firstCompletelyVisibleItemPosition+"---" +
                        "lc="+lastCompletelyVisibleItemPosition);
                if(rvStatus != 0){
                    if(firstVisibleItemPosition == 0){
                        segment_tab.setCurrentTab(0);
                    } else if(firstVisibleItemPosition == 1){
                        segment_tab.setCurrentTab(1);
                    }else if(firstVisibleItemPosition == 3){
                        segment_tab.setCurrentTab(2);
                    }else if(firstVisibleItemPosition == 5){
                        segment_tab.setCurrentTab(3);
                    }
                }
            }
        });

        segment_tab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if(position == 0){
                    rv.scrollToPosition(0);
                } else if(position == 1){
                    rv.scrollToPosition(1);
                }else if(position == 2){
                    rv.scrollToPosition(3);
                    segment_tab.setCurrentTab(2);
                }else if(position == 3){
                    rv.scrollToPosition(5);
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        rv.addOnItemTouchListener(new RvClickListenerIml(){
            @Override
            public void onItemClick(BaseAdapter adapter, View view, int position) {
                if(adapter instanceof ArtGridAdapter){
                    ArtDetailActivity.comeIn(ArtDetailActivity.this,new Bundle());
                }
            }

            @Override
            public void onItemChildClick(BaseAdapter adapter, View view, int position) {
                if(view.getId() == R.id.rl_art_man){
                    ArtManDetailActivity.comeIn(ArtDetailActivity.this,new Bundle());
                }
            }
        });
        RxViewUtil.addOnClick(mRxManager, iv_back, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                onBackPressed();
            }
        });
        RxViewUtil.addOnClick(mRxManager, iv_home, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                startActivity(new Intent(ArtDetailActivity.this, MainActivity.class));
            }
        });
    }

    public static void comeIn(Activity activity, Bundle bundle){
        Intent intent = new Intent(activity,ArtDetailActivity.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    @Override
    public void initPresenter() {
        mPresenter.setMV(mModel,this);
    }

    @Override
    protected void extraInit() {
        mPresenter.getArtDetail("");
    }

    @Override
    public void getArtDetailSuccess(ArtDetailResult result) {

    }
}
