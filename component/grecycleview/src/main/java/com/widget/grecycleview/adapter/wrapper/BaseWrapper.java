package com.widget.grecycleview.adapter.wrapper;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.framework.core.log.PrintLog;
import com.framework.core.util.NetWorkUtil;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.adapter.tree.TreeRecyclerAdapter;
import com.widget.grecycleview.manager.CheckItemManager;
import com.widget.grecycleview.viewholder.BaseViewHolder;

import java.util.List;


/**
 * @author : gaojigong
 * @date : 2017/11/28
 * @description:
 * 基础 无数据无网络包装类
 *
 * 将在 Operation层提供默认实现
 */


public abstract class BaseWrapper<T,EM,ER> extends BaseAdapter<T> {
    private static final int ITEM_TYPE_EMPTY = Integer.MIN_VALUE + 1;
    private static final int ITEM_TYPE_NET_ERROR = Integer.MIN_VALUE + 2;

    protected BaseAdapter<T> mAdapter;

    private EM mEmptyData;
    private ER mNetErrorData;

    //Header，Footer的数量基本限制在100个以内
    private final static int ITEM_TYPE_HEEADER_START = Integer.MIN_VALUE+100;
    private final static int ITEM_TYPE_FOOTER_START = Integer.MIN_VALUE+200;

    //key为ItemType
    private SparseArray<View> mHeaderViews;
    private SparseArray<View> mFooterViews;

    public BaseWrapper(Context context, BaseAdapter<T> adapter) {
        super(context);
        this.mAdapter = adapter;
        mHeaderViews = new SparseArray<>();
        mFooterViews = new SparseArray<>();
    }

    @Override
    public int geLayoutId(int position) {
        return mAdapter.geLayoutId(position);
    }

