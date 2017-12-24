package com.bohui.art.detail.art.adapter;

import android.content.Context;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.bohui.art.R;
import com.bohui.art.common.rv.ItemType;
import com.bohui.art.detail.art.bean.ArtDetailBean;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;

/**
 * @author : gaojigong
 * @date : 2017/12/18
 * @description:
 */


public class IntroAdapter extends BaseAdapter<ArtDetailBean> {
    public IntroAdapter(Context context,ArtDetailBean artDetailBean) {
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
    public void bindViewHolder(BaseViewHolder holder, ArtDetailBean itemData, int position) {
        holder.setText(R.id.tv_intro,itemData.getIntro());
    }
    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new SingleLayoutHelper();
    }
}
