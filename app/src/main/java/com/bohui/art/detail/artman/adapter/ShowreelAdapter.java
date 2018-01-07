package com.bohui.art.detail.artman.adapter;

import android.content.Context;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.bohui.art.R;
import com.bohui.art.common.widget.rv.ItemType;
import com.bohui.art.bean.detail.ShowreelBean;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;

/**
 * @author : gaojigong
 * @date : 2017/12/23
 * @description:
 * 作品集列表页
 */


public class ShowreelAdapter extends BaseAdapter<ShowreelBean> {
    public ShowreelAdapter(Context context) {
        super(context);
    }

    @Override
    public int geViewType(int position) {
        return ItemType.ART_MAN_DETAIL_SHOWREEL;
    }

    @Override
    public int geLayoutId(int position) {
        return R.layout.detail_art_man_showreel;
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, ShowreelBean itemData, int position) {

    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(2);
        gridLayoutHelper.setHGap(10);
        return gridLayoutHelper;
    }
}
