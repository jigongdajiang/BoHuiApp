package com.widget.grecycleview.item;


import com.widget.grecycleview.viewholder.BaseViewHolder;

/**
 * @author : gaojigong
 * @date : 2017/11/28
 * @description:
 *  混合布局时，列表封装成改对象进行处理
 */


public abstract class BaseLayoutItem<T> {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public abstract int getLayoutId();

    public abstract void bindViewHolder(BaseViewHolder holder, T itemData, int position);
}
