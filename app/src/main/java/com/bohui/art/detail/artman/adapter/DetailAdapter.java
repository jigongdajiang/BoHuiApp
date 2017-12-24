package com.bohui.art.detail.artman.adapter;

import android.content.Context;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.bohui.art.R;
import com.bohui.art.common.rv.ItemType;
import com.bohui.art.detail.artman.bean.ArtMainDetailResult;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;

/**
 * @author : gaojigong
 * @date : 2017/12/23
 * @description:
 */


public class DetailAdapter extends BaseAdapter<ArtMainDetailResult> {
    public DetailAdapter(Context context,ArtMainDetailResult result) {
        super(context);
        addItem(result);
    }

    @Override
    public int geViewType(int position) {
        return ItemType.ART_MAN_DETAIL_DETAIL;
    }

    @Override
    public int geLayoutId(int position) {
        return R.layout.detail_art_man_detail;
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, ArtMainDetailResult itemData, int position) {

    }
    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new SingleLayoutHelper();
    }
}
