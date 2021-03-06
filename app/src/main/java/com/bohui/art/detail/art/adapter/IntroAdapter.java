package com.bohui.art.detail.art.adapter;

import android.content.Context;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.bohui.art.R;
import com.bohui.art.common.widget.rv.ItemType;
import com.bohui.art.bean.detail.ArtDetailResult;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;

/**
 * @author : gaojigong
 * @date : 2017/12/18
 * @description:
 */


public class IntroAdapter extends BaseAdapter<ArtDetailResult> {
    public IntroAdapter(Context context,ArtDetailResult artDetailBean) {
        super(context);
        addItem(artDetailBean);
    }

    @Override
    public int geViewType(int position) {
        return ItemType.ART_DETAIL_INTRO;
    }
    @Override
    public int geLayoutId(int position) {
        return R.layout.detail_art_intro;
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, ArtDetailResult itemData, int position) {
        holder.setText(R.id.tv_intro,itemData.getDesc());
    }
    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new SingleLayoutHelper();
    }
}
