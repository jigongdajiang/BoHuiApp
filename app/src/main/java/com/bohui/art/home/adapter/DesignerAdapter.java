package com.bohui.art.home.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bohui.art.R;
import com.bohui.art.bean.home.DesignerItemBean;
import com.bohui.art.bean.home.RecommendDesignerBean;
import com.framework.core.glideext.GlideUtil;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;

/**
 * @author : gaojigong
 * @date : 2017/12/11
 * @description:
 */


public class DesignerAdapter extends BaseAdapter<DesignerItemBean> {
    public DesignerAdapter(Context context) {
        super(context);
    }

    @Override
    public int geViewType(int position) {
        return 0x6;
    }

    @Override
    public int geLayoutId(int position) {
        return R.layout.item_designer;
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, DesignerItemBean itemData, int position) {
        ImageView iv = holder.getView(R.id.iv_designer_logo);
        GlideUtil.display(mContext,iv,itemData.getCover());
        holder.setText(R.id.tv_designer_name,itemData.getName());
    }
}
