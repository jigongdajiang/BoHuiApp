package com.bohui.art.home;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bohui.art.R;
import com.bohui.art.bean.common.BannerBean;
import com.bohui.art.bean.common.BannerResult;
import com.bohui.art.bean.home.ArtItemBean;
import com.bohui.art.bean.home.ClassifyLevelBean;
import com.bohui.art.bean.home.DesignerItemBean;
import com.bohui.art.bean.home.RecommendDesignerBean;
import com.bohui.art.bean.home.RecommendListItemBean;
import com.bohui.art.bean.home.RecommendMechanismBean;
import com.bohui.art.bean.home.RecommendResult;
import com.bohui.art.common.fragment.AbsNetBaseFragment;
import com.bohui.art.common.util.helperutil.NetBaseHelperUtil;
import com.bohui.art.detail.art.ArtDetailActivity;
import com.bohui.art.home.adapter.ArtGridAdapter;
import com.bohui.art.home.adapter.BannerAdapter;
import com.bohui.art.home.adapter.DesignerAdapter;
import com.bohui.art.home.adapter.OrgGridAdapter;
import com.bohui.art.home.adapter.Art1Plus2Adapter;
import com.bohui.art.home.adapter.TypeTopAdapter;
import com.bohui.art.home.art1.Art1Activity;
import com.bohui.art.bean.home.ArtCoverItemBean;
import com.bohui.art.home.mvp.HomeContact;
import com.bohui.art.home.mvp.RecommendModel;
import com.bohui.art.home.mvp.RecommendPresenter;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.framework.core.http.exception.ApiException;
import com.framework.core.util.CollectionUtil;
import com.framework.core.util.ResUtil;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.listener.RvClickListenerIml;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author : gaojigong
 * @date : 2017/12/10
 * @description:
 */


public class RecommendFragment extends AbsNetBaseFragment<RecommendPresenter,RecommendModel> implements HomeContact.IRecommendView{
    @BindView(R.id.ptr)
    PtrClassicFrameLayout mPtrClassicFrameLayout;
    @BindView(R.id.rv)
    RecyclerView rv;

    private RecommendResult mResult;
    private DelegateAdapter mDelegateAdapter;
    public static String imgs[] = new String[]{
        "http://pic48.nipic.com/file/20140912/7487939_223919315000_2.jpg",
            "http://fd.topitme.com/d/a8/1d/11315383988791da8do.jpg",
            "http://img0.imgtn.bdimg.com/it/u=3424226810,3788025634&fm=214&gp=0.jpg",
            "http://image.tianjimedia.com/uploadImages/2014/289/01/IGS09651F94M.jpg",
            "http://img.sc115.com/mm/mm3/mm112013852nhei3knpfbu.jpg",
    };
    @Override
    public int getLayoutId() {
        return R.layout.fragment_recommend;
    }

