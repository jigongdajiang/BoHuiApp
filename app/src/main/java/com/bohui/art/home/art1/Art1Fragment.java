package com.bohui.art.home.art1;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bohui.art.R;
import com.bohui.art.bean.home.ArtItemBean;
import com.bohui.art.bean.home.ArtListResult;
import com.bohui.art.bean.home.ClassifyLevelBean;
import com.bohui.art.common.fragment.AbsNetBaseFragment;
import com.bohui.art.detail.art.ArtDetailActivity;
import com.bohui.art.home.adapter.ArtGridAdapter;
import com.bohui.art.home.art1.mvp.ArtListContact;
import com.bohui.art.home.art1.mvp.ArtListModel;
import com.bohui.art.home.art1.mvp.ArtListPresenter;
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


public class Art1Fragment extends AbsNetBaseFragment<ArtListPresenter,ArtListModel> implements ArtListContact.View {
    @BindView(R.id.rv)
    RecyclerView rv;
    public static final String TYPE = "type";
    private ClassifyLevelBean mType;
    public static Art1Fragment newInstance(ClassifyLevelBean type){
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
        List<ArtItemBean> artBeansLikes = new ArrayList<>();
        for(int j=0;j<20;j++){
            artBeansLikes.add(new ArtItemBean());
        }
        artGridAdapter.setDatas(artBeansLikes);
        delegateAdapter.addAdapter(artGridAdapter);
        rv.setLayoutManager(virtualLayoutManager);
        rv.setAdapter(delegateAdapter);
        rv.addOnItemTouchListener(new RvClickListenerIml(){
            @Override
            public void onItemClick(BaseAdapter adapter, View view, int position) {
                ArtDetailActivity.comeIn(getActivity(),new Bundle());
            }
        });
    }

    @Override
    public void initPresenter() {
        mPresenter.setMV(mModel,this);
    }

    @Override
    protected void doLoad() {
        mPresenter.getArtList("1",1,10);
    }

    @Override
    public void getArtListSuccess(ArtListResult result) {

    }
}
