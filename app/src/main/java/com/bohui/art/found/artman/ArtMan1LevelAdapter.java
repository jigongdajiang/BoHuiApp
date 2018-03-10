package com.bohui.art.found.artman;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.bohui.art.R;
import com.bohui.art.bean.found.ArtMan1LevelBean;
import com.bohui.art.common.widget.rv.ItemType;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;


/**
 * @author : gaojigong
 * @date : 2018/3/10
 * @description:
 */


public class ArtMan1LevelAdapter extends BaseAdapter<ArtMan1LevelBean> {
    private ArtMan1LevelBean artMan1LevelBean;
    public ArtMan1LevelAdapter(Context context, ArtMan1LevelBean artMan1LevelBean) {
        super(context);
        this.artMan1LevelBean = artMan1LevelBean;
        addItem(artMan1LevelBean);
    }

    public ArtMan1LevelAdapter(Context context) {
        super(context);
    }

    @Override
    public int geViewType(int position) {
        return ItemType.ART_MAN_1LEVEL;
    }

    @Override
    public int geLayoutId(int position) {
        return R.layout.item_art_man_1level;
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, ArtMan1LevelBean itemData, int position) {
        holder.setText(R.id.tv_type_top,artMan1LevelBean.getArtManLevel1Name());
    }
    @Override
    public LayoutHelper onCreateLayoutHelper() {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        singleLayoutHelper.setMargin(0, 0,0,0);
        return singleLayoutHelper;
    }
}
