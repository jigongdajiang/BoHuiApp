package com.bohui.art.classify;

import android.content.Context;

import com.bohui.art.R;
import com.bohui.art.common.widget.rv.ItemType;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;

/**
 * @author : gaojigong
 * @date : 2017/12/13
 * @description:
 */


public class ClassifyTypeAdapter extends BaseAdapter<ClassifyTypeBean> {
    public ClassifyTypeAdapter(Context context) {
        super(context);
    }

    @Override
    public int geViewType(int position) {
        return ItemType.CLASSIFY_ITEM_TYPE;
    }

    @Override
    public int geLayoutId(int position) {
        return R.layout.item_classify_type;
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, ClassifyTypeBean itemData, int position) {
        holder.setText(R.id.cb_classify_type,itemData.getType());
        holder.setChecked(R.id.cb_classify_type,itemData.isChecked());
    }
}
