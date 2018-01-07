package com.bohui.art.home;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bohui.art.R;
import com.bohui.art.bean.common.BannerBean;
import com.bohui.art.bean.common.BannerResult;
import com.bohui.art.bean.home.RecommendResult;
import com.bohui.art.common.fragment.AbsNetBaseFragment;
import com.bohui.art.common.util.helperutil.NetBaseHelperUtil;
import com.bohui.art.common.util.BannerHelper;
import com.bohui.art.detail.art.ArtDetailActivity;
import com.bohui.art.home.adapter.ArtGridAdapter;
import com.bohui.art.home.adapter.BannerAdapter;
import com.bohui.art.home.adapter.DesignerAdapter;
import com.bohui.art.home.adapter.OrgGridAdapter;
import com.bohui.art.home.adapter.Art1Plus2Adapter;
import com.bohui.art.home.adapter.TypeTopAdapter;
import com.bohui.art.home.art1.Art1Activity;
import com.bohui.art.bean.home.ArtBean;
import com.bohui.art.bean.home.DesignerBean;
import com.bohui.art.bean.home.TypeTopBean;
import com.bohui.art.home.mvp.HomeContact;
import com.bohui.art.home.mvp.RecommendModel;
import com.bohui.art.home.mvp.RecommendPresenter;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.framework.core.util.ResUtil;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.listener.RvClickListenerIml;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

/**
 * @author : gaojigong
 * @date : 2017/12/10
 * @description:
 */


