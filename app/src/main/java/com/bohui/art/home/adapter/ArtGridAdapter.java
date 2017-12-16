package com.bohui.art.home.adapter;

import android.content.Context;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.bohui.art.R;
import com.bohui.art.home.bean.ArtBean;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;

/**
 * @author : gaojigong
 * @date : 2017/12/11
 * @description:
 */


public class ArtGridAdapter extends BaseAdapter<ArtBean> {
    public ArtGridAdapter(Context context) {
        super(context);
    }

    @Override
    public int geViewType(int position) {
        return 0x7;
    }

    @Override
    public int geLayoutId(int position) {
        return R.layout.item_art_2;
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, ArtBean itemData, int position) {

    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(2);
        gridLayoutHelper.setVGap(2);
        gridLayoutHelper.setHGap(2);
        return gridLayoutHelper;
    }
}