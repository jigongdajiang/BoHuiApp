package com.bohui.art.home;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bohui.art.R;
import com.bohui.art.common.bean.BannerBean;
import com.bohui.art.common.bean.BannerBeans;
import com.bohui.art.common.fragment.AbsNetBaseFragment;
import com.bohui.art.common.rv.adapter.NormalWrapAdapter;
import com.bohui.art.common.util.BannerHelper;
import com.bohui.art.home.adapter.BannerAdapter;
import com.bohui.art.home.adapter.Recommend1Plus2Adapter;
import com.bohui.art.home.adapter.TypeTopAdapter;
import com.bohui.art.home.bean.ArtBean;
import com.bohui.art.home.bean.TypeTopBean;
import com.framework.core.util.ResUtil;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.listener.RvClickListenerIml;
import com.widget.smallelement.banner.ConvenientBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author : gaojigong
 * @date : 2017/12/10
 * @description:
 */


public class RecommendFragment extends AbsNetBaseFragment{
    @BindView(R.id.rv)
    RecyclerView rv;
    private View bannerHeader;
    private BannerHelper bannerHelper;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_recommend;
    }

    @Override
    public void initView() {
        BannerAdapter bannerAdapter = new BannerAdapter(mContext);
        List<BannerBean> bannerDatas = new ArrayList<>();
        bannerDatas.add(new BannerBean("banner1","http://baidu.com","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1512897042545&di=053c45cd7e7da1412e81221e88c9824d&imgtype=0&src=http%3A%2F%2Fh.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Fc2cec3fdfc03924589eab7228c94a4c27d1e25bb.jpg"));
        bannerDatas.add(new BannerBean("banner2","http://baidu.com","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1512897042545&di=7f002625cee037a453bec91218d26416&imgtype=0&src=http%3A%2F%2Fh.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Fa9d3fd1f4134970a7f507e029ecad1c8a7865dff.jpg"));
        bannerDatas.add(new BannerBean("banner2","http://baidu.com","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1512897042544&di=4558d62428b5a41ca639f10455457620&imgtype=0&src=http%3A%2F%2Fb.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F9c16fdfaaf51f3dedda391499feef01f3a29798d.jpg"));
        BannerBeans bannerBeans = new BannerBeans();
        bannerBeans.setBannerBeans(bannerDatas);
        bannerAdapter.addItem(bannerBeans);

        BannerAdapter advAdapter = new BannerAdapter(mContext);
        advAdapter.setVertical(true);
        advAdapter.setBannerHeight(ResUtil.getResDimensionPixelOffset(mContext,R.dimen.dp_100));
        advAdapter.setBannerMargin(ResUtil.getResDimensionPixelOffset(mContext,R.dimen.sys_margin_small));
        advAdapter.setTuringTime(8000);
        List<BannerBean> advBannerDatas = new ArrayList<>();
        advBannerDatas.add(new BannerBean("banner2","http://baidu.com","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1512897042545&di=7f002625cee037a453bec91218d26416&imgtype=0&src=http%3A%2F%2Fh.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Fa9d3fd1f4134970a7f507e029ecad1c8a7865dff.jpg"));
        advBannerDatas.add(new BannerBean("banner2","http://baidu.com","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1512897042544&di=4558d62428b5a41ca639f10455457620&imgtype=0&src=http%3A%2F%2Fb.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F9c16fdfaaf51f3dedda391499feef01f3a29798d.jpg"));
        BannerBeans advBannerBeans = new BannerBeans();
        advBannerBeans.setBannerBeans(advBannerDatas);
        advAdapter.addItem(advBannerBeans);

        TypeTopAdapter typeTopAdapter1 = new TypeTopAdapter(mContext,new TypeTopBean("国画",1));
        TypeTopAdapter typeTopAdapter2 = new TypeTopAdapter(mContext,new TypeTopBean("油画",2));
        Recommend1Plus2Adapter onePuls2adapter = new Recommend1Plus2Adapter(getActivity());
        List<ArtBean> artBeans = new ArrayList<>();
        for(int i=0;i<3;i++){
            artBeans.add(new ArtBean());
        }
        onePuls2adapter.setDatas(artBeans);
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(getActivity());
        final DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        delegateAdapter.addAdapter(bannerAdapter);
        delegateAdapter.addAdapter(advAdapter);
        delegateAdapter.addAdapter(typeTopAdapter1);
        delegateAdapter.addAdapter(onePuls2adapter);
        delegateAdapter.addAdapter(typeTopAdapter2);
        delegateAdapter.addAdapter(onePuls2adapter);
        rv.setLayoutManager(virtualLayoutManager);
        rv.setAdapter(delegateAdapter);
        rv.addOnItemTouchListener(new RvClickListenerIml(){
            @Override
            public void onItemClick(BaseAdapter adapter, View view, int position) {
                String a = "a";
            }
        });
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
}
