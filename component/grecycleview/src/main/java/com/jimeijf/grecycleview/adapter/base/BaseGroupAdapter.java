package com.jimeijf.grecycleview.adapter.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jimeijf.grecycleview.sticky.StickyRecyclerHeadersAdapter;
import com.jimeijf.grecycleview.viewholder.BaseViewHolder;

/**
 * @author gaojigong
 * @version V1.0
 * @Description: 针对有悬停头部的列表的适配器
 * @date 16/11/01.
 */
public abstract class BaseGroupAdapter<T> extends BaseAdapter<T> implements StickyRecyclerHeadersAdapter<BaseViewHolder> {
    private int mstickyLayouResId;

    public BaseGroupAdapter(Context context) {
        super(context);
    }


    @Override
    public BaseViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        if (mContext == null) {
            mContext = parent.getContext();
        }

        View stickyView = LayoutInflater.from(mContext).inflate(mstickyLayouResId, parent, false);
        return BaseViewHolder.createViewHolder(stickyView);
    }

    @Override
    public void onBindHeaderViewHolder(BaseViewHolder holder, int position) {
        convertSticky(holder, getData(position));
    }

    public abstract void convertSticky(BaseViewHolder holder, T itemData);
}
