package com.jimeijf.grecycleview.item;

/**
 * @author : gongdaocai
 * @date : 2017/11/28
 * FileName:
 * @description:
 *  混合布局时，列表封装成改对象进行处理
 */


public class BaseLayoutItem<T> {
    private T data;
    private int layoutId;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }
}
