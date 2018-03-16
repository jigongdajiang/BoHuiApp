package com.bohui.art.detail.art;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bohui.art.R;
import com.bohui.art.bean.detail.CAResult;
import com.bohui.art.bean.home.ArtItemBean;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.app.AppFuntion;
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
import com.bohui.art.start.login.LoginActivity;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.framework.core.log.PrintLog;
import com.framework.core.util.CollectionUtil;
import com.framework.core.util.ResUtil;
import com.framework.core.util.StrOperationUtil;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.listener.RvClickListenerIml;
import com.widget.smallelement.banner.listener.OnItemClickListener;
import com.widget.smallelement.photoview.ImagePagerActivity;

import java.util.ArrayList;
import java.util.Arrays;
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

    @BindView(R.id.tv_eye)
    TextView tv_eye;
    @BindView(R.id.tv_like)
    TextView tv_like;

    private long id;
    private static final String[] mTabTitles = {"产品", "简介", "详情", "推荐"};
    public static final String  ART_ID = "art_id";
    private int isfollow;//0未收藏，1已收藏
    public static void comeIn(Activity activity, long id){
        Bundle bundle = new Bundle();
        bundle.putLong(ART_ID,id);
        Intent intent = new Intent(activity,ArtDetailActivity.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    @Override
    protected void doBeforeSetContentView() {
        super.doBeforeSetContentView();
        id = getIntent().getLongExtra(ART_ID,0);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_art_detail;
    }

    private int rvStatus = 0;
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
                startActivity(new Intent(ArtDetailActivity.this, MainActivity.class));
            }
        });
        RxViewUtil.addOnClick(mRxManager, tv_like, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                //没登录去登录
                if(!AppFuntion.isLogin()){
                    startAty(LoginActivity.class);
                    return;
                }
                if(isfollow == 0){
                    //未收藏，则为收藏
                    mPresenter.collectArt(AppFuntion.getUid(),id,1);
                }else {
                    //已收藏，则为取消收藏
                    mPresenter.collectArt(AppFuntion.getUid(),id,2);
                }
            }
        });
    }



    @Override
    public void initPresenter() {
        mPresenter.setMV(mModel,this);
    }

    @Override
    protected void extraInit() {
        mPresenter.getArtDetail(AppFuntion.getUid(),id);
    }

    private ProductAdapter productAdapter;

    private static final int IMAGE_START_REQUEST_CODE = 0x3001;
    @Override
    public void getArtDetailSuccess(final ArtDetailResult result) {
        List<String> imgs = result.getImgs();
        if(CollectionUtil.isEmpty(imgs)){
            imgs = new ArrayList<>();
            result.setImgs(imgs);
        }
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(mContext);
        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        //产品  0
        productAdapter = new ProductAdapter(mContext,result);
        productAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                List<String> imgs = result.getImgs();
                if(!CollectionUtil.isEmpty(imgs)){
                    ArrayList<String> imgArry = new ArrayList<>();
                    imgArry.addAll(imgs);
                    ImagePagerActivity.comeIn(ArtDetailActivity.this,position, imgArry,IMAGE_START_REQUEST_CODE);
                }
            }
        });
        delegateAdapter.addAdapter(productAdapter);
        //简介 Guide  1
        DetailGuideAdapter detailGuideAdapterIntro = new DetailGuideAdapter(mContext,"简介");
        delegateAdapter.addAdapter(detailGuideAdapterIntro);
        //简介 2
        IntroAdapter introAdapter = new IntroAdapter(mContext,result);
        delegateAdapter.addAdapter(introAdapter);
        //详情 Guide  3
        DetailGuideAdapter detailGuideAdapterDetail = new DetailGuideAdapter(mContext,"详情");
        delegateAdapter.addAdapter(detailGuideAdapterDetail);
        //详情 Web显示 4
        DetailAdapter detailAdapter = new DetailAdapter(mContext,result);
        delegateAdapter.addAdapter(detailAdapter);
        //推荐 Guide  5
        DetailGuideAdapter detailGuideAdapterRecommend = new DetailGuideAdapter(mContext,"推荐");
        delegateAdapter.addAdapter(detailGuideAdapterRecommend);
        //推荐 6+
        ArtGridAdapter artGridAdapter = new ArtGridAdapter(mContext);
        List<ArtItemBean> artBeansLikes = result.getRecommendList();
        artGridAdapter.setDatas(artBeansLikes);
        delegateAdapter.addAdapter(artGridAdapter);

        rv.setLayoutManager(virtualLayoutManager);
        rv.setAdapter(delegateAdapter);

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                rvStatus = newState;
//                PrintLog.e("onScrollStateChanged","newState="+newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                PrintLog.e("onScrolled","dx="+dx+"---dy="+dy);

                VirtualLayoutManager layoutManager = (VirtualLayoutManager) recyclerView.getLayoutManager();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                int firstCompletelyVisibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition();
                int lastCompletelyVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition();

//                PrintLog.e("onScrolled_position","f="+firstVisibleItemPosition+"---" +
//                        "l="+lastVisibleItemPosition+"---" +
//                        "fc="+firstCompletelyVisibleItemPosition+"---" +
//                        "lc="+lastCompletelyVisibleItemPosition);
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
                    ArtDetailActivity.comeIn(ArtDetailActivity.this,((ArtGridAdapter) adapter).getData(position).getAid());
                }
            }

            @Override
            public void onItemChildClick(BaseAdapter adapter, View view, int position) {
                if(view.getId() == R.id.rl_art_man){
                    ArtManDetailActivity.comeIn(ArtDetailActivity.this,result.getAid());
                }
            }
        });
        //浏览量
        tv_eye.setText(result.getLooknum()+"人浏览");
        isfollow = result.getIsfollow();
        if(isfollow == 0){
            tv_like.setText("喜欢并收藏");
        }else{
            tv_like.setText("已收藏");
        }
    }

    @Override
    public void collectSuccess(CAResult result) {
        if(isfollow == 0){
            //收藏的结果
            if(result.getIsRes() == 1){
                showMsgDialg("收藏成功");
                isfollow = 1;
                tv_like.setText("已收藏");
            }
        }else{
            //取消收藏的结果
            if(result.getIsRes() == 1){
                showMsgDialg("取消收藏成功");
                isfollow = 0;
                tv_like.setText("喜欢并收藏");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IMAGE_START_REQUEST_CODE) {
                int position = data.getIntExtra(ImagePagerActivity.EXTRA_BACK_POSITION, 0);
                productAdapter.setCurrentItem(position);
            }
        }
    }
}
