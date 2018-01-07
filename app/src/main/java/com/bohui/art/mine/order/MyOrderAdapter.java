package com.bohui.art.mine.order;

import android.content.Context;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.bohui.art.R;
import com.bohui.art.bean.mine.MyOrderBean;
import com.bohui.art.common.widget.rv.ItemType;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;

/**
 * @author : gaojigong
 * @date : 2017/12/16
 * @description:
 */


public class MyOrderAdapter extends BaseAdapter<MyOrderBean> {
    public MyOrderAdapter(Context context) {
        super(context);
    }

    @Override
    public int geViewType(int position) {
        return ItemType.My_ORDER_ITEM;
    }

    @Override
    public int geLayoutId(int position) {
        return R.layout.item_my_order;
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, MyOrderBean itemData, int position) {

    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setDividerHeight(2);
        return linearLayoutHelper;
    }
}
