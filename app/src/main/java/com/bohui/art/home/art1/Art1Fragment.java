package com.bohui.art.home.art1;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bohui.art.R;
import com.bohui.art.bean.common.ArtListParam;
import com.bohui.art.bean.common.ArtListResult;
import com.bohui.art.bean.home.ArtItemBean;
import com.bohui.art.bean.home.ClassifyLevelBean;
import com.bohui.art.common.fragment.AbsNetBaseFragment;
import com.bohui.art.common.mvp.ArtListContact;
import com.bohui.art.common.mvp.ArtListModel;
import com.bohui.art.common.mvp.ArtListPresenter;
import com.bohui.art.common.widget.rv.adapter.NormalWrapAdapter;
import com.bohui.art.detail.art.ArtDetailActivity;
import com.bohui.art.home.adapter.ArtGridAdapter;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.framework.core.http.exception.ApiException;
import com.framework.core.util.CollectionUtil;
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


public class Art1Fragment extends AbsNetBaseFragment<ArtListPresenter, ArtListModel> implements ArtListContact.View {
    @BindView(R.id.ptr)
    PtrClassicFrameLayout ptrClassicFrameLayout;
    @BindView(R.id.rv)
    RecyclerView rv;
    public static final String TYPE = "type";
    private ClassifyLevelBean mType;
    private ArtGridAdapter artGridAdapter;
    private ArtListParam param = new ArtListParam();
    private boolean isRefresh;
    private boolean isRequesting;

    public static Art1Fragment newInstance(ClassifyLevelBean type) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(TYPE, type);
        Art1Fragment fragment = new Art1Fragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void doBeforeOnCreateView() {
        super.doBeforeOnCreateView();
        mType = (ClassifyLevelBean) getArguments().getSerializable(TYPE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_refresh_rv;
    }

    @Override
    public void initView() {
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(getActivity());
        final DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        artGridAdapter = new ArtGridAdapter(mContext);
        artGridAdapter.setDelegateAdapter(delegateAdapter);
        NormalWrapAdapter wrapper = new NormalWrapAdapter(mContext, artGridAdapter);
        artGridAdapter.setWrapper(wrapper);
        wrapper.setDelegateAdapter(delegateAdapter);
        delegateAdapter.addAdapter(wrapper);
        rv.setLayoutManager(virtualLayoutManager);
        rv.setAdapter(delegateAdapter);
        rv.addOnItemTouchListener(new RvClickListenerIml() {
            @Override
            public void onItemClick(BaseAdapter adapter, View view, int position) {
                ArtItemBean itemBean = (ArtItemBean) adapter.getData(position);
                if (null != itemBean) {
                    ArtDetailActivity.comeIn(getActivity(), itemBean.getAid());
                }
            }
        });
        ptrClassicFrameLayout.setAutoLoadMoreEnable(false);
        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                requestFirstPage();
            }
        });
        ptrClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                requestNextPage();
            }
        });
    }

    @Override
    public void initPresenter() {
        mPresenter.setMV(mModel, this);
    }

    @Override
    protected void doLoad() {
        List<Long> oneClass = new ArrayList<>();
        oneClass.add(mType.getPid());
        param.setOneclass(oneClass);

        List<Long> towClass = new ArrayList<>();
        towClass.add(mType.getId());
        param.setTowclass(towClass);
        requestFirstPage();
    }

    private void requestFirstPage() {
        isRefresh = true;
        param.setStart(0);
        request();
    }

    private void requestNextPage() {
        isRefresh = false;
        param.setStart(param.getStart() + param.getLength());
        request();
    }

    private void request() {
        if (!isRequesting) {
            mPresenter.getArtList(param);
            isRequesting = true;
        }
    }

    @Override
    protected boolean childInterceptException(String apiName, ApiException e) {
        isRequesting = false;
        if (isRefresh) {
            ptrClassicFrameLayout.refreshComplete();
        } else {
            ptrClassicFrameLayout.setLoadMoreEnable(false);
            ptrClassicFrameLayout.loadMoreComplete(false);
        }
        return super.childInterceptException(apiName, e);
    }

    @Override
    public void getArtListSuccess(ArtListResult result) {
        isRequesting = false;
        List<ArtItemBean> list = result.getPaintingList();
        if (isRefresh) {
            ptrClassicFrameLayout.refreshComplete();
            if (!CollectionUtil.isEmpty(list)) {
                artGridAdapter.replaceAllItem(result.getPaintingList());
                if (list.size() >= param.getLength()) {
                    ptrClassicFrameLayout.setLoadMoreEnable(true);
                } else {
                    ptrClassicFrameLayout.setLoadMoreEnable(false);
                }
            } else {
                ptrClassicFrameLayout.setLoadMoreEnable(false);
            }
        } else {
            if (CollectionUtil.isEmpty(list)) {
                ptrClassicFrameLayout.loadMoreComplete(false);
            } else {
                artGridAdapter.addItems(list);
                if (list.size() >= param.getLength()) {
                    ptrClassicFrameLayout.loadMoreComplete(true);
                } else {
                    ptrClassicFrameLayout.loadMoreComplete(false);
                }
            }
        }
    }
}
