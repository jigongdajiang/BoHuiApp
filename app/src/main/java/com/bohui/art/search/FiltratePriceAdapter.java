package com.bohui.art.search;

import android.content.Context;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.bohui.art.R;
import com.bohui.art.common.widget.rv.ItemType;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;

/**
 * @author : gaojigong
 * @date : 2017/12/17
 * @description:
 */


public class FiltratePriceAdapter extends BaseAdapter<String> {
    private String des;

    public FiltratePriceAdapter(Context context, String des) {
        super(context);
        addItem(des);
    }

    @Override
    public int geViewType(int position) {
        return ItemType.SEARCH_FILTRATE_PRICE;
    }

    @Override
    public int geLayoutId(int position) {
        return R.layout.item_filtrate_price;
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, String itemData, int position) {
        holder.setText(R.id.tv_des,itemData);
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        return singleLayoutHelper;
    }
}
