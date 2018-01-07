package com.bohui.art.detail.artman.adapter;

import android.content.Context;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.bohui.art.R;
import com.bohui.art.common.widget.rv.ItemType;
import com.bohui.art.bean.detail.ArtMainDetailResult;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;

/**
 * @author : gaojigong
 * @date : 2017/12/23
 * @description:
 */


public class IntroAdapter extends BaseAdapter<ArtMainDetailResult> {
    public IntroAdapter(Context context,ArtMainDetailResult result) {
        super(context);
        addItem(result);
    }

    @Override
    public int geViewType(int position) {
        return ItemType.ART_MAN_DETAIL_INTRO;
    }

    @Override
    public int geLayoutId(int position) {
        return R.layout.detail_art_man_intro;
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, ArtMainDetailResult itemData, int position) {

    }
    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new SingleLayoutHelper();
    }
}
