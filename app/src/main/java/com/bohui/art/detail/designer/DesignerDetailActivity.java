package com.bohui.art.detail.designer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bohui.art.R;
import com.bohui.art.bean.detail.DesignerDetailBean;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.util.CallUitl;
import com.bohui.art.common.util.RxViewUtil;
import com.bohui.art.detail.art.adapter.DetailGuideAdapter;
import com.bohui.art.detail.designer.adapter.CaseAdapter;
import com.bohui.art.detail.designer.adapter.DetailAdapter;
import com.bohui.art.detail.designer.adapter.IntroAdapter;
import com.bohui.art.bean.detail.CaseBean;
import com.bohui.art.bean.detail.DesignerDetailResult;
import com.bohui.art.detail.designer.mvp.DesignerDetailContact;
import com.bohui.art.detail.designer.mvp.DesignerDetailModel;
import com.bohui.art.detail.designer.mvp.DesignerDetailPresenter;
import com.bohui.art.start.MainActivity;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.framework.core.log.PrintLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * @author : gaojigong
 * @date : 2017/12/24
 * @description:
 */


public class DesignerDetailActivity extends AbsNetBaseActivity<DesignerDetailPresenter,DesignerDetailModel> implements DesignerDetailContact.View {
    @BindView(R.id.segment_tab)
    SegmentTabLayout segment_tab;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.iv_home)
    ImageView iv_home;

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.rl_call)
    RelativeLayout rl_call;
    private String[] mTabTitles = {"设计师", "案例", "TA的简介"};
    private int rvStatus = 0;

    private int alPosition = 1;//案例导航条
    private int jnPosition = 1;//简介导航条

    private long uid;
    private long did;
    private String mobile;
    public static final String DESIGNER_UID = "designer_uid";
    public static final String DESIGNER_DID = "designer_did";
    public static void comeIn(Activity activity, long uid,long did){
        Intent intent = new Intent(activity,DesignerDetailActivity.class);
        intent.putExtra(DESIGNER_UID,uid);
        intent.putExtra(DESIGNER_DID,did);
        activity.startActivity(intent);
    }

    @Override
    protected void doBeforeSetContentView() {
        super.doBeforeSetContentView();
        uid = getIntent().getLongExtra(DESIGNER_UID,0);
        did = getIntent().getLongExtra(DESIGNER_DID,0);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_designer_detail;
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
                startActivity(new Intent(DesignerDetailActivity.this, MainActivity.class));
            }
        });
        RxViewUtil.addOnClick(mRxManager, rl_call, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                CallUitl.call(DesignerDetailActivity.this,mobile);
            }
        });
    }

    @Override
    public void initPresenter() {
        mPresenter.setMV(mModel,this);
    }

    @Override
    protected void extraInit() {
        mPresenter.getDesignerDetail(uid,did);
    }

    @Override
    public void getDesignerDetailSuccess(DesignerDetailResult result) {
        DesignerDetailBean designerDetailResult = result.getDesignerDetail();
        final VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(mContext);
        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);

        //详情
        DetailAdapter detailAdapter = new DetailAdapter(mContext,designerDetailResult);
        delegateAdapter.addAdapter(detailAdapter);

        //案例Tab
        alPosition = 1;
        DetailGuideAdapter detailGuideAdapter = new DetailGuideAdapter(mContext,mTabTitles[1]);
        delegateAdapter.addAdapter(detailGuideAdapter);

        //案例内容
        List<CaseBean> caseBeans = designerDetailResult.getOpusList();
        CaseAdapter caseAdapter = new CaseAdapter(mContext);
        caseAdapter.setDatas(caseBeans);
        delegateAdapter.addAdapter(caseAdapter);

        //TA的简介Tab
        jnPosition = caseAdapter.getItemCount() + alPosition + 1;
        DetailGuideAdapter detailGuideAdapterJn = new DetailGuideAdapter(mContext,mTabTitles[2]);
        delegateAdapter.addAdapter(detailGuideAdapterJn);

        //TA的简介内容
        IntroAdapter introAdapter = new IntroAdapter(mContext,designerDetailResult);
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
                    } else if(firstVisibleItemPosition == alPosition){
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
                    virtualLayoutManager.scrollToPositionWithOffset(0,0);
                } else if(position == 1){
                    virtualLayoutManager.scrollToPositionWithOffset(alPosition,0);
                }else if(position == 2){
                    virtualLayoutManager.scrollToPositionWithOffset(jnPosition,0);
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mobile = designerDetailResult.getMobile();
    }
}
