package com.bohui.art.home.adapter;


import android.content.Context;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.OnePlusNLayoutHelper;
import com.bohui.art.R;
import com.bohui.art.home.bean.ArtBean;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;

/**
 * @author : gaojigong
 * @date : 2017/12/10
 * @description:
 */


public class Recommend1Plus2Adapter extends BaseAdapter<ArtBean> {
    public Recommend1Plus2Adapter(Context context) {
        super(context);
    }

    @Override
    public int geLayoutId(int position) {
        return R.layout.item_1plus2;
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, ArtBean itemData, int position) {

    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new OnePlusNLayoutHelper();
    }
}
