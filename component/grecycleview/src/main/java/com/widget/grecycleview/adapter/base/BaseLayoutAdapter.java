package com.widget.grecycleview.adapter.base;


import android.content.Context;

import com.widget.grecycleview.item.BaseLayoutItem;
import com.widget.grecycleview.viewholder.BaseViewHolder;


/**
 * @author : gaojigong
 * @date : 2017/11/28
 * @description:
 * 数据和布局不统一时使用
 */


public abstract class BaseLayoutAdapter extends  BaseAdapter<BaseLayoutItem> {

    public BaseLayoutAdapter(Context context) {
        super(context);
    }

    @Override
    public int geLayoutId(int position) {
        return getData(position).getLayoutId();
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, BaseLayoutItem itemData, int position) {
        itemData.bindViewHolder(holder,itemData.getData(),position);
    }
}
