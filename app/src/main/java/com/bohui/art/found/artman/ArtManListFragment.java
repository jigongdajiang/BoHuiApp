package com.bohui.art.found.artman;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bohui.art.R;
import com.bohui.art.bean.common.ArtManListParam;
import com.bohui.art.bean.detail.ArtManLevelBean;
import com.bohui.art.bean.found.ArtManItemBean;
import com.bohui.art.bean.found.ArtManListResult;
import com.bohui.art.bean.home.ArtItemBean;
import com.bohui.art.common.fragment.AbsNetBaseFragment;
import com.bohui.art.detail.artman.ArtManDetailActivity;
import com.bohui.art.found.artman.mvp.ArtManListContact;
import com.bohui.art.found.artman.mvp.ArtManListModel;
import com.bohui.art.found.artman.mvp.ArtManListPresenter;
import com.bohui.art.home.RecommendFragment;
import com.bohui.art.bean.home.ArtCoverItemBean;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.listener.RvClickListenerIml;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author : gaojigong
 * @date : 2017/12/16
 * @description:
 * 艺术家列表页
 */


public class ArtManListFragment extends AbsNetBaseFragment<ArtManListPresenter,ArtManListModel> implements ArtManListContact.IArtManListView{
    @BindView(R.id.rv)
    RecyclerView rv;
    public static final String TYPE = "type";
    private ArtManLevelBean mType;
    private ArtManListAdapter artManListAdapter;
    public static ArtManListFragment newInstance(ArtManLevelBean type){
        Bundle bundle = new Bundle();
        bundle.putSerializable(TYPE,type);
        ArtManListFragment fragment = new ArtManListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void doBeforeOnCreateView() {
        super.doBeforeOnCreateView();
        mType = (ArtManLevelBean) getArguments().getSerializable(TYPE);
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
        artManListAdapter = new ArtManListAdapter(mContext);
        artManListAdapter.setDelegateAdapter(delegateAdapter);
        delegateAdapter.addAdapter(artManListAdapter);
        rv.setLayoutManager(virtualLayoutManager);
        rv.setAdapter(delegateAdapter);
        rv.addOnItemTouchListener(new RvClickListenerIml(){
            @Override
            public void onItemClick(BaseAdapter adapter, View view, int position) {
                ArtManItemBean itemBean = (ArtManItemBean) adapter.getData(position);
                ArtManDetailActivity.comeIn(getActivity(),itemBean.getAid());
            }
        });
    }

    @Override
    public void initPresenter() {
        mPresenter.setMV(mModel,this);
    }

    @Override
    protected void extraInit() {
        ArtManListParam param = new ArtManListParam();
        param.setLevel(mType.getId());
        mPresenter.getArtManList(param);
    }

    @Override
    public void getArtManListSuccess(ArtManListResult result) {
        if(result != null){
            artManListAdapter.replaceAllItem(result.getArtistList());
        }
    }
}
