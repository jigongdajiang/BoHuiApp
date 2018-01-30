package com.bohui.art.home.art1;

import android.content.Context;
import android.widget.ImageView;

import com.bohui.art.R;
import com.bohui.art.bean.home.ArtItemBean;
import com.bohui.art.common.widget.rv.ItemType;
import com.bohui.art.bean.home.ArtCoverItemBean;
import com.framework.core.glideext.GlideUtil;
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
        //缩略图
        ImageView iv_art = holder.getView(R.id.iv_art);
        GlideUtil.display(mContext,iv_art,itemData.getCover());
        holder.setText(R.id.tv_art_des,itemData.getName());
        //价格
        holder.setText(R.id.tv_art_price,"￥ "+String.valueOf(itemData.getSalePrice()));
        //尺寸
        holder.setText(R.id.tv_eye,itemData.getLookNum()+"人浏览");
    }
}