public class RecommendFragment extends AbsNetBaseFragment<RecommendPresenter,RecommendModel> implements HomeContact.IRecommendView{
    @BindView(R.id.ptr)
    PtrClassicFrameLayout ptrClassicFrameLayout;
    @BindView(R.id.rv)
    RecyclerView rv;
    private View bannerHeader;
    private BannerHelper bannerHelper;
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
        final DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);

        //Banner
        final BannerAdapter bannerAdapter = new BannerAdapter(mContext);
        List<BannerBean> bannerDatas = new ArrayList<>();
        bannerDatas.add(new BannerBean("banner1","http://www.baidu.com",imgs[0]
        ));
        bannerDatas.add(new BannerBean("banner2","http://www.baidu.com",imgs[1]
        ));
        bannerDatas.add(new BannerBean("banner2","http://www.baidu.com",imgs[2]
        ));
        BannerResult bannerBeans = new BannerResult();
        bannerBeans.setBannerBeans(bannerDatas);
        bannerAdapter.addItem(bannerBeans);
        bannerAdapter.setDelegateAdapter(delegateAdapter);
        delegateAdapter.addAdapter(bannerAdapter);

        //广告条
        BannerAdapter advAdapter = new BannerAdapter(mContext);
        advAdapter.setVertical(true);
        advAdapter.setBannerHeight(ResUtil.getResDimensionPixelOffset(mContext,R.dimen.dp_100));
        advAdapter.setBannerMargin(ResUtil.getResDimensionPixelOffset(mContext,R.dimen.sys_margin_small));
        advAdapter.setTuringTime(8000);
        List<BannerBean> advBannerDatas = new ArrayList<>();
        advBannerDatas.add(new BannerBean("banner2","http://baidu.com",imgs[2]
        ));
        advBannerDatas.add(new BannerBean("banner2","http://baidu.com",imgs[1]
        ));
        BannerResult advBannerBeans = new BannerResult();
        advBannerBeans.setBannerBeans(advBannerDatas);
        advAdapter.addItem(advBannerBeans);
        delegateAdapter.addAdapter(advAdapter);


        //第一个分类(国画)的Top
        TypeTopAdapter typeTopAdapter1 = new TypeTopAdapter(mContext,new TypeTopBean("国画",1));
        delegateAdapter.addAdapter(typeTopAdapter1);

        //国画数据适配器
        Art1Plus2Adapter onePuls2adapter = new Art1Plus2Adapter(getActivity());
        List<ArtBean> artBeans = new ArrayList<>();
        for(int i=0;i<3;i++){
            artBeans.add(new ArtBean());
        }
        onePuls2adapter.setDatas(artBeans);
        delegateAdapter.addAdapter(onePuls2adapter);

        //第二个分类(油画)
        TypeTopAdapter typeTopAdapter2 = new TypeTopAdapter(mContext,new TypeTopBean("油画",2));
        delegateAdapter.addAdapter(typeTopAdapter2);

        //油画数据适配器
        OrgGridAdapter org2ItemAdapter = new OrgGridAdapter(mContext);
        List<ArtBean> youhuaBeans = new ArrayList<>();
        for(int i=0;i<3;i++){
            youhuaBeans.add(new ArtBean());
        }
        org2ItemAdapter.setDatas(youhuaBeans);
        delegateAdapter.addAdapter(org2ItemAdapter);

        //第三个分类(机构推荐)
        TypeTopAdapter typeTopAdapter3 = new TypeTopAdapter(mContext,new TypeTopBean("机构推荐",3));
        delegateAdapter.addAdapter(typeTopAdapter3);

        //机构推荐数据适配器
        OrgGridAdapter org2ItemAdapter2 = new OrgGridAdapter(mContext);
        List<ArtBean> jigoubeans = new ArrayList<>();
        for(int i=0;i<4;i++){
            jigoubeans.add(new ArtBean());
        }
        org2ItemAdapter2.setDatas(jigoubeans);
        delegateAdapter.addAdapter(org2ItemAdapter2);

        //第四个分类(设计师)
        TypeTopAdapter typeTopAdapter4 = new TypeTopAdapter(mContext,new TypeTopBean("知名设计师",4));
        delegateAdapter.addAdapter(typeTopAdapter4);

        //设计师据适配器
        DesignerAdapter designerAdapter = new DesignerAdapter(mContext);
        List<DesignerBean> designerBeans = new ArrayList<>();
        for(int i=0;i<2;i++){
            designerBeans.add(new DesignerBean());
        }
        designerAdapter.setDatas(designerBeans);
        delegateAdapter.addAdapter(designerAdapter);

        //第五个分类(猜你喜欢)
        TypeTopAdapter typeTopAdapter5 = new TypeTopAdapter(mContext,new TypeTopBean("猜你喜欢",5));
        delegateAdapter.addAdapter(typeTopAdapter5);

        //猜你喜欢数据适配器
        final ArtGridAdapter artGridAdapter = new ArtGridAdapter(mContext);
        List<ArtBean> artBeansLikes = new ArrayList<>();
        for(int i=0;i<6;i++){
            artBeansLikes.add(new ArtBean());
        }
        artGridAdapter.setDatas(artBeansLikes);
        delegateAdapter.addAdapter(artGridAdapter);

        rv.setLayoutManager(virtualLayoutManager);
        rv.setAdapter(delegateAdapter);
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
        ptrClassicFrameLayout.postDelayed(new Runnable() {

            @Override
            public void run() {
                ptrClassicFrameLayout.autoRefresh(true);
            }
        }, 150);
        final Handler handler = new Handler();
        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<BannerBean> bannerDatas2 = new ArrayList<>();
                        bannerDatas2.add(new BannerBean("banner1","http://www.baidu.com",imgs[new Random().nextInt(100) % imgs.length]
                        ));
                        bannerDatas2.add(new BannerBean("banner1","http://www.baidu.com",imgs[new Random().nextInt(100) % imgs.length]
                        ));
                        bannerDatas2.add(new BannerBean("banner1","http://www.baidu.com",imgs[new Random().nextInt(100) % imgs.length]
                        ));
                        bannerDatas2.add(new BannerBean("banner1","http://www.baidu.com",imgs[new Random().nextInt(100) % imgs.length]
                        ));
                        BannerResult bannerBeans2 = new BannerResult();
                        bannerBeans2.setBannerBeans(bannerDatas2);
                        bannerAdapter.replaceItem(0,bannerBeans2);

                        List<ArtBean> artBeansLikes2 = new ArrayList<>();
                        for(int i=0;i<6;i++){
                            artBeansLikes2.add(new ArtBean("title"+new Random().nextInt()));
                        }
                        artGridAdapter.replaceAllItem(artBeansLikes2);
                        ptrClassicFrameLayout.refreshComplete();
                    }
                }, 1500);
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
    public void onResume() {
        super.onResume();
        if (null != bannerHelper) {
            bannerHelper.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (null != bannerHelper) {
            bannerHelper.onPause();
        }
    }

    @Override
    public void getRecommendSuccess(RecommendResult result) {
        //刷新数据
    }
}
