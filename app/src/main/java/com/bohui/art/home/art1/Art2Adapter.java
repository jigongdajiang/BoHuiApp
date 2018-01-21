package com.bohui.art.home.art1;

import android.content.Context;

import com.bohui.art.R;
import com.bohui.art.bean.home.ArtItemBean;
import com.bohui.art.common.widget.rv.ItemType;
import com.bohui.art.bean.home.ArtCoverItemBean;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;

/**
 * @author : gaojigong
 * @date : 2017/12/15
 * @description:
 */


public class Art2Adapter extends BaseAdapter<ArtItemBean> {
    public Art2Adapter(Context context) {
        super(context);
    }

    @Override
    public int geViewType(int position) {
        return ItemType.ART_2;
    }

    @Override
    public int geLayoutId(int position) {
        return R.layout.item_art2;
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, ArtItemBean itemData, int position) {
        holder.setText(R.id.tv_art_des,itemData.getName());
    }
}
