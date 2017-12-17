package com.bohui.art.found;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bohui.art.R;
import com.bohui.art.common.activity.CommonStaticActivity;
import com.bohui.art.common.bean.BannerBean;
import com.bohui.art.common.bean.BannerBeans;
import com.bohui.art.common.fragment.AbsNetBaseFragment;
import com.bohui.art.found.artman.ArtManActivity;
import com.bohui.art.found.designer.DesignerActivity;
import com.bohui.art.found.order.OrderActivity;
import com.bohui.art.home.RecommendFragment;
import com.bohui.art.home.adapter.BannerAdapter;
import com.framework.core.base.AbsHelperUtil;
import com.framework.core.base.BaseHelperUtil;
import com.framework.core.util.ResUtil;
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


public class FoundFragment extends AbsNetBaseFragment {
    @BindView(R.id.rv_found)
    RecyclerView rv_found;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_found;
    }

    @Override
    public void initView() {
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(mContext);
        DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager);
        //Banner
        BannerAdapter bannerAdapter = new BannerAdapter(mContext);
        bannerAdapter.setBannerHeight(ResUtil.getResDimensionPixelOffset(mContext,R.dimen.dp_250));
        List<BannerBean> bannerDatas = new ArrayList<>();
        bannerDatas.add(new BannerBean("banner1","http://www.baidu.com", RecommendFragment.imgs[0]
        ));
        bannerDatas.add(new BannerBean("banner2","http://www.baidu.com",RecommendFragment.imgs[1]
        ));
        bannerDatas.add(new BannerBean("banner2","http://www.baidu.com",RecommendFragment.imgs[2]
        ));
        BannerBeans bannerBeans = new BannerBeans();
        bannerBeans.setBannerBeans(bannerDatas);
        bannerAdapter.addItem(bannerBeans);
        delegateAdapter.addAdapter(bannerAdapter);

        GuideItemAdapter foundItemAdapter = new GuideItemAdapter(mContext);
        List<GuideItemBean> foundItemBeans = new ArrayList<>();

        final GuideItemBean artManItem = new GuideItemBean("艺术家",R.mipmap.ic_art_man,0,R.dimen.dp_0);
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
                        Intent intent = new Intent();
                        intent.setClass(mContext, CommonStaticActivity.class);
                        intent.putExtra(CommonStaticActivity.WEB_URL_CONTENT, "http://www.baidu.com");
                        mContext.startActivity(intent);
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
}
