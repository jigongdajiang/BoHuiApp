package com.bohui.art.mine;

import android.content.Context;
import android.widget.ImageView;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.bohui.art.R;
import com.bohui.art.common.rv.ItemType;
import com.framework.core.glideext.GlideUtil;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;

/**
 * @author : gaojigong
 * @date : 2017/12/13
 * @description:
 */


public class MineTopAdapter extends BaseAdapter<UserBean> {
    public MineTopAdapter(Context context, UserBean userBean) {
        super(context);
        addItem(userBean);
    }

    @Override
    public int geViewType(int position) {
        return ItemType.MINE_TOP;
    }

    @Override
    public int geLayoutId(int position) {
        return R.layout.item_mine_top;
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, UserBean itemData, int position) {
        GlideUtil.displayCircle(mContext,(ImageView) holder.getView(R.id.iv_mine_avr),itemData.getAvrUrl());
        holder.setText(R.id.tv_mine_name,itemData.getName());
        holder.setText(R.id.tv_mine_sample_des,itemData.getDes());
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new SingleLayoutHelper();
    }
}