    @Override
    public int getItemViewType(int position) {
        PrintLog.e("test-getItemViewType");
        if(isNetError()){
            return ITEM_TYPE_NET_ERROR;
        }else if(isEmpty()){
            return ITEM_TYPE_EMPTY;
        }else if(isHeaderByPosition(position)){
            return getHeaderItemTypeByPosition(position);
        }else if(isFooterByPosition(position)){
            return getFooterItemTypeByPosition(position);
        }else{
            return mAdapter.getItemViewType(position);
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PrintLog.e("test-onCreateViewHolder");
        if(viewType == ITEM_TYPE_NET_ERROR){
            return BaseViewHolder.createViewHolder(parent,getNetErrorLayoutId());
        }else if(viewType == ITEM_TYPE_EMPTY){
            return BaseViewHolder.createViewHolder(parent,getEmptyLayoutId());
        }else if(isHeaderByItemType(viewType)){
            return createHeaderOrFooterHolder(getHeaderByItemType(viewType));
        }else if(isFooterByItemType(viewType)){
            return createHeaderOrFooterHolder(getFooterByItemType(viewType));
        }else{
            return mAdapter.onCreateViewHolder(parent,viewType);
        }
    }
    @Override
    public void onBindViewHolderClick(BaseViewHolder holder, View itemView) {
        if(isEmpty() ||
                isNetError() ||
                isHeaderByPosition(holder.getLayoutPosition()) ||
                isFooterByPosition(holder.getLayoutPosition())){
            return;
        }
        mAdapter.onBindViewHolderClick(holder, itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        PrintLog.e("test-onBindViewHolder");
        if(isNetError()){
            if(mNetErrorData != null){
                bindNetError(holder, mNetErrorData);
            }
        }else if(isEmpty()){
            if(mEmptyData != null){
                bindEmpty(holder, mEmptyData);
            }
        }else if(isHeaderByPosition(position) || isFooterByPosition(position)){
            // noting
        }else{
            mAdapter.onBindViewHolder(holder, getCheckItemManager().getAfterCheckingPosition(position));
        }
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, T itemData, int position) {
        mAdapter.bindViewHolder(holder,itemData,position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        adjustSpanSize(recyclerView);
        mAdapter.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public T getData(int position) {
        return mAdapter.getData(position);
    }

    @Override
    public List<T> getDatas() {
        return mAdapter.getDatas();
    }

    @Override
    public void setDatas(List<T> datas) {
        mAdapter.setDatas(datas);
    }


    @Override
    public int getItemCount() {
        if(isNetError() || isEmpty()){
            return 1;
        }
        return mAdapter.getDatas().size() + mHeaderViews.size() + mFooterViews.size();
    }

    @Override
    public CheckItemManager getCheckItemManager() {
        if(mAdapter instanceof TreeRecyclerAdapter){
            mCheckItemManager = new CheckItemManager() {
                @Override
                public boolean checkPosition(int position) {
                    return !isHeaderByPosition(position) &&
                            !isFooterByPosition(position) &&
                            !isEmpty() &&
                            !isNetError();
                }
                @Override
                public int getAfterCheckingPosition(int position) {
                    return position - mHeaderViews.size();
                }
            };
            return mCheckItemManager;
        }else{
            mCheckItemManager = new CheckItemManager() {
                @Override
                public boolean checkPosition(int position) {
                    return !isHeaderByPosition(position) && !isFooterByPosition(position) ;
                }
                @Override
                public int getAfterCheckingPosition(int position) {
                    return position - mHeaderViews.size();
                }
            };
            return mCheckItemManager;
        }
    }


    /**
     * 更新无数据时的数据内容
     * @param emptyData
     */
    public void setEmptyData(EM emptyData) {
        this.mEmptyData = emptyData;
        notifyDataSetChanged();
    }

    /**
     * 更新无网络是的数据内容
     * @param netErrorData
     */
    public void setNetErrorData(ER netErrorData) {
        this.mNetErrorData = netErrorData;
        notifyDataSetChanged();
    }

    /**
     * 可以通过复写，改变无数据规则
     * @return
     */
    public boolean isEmpty(){
        return getEmptyLayoutId() != 0 &&
                mEmptyData != null && //是为了防止一进入页面就会显示无数据情况，所以一般在请求完成后，手动调用setEmptyData来显示无数据页
                getDatas().size() == 0;
    }

    /**
     * 可以通过复写，改变无网络规则
     * @return
     */
    public boolean isNetError(){
        return getNetErrorLayoutId() != 0 &&
                getDatas().size() == 0 &&
                !NetWorkUtil.isNetworkAvailable(mContext);
    }

    /**
     * 如果是网格或者瀑布流时需要调用此方法类确定横铺情况
     */
    private void adjustSpanSize(RecyclerView recycler) {
        if (recycler.getLayoutManager() instanceof GridLayoutManager) {
            final GridLayoutManager layoutManager = (GridLayoutManager) recycler.getLayoutManager();
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

                @Override
                public int getSpanSize(int position) {
                    boolean isHeaderOrFooter =
                            isHeaderByPosition(position) || isFooterByPosition(position);
                    return isHeaderOrFooter ? layoutManager.getSpanCount() : 1;
                }
            });
        }
        if (recycler.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            this.isStaggeredGrid = true;
        }
    }
    //是否是Grid和Stagger的标志
    private boolean isStaggeredGrid;
    /**
     * 瀑布流时头部和尾部时需要占满横屏的
     */
    private BaseViewHolder createHeaderOrFooterHolder(View view) {
        if (isStaggeredGrid) {
            StaggeredGridLayoutManager.LayoutParams params = new StaggeredGridLayoutManager.LayoutParams(
                    StaggeredGridLayoutManager.LayoutParams.MATCH_PARENT, StaggeredGridLayoutManager.LayoutParams.WRAP_CONTENT);
            params.setFullSpan(true);
            view.setLayoutParams(params);
        }
        return BaseViewHolder.createViewHolder(view);
    }
    public void addHeader(View headerView){
        mHeaderViews.append(ITEM_TYPE_HEEADER_START+ mHeaderViews.size()+1,headerView);
    }
    public void addFooter(View footerView){
        mFooterViews.append(ITEM_TYPE_FOOTER_START+ mFooterViews.size()+1,footerView);
    }
    public void removeHeader(View headerView){
        mHeaderViews.removeAt(mHeaderViews.indexOfValue(headerView));
    }
    public void removeFooter(View footerView){
        mFooterViews.removeAt(mFooterViews.indexOfValue(footerView));
    }

    public boolean isHeaderByPosition(int position){
        return position < mHeaderViews.size();
    }
    public boolean isFooterByPosition(int position){
        return position >= (mHeaderViews.size() + getDatas().size());
    }
    public boolean isHeaderByItemType(int itemType){

        return itemType > ITEM_TYPE_HEEADER_START && itemType <= ITEM_TYPE_HEEADER_START + mHeaderViews.size();
    }
    public boolean isFooterByItemType(int itemType){
        return itemType > ITEM_TYPE_FOOTER_START && itemType <= ITEM_TYPE_FOOTER_START + mFooterViews.size();
    }
    public View getHeaderByPosition(int position){
        if(isHeaderByPosition(position)){
            return mHeaderViews.get(position);
        }
        return null;
    }
    public View getFooterByPosition(int position){
        if(isFooterByPosition(position)){
            return mFooterViews.get(position - getDatas().size() - mHeaderViews.size());
        }
        return null;
    }
    private View getHeaderByItemType(int itemType){
        if(isHeaderByItemType(itemType)){
            return mHeaderViews.get(itemType);
        }
        return null;
    }
    private View getFooterByItemType(int itemType){
        if(isFooterByItemType(itemType)){
            return mFooterViews.get(itemType);
        }
        return null;
    }

    private int getHeaderItemTypeByPosition(int position){
        if(isHeaderByPosition(position)){
            return ITEM_TYPE_HEEADER_START + position + 1;
        }
        return 0;
    }

    private int getFooterItemTypeByPosition(int position){
        if(isFooterByPosition(position)){
            return ITEM_TYPE_FOOTER_START + (position - getDatas().size() - mHeaderViews.size() + 1);
        }
        return 0;
    }

    protected abstract int getEmptyLayoutId();

    protected abstract int getNetErrorLayoutId();

    protected abstract void bindEmpty(BaseViewHolder holder, EM emptyData);

    protected abstract void bindNetError(BaseViewHolder holder, ER netErrorData);
}
