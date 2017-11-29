package com.jimeijf.grecycleview.adapter.base;


import android.content.Context;

import com.jimeijf.grecycleview.item.BaseLayoutItem;

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

}
