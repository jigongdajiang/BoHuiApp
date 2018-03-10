package com.bohui.art.detail.comapny;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bohui.art.R;
import com.bohui.art.bean.detail.CompanyDetailResult;
import com.bohui.art.bean.detail.ShowreelBean;
import com.bohui.art.bean.home.ArtItemBean;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.app.AppFuntion;
import com.bohui.art.common.util.RxViewUtil;
import com.bohui.art.detail.art.ArtDetailActivity;
import com.bohui.art.detail.art.adapter.DetailGuideAdapter;
import com.bohui.art.detail.artman.ArtJiActivity;
import com.bohui.art.detail.artman.ArtManDetailActivity;
import com.bohui.art.detail.artman.adapter.ShowreelAdapter;
import com.bohui.art.detail.comapny.adapter.DetailAdapter;
import com.bohui.art.detail.comapny.adapter.IntroAdapter;
import com.bohui.art.home.art1.Art2Adapter;
import com.bohui.art.start.MainActivity;
import com.bohui.art.start.login.LoginActivity;
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
 * @date : 2018/3/10
 * @description:
 */


public class CompanyDetailActivity extends AbsNetBaseActivity {
    @BindView(R.id.segment_tab)
    SegmentTabLayout segment_tab;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.iv_home)
    ImageView iv_home;

    @BindView(R.id.rv)
    RecyclerView rv;
    private String[] mTabTitles = {"机构", "代表作品", "机构详述"};
    private int rvStatus = 0;

    private int dbzPosition = 1;
    private int jnPosition = 1;
    private long aid;
    private int isfouce;//0未关注，1已关注
    private DetailAdapter detailAdapter;
    public static final String COMPANY_ID = "company_id";
    public static void comeIn(Activity activity, long aid){
        Intent intent = new Intent(activity,CompanyDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong(COMPANY_ID,aid);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    @Override
    protected void doBeforeSetContentView() {
        super.doBeforeSetContentView();
        aid = getIntent().getLongExtra(COMPANY_ID,0);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_company_detail;

    }

    @Override
    public void initView() {
        segment_tab.setTabData(mTabTitles);
        RxViewUtil.addOnClick(mRxManager, iv_back, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                onBackPressed();
            }
        });
        RxViewUtil.addOnClick(mRxManager, iv_home, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                startActivity(new Intent(CompanyDetailActivity.this, MainActivity.class));
            }
        });
    }

    @Override
    protected void extraInit() {
        super.extraInit();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        CompanyDetailResult detailResult = new CompanyDetailResult();
        detailResult.setId(1);
        detailResult.setLogo("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1520704210497&di=5d4bdbcc149d50a8714f6c4b65acbc62&imgtype=0&src=http%3A%2F%2Fdealer0.autoimg.cn%2Fdl%2F125117%2Fnewsimg%2F130492619601208820.jpg");
        detailResult.setAddress("北京市海淀区");
        detailResult.setDes("艺术是生命之光");
        detailResult.setIsAttention(1);
        detailResult.setName("牛逼机构");
        detailResult.setNum(23);
        detailResult.setDetail(ResUtil.getResString(mContext,R.string.temp_jianjie));
        List<ArtItemBean> artItemBeanList = new ArrayList<>();
        for (int i=0;i<10;i++){
            ArtItemBean artItemBean = new ArtItemBean();
            artItemBean.setAid(1);
            artItemBean.setId(12);
            artItemBean.setLookNum(12);
            artItemBean.setCover("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1520704210497&di=5d4bdbcc149d50a8714f6c4b65acbc62&imgtype=0&src=http%3A%2F%2Fdealer0.autoimg.cn%2Fdl%2F125117%2Fnewsimg%2F130492619601208820.jpg");
            artItemBean.setSalePrice(100.00);
            artItemBean.setName("山水文园");
            artItemBean.setSize("12cm*12cm");
            artItemBeanList.add(artItemBean);
        }
        detailResult.setArtItemBeans(artItemBeanList);
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(mContext);
        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);

        isfouce = detailResult.getIsAttention();

        //简介 0
        detailAdapter = new DetailAdapter(mContext,detailResult);
        delegateAdapter.addAdapter(detailAdapter);

        //代表艺术品导航 1
        DetailGuideAdapter detailGuideAdapter = new DetailGuideAdapter(mContext,"代表作");
        delegateAdapter.addAdapter(detailGuideAdapter);
        dbzPosition = 1;
        //代表作列表 2
        Art2Adapter art2Adapter = new Art2Adapter(mContext,detailResult.getArtItemBeans());
        delegateAdapter.addAdapter(art2Adapter);
        //机构描述导航
        jnPosition = art2Adapter.getItemCount() + dbzPosition +1;
        DetailGuideAdapter detailGuideAdapterJj = new DetailGuideAdapter(mContext,"机构详述");
        delegateAdapter.addAdapter(detailGuideAdapterJj);
        //机构描述
        IntroAdapter introAdapter = new IntroAdapter(mContext,detailResult);
        delegateAdapter.addAdapter(introAdapter);

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
                    } else if(firstVisibleItemPosition == dbzPosition){
                        segment_tab.setCurrentTab(1);
                    }else if(firstVisibleItemPosition == jnPosition){
                        segment_tab.setCurrentTab(2);
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
                    rv.scrollToPosition(dbzPosition);
                }else if(position == 2){
                    rv.scrollToPosition(jnPosition);
                    segment_tab.setCurrentTab(2);
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        rv.addOnItemTouchListener(new RvClickListenerIml(){
            @Override
            public void onItemClick(BaseAdapter adapter, View view, int position) {
                if(adapter instanceof Art2Adapter){
                    ArtItemBean itemBean = (ArtItemBean) adapter.getData(position);
                    ArtDetailActivity.comeIn(CompanyDetailActivity.this,itemBean.getAid());
                }else if(adapter instanceof DetailGuideAdapter){
                    //进入机构作品列表页
                    String des = ((DetailGuideAdapter)adapter).getData(position);
                    if("代表作".equals(des)){
                        startAty(CompanyArtListActivity.class);
                    }
                }
            }

            @Override
            public void onItemChildClick(BaseAdapter adapter, View view, int position) {
                if(view.getId() == R.id.tv_attention){
                    //没登录去登录
                    if(!AppFuntion.isLogin()){
                        startAty(LoginActivity.class);
                        return;
                    }
                    if(isfouce == 0){
                        //未关注，则为关注
                    }else {
                        //已关注，则为取消关注
                    }
                }
            }
        });
    }
}
