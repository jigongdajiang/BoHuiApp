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
    public int getItemViewType(int position) {
        //将position 和 一个正整数进行托尔运算得到最终的type类型 这个类型一定是正数
        int type = getData(position).getViewType();
        return (int) getCantor(type,position);
    }
    @Override
    public int geLayoutId(int viewType) {
        //通过反托尔运算得到position
        // 如果不是一致的，也就是类型经过了托尔运算
        // 反向托尔函数，用来来解析出原来的真实的itemType和position
        long w = (int) (Math.floor(Math.sqrt(8.0 * viewType + 1) - 1) / 2);
        long t = (w * w + w) / 2;

        //得到索引
        int position = (int) (viewType - t);
        //得到ItemType
        int trueType = (int) (w - position);
        return getData(position).getLayoutId();
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, BaseLayoutItem itemData, int position) {
        itemData.bindViewHolder(holder,itemData.getData(),position);
    }

    /**
     * 托尔函数
     */
    private long getCantor(long k1, long k2) {
        return (k1 + k2) * (k1 + k2 + 1) / 2 + k2;
    }
}
