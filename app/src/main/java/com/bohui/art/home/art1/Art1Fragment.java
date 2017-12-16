package com.bohui.art.home.art1;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bohui.art.R;
import com.bohui.art.common.fragment.AbsNetBaseFragment;
import com.bohui.art.home.TypeFragment;
import com.bohui.art.home.adapter.ArtGridAdapter;
import com.bohui.art.home.bean.ArtBean;
import com.bohui.art.home.bean.TypeBean;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.listener.RvClickListenerIml;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author : gaojigong
 * @date : 2017/12/15
 * @description:
 */


public class Art1Fragment extends AbsNetBaseFragment {
    @BindView(R.id.rv)
    RecyclerView rv;
    public static final String TYPE = "type";
    private TypeBean mType;
    public static Art1Fragment newInstance(TypeBean type){
        Bundle bundle = new Bundle();
        bundle.putSerializable(TYPE,type);
        Art1Fragment fragment = new Art1Fragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public int getLayoutId() {
        return R.layout.layout_common_rv;
    }

    @Override
    public void initView() {
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(getActivity());
        final DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        //猜你喜欢数据适配器
        ArtGridAdapter artGridAdapter = new ArtGridAdapter(mContext);
        List<ArtBean> artBeansLikes = new ArrayList<>();
        for(int j=0;j<20;j++){
            artBeansLikes.add(new ArtBean());
        }
        artGridAdapter.setDatas(artBeansLikes);
        delegateAdapter.addAdapter(artGridAdapter);
        rv.setLayoutManager(virtualLayoutManager);
        rv.setAdapter(delegateAdapter);
        rv.addOnItemTouchListener(new RvClickListenerIml(){
            @Override
            public void onItemClick(BaseAdapter adapter, View view, int position) {
                String a = "a";
            }
        });
    }
}
