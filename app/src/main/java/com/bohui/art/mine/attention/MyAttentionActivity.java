package com.bohui.art.mine.attention;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bohui.art.R;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.widget.title.DefaultTitleBar;
import com.bohui.art.found.artman.ArtManListAdapter;
import com.bohui.art.found.artman.ArtManListItemBean;
import com.bohui.art.home.RecommendFragment;
import com.bohui.art.home.bean.ArtBean;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.listener.RvClickListenerIml;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author : gaojigong
 * @date : 2017/12/16
 * @description: 我的关注
 */


public class MyAttentionActivity extends AbsNetBaseActivity {
    @BindView(R.id.rv)
    RecyclerView rv;
    @Override
    public int getLayoutId() {
        return R.layout.layout_common_rv;
    }

    @Override
    public void initView() {
        new DefaultTitleBar.DefaultBuilder(mContext)
                .setTitle("我的关注")
                .builder();
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(mContext);
        final DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        //猜你喜欢数据适配器
        ArtManListAdapter artManListAdapter = new ArtManListAdapter(mContext);
        List<ArtManListItemBean> artManListItemBeans = new ArrayList<>();
        for(int j=0;j<20;j++){
            ArtManListItemBean artManListItemBean = new ArtManListItemBean();
            artManListItemBean.setArtManAvr(RecommendFragment.imgs[j%RecommendFragment.imgs.length]);
            List<ArtBean> artBeans = new ArrayList<>();
            for(int m=0; m<10;m++ ){
                ArtBean artBean = new ArtBean();
                artBean.setImgUrl(RecommendFragment.imgs[m%RecommendFragment.imgs.length]);
                artBeans.add(artBean);
            }
            artManListItemBean.setArtBeans(artBeans);
            artManListItemBeans.add(artManListItemBean);
        }
        artManListAdapter.setDatas(artManListItemBeans);
        delegateAdapter.addAdapter(artManListAdapter);
        rv.setLayoutManager(virtualLayoutManager);
        rv.setAdapter(delegateAdapter);
        rv.addOnItemTouchListener(new RvClickListenerIml(){
            @Override
            public void onItemClick(BaseAdapter adapter, View view, int position) {
                //艺术家详情
            }
        });
    }
}
