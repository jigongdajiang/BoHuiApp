package com.bohui.art.mine.collect;

import android.support.v7.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bohui.art.R;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.widget.title.DefaultTitleBar;
import com.bohui.art.home.art1.Art2Adapter;
import com.bohui.art.home.bean.ArtBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author : gaojigong
 * @date : 2017/12/16
 * @description:
 */


public class MyCollectActivity extends AbsNetBaseActivity {
    @BindView(R.id.rv)
    RecyclerView rv;
    @Override
    public int getLayoutId() {
        return R.layout.layout_common_rv;
    }

    @Override
    public void initView() {
        new DefaultTitleBar.DefaultBuilder(mContext)
                .setTitle("我的收藏")
                .builder();
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(mContext);
        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        Art2Adapter art2Adapter = new Art2Adapter(mContext);
        List<ArtBean> artBeans = new ArrayList<>();
        for(int i=0;i<20;i++){
            artBeans.add(new ArtBean());
        }
        art2Adapter.setDatas(artBeans);
        delegateAdapter.addAdapter(art2Adapter);
        rv.setLayoutManager(virtualLayoutManager);
        rv.setAdapter(delegateAdapter);

    }
}
