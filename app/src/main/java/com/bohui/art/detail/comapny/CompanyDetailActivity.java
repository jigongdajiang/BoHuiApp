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
import com.bohui.art.bean.detail.CAResult;
import com.bohui.art.bean.detail.CompanyAttentionResult;
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
import com.bohui.art.detail.comapny.mvp.CompanyDetailContact;
import com.bohui.art.detail.comapny.mvp.CompanyDetailModel;
import com.bohui.art.detail.comapny.mvp.CompanyDetailPresenter;
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


public class CompanyDetailActivity extends AbsNetBaseActivity<CompanyDetailPresenter,CompanyDetailModel> implements CompanyDetailContact.ICompanyDetailView {
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
    private int aid;
    private int isfouce;//0未关注，1已关注
    private DetailAdapter detailAdapter;
    public static final String COMPANY_ID = "company_id";
    public static void comeIn(Activity activity, int aid){
        Intent intent = new Intent(activity,CompanyDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(COMPANY_ID,aid);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    @Override
    protected void doBeforeSetContentView() {
        super.doBeforeSetContentView();
        aid = getIntent().getIntExtra(COMPANY_ID,0);
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
    public void initPresenter() {
        super.initPresenter();
        mPresenter.setMV(mModel,this);
    }

    @Override
    protected void extraInit() {
        super.extraInit();
        mPresenter.getCompanyDetail(AppFuntion.getUid(),aid);
    }

    @Override
    public void getCompanyDetailSuccess(final CompanyDetailResult detailResult) {
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(mContext);
        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);

        isfouce = detailResult.getIsfollow();

        //简介 0
        detailAdapter = new DetailAdapter(mContext,detailResult);
        delegateAdapter.addAdapter(detailAdapter);

        //代表艺术品导航 1
        DetailGuideAdapter detailGuideAdapter = new DetailGuideAdapter(mContext,"代表作");
        delegateAdapter.addAdapter(detailGuideAdapter);
        dbzPosition = 1;
        //代表作列表 2
        Art2Adapter art2Adapter = new Art2Adapter(mContext,detailResult.getList());
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
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(CompanyArtListActivity.TYPE,detailResult);
                        startAty(CompanyArtListActivity.class,bundle);
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
                        mPresenter.attentionCompany(AppFuntion.getUid(),aid,1);
                    }else {
                        //已关注，则为取消关注
                        mPresenter.attentionCompany(AppFuntion.getUid(),aid,2);
                    }
                }
            }
        });
    }

    @Override
    public void attentionCompanySuccess(CompanyAttentionResult result) {
        if(isfouce == 0){
            //关注的结果
            if(result.getIs() == 1){
                showMsgDialg("关注成功");
                isfouce = 1;
                detailAdapter.changeAttentionText(isfouce);
            }
        }else{
            //取消关注的结果
            if(result.getIs() == 1){
                showMsgDialg("取消关注成功");
                isfouce = 0;
                detailAdapter.changeAttentionText(isfouce);
            }
        }
    }
}
