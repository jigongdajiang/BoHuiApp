package com.bohui.art.home.art2;

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
 * @date : 2017/12/15
 * @description:
 */


public class Art2Activity extends AbsNetBaseActivity {
    @BindView(R.id.rv)
    RecyclerView rv;
    @Override
    public int getLayoutId() {
        return R.layout.activity_art2;
    }

    @Override
    public void initView() {
        new DefaultTitleBar.DefaultBuilder(mContext)
                .setTitle("国画-山水")
                .setRightImage1(R.mipmap.ic_search)
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
