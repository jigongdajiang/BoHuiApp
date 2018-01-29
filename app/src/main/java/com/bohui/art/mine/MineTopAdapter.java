package com.bohui.art.mine;

import android.content.Context;
import android.widget.ImageView;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.bohui.art.R;
import com.bohui.art.bean.mine.MineInfoResult;
import com.bohui.art.bean.mine.UserBean;
import com.bohui.art.common.widget.rv.ItemType;
import com.framework.core.glideext.GlideUtil;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;

/**
 * @author : gaojigong
 * @date : 2017/12/13
 * @description:
 */


public class MineTopAdapter extends BaseAdapter<MineInfoResult> {
    public MineTopAdapter(Context context, MineInfoResult userBean) {
        super(context);
        addItem(userBean);
    }
    public void refresh(MineInfoResult userBean){
        getDatas().clear();
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
    public void bindViewHolder(BaseViewHolder holder, MineInfoResult itemData, int position) {
        GlideUtil.displayCircle(mContext,(ImageView) holder.getView(R.id.iv_mine_avr),itemData.getPhoto());
        holder.setText(R.id.tv_mine_name,itemData.getName());
        holder.setText(R.id.tv_mine_sample_des,itemData.getIndustry());
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new SingleLayoutHelper();
    }
}
