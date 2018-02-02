package com.bohui.art.detail.artman;

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
import com.bohui.art.bean.home.ArtItemBean;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.app.AppFuntion;
import com.bohui.art.common.util.RxViewUtil;
import com.bohui.art.detail.art.ArtDetailActivity;
import com.bohui.art.detail.art.adapter.DetailGuideAdapter;
import com.bohui.art.detail.artman.adapter.DetailAdapter;
import com.bohui.art.detail.artman.adapter.IntroAdapter;
import com.bohui.art.detail.artman.adapter.ShowreelAdapter;
import com.bohui.art.bean.detail.ArtMainDetailResult;
import com.bohui.art.bean.detail.ShowreelBean;
import com.bohui.art.detail.artman.mvp.ArtManDetailContact;
import com.bohui.art.detail.artman.mvp.ArtManDetailModel;
import com.bohui.art.detail.artman.mvp.ArtManDetailPresenter;
import com.bohui.art.home.art1.Art2Adapter;
import com.bohui.art.start.MainActivity;
import com.bohui.art.start.login.LoginActivity;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.framework.core.log.PrintLog;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.listener.RvClickListenerIml;

import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * @author : gaojigong
 * @date : 2017/12/23
 * @description:
 */


public class ArtManDetailActivity extends AbsNetBaseActivity<ArtManDetailPresenter,ArtManDetailModel> implements ArtManDetailContact.View {
    @BindView(R.id.segment_tab)
    SegmentTabLayout segment_tab;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.iv_home)
    ImageView iv_home;

    @BindView(R.id.rv)
    RecyclerView rv;
    private String[] mTabTitles = {"艺术家", "代表作", "作品集", "Ta的简介"};
    private int rvStatus = 0;

    private int dbzPosition = 1;
    private int zpjPosition = 1;
    private int jnPosition = 1;
    private long aid;
    private int isfouce;//0未关注，1已关注
    private  DetailAdapter detailAdapter;
    public static final String ART_MAN_ID = "art_man_id";
    public static void comeIn(Activity activity, long aid){
        Intent intent = new Intent(activity,ArtManDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong(ART_MAN_ID,aid);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    @Override
    protected void doBeforeSetContentView() {
        super.doBeforeSetContentView();
        aid = getIntent().getLongExtra(ART_MAN_ID,0);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_art_man_detail;
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
                startActivity(new Intent(ArtManDetailActivity.this, MainActivity.class));
            }
        });
    }

    @Override
    public void initPresenter() {
        mPresenter.setMV(mModel,this);
    }

    @Override
    protected void extraInit() {
        mPresenter.getArtManDetail(AppFuntion.getUid(),aid);
    }

    @Override
    public void getArtManDetailSuccess(ArtMainDetailResult artMainDetailResult) {
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(mContext);
        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);

        isfouce = artMainDetailResult.getIsfouce();
        //简介 0
        detailAdapter = new DetailAdapter(mContext,artMainDetailResult);
        delegateAdapter.addAdapter(detailAdapter);

        //代表艺术品导航 1
        DetailGuideAdapter detailGuideAdapter = new DetailGuideAdapter(mContext,"代表作");
        delegateAdapter.addAdapter(detailGuideAdapter);
        dbzPosition = 1;
        //代表艺术品 2+
        List<ArtItemBean> artBeans = artMainDetailResult.getDlist();
        Art2Adapter art2Adapter = new Art2Adapter(mContext);
        art2Adapter.setDatas(artBeans);
        delegateAdapter.addAdapter(art2Adapter);

        //作品集导航
        zpjPosition = art2Adapter.getItemCount() + dbzPosition+1;
        DetailGuideAdapter detailGuideAdapterZpj = new DetailGuideAdapter(mContext,"作品集");
        delegateAdapter.addAdapter(detailGuideAdapterZpj);

        ShowreelAdapter showreelAdapter = new ShowreelAdapter(mContext);
        List<ShowreelBean> showreelBeans = artMainDetailResult.getSetlist();
        showreelAdapter.setDatas(showreelBeans);
        delegateAdapter.addAdapter(showreelAdapter);

        //Ta的简介
        jnPosition = showreelAdapter.getItemCount() + zpjPosition +1;
        DetailGuideAdapter detailGuideAdapterJj = new DetailGuideAdapter(mContext,"TA的简介");
        delegateAdapter.addAdapter(detailGuideAdapterJj);

        IntroAdapter introAdapter = new IntroAdapter(mContext,artMainDetailResult);
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
                    }else if(firstVisibleItemPosition == zpjPosition){
                        segment_tab.setCurrentTab(2);
                    }else if(firstVisibleItemPosition == jnPosition){
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
                    rv.scrollToPosition(dbzPosition);
                }else if(position == 2){
                    rv.scrollToPosition(zpjPosition);
                    segment_tab.setCurrentTab(2);
                }else if(position == 3){
                    rv.scrollToPosition(jnPosition);
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
                    ArtDetailActivity.comeIn(ArtManDetailActivity.this,itemBean.getAid());
                }else if(adapter instanceof ShowreelAdapter){
                    ShowreelBean showreelBean = ((ShowreelAdapter) adapter).getData(position);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(ArtJiActivity.ART_JI_ITEM,showreelBean);
                    startAty(ArtJiActivity.class,bundle);
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
                        mPresenter.attentionArtMan(AppFuntion.getUid(),aid,1);
                    }else {
                        //已关注，则为取消关注
                        mPresenter.attentionArtMan(AppFuntion.getUid(),aid,2);
                    }
                }
            }
        });
    }

    @Override
    public void attentionArtManSuccess(CAResult result) {
        if(isfouce == 0){
            //关注的结果
            if(result.getIsRes() == 1){
                showMsgDialg("关注成功");
                isfouce = 1;
                detailAdapter.changeAttentionText(isfouce);
            }
        }else{
            //取消关注的结果
            if(result.getIsRes() == 1){
                showMsgDialg("取消关注成功");
                isfouce = 0;
                detailAdapter.changeAttentionText(isfouce);
            }
        }
    }
}
