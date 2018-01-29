package com.bohui.art.found;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bohui.art.R;
import com.bohui.art.bean.found.FoundResult;
import com.bohui.art.common.activity.CommonStaticActivity;
import com.bohui.art.bean.common.BannerBean;
import com.bohui.art.bean.common.BannerResult;
import com.bohui.art.common.fragment.AbsMianFragment;
import com.bohui.art.common.fragment.AbsNetBaseFragment;
import com.bohui.art.found.artman.ArtManActivity;
import com.bohui.art.found.designer.DesignerActivity;
import com.bohui.art.found.mvp.FoundContact;
import com.bohui.art.found.mvp.FoundModel;
import com.bohui.art.found.mvp.FoundPresenter;
import com.bohui.art.found.order.OrderActivity;
import com.bohui.art.home.RecommendFragment;
import com.bohui.art.home.adapter.BannerAdapter;
import com.framework.core.base.BaseHelperUtil;
import com.framework.core.util.CollectionUtil;
import com.framework.core.util.ResUtil;
import com.framework.core.util.StrOperationUtil;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.listener.RvClickListenerIml;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author : gaojigong
 * @date : 2017/12/9
 * @description:
 */


public class FoundFragment extends AbsMianFragment<FoundPresenter,FoundModel> implements FoundContact.View {
    @BindView(R.id.rv_found)
    RecyclerView rv_found;

    private BannerAdapter bannerAdapter;
    private String coopDescUrl;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_found;
    }

    @Override
    public void initView() {
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(mContext);
        DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager);
        //Banner
        bannerAdapter = new BannerAdapter(mContext);
        bannerAdapter.refreshBanner(new ArrayList<BannerBean>());
        bannerAdapter.setDelegateAdapter(delegateAdapter);
        bannerAdapter.setBannerHeight(ResUtil.getResDimensionPixelOffset(mContext,R.dimen.dp_250));
        delegateAdapter.addAdapter(bannerAdapter);

        GuideItemAdapter foundItemAdapter = new GuideItemAdapter(mContext);
        foundItemAdapter.setDelegateAdapter(delegateAdapter);
        List<GuideItemBean> foundItemBeans = new ArrayList<>();

        GuideItemBean artManItem = new GuideItemBean("艺术家",R.mipmap.ic_art_man,0,R.dimen.dp_1);
        foundItemBeans.add(artManItem);

        GuideItemBean designerItem = new GuideItemBean("设计师",R.mipmap.ic_stylist,1,R.dimen.dp_1);
        foundItemBeans.add(designerItem);

        GuideItemBean artPenItem = new GuideItemBean("艺术圈",R.mipmap.ic_art_pen,2,R.dimen.dp_10);
        foundItemBeans.add(artPenItem);

        GuideItemBean cooperationItem = new GuideItemBean("合作说明",R.mipmap.ic_cooperation,3,R.dimen.dp_1);
        foundItemBeans.add(cooperationItem);

        GuideItemBean customizationItem = new GuideItemBean("我要定制",R.mipmap.ic_customization,4,R.dimen.dp_10);
        foundItemBeans.add(customizationItem);

        GuideItemBean extItem = new GuideItemBean("更多",R.mipmap.ic_ext,5,R.dimen.dp_10);
        foundItemBeans.add(extItem);

        foundItemAdapter.setDatas(foundItemBeans);

        delegateAdapter.addAdapter(foundItemAdapter);

        rv_found.setLayoutManager(layoutManager);
        rv_found.setAdapter(delegateAdapter);
        rv_found.addOnItemTouchListener(new RvClickListenerIml(){
            @Override
            public void onItemClick(BaseAdapter adapter, View view, int position) {
                GuideItemBean itemBean = (GuideItemBean) adapter.getData(position);
                switch (itemBean.getTypeId()){
                    case 0:
                        ((BaseHelperUtil)mHelperUtil).startAty(ArtManActivity.class);
                        break;
                    case 1:
                        ((BaseHelperUtil)mHelperUtil).startAty(DesignerActivity.class);
                        break;
                    case 2:
                        ((BaseHelperUtil)mHelperUtil).toastShort("敬请期待");
                        break;
                    case 3:
                        if(!StrOperationUtil.isEmpty(coopDescUrl)){
                            Bundle bundle = new Bundle();
                            bundle.putString(CommonStaticActivity.WEB_URL_CONTENT,coopDescUrl);
                            startAty(CommonStaticActivity.class,bundle);
                        }
                        break;
                    case 4:
                        ((BaseHelperUtil)mHelperUtil).startAty(OrderActivity.class);
                        break;
                    case 5:
                        ((BaseHelperUtil)mHelperUtil).toastShort("敬请期待");
                        break;
                }
            }
        });
    }

    @Override
    public void initPresenter() {
        mPresenter.setMV(mModel,this);
    }

    @Override
    protected void come() {
        mPresenter.getFoundBanner();
    }

    @Override
    protected void leave() {

    }

    @Override
    public void getFoundBannerSuccess(FoundResult result) {
        List<BannerBean> bannerBeans = result.getBannerList();
        if(!CollectionUtil.isEmpty(bannerBeans)){
            bannerAdapter.refreshBanner(bannerBeans);
        }
        coopDescUrl = result.getCoopDescUrl();
    }

}
