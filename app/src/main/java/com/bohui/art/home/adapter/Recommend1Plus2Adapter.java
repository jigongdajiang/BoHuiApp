package com.bohui.art.home.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
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


public class Recommend1Plus2Adapter extends BaseAdapter<ArtBean> {
    public Recommend1Plus2Adapter(Context context) {
        super(context);
    }

    @Override
    public int geViewType(int position) {
        return 0x1;
    }

    @Override
    public int geLayoutId(int viewType) {
        return R.layout.item_1plus2;
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, ArtBean itemData, int position) {
        if(position == 0){
            ImageView imageView = holder.getView(R.id.iv_img);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
            params.height = ResUtil.getResDimensionPixelOffset(mContext,R.dimen.dp_400);

        }else if(position == 1){
            RelativeLayout itemView = (RelativeLayout) holder.getConvertView();
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) itemView.getLayoutParams();
            params.leftMargin = 2;
            itemView.setLayoutParams(params);
        }else if(position == 2){
            RelativeLayout itemView = (RelativeLayout) holder.getConvertView();
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) itemView.getLayoutParams();
            params.leftMargin = 2;
            params.topMargin = 2;
            itemView.setLayoutParams(params);
        }
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        OnePlusNLayoutHelper onePlusNLayoutHelper = new OnePlusNLayoutHelper();
        onePlusNLayoutHelper.setMargin(0,2,0,0);
        return onePlusNLayoutHelper;
    }
}
