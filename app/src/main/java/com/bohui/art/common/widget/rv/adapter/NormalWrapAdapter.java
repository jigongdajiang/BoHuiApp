package com.bohui.art.common.widget.rv.adapter;

import android.content.Context;

import com.bohui.art.R;
import com.bohui.art.common.widget.rv.bean.EmptyBean;
import com.bohui.art.common.widget.rv.bean.NetErrorBean;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.adapter.wrapper.BaseWrapper;
import com.widget.grecycleview.viewholder.BaseViewHolder;


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
