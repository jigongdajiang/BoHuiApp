package com.bohui.art.home.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.bohui.art.R;
import com.bohui.art.bean.home.ArtCoverItemBean;
import com.bohui.art.bean.home.ArtItemBean;
import com.framework.core.glideext.GlideUtil;
import com.framework.core.util.DisplayUtil;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;

/**
 * @author : gaojigong
 * @date : 2017/12/11
 * @description:
 */


public class ArtGridAdapter extends BaseAdapter<ArtItemBean> {
    public ArtGridAdapter(Context context) {
        super(context);
    }

    @Override
    public int geViewType(int position) {
        return 0x7;
    }

    @Override
    public int geLayoutId(int position) {
        return R.layout.item_art_2;
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, ArtItemBean itemData, int position) {
        ImageView iv = holder.getView(R.id.iv_img);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv.getLayoutParams();
        params.height = DisplayUtil.getScreenWidth(mContext)/2 - 1;
        iv.setLayoutParams(params);
        GlideUtil.display(mContext,iv,itemData.getCover());

        holder.setText(R.id.tv_des,itemData.getName());
        holder.setText(R.id.tv_price,"￥"+itemData.getSalePrice());
        holder.setText(R.id.tv_tab,itemData.getLookNum()+"人浏览");
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(2);
        gridLayoutHelper.setVGap(10);
        gridLayoutHelper.setHGap(10);
        return gridLayoutHelper;
    }
}
