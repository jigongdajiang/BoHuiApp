package com.bohui.art.home.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.OnePlusNLayoutHelper;
import com.bohui.art.R;
import com.bohui.art.bean.home.ArtCoverItemBean;
import com.framework.core.glideext.GlideUtil;
import com.framework.core.util.ResUtil;
import com.framework.core.util.StrOperationUtil;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;

/**
 * @author : gaojigong
 * @date : 2017/12/10
 * @description:
 */


public class Art1Plus2Adapter extends BaseAdapter<ArtCoverItemBean> {
    public Art1Plus2Adapter(Context context) {
        super(context);
    }

    @Override
    public int geViewType(int position) {
        return 0x4;
    }

    @Override
    public int geLayoutId(int viewType) {
        return R.layout.item_art_1;
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, ArtCoverItemBean itemData, int position) {
        ImageView imageView = holder.getView(R.id.iv_img);
        holder.setBackgroundColor(R.id.iv_img,ResUtil.getResColor(mContext,R.color.sys_color_content_bg));
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageView.getLayoutParams();
        if (position == 0) {
            params.height = ResUtil.getResDimensionPixelOffset(mContext, R.dimen.dp_180);
        } else {
            params.height = ResUtil.getResDimensionPixelOffset(mContext, R.dimen.dp_50);
        }
        imageView.setLayoutParams(params);
        GlideUtil.display(mContext,imageView,itemData.getCover());
        LinearLayout itemView = (LinearLayout) holder.getConvertView();
        RecyclerView.LayoutParams itemViewLayoutParams = (RecyclerView.LayoutParams) itemView.getLayoutParams();
        if (position == 1) {
            itemViewLayoutParams.leftMargin = 2;
            itemViewLayoutParams.topMargin = 0;
        } else if (position == 2) {
            itemViewLayoutParams.leftMargin = 2;
            itemViewLayoutParams.topMargin = 2;
        }
        itemView.setLayoutParams(itemViewLayoutParams);
        holder.setText(R.id.tv_des,itemData.getName());
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        OnePlusNLayoutHelper onePlusNLayoutHelper = new OnePlusNLayoutHelper();
        onePlusNLayoutHelper.setMargin(0, 2, 0, 0);
        return onePlusNLayoutHelper;
    }
}
