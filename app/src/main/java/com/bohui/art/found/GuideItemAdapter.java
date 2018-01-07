package com.bohui.art.found;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bohui.art.R;
import com.bohui.art.common.widget.rv.ItemType;
import com.framework.core.glideext.GlideUtil;
import com.framework.core.util.ResUtil;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;

/**
 * @author : gaojigong
 * @date : 2017/12/13
 * @description:
 */


public class GuideItemAdapter extends BaseAdapter<GuideItemBean> {
    public GuideItemAdapter(Context context) {
        super(context);
    }

    @Override
    public int geViewType(int position) {
        return ItemType.GUIDE_ITEM_TYPE;
    }

    @Override
    public int geLayoutId(int position) {
        return R.layout.item_guide;
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, GuideItemBean itemData, int position) {
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.getConvertView().getLayoutParams();
        params.topMargin = ResUtil.getResDimensionPixelOffset(mContext,itemData.getMarginTopResId());
        holder.getConvertView().setLayoutParams(params);
        holder.setText(R.id.tv_guide_left,itemData.getDes());
        GlideUtil.display(mContext,(ImageView) holder.getView(R.id.iv_guide_left),itemData.getLeftImgResId());
        if(itemData.getRightArrowResId() == 0){
            holder.setVisible(R.id.iv_guide_arrow_right,false);
        }else{
            holder.setVisible(R.id.iv_guide_arrow_right,true);
            GlideUtil.display(mContext,(ImageView) holder.getView(R.id.iv_guide_arrow_right),itemData.getRightArrowResId());
        }
    }
}