    @Override
    public void initView() {
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(getActivity());
        mDelegateAdapter = new DelegateAdapter(virtualLayoutManager);
        rv.setLayoutManager(virtualLayoutManager);
        rv.setAdapter(mDelegateAdapter);
        rv.addOnItemTouchListener(new RvClickListenerIml(){
            @Override
            public void onItemClick(BaseAdapter adapter, View view, int position) {
                if(adapter instanceof TypeTopAdapter){
                    //进入二级艺术品列表页
                    if(mHelperUtil != null && mHelperUtil instanceof NetBaseHelperUtil){
                        ((NetBaseHelperUtil)mHelperUtil).startAty(Art1Activity.class);
                    }
                }else if(adapter instanceof  Art1Plus2Adapter
                        || adapter instanceof OrgGridAdapter
                        || adapter instanceof ArtGridAdapter){
                    ArtDetailActivity.comeIn(getActivity(),new Bundle());
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
        mPresenter.setMV(mModel,this);
    }

    @Override
    protected void doLoad() {
        mPresenter.getRecommend();
    }

    @Override
    public void getRecommendSuccess(RecommendResult result) {
        mPtrClassicFrameLayout.refreshComplete();
        //刷新数据
        if(mResult == null || (result != null && !mResult.toString().equals(result.toString()))){
            mResult = result;
            refreshData();
        }
    }

    @Override
    protected boolean childInterceptException(String apiName, ApiException e) {
        mPtrClassicFrameLayout.refreshComplete();
        return super.childInterceptException(apiName, e);
    }

    private void refreshData() {
        List<DelegateAdapter.Adapter> adapters = new ArrayList<>();

        //Banner
        List<BannerBean> bannerList = mResult.getBannerList();
        if(!CollectionUtil.isEmpty(bannerList)){
            BannerResult bannerBeans = new BannerResult();
            bannerBeans.setBannerBeans(bannerList);

            BannerAdapter bannerAdapter = new BannerAdapter(mContext);
            bannerAdapter.addItem(bannerBeans);
            bannerAdapter.setDelegateAdapter(mDelegateAdapter);
            adapters.add(bannerAdapter);
        }
        //Adv
        List<BannerBean> advList = mResult.getAdList();
        if(!CollectionUtil.isEmpty(advList)){
            BannerAdapter advAdapter = new BannerAdapter(mContext);
            advAdapter.setVertical(true);
            advAdapter.setBannerHeight(ResUtil.getResDimensionPixelOffset(mContext,R.dimen.dp_100));
            advAdapter.setBannerMargin(ResUtil.getResDimensionPixelOffset(mContext,R.dimen.sys_margin_small));
            advAdapter.setTuringTime(8000);
            BannerResult advBannerBeans = new BannerResult();
            advBannerBeans.setBannerBeans(advList);
            advAdapter.addItem(advBannerBeans);
            adapters.add(advAdapter);
        }

        //一级分类推荐列表
        List<RecommendListItemBean> remomerList = mResult.getRemomerList();
        if(!CollectionUtil.isEmpty(remomerList)){
            int size = remomerList.size();
            for(int i=0;i<size;i++){
                String typeName = remomerList.get(i).getName();
                long typeId = remomerList.get(i).getId();
                List<ArtCoverItemBean> list = remomerList.get(i).getList();
                TypeTopAdapter typeTopAdapter = new TypeTopAdapter(mContext,new ClassifyLevelBean(typeName,typeId));
                adapters.add(typeTopAdapter);
                if(list != null && list.size() > 0){
                    int listSize = list.size();
                    if(listSize == 3){
                        Art1Plus2Adapter onePuls2adapter = new Art1Plus2Adapter(getActivity());
                        onePuls2adapter.setDatas(list);
                        adapters.add(onePuls2adapter);
                    }else{
                        OrgGridAdapter org2ItemAdapter = new OrgGridAdapter(mContext);
                        org2ItemAdapter.setDatas(list);
                        adapters.add(org2ItemAdapter);
                    }
                }
            }
        }
        //机构推荐
        RecommendMechanismBean recommendMechanismBean = mResult.getMechanism();
        if(recommendMechanismBean != null){
            TypeTopAdapter jigouTopAdapter = new TypeTopAdapter(mContext,new ClassifyLevelBean(recommendMechanismBean.getName(),3));
            adapters.add(jigouTopAdapter);
            List<ArtItemBean> list = recommendMechanismBean.getList();
            if(!CollectionUtil.isEmpty(list)){
                ArtGridAdapter mechanisAdapter = new ArtGridAdapter(mContext);
                mechanisAdapter.setDatas(list);
                adapters.add(mechanisAdapter);
            }
        }

        //设计师推荐
        RecommendDesignerBean recommendDesignerBean = mResult.getDesigner();
        if(recommendDesignerBean != null){
            TypeTopAdapter designerTopAdapter = new TypeTopAdapter(mContext,new ClassifyLevelBean(recommendDesignerBean.getName(),4));
            adapters.add(designerTopAdapter);
            List<DesignerItemBean> list = recommendDesignerBean.getList();
            if(!CollectionUtil.isEmpty(list)){
                DesignerAdapter designerAdapter = new DesignerAdapter(mContext);
                designerAdapter.setDatas(list);
                adapters.add(designerAdapter);
            }
        }

        //猜你喜欢
        List<ArtItemBean> guessLike = mResult.getGuessLike();
        if(!CollectionUtil.isEmpty(guessLike)){
            TypeTopAdapter guessLikeTopAdapter = new TypeTopAdapter(mContext,new ClassifyLevelBean("猜你喜欢",5));
            adapters.add(guessLikeTopAdapter);
            ArtGridAdapter guessLikeAdapter = new ArtGridAdapter(mContext);
            guessLikeAdapter.setDatas(guessLike);
            adapters.add(guessLikeAdapter);
        }
        mDelegateAdapter.setAdapters(adapters);
    }
}
