package com.bohui.art.detail.designer.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.bohui.art.R;
import com.bohui.art.bean.detail.DesignerDetailBean;
import com.bohui.art.common.widget.rv.ItemType;
import com.bohui.art.bean.detail.DesignerDetailResult;
import com.framework.core.glideext.GlideUtil;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;

/**
 * @author : gaojigong
 * @date : 2017/12/24
 * @description:
 */


public class DetailAdapter extends BaseAdapter<DesignerDetailBean> {
    public DetailAdapter(Context context,DesignerDetailBean result) {
        super(context);
        addItem(result);
    }

    @Override
    public int geViewType(int position) {
        return ItemType.DESIGNER_DETAL_DETAIL;
    }

    @Override
    public int geLayoutId(int position) {
        return R.layout.detail_designer_detail;
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, DesignerDetailBean itemData, int position) {
        ImageView iv_avr = holder.getView(R.id.iv_avr);
        GlideUtil.display(mContext,iv_avr,itemData.getPhoto());
        holder.setText(R.id.tv_name,itemData.getName());
        holder.setText(R.id.tv_attention,itemData.getIsfollow() == 0?"未关注":"已关注");
        holder.setText(R.id.tv_tab,itemData.getTag());
        holder.setText(R.id.tv_intro,itemData.getIntroduction());
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new SingleLayoutHelper();
    }
}
