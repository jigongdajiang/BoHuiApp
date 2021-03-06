package com.widget.grecycleview.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.widget.grecycleview.adapter.wrapper.BaseWrapper;
import com.widget.grecycleview.manager.CheckItemManager;
import com.widget.grecycleview.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : gaojigong
 * @date : 2017/11/26
 * @description: 默认一个Adapter，一个布局，对应一种数据类型
 * <p>
 * 如果需要交替性较强复杂的布局混合，可以将数据封装成BaseRvBean
 * <p>
 * 如果是分区域话较明显的
 * <p>
 * 这里只关注内部的Adapter，可能只是列表的一部分
 */


public abstract class BaseAdapter<T> extends DelegateAdapter.Adapter<BaseViewHolder> {
    //所有Rv内的数据对象最终将封装成一个BaseRvBean的集合
    private List<T> mDatas;
    //上下文对象
    protected Context mContext;
    //解析器，存储是为了每次都重复创建
    protected LayoutInflater mLayoutInflater;
    //数据检查器，有Header或者footer时使用
    protected CheckItemManager mCheckItemManager;
    //持有一个其外层对象
    protected DelegateAdapter delegateAdapter;
    //持有一个装饰器对象
    protected BaseWrapper wrapper;


    public BaseAdapter(Context context,List<T> datas) {
        this.mContext = context;
        if(mContext != null){
            mLayoutInflater = LayoutInflater.from(mContext);
        }
        if (datas == null) {
            datas = new ArrayList<>();
        }
        this.mDatas = datas;
    }

    public BaseAdapter(Context context) {
        this(context,null);
    }

    public void setDelegateAdapter(DelegateAdapter delegateAdapter) {
        this.delegateAdapter = delegateAdapter;
    }

    public void setWrapper(BaseWrapper wrapper) {
        this.wrapper = wrapper;
    }

    /**
     * 返回对应的类型
     */
    @Override
    public int getItemViewType(int position) {
        return geViewType(position);
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        if(mLayoutInflater == null){
            mLayoutInflater = LayoutInflater.from(mContext);
        }
        BaseViewHolder holder = BaseViewHolder.createViewHolder(parent,geLayoutId(viewType));
        onBindViewHolderClick(holder, holder.itemView);
        return holder;
    }

    /**
     * 供TreeAdapter设置展开操作用
     * @param holder
     * @param itemView
     */
    public void onBindViewHolderClick(BaseViewHolder holder, View itemView){

    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        T itemData = getData(position);
        if (null != itemData) {
            //防止空指针
            bindViewHolder(holder, getData(position), position);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public long getItemId(int position) {
        if (mDatas.size() > position) {
            return mDatas.get(position).hashCode();
        } else {
            return super.getItemId(position);
        }
    }
    public List<T> getDatas() {
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        }
        return mDatas;
    }

    public void setDatas(List<T> datas) {
        if (datas != null) {
            getDatas().clear();
            getDatas().addAll(datas);
        }
    }
    public T getData(int position) {
        if (mDatas.size() > position) {//防止越界异常
            return mDatas.get(position);
        } else {
            return null;
        }
    }


    /**
     * 默认实现的CheckItem接口
     *
     * @return
     */
    public CheckItemManager getCheckItemManager() {
        if (mCheckItemManager == null) {
            mCheckItemManager = new CheckItemManager(){

                @Override
                public boolean checkPosition(int position) {
                    return true;
                }

                @Override
                public int getAfterCheckingPosition(int position) {
                    return position;
                }
            };
        }
        return mCheckItemManager;
    }


    public void addItem(T item) {
        getDatas().add(item);
        notifyDataSetChanged();
        if(wrapper != null){
            wrapper.notifyDataSetChanged();
        }
        if(delegateAdapter != null){
            delegateAdapter.notifyDataSetChanged();
        }
    }

    public void addItem(int index, T data) {
        if (0 <= index && index < getDatas().size()) {
            getDatas().add(index, data);
            notifyDataSetChanged();
            if(wrapper != null){
                wrapper.notifyDataSetChanged();
            }
            if(delegateAdapter != null){
                delegateAdapter.notifyDataSetChanged();
            }
        } else {
            throw new ArrayIndexOutOfBoundsException("inserted position most greater than 0 and less than data size");
        }
    }

    public void addItems(List<T> datas) {
        if (datas != null) {
            getDatas().addAll(datas);
            notifyDataSetChanged();
            if(wrapper != null){
                wrapper.notifyDataSetChanged();
            }
            if(delegateAdapter != null){
                delegateAdapter.notifyDataSetChanged();
            }
        }
    }

    public void addItems(int position, List<T> datas) {
        if (0 <= position && position < getDatas().size()) {
            getDatas().addAll(position, datas);
            notifyDataSetChanged();
            if(wrapper != null){
                wrapper.notifyDataSetChanged();
            }
            if(delegateAdapter != null){
                delegateAdapter.notifyDataSetChanged();
            }
            throw new ArrayIndexOutOfBoundsException("inserted position most greater than 0 and less than data size");
        }
    }

    public void removeItem(T data) {
        getDatas().remove(data);
        notifyDataSetChanged();
        if(wrapper != null){
            wrapper.notifyDataSetChanged();
        }
        if(delegateAdapter != null){
            delegateAdapter.notifyDataSetChanged();
        }
    }

    public void removeItem(int position) {
        getDatas().remove(position);
        notifyDataSetChanged();
        if(wrapper != null){
            wrapper.notifyDataSetChanged();
        }
        if(delegateAdapter != null){
            delegateAdapter.notifyDataSetChanged();
        }
    }

    public void removeItems(List<T> datas) {
        getDatas().removeAll(datas);
        notifyDataSetChanged();
        if(wrapper != null){
            wrapper.notifyDataSetChanged();
        }
        if(delegateAdapter != null){
            delegateAdapter.notifyDataSetChanged();
        }
    }

    public void clear() {
        getDatas().clear();
        notifyDataSetChanged();
        if(wrapper != null){
            wrapper.notifyDataSetChanged();
        }
        if(delegateAdapter != null){
            delegateAdapter.notifyDataSetChanged();
        }

    }

    public void replaceItem(int position, T item) {
        getDatas().set(position, item);
        notifyDataSetChanged();
        if(wrapper != null){
            wrapper.notifyDataSetChanged();
        }
        if(delegateAdapter != null){
            delegateAdapter.notifyDataSetChanged();
        }
    }

    public void replaceAllItem(List<T> items) {
        if (items != null) {
            setDatas(items);
            notifyDataSetChanged();
            if(wrapper != null){
                wrapper.notifyDataSetChanged();
            }
            if(delegateAdapter != null){
                delegateAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 指定布局
     * 目的是为了解耦ItemType
     */
    public abstract int geViewType(int position);
    public abstract int geLayoutId(int position);

    /**
     * 子类必须实现的方法，用于完成数据和页面的绑定
     */
    public abstract void bindViewHolder(BaseViewHolder holder, T itemData,int position);

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new LinearLayoutHelper();
    }
}
