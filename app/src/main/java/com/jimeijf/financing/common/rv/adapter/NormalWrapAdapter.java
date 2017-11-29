package com.jimeijf.financing.common.rv.adapter;

import android.content.Context;

import com.jimeijf.financing.R;
import com.jimeijf.financing.common.rv.bean.EmptyBean;
import com.jimeijf.financing.common.rv.bean.NetErrorBean;
import com.jimeijf.grecycleview.adapter.base.BaseAdapter;
import com.jimeijf.grecycleview.adapter.wrapper.BaseWrapper;
import com.jimeijf.grecycleview.viewholder.BaseViewHolder;


/**
 * @author : gaojigong
 * @date : 2017/11/28
 * @description:
 */


public class NormalWrapAdapter<T> extends BaseWrapper<T,EmptyBean,NetErrorBean> {
    public NormalWrapAdapter(Context context, BaseAdapter<T> adapter) {
        super(context, adapter);
    }

    @Override
    protected int getEmptyLayoutId() {
        return R.layout.layout_empty;
    }

    @Override
    protected int getNetErrorLayoutId() {
        return R.layout.layout_net_error;
    }

    @Override
    protected void bindEmpty(BaseViewHolder holder, EmptyBean emptyData) {
        holder.setText(R.id.tv_empty_title,emptyData.getTitle());
        holder.setText(R.id.tv_empty_message,emptyData.getMessage());
    }

    @Override
    protected void bindNetError(BaseViewHolder holder, NetErrorBean netErrorData) {
        holder.setText(R.id.tv_net_error_title,netErrorData.getTitle());
        holder.setText(R.id.tv_net_error_message,netErrorData.getDes());
    }
}
