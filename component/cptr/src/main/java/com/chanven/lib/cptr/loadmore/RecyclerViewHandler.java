package com.chanven.lib.cptr.loadmore;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.widget.grecycleview.adapter.wrapper.BaseWrapper;

public class RecyclerViewHandler implements LoadMoreHandler {

    private BaseWrapper mRecyclerAdapter;
    private View mFooter;

    @Override
    public boolean handleSetAdapter(View contentView, ILoadMoreViewFactory.ILoadMoreView loadMoreView, OnClickListener onClickLoadMoreListener) {
        final RecyclerView recyclerView = (RecyclerView) contentView;
        boolean hasInit = false;
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if(adapter instanceof DelegateAdapter){
            RecyclerView.Adapter dLastAdapter = ((DelegateAdapter)adapter).getLastAdapter();
            if(dLastAdapter instanceof BaseWrapper){
                mRecyclerAdapter = (BaseWrapper) dLastAdapter;
            }
        }else{
            mRecyclerAdapter = (BaseWrapper) recyclerView.getAdapter();
        }
        if (loadMoreView != null) {
            final Context context = recyclerView.getContext().getApplicationContext();
            loadMoreView.init(new ILoadMoreViewFactory.FootViewAdder() {

                @Override
                public View addFootView(int layoutId) {
                    View view = LayoutInflater.from(context).inflate(layoutId, recyclerView, false);
                    mFooter = view;
                    return addFootView(view);
                }

                @Override
                public View addFootView(View view) {
                    mRecyclerAdapter.addFooter(view);
                    return view;
                }
            }, onClickLoadMoreListener);
            hasInit = true;
        }
        return hasInit;
    }

    @Override
    public void addFooter() {
        if (null != mFooter && !mRecyclerAdapter.isContainFooter(mFooter)) {
            mRecyclerAdapter.addFooter(mFooter);
        }
    }

    @Override
    public void removeFooter() {
        if (null != mFooter && mRecyclerAdapter.isContainFooter(mFooter)) {
            mRecyclerAdapter.removeFooter(mFooter);
        }
    }

    @Override
    public void setOnScrollBottomListener(View contentView, OnScrollBottomListener onScrollBottomListener) {
        final RecyclerView recyclerView = (RecyclerView) contentView;
        recyclerView.addOnScrollListener(new RecyclerViewOnScrollListener(onScrollBottomListener));
    }

    /**
     * 滑动监听
     */
    private static class RecyclerViewOnScrollListener extends RecyclerView.OnScrollListener {
        private OnScrollBottomListener onScrollBottomListener;

        public RecyclerViewOnScrollListener(OnScrollBottomListener onScrollBottomListener) {
            super();
            this.onScrollBottomListener = onScrollBottomListener;
        }

        @Override
        public void onScrollStateChanged(android.support.v7.widget.RecyclerView recyclerView, int newState) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE && isScollBottom(recyclerView)) {
                if (onScrollBottomListener != null) {
                    onScrollBottomListener.onScorllBootom();
                }
            }
        }

        private boolean isScollBottom(RecyclerView recyclerView) {
            return !isCanScollVertically(recyclerView);
        }

        private boolean isCanScollVertically(RecyclerView recyclerView) {
            return recyclerView.canScrollVertically(1);
        }
        @Override
        public void onScrolled(android.support.v7.widget.RecyclerView recyclerView, int dx, int dy) {

        }
    }
}
