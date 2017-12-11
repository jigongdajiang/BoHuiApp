package com.bohui.art.home.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.OnePlusNLayoutHelper;
import com.bohui.art.R;
import com.bohui.art.home.bean.ArtBean;
import com.framework.core.util.ResUtil;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;

/**
 * @author : gaojigong
 * @date : 2017/12/10
 * @description:
 */


public class Art1Plus2Adapter extends BaseAdapter<ArtBean> {
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
    public void bindViewHolder(BaseViewHolder holder, ArtBean itemData, int position) {
        ImageView imageView = holder.getView(R.id.iv_img);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageView.getLayoutParams();
        if (position == 0) {
            params.height = ResUtil.getResDimensionPixelOffset(mContext, R.dimen.dp_180);
        } else {
            params.height = ResUtil.getResDimensionPixelOffset(mContext, R.dimen.dp_50);
        }
        imageView.setLayoutParams(params);
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

    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        OnePlusNLayoutHelper onePlusNLayoutHelper = new OnePlusNLayoutHelper();
        onePlusNLayoutHelper.setMargin(0, 2, 0, 0);
        return onePlusNLayoutHelper;
    }
}
